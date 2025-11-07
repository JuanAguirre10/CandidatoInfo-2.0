package com.tecsup.candidatoinfo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.remote.RetrofitClient
import com.tecsup.candidatoinfo.data.repository.CandidatoRepository
import com.tecsup.candidatoinfo.data.repository.CircunscripcionRepository
import com.tecsup.candidatoinfo.domain.usecase.GetCandidatosParlamentoAndinoUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetCandidatosPresidencialesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetCircunscripcionesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetDiputadosUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetSenadoresNacionalesUseCase
import com.tecsup.candidatoinfo.domain.usecase.GetSenadoresRegionalesUseCase
import com.tecsup.candidatoinfo.presentation.screens.home.HomeScreen
import com.tecsup.candidatoinfo.presentation.screens.home.HomeViewModel
import com.tecsup.candidatoinfo.presentation.screens.home.HomeViewModelFactory
import com.tecsup.candidatoinfo.presentation.screens.region.RegionSelectionScreen
import com.tecsup.candidatoinfo.presentation.screens.region.RegionSelectionViewModel
import com.tecsup.candidatoinfo.presentation.screens.region.RegionSelectionViewModelFactory

sealed class Screen(val route: String) {
    object RegionSelection : Screen("region_selection")
    object Home : Screen("home")
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
                }
            )
        }
    }
}