package com.tecsup.candidatoinfo.presentation.screens.candidatos

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.candidatoinfo.presentation.theme.BackgroundLight
import com.tecsup.candidatoinfo.presentation.theme.PinkPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidatosScreen(
    viewModel: CandidatosViewModel,
    initialFilter: TipoCandidato = TipoCandidato.PRESIDENCIAL,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToPartidos: () -> Unit,
    onNavigateToComparar: (String, Int, Int) -> Unit,
    onCandidatoClick: (String, Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }

    LaunchedEffect(initialFilter) {
        viewModel.setFilter(initialFilter)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Candidatos",
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

        ScrollableTabRow(
            selectedTabIndex = uiState.selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            contentColor = PinkPrimary,
            edgePadding = 16.dp
        ) {
            Tab(
                selected = uiState.selectedTabIndex == 0,
                onClick = { viewModel.setFilter(TipoCandidato.PRESIDENCIAL) },
                text = { Text("Presidencial") }
            )
            Tab(
                selected = uiState.selectedTabIndex == 1,
                onClick = { viewModel.setFilter(TipoCandidato.SENADOR_NACIONAL) },
                text = { Text("Sen. Nacionales") }
            )
            Tab(
                selected = uiState.selectedTabIndex == 2,
                onClick = { viewModel.setFilter(TipoCandidato.SENADOR_REGIONAL) },
                text = { Text("Sen. Regionales") }
            )
            Tab(
                selected = uiState.selectedTabIndex == 3,
                onClick = { viewModel.setFilter(TipoCandidato.DIPUTADO) },
                text = { Text("Diputados") }
            )
            Tab(
                selected = uiState.selectedTabIndex == 4,
                onClick = { viewModel.setFilter(TipoCandidato.PARLAMENTO_ANDINO) },
                text = { Text("Parl. Andino") }
            )
        }

        when {
            uiState.selectedCandidatos.isNotEmpty() -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = PinkPrimary.copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (uiState.selectedCandidatos.size == 1)
                                    "1 candidato seleccionado - Selecciona otro para comparar"
                                else
                                    "2 candidatos seleccionados",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (uiState.selectedCandidatos.size == 2) {
                                val tipo = uiState.candidatos.firstOrNull()?.tipo ?: ""
                                Button(
                                    onClick = {
                                        val ids = uiState.selectedCandidatos.toList()
                                        onNavigateToComparar(tipo, ids[0], ids[1])
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = PinkPrimary
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CompareArrows,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Comparar")
                                }
                            }

                            TextButton(onClick = { viewModel.clearSelection() }) {
                                Text("Cancelar", color = PinkPrimary)
                            }
                        }
                    }
                }
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = uiState.error ?: "Error desconocido",
                            color = Color.Gray
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
            }
            uiState.candidatos.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay candidatos disponibles",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.candidatos) { candidato ->
                        CandidatoCard(
                            candidato = candidato,
                            isSelected = uiState.selectedCandidatos.contains(candidato.id),
                            onToggleSelection = { viewModel.toggleCandidatoSelection(candidato.id) },
                            onClick = { onCandidatoClick(candidato.tipo, candidato.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CandidatoCard(
    candidato: CandidatoUI,
    isSelected: Boolean,
    onToggleSelection: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) PinkPrimary.copy(alpha = 0.1f) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggleSelection() },
                colors = CheckboxDefaults.colors(
                    checkedColor = PinkPrimary
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            AsyncImage(
                model = candidato.fotoUrl,
                contentDescription = "Foto de ${candidato.nombre}",
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = candidato.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (candidato.partidoLogo != null) {
                        AsyncImage(
                            model = candidato.partidoLogo,
                            contentDescription = "Logo partido",
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }

                    Text(
                        text = candidato.partidoNombre ?: "Sin partido",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (candidato.numeroLista != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "N° ${candidato.numeroLista}",
                        fontSize = 12.sp,
                        color = PinkPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkPrimary
                ),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Ver más",
                    fontSize = 12.sp
                )
            }
        }
    }
}