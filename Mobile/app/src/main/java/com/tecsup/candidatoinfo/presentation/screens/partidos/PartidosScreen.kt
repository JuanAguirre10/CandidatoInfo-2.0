package com.tecsup.candidatoinfo.presentation.screens.partidos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tecsup.candidatoinfo.domain.model.Partido
import com.tecsup.candidatoinfo.presentation.theme.BackgroundLight
import com.tecsup.candidatoinfo.presentation.theme.PinkPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartidosScreen(
    viewModel: PartidosViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onPartidoClick: (Partido) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        // Top Bar con todos los íconos
        TopAppBar(
            title = {
                Text(
                    text = "CandidatoInfo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkPrimary
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.Gray
                    )
                }
            },
            actions = {
                // Ícono de Casa (Home)
                IconButton(onClick = onNavigateToHome) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Inicio",
                        tint = Color.Gray
                    )
                }

                // Ícono de Ayuda (?)
                IconButton(onClick = { showHelpDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Help,
                        contentDescription = "Ayuda",
                        tint = Color.Gray
                    )
                }

                // Menú hamburguesa
                Box {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú",
                            tint = Color.Gray
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Inicio") },
                            onClick = {
                                showMenu = false
                                onNavigateToHome()
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Home, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Partidos Políticos") },
                            onClick = { showMenu = false },
                            leadingIcon = {
                                Icon(Icons.Default.AccountBalance, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Candidatos") },
                            onClick = { showMenu = false },
                            leadingIcon = {
                                Icon(Icons.Default.Person, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Votar") },
                            onClick = { showMenu = false },
                            leadingIcon = {
                                Icon(Icons.Default.HowToVote, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Estadísticas") },
                            onClick = { showMenu = false },
                            leadingIcon = {
                                Icon(Icons.Default.BarChart, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Comparar") },
                            onClick = { showMenu = false },
                            leadingIcon = {
                                Icon(Icons.Default.CompareArrows, contentDescription = null)
                            }
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        // Header Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = PinkPrimary.copy(alpha = 0.1f)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Partidos Políticos",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkPrimary
                )
            }
        }

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = PinkPrimary)
                }
            }

            uiState.error != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = uiState.error ?: "",
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.retry() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PinkPrimary
                        )
                    ) {
                        Text("Reintentar")
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.partidos) { partido ->
                        PartidoCard(
                            partido = partido,
                            onClick = { onPartidoClick(partido) }
                        )
                    }
                }
            }
        }
    }

    // Diálogo de Ayuda
    if (showHelpDialog) {
        AlertDialog(
            onDismissRequest = { showHelpDialog = false },
            title = {
                Text(
                    text = "Ayuda - Partidos Políticos",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "En esta sección puedes ver todos los partidos políticos inscritos para las Elecciones Generales 2026.\n\n" +
                            "• Presiona sobre un partido para ver sus candidatos\n" +
                            "• Usa el ícono de casa (🏠) para volver al inicio\n" +
                            "• Usa el menú (☰) para navegar a otras secciones"
                )
            },
            confirmButton = {
                TextButton(onClick = { showHelpDialog = false }) {
                    Text("Entendido", color = PinkPrimary)
                }
            }
        )
    }
}

@Composable
fun PartidoCard(
    partido: Partido,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    // Log para debug
    android.util.Log.d("PartidoCard", "Loading logo for ${partido.nombre}: ${partido.logoUrl}")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color del partido (círculo de color)
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(
                        when (partido.colorPrincipal?.lowercase()) {
                            "rojo" -> Color.Red
                            "azul" -> Color.Blue
                            "verde" -> Color.Green
                            "naranja" -> Color(0xFFFFA500)
                            "morado" -> Color(0xFF800080)
                            "celeste" -> Color(0xFF87CEEB)
                            "rosa", "rosado" -> Color(0xFFFF69B4)
                            "amarillo" -> Color(0xFFFFD700)
                            else -> Color.Gray
                        }
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Logo del partido con mejor manejo de errores
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                if (!partido.logoUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(partido.logoUrl)
                            .crossfade(true)
                            .listener(
                                onError = { _, result ->
                                    android.util.Log.e("PartidoCard", "Error loading image: ${result.throwable.message}")
                                },
                                onSuccess = { _, _ ->
                                    android.util.Log.d("PartidoCard", "Image loaded successfully")
                                }
                            )
                            .build(),
                        contentDescription = "Logo de ${partido.nombre}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    // Mostrar ícono placeholder si no hay URL
                    Icon(
                        imageVector = Icons.Default.AccountBalance,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Información del partido
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = partido.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                if (!partido.siglas.isNullOrEmpty()) {
                    Text(
                        text = partido.siglas,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
                Text(
                    text = "Ver candidatos del partido",
                    fontSize = 14.sp,
                    color = PinkPrimary
                )
            }

            // Flecha
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}