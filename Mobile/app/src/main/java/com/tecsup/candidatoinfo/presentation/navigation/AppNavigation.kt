package com.tecsup.candidatoinfo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.remote.RetrofitClient
import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.data.repository.CircunscripcionRepository
import com.tecsup.candidatoinfo.data.repository.InformacionRepository
import com.tecsup.candidatoinfo.data.repository.PartidoRepository
import com.tecsup.candidatoinfo.domain.usecase.GetCandidatosParlamentoAndinoUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetCandidatosPresidencialesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetCircunscripcionesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetDiputadosUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetSenadoresNacionalesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetSenadoresRegionalesUseCase
import com.tecsup.candidatoinfo.presentation.screens.candidato_detalle.CandidatoDetalleScreen
import com.tecsup.candidatoinfo.presentation.screens.candidato_detalle.CandidatoDetalleViewModel
import com.tecsup.candidatoinfo.presentation.screens.candidato_detalle.CandidatoDetalleViewModelFactory
import com.tecsup.candidatoinfo.presentation.screens.candidatos.CandidatosScreen
import com.tecsup.candidatoinfo.presentation.screens.candidatos.CandidatosViewModel
import com.tecsup.candidatoinfo.presentation.screens.candidatos.CandidatosViewModelFactory
import com.tecsup.candidatoinfo.presentation.screens.candidatos.TipoCandidato
import com.tecsup.candidatoinfo.presentation.screens.comparacion.ComparacionScreen
import com.tecsup.candidatoinfo.presentation.screens.comparacion.ComparacionViewModel
import com.tecsup.candidatoinfo.presentation.screens.comparacion.ComparacionViewModelFactory
import com.tecsup.candidatoinfo.presentation.screens.home.HomeScreen
import com.tecsup.candidatoinfo.presentation.screens.home.HomeViewModel
import com.tecsup.candidatoinfo.presentation.screens.home.HomeViewModelFactory
import com.tecsup.candidatoinfo.presentation.screens.partidos.PartidosScreen
import com.tecsup.candidatoinfo.presentation.screens.partidos.PartidosViewModel
import com.tecsup.candidatoinfo.presentation.screens.partidos.PartidosViewModelFactory
import com.tecsup.candidatoinfo.presentation.screens.region.RegionSelectionScreen
import com.tecsup.candidatoinfo.presentation.screens.region.RegionSelectionViewModel
import com.tecsup.candidatoinfo.presentation.screens.region.RegionSelectionViewModelFactory
import com.tecsup.candidatoinfo.presentation.screens.votacion.*;
import com.tecsup.candidatoinfo.data.repository.VotacionRepository
import com.tecsup.candidatoinfo.presentation.screens.estadisticas.EstadisticasScreen
import com.tecsup.candidatoinfo.presentation.screens.estadisticas.EstadisticasViewModel
import com.tecsup.candidatoinfo.presentation.screens.estadisticas.EstadisticasViewModelFactory
import com.tecsup.candidatoinfo.data.repository.*
sealed class Screen(val route: String) {
    object RegionSelection : Screen("region_selection")
    object Home : Screen("home")
    object Partidos : Screen("partidos")
    object Candidatos : Screen("candidatos/{filterType}") {
        fun createRoute(filterType: Int) = "candidatos/$filterType"
    }
    object CandidatoDetalle : Screen("candidato_detalle/{tipo}/{id}") {
        fun createRoute(tipo: String, id: Int) = "candidato_detalle/$tipo/$id"
    }
    object Comparacion : Screen("comparacion/{tipo}/{id1}/{id2}") {
        fun createRoute(tipo: String, id1: Int, id2: Int) = "comparacion/$tipo/$id1/$id2"
    }
    object Votacion : Screen("votacion")

    object Estadisticas : Screen("estadisticas")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    preferencesManager: PreferencesManager
) {
    val hasSelectedRegion by preferencesManager.hasSelectedRegion.collectAsState(initial = false)
    val startDestination = if (hasSelectedRegion) Screen.Home.route else Screen.RegionSelection.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.RegionSelection.route) {
            val apiService = RetrofitClient.apiService
            val repository = CircunscripcionRepository(apiService)
            val useCase = GetCircunscripcionesUseCase(repository)

            val viewModel: RegionSelectionViewModel = viewModel(
                factory = RegionSelectionViewModelFactory(useCase, preferencesManager)
            )

            RegionSelectionScreen(
                viewModel = viewModel,
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.RegionSelection.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            val apiService = RetrofitClient.apiService
            val candidatoRepository = CandidatoRepository(apiService)

            val getCandidatosPresidencialesUseCase = GetCandidatosPresidencialesUseCase(candidatoRepository)
            val getSenadoresNacionalesUseCase = GetSenadoresNacionalesUseCase(candidatoRepository)
            val getSenadoresRegionalesUseCase = GetSenadoresRegionalesUseCase(candidatoRepository)
            val getDiputadosUseCase = GetDiputadosUseCase(candidatoRepository)
            val getCandidatosParlamentoAndinoUseCase = GetCandidatosParlamentoAndinoUseCase(candidatoRepository)

            val viewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(
                    getCandidatosPresidencialesUseCase,
                    getSenadoresNacionalesUseCase,
                    getSenadoresRegionalesUseCase,
                    getDiputadosUseCase,
                    getCandidatosParlamentoAndinoUseCase,
                    preferencesManager
                )
            )

            HomeScreen(

                viewModel = viewModel,
                onChangeRegion = {
                    navController.navigate(Screen.RegionSelection.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToPartidos = {
                    navController.navigate(Screen.Partidos.route)
                },
                onNavigateToCandidatos = { filterType ->
                    navController.navigate(Screen.Candidatos.createRoute(filterType))
                },
                onNavigateToVotar = {
                    navController.navigate(Screen.Votacion.route)
                },
                onNavigateToEstadisticas = {
                    navController.navigate(Screen.Estadisticas.route)
                }

            )
        }

        composable(Screen.Partidos.route) {
            val apiService = RetrofitClient.apiService
            val partidoRepository = PartidoRepository(apiService)

            val viewModel: PartidosViewModel = viewModel(
                factory = PartidosViewModelFactory(partidoRepository)
            )

            PartidosScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onPartidoClick = { partido ->
                }
            )
        }

        composable(
            route = Screen.Candidatos.route,
            arguments = listOf(
                navArgument("filterType") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) { backStackEntry ->
            val filterType = backStackEntry.arguments?.getInt("filterType") ?: 0
            val tipoCandidato = TipoCandidato.values()[filterType]

            val apiService = RetrofitClient.apiService
            val candidatoRepository = CandidatoRepository(apiService)

            val viewModel: CandidatosViewModel = viewModel(
                factory = CandidatosViewModelFactory(candidatoRepository, preferencesManager)
            )

            CandidatosScreen(
                viewModel = viewModel,
                initialFilter = tipoCandidato,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToPartidos = {
                    navController.navigate(Screen.Partidos.route)
                },
                onNavigateToComparar = { tipo, id1, id2 ->
                    navController.navigate(Screen.Comparacion.createRoute(tipo, id1, id2))
                },
                onCandidatoClick = { tipo, id ->
                    navController.navigate(Screen.CandidatoDetalle.createRoute(tipo, id))
                }
            )
        }

        composable(
            route = Screen.CandidatoDetalle.route,
            arguments = listOf(
                navArgument("tipo") { type = NavType.StringType },
                navArgument("id") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val tipo = backStackEntry.arguments?.getString("tipo") ?: ""
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            val apiService = RetrofitClient.apiService
            val candidatoRepository = CandidatoRepository(apiService)
            val informacionRepository = InformacionRepository(apiService)

            val viewModel: CandidatoDetalleViewModel = viewModel(
                factory = CandidatoDetalleViewModelFactory(
                    candidatoRepository,
                    informacionRepository,
                    preferencesManager,
                    tipo,
                    id
                )
            )

            CandidatoDetalleScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToPartidos = {
                    navController.navigate(Screen.Partidos.route)
                }
            )
        }

        composable(
            route = Screen.Comparacion.route,
            arguments = listOf(
                navArgument("tipo") { type = NavType.StringType },
                navArgument("id1") { type = NavType.IntType },
                navArgument("id2") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val tipo = backStackEntry.arguments?.getString("tipo") ?: ""
            val id1 = backStackEntry.arguments?.getInt("id1") ?: 0
            val id2 = backStackEntry.arguments?.getInt("id2") ?: 0

            val apiService = RetrofitClient.apiService
            val candidatoRepository = CandidatoRepository(apiService)
            val informacionRepository = InformacionRepository(apiService)

            val viewModel: ComparacionViewModel = viewModel(
                factory = ComparacionViewModelFactory(
                    candidatoRepository,
                    informacionRepository,
                    preferencesManager,
                    tipo,
                    id1,
                    id2
                )
            )

            ComparacionScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Votacion.route) {
            val apiService = RetrofitClient.apiService
            val candidatoRepository = CandidatoRepository(apiService)
            val votacionRepository = VotacionRepository(apiService)

            val viewModel: VotacionViewModel = viewModel(
                factory = VotacionViewModelFactory(
                    candidatoRepository,
                    votacionRepository,
                    preferencesManager
                )
            )

            VotacionScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Estadisticas.route) {
            val apiService = RetrofitClient.apiService
            val estadisticasRepository = EstadisticasRepository(apiService)

            val viewModel: EstadisticasViewModel = viewModel(
                factory = EstadisticasViewModelFactory(
                    estadisticasRepository,
                    preferencesManager
                )
            )

            EstadisticasScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

    }

}