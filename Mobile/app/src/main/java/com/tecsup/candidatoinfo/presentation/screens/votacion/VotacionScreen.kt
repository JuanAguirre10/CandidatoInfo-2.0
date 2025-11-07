package com.tecsup.candidatoinfo.presentation.screens.votacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.candidatoinfo.presentation.theme.BackgroundLight
import com.tecsup.candidatoinfo.presentation.theme.PinkPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VotacionScreen(
    viewModel: VotacionViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Votar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateToHome) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Inicio",
                        tint = Color.Gray
                    )
                }
            },
            actions = {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = Color.Gray
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        when (uiState.currentStep) {
            VotacionStep.VALIDACION_DNI -> {
                ValidacionDNIScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onCancel = onNavigateBack
                )
            }
            VotacionStep.PRESIDENCIAL -> {
                VotacionCategoriaScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    titulo = "Presidente",
                    maxSelecciones = 1
                )
            }
            VotacionStep.SENADOR_NACIONAL -> {
                VotacionCategoriaScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    titulo = "Senadores Nacionales",
                    maxSelecciones = 2
                )
            }
            VotacionStep.SENADOR_REGIONAL -> {
                VotacionCategoriaScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    titulo = "Senadores Regionales",
                    maxSelecciones = 2
                )
            }
            VotacionStep.DIPUTADO -> {
                VotacionCategoriaScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    titulo = "Diputados",
                    maxSelecciones = 2
                )
            }
            VotacionStep.PARLAMENTO_ANDINO -> {
                VotacionCategoriaScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    titulo = "Parlamento Andino",
                    maxSelecciones = 2
                )
            }
            VotacionStep.RESUMEN -> {
                ResumenVotacionScreen(
                    viewModel = viewModel,
                    uiState = uiState,
                    onNavigateToHome = onNavigateToHome
                )
            }
        }
    }
}

@Composable
fun ValidacionDNIScreen(
    viewModel: VotacionViewModel,
    uiState: VotacionUiState,
    onCancel: () -> Unit
) {
    var dni by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = PinkPrimary.copy(alpha = 0.1f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.HowToVote,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = PinkPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Sistema de Votación",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkPrimary,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Elecciones Generales Perú 2026",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Ingresa tu DNI",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = dni,
            onValueChange = {
                if (it.length <= 8 && it.all { char -> char.isDigit() }) {
                    dni = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("12345678") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PinkPrimary,
                unfocusedBorderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "DNI de 8 dígitos numéricos",
            fontSize = 12.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator(color = PinkPrimary)
        } else {
            Button(
                onClick = { viewModel.validarDNI(dni) },
                modifier = Modifier.fillMaxWidth(),
                enabled = dni.length == 8,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkPrimary,
                    disabledContainerColor = Color.Gray
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Continuar",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 16.sp
                )
            }
        }

        if (uiState.error != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFEBEE)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null,
                        tint = Color(0xFFD32F2F)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = uiState.error,
                        color = Color(0xFFD32F2F),
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = PinkPrimary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Cancelar",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}