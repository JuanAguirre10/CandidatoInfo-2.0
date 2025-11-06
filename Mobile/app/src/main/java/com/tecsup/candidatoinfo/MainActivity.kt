package com.tecsup.candidatoinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.candidatoinfo.data.preferences.PreferencesManager
import com.tecsup.candidatoinfo.data.remote.RetrofitClient
import com.tecsup.candidatoinfo.data.repository.CircunscripcionRepository
import com.tecsup.candidatoinfo.domain.usecase.GetCircunscripcionesUseCase
import com.tecsup.candidatoinfo.presentation.screens.region.RegionSelectionScreen
import com.tecsup.candidatoinfo.presentation.screens.region.RegionSelectionViewModel
import com.tecsup.candidatoinfo.presentation.theme.CandidatoInfoTheme
import com.tecsup.candidatoinfo.presentation.screens.region.RegionSelectionViewModelFactory
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferencesManager = PreferencesManager(this)

        setContent {
            CandidatoInfoTheme {
                AppNavigation(preferencesManager)
            }
        }
    }
}

@Composable
fun AppNavigation(preferencesManager: PreferencesManager) {
    val navController = rememberNavController()
    val hasSelectedRegion by preferencesManager.hasSelectedRegion.collectAsState(initial = false)

    val startDestination = if (hasSelectedRegion) "home" else "region_selection"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("region_selection") {
            val apiService = RetrofitClient.apiService
            val repository = CircunscripcionRepository(apiService)
            val useCase = GetCircunscripcionesUseCase(repository)
            val viewModel: RegionSelectionViewModel = viewModel(
                factory = RegionSelectionViewModelFactory(useCase, preferencesManager)
            )

            RegionSelectionScreen(
                viewModel = viewModel,
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("region_selection") { inclusive = true }
                    }
                }
            )
        }

        composable("home") {
            HomeScreenPlaceholder()
        }
    }
}

@Composable
fun HomeScreenPlaceholder() {
    val context = androidx.compose.ui.platform.LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Home Screen - Próximamente",
                fontSize = 24.sp
            )
            Button(
                onClick = {
                    scope.launch {
                        preferencesManager.clearSelectedRegion()
                        (context as? ComponentActivity)?.recreate()
                    }
                }
            ) {
                Text("Cambiar región")
            }
        }
    }
}