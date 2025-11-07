package com.tecsup.candidatoinfo.presentation.screens.estadisticas

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.candidatoinfo.presentation.theme.BackgroundLight
import com.tecsup.candidatoinfo.presentation.theme.PinkPrimary
import com.tecsup.candidatoinfo.data.repository.*
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.drawscope.Fill

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstadisticasScreen(
    viewModel: EstadisticasViewModel,
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
                    text = "Estadísticas",
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
            colors = CardDefaults.cardColors(
                containerColor = PinkPrimary.copy(alpha = 0.1f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.BarChart,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = PinkPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Estadísticas de Votación",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PinkPrimary
                )
            }
        }

        ScrollableTabRow(
            selectedTabIndex = uiState.selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White,
            contentColor = PinkPrimary,
            edgePadding = 16.dp
        ) {
            Tab(
                selected = uiState.selectedTabIndex == 0,
                onClick = { viewModel.setTipoEleccion(TipoEleccion.PRESIDENCIAL) },
                text = { Text("Presidencial") }
            )
            Tab(
                selected = uiState.selectedTabIndex == 1,
                onClick = { viewModel.setTipoEleccion(TipoEleccion.SENADOR_NACIONAL) },
                text = { Text("Sen. Nacionales") }
            )
            Tab(
                selected = uiState.selectedTabIndex == 2,
                onClick = { viewModel.setTipoEleccion(TipoEleccion.SENADOR_REGIONAL) },
                text = { Text("Sen. Regionales") }
            )
            Tab(
                selected = uiState.selectedTabIndex == 3,
                onClick = { viewModel.setTipoEleccion(TipoEleccion.DIPUTADO) },
                text = { Text("Diputados") }
            )
            Tab(
                selected = uiState.selectedTabIndex == 4,
                onClick = { viewModel.setTipoEleccion(TipoEleccion.PARLAMENTO_ANDINO) },
                text = { Text("Parl. Andino") }
            )
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = uiState.error ?: "", color = Color.Gray)
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        GraficoBarrasCard(
                            datos = uiState.top5Candidatos,
                            totalVotos = uiState.totalVotos
                        )
                    }

                    item {
                        GraficoCircularCard(
                            datos = uiState.votosPorPartido
                        )
                    }

                    item {
                        RankingCandidatosCard(
                            candidatos = uiState.rankingCompleto
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GraficoBarrasCard(
    datos: List<EstadisticaCandidato>,
    totalVotos: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Gráfico de Barras",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (datos.isEmpty()) {
                Text(
                    text = "No hay votos registrados",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                val maxVotos = datos.maxOfOrNull { it.votos } ?: 1

                datos.forEach { candidato ->
                    BarraVotacion(
                        nombre = candidato.nombre,
                        votos = candidato.votos,
                        maxVotos = maxVotos,
                        color = candidato.color
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun BarraVotacion(
    nombre: String,
    votos: Int,
    maxVotos: Int,
    color: Color
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = nombre,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$votos votos",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(24.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Gray.copy(alpha = 0.1f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = if (maxVotos > 0) votos.toFloat() / maxVotos else 0f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color)
            )
        }
    }
}

@Composable
fun GraficoCircularCard(
    datos: List<VotosPartido>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Gráfico Circular",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (datos.isEmpty()) {
                Text(
                    text = "No hay votos registrados",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    PieChart(datos = datos)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Leyenda
                datos.forEach { partido ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(partido.color)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = partido.nombre,
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f),
                            maxLines = 1
                        )
                        Text(
                            text = "${partido.porcentaje}%",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PinkPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PieChart(datos: List<VotosPartido>) {
    Canvas(
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp)
    ) {
        val total = datos.sumOf { it.votos }
        if (total == 0) return@Canvas

        var currentAngle = -90f // Empezar desde arriba

        datos.forEach { partido ->
            val sweepAngle = (partido.votos.toFloat() / total) * 360f

            drawArc(
                color = partido.color,
                startAngle = currentAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = size,
                style = androidx.compose.ui.graphics.drawscope.Fill
            )

            currentAngle += sweepAngle
        }

        // Círculo blanco en el centro para hacerlo tipo "donut"
        drawCircle(
            color = Color.White,
            radius = size.minDimension / 4,
            center = center
        )
    }
}

@Composable
fun SimpleCircularChart(datos: List<VotosPartido>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        datos.take(6).forEach { partido ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(partido.color)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = partido.nombre,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${partido.porcentaje}%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun RankingCandidatosCard(
    candidatos: List<CandidatoRanking>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Ranking de Candidatos",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (candidatos.isEmpty()) {
                Text(
                    text = "No hay votos registrados",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                candidatos.forEachIndexed { index, candidato ->
                    RankingItem(
                        posicion = index + 1,
                        candidato = candidato
                    )
                    if (index < candidatos.size - 1) {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun RankingItem(
    posicion: Int,
    candidato: CandidatoRanking
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(
                    when (posicion) {
                        1 -> Color(0xFFFFD700)
                        2 -> Color(0xFFC0C0C0)
                        3 -> Color(0xFFCD7F32)
                        else -> Color.Gray.copy(alpha = 0.2f)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$posicion",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (posicion <= 3) Color.White else Color.Black
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        AsyncImage(
            model = candidato.fotoUrl,
            contentDescription = "Foto",
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = candidato.nombre,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )
            Text(
                text = candidato.partido,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1
            )
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${candidato.votos} votos",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${candidato.porcentaje}%",
                fontSize = 12.sp,
                color = PinkPrimary
            )
        }
    }
}