package com.tecsup.candidatoinfo.presentation.screens.votacion

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.candidatoinfo.presentation.theme.BackgroundLight
import com.tecsup.candidatoinfo.presentation.theme.PinkPrimary

@Composable
fun VotacionCategoriaScreen(
    viewModel: VotacionViewModel,
    uiState: VotacionUiState,
    titulo: String,
    maxSelecciones: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = uiState.nombreVotante,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = titulo,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    Text(
                        text = "${uiState.votosActuales.size} votos seleccionados",
                        fontSize = 12.sp,
                        color = PinkPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (maxSelecciones > 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = uiState.votosActuales.size.toFloat() / maxSelecciones,
                        modifier = Modifier.fillMaxWidth(),
                        color = PinkPrimary,
                        trackColor = Color.Gray.copy(alpha = 0.2f)
                    )
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = uiState.error, color = Color.Gray)
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.candidatosDisponibles) { candidato ->
                        CandidatoVotacionCard(
                            candidato = candidato,
                            isSelected = uiState.votosActuales.contains(candidato.id),
                            onSelect = {
                                if (uiState.votosActuales.contains(candidato.id)) {
                                    viewModel.deseleccionarCandidato(candidato.id)
                                } else {
                                    if (uiState.votosActuales.size < maxSelecciones) {
                                        viewModel.seleccionarCandidato(candidato.id)
                                    }
                                }
                            }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.volverAtras() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = PinkPrimary
                        )
                    ) {
                        Text("Saltar")
                    }

                    Button(
                        onClick = { viewModel.continuarSiguienteCategoria() },
                        modifier = Modifier.weight(1f),
                        enabled = uiState.votosActuales.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PinkPrimary
                        )
                    ) {
                        Text("Continuar")
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CandidatoVotacionCard(
    candidato: CandidatoVotacion,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) PinkPrimary.copy(alpha = 0.1f) else Color.White
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, PinkPrimary)
        } else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(PinkPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.1f))
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            AsyncImage(
                model = candidato.fotoUrl,
                contentDescription = "Foto",
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = candidato.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (candidato.partidoLogo != null) {
                        AsyncImage(
                            model = candidato.partidoLogo,
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = candidato.partido,
                        fontSize = 14.sp,
                        color = Color.Gray
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
        }
    }
}

@Composable
fun ResumenVotacionScreen(
    viewModel: VotacionViewModel,
    uiState: VotacionUiState,
    onNavigateToHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E9)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Resumen de tu Votación",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Revisa tus selecciones antes de confirmar",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            uiState.resumenVotos.forEach { (categoria, candidatos) ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = categoria,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PinkPrimary
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            candidatos.forEach { candidato ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AsyncImage(
                                        model = candidato.fotoUrl,
                                        contentDescription = "Foto",
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = candidato.nombre,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = candidato.partido,
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = PinkPrimary
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.volverAtras() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Volver")
                    }

                    Button(
                        onClick = { viewModel.confirmarVotos() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Confirmar Votos")
                    }
                }
            }

            if (uiState.votoConfirmado) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFE8F5E9)
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "¡Voto Registrado!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4CAF50)
                        )
                        Text(
                            text = uiState.nombreVotante,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onNavigateToHome,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PinkPrimary
                            )
                        ) {
                            Text("Volver al Inicio")
                        }
                    }
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
        }
    }
}