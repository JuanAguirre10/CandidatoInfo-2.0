package com.tecsup.candidatoinfo.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.candidatoinfo.presentation.theme.BackgroundLight
import com.tecsup.candidatoinfo.presentation.theme.PinkPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onChangeRegion: () -> Unit,
    onNavigateToPartidos: () -> Unit,
    onNavigateToCandidatos: (Int) -> Unit,
    onNavigateToVotar: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.regionId) {
        if (uiState.regionId != null) {
            viewModel.refreshCounts()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "CandidatoInfo",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkPrimary
                )
            },
            actions = {
                IconButton(onClick = onChangeRegion) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Cambiar región",
                        tint = Color.Gray
                    )
                }
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
                            onClick = { showMenu = false },
                            leadingIcon = {
                                Icon(Icons.Default.Home, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Partidos Políticos") },
                            onClick = {
                                showMenu = false
                                onNavigateToPartidos()
                            },
                            leadingIcon = {
                                Icon(Icons.Default.AccountBalance, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Candidatos") },
                            onClick = {
                                showMenu = false
                                onNavigateToCandidatos(0)
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Person, contentDescription = null)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Votar") },
                            onClick = {
                                showMenu = false
                                onNavigateToVotar()},
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
                        Divider()
                        DropdownMenuItem(
                            text = {
                                Column {
                                    Text("Región:", fontSize = 12.sp, color = Color.Gray)
                                    Text(
                                        text = uiState.regionName ?: "Sin seleccionar",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            },
                            onClick = onChangeRegion,
                            trailingIcon = {
                                Text("cambiar región", fontSize = 10.sp, color = PinkPrimary)
                            }
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = PinkPrimary.copy(alpha = 0.1f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Elecciones",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkPrimary
                )
                Text(
                    text = "Generales 2026",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = PinkPrimary)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = uiState.regionName ?: "Lima",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NavigationCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.AccountBalance,
                label = "Partidos",
                onClick = onNavigateToPartidos
            )
            NavigationCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.HowToVote,
                label = "Votar",
                onClick = onNavigateToVotar
            )
            NavigationCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Default.BarChart,
                label = "Estadísticas",
                onClick = { }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tipos de Elección",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                ElectionTypeCard(
                    icon = Icons.Default.Person,
                    title = "Presidente",
                    count = "${uiState.countPresidenciales} postulantes",
                    onClick = { onNavigateToCandidatos(0) }
                )
            }

            item {
                ElectionTypeCard(
                    icon = Icons.Default.Groups,
                    title = "Senadores Nacionales",
                    count = "${uiState.countSenadoresNacionales} postulantes",
                    onClick = { onNavigateToCandidatos(1) }
                )
            }

            item {
                ElectionTypeCard(
                    icon = Icons.Default.Group,
                    title = "Senadores Regionales",
                    count = if (uiState.regionName != null) {
                        "${uiState.countSenadoresRegionales} postulantes en ${uiState.regionName}"
                    } else {
                        "Selecciona una región"
                    },
                    onClick = {
                        if (uiState.regionName != null) {
                            onNavigateToCandidatos(2)
                        } else {
                            onChangeRegion()
                        }
                    }
                )
            }

            item {
                ElectionTypeCard(
                    icon = Icons.Default.People,
                    title = "Diputados",
                    count = if (uiState.regionName != null) {
                        "${uiState.countDiputados} postulantes en ${uiState.regionName}"
                    } else {
                        "Selecciona una región"
                    },
                    onClick = {
                        if (uiState.regionName != null) {
                            onNavigateToCandidatos(3)
                        } else {
                            onChangeRegion()
                        }
                    }
                )
            }

            item {
                ElectionTypeCard(
                    icon = Icons.Default.Public,
                    title = "Parlamento Andino",
                    count = "${uiState.countParlamentoAndino} postulantes",
                    onClick = { onNavigateToCandidatos(4) }
                )
            }
        }
    }
}

@Composable
fun NavigationCard(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(80.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PinkPrimary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun ElectionTypeCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    count: String,
    onClick: () -> Unit
) {
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
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PinkPrimary.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = PinkPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = count,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}