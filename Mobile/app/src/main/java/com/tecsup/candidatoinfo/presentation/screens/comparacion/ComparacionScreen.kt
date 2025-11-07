package com.tecsup.candidatoinfo.presentation.screens.comparacion

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.candidatoinfo.presentation.theme.BackgroundLight
import com.tecsup.candidatoinfo.presentation.theme.PinkPrimary
import com.tecsup.candidatoinfo.domain.model.CandidatoComparacion
import com.tecsup.candidatoinfo.domain.model.ItemComparacion
import com.tecsup.candidatoinfo.domain.model.ComparacionUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComparacionScreen(
    viewModel: ComparacionViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Comparar Candidatos",
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
                IconButton(onClick = onNavigateToHome) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Inicio",
                        tint = Color.Gray
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )

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
                        Text(text = uiState.error ?: "Error desconocido", color = Color.Gray)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.retry() },
                            colors = ButtonDefaults.buttonColors(containerColor = PinkPrimary)
                        ) {
                            Text("Reintentar")
                        }
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            CandidatoCompactCard(
                                candidato = uiState.candidato1,
                                modifier = Modifier.weight(1f)
                            )
                            CandidatoCompactCard(
                                candidato = uiState.candidato2,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    item {
                        Text(
                            text = "Información Personal",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    item {
                        ComparisonCard {
                            ComparisonRow("DNI", uiState.candidato1?.dni, uiState.candidato2?.dni)
                            ComparisonRow("Género", uiState.candidato1?.genero, uiState.candidato2?.genero)
                            ComparisonRow("Edad", uiState.candidato1?.edad?.toString(), uiState.candidato2?.edad?.toString())
                            ComparisonRow("Profesión", uiState.candidato1?.profesion, uiState.candidato2?.profesion)
                            if (uiState.candidato1?.numeroLista != null || uiState.candidato2?.numeroLista != null) {
                                ComparisonRow("N° Lista", uiState.candidato1?.numeroLista?.toString(), uiState.candidato2?.numeroLista?.toString())
                            }
                            if (uiState.candidato1?.posicionLista != null || uiState.candidato2?.posicionLista != null) {
                                ComparisonRow("Posición Lista", uiState.candidato1?.posicionLista?.toString(), uiState.candidato2?.posicionLista?.toString())
                            }
                            if (uiState.candidato1?.circunscripcion != null || uiState.candidato2?.circunscripcion != null) {
                                ComparisonRow("Circunscripción", uiState.candidato1?.circunscripcion, uiState.candidato2?.circunscripcion)
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Propuestas",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ItemListCard(
                                items = uiState.propuestasCandidato1,
                                emptyMessage = "Sin propuestas",
                                modifier = Modifier.weight(1f),
                                onItemClick = { url ->
                                    url?.let {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                        context.startActivity(intent)
                                    }
                                }
                            )
                            ItemListCard(
                                items = uiState.propuestasCandidato2,
                                emptyMessage = "Sin propuestas",
                                modifier = Modifier.weight(1f),
                                onItemClick = { url ->
                                    url?.let {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                        context.startActivity(intent)
                                    }
                                }
                            )
                        }
                    }

                    item {
                        Text(
                            text = "Proyectos Realizados",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ItemListCard(
                                items = uiState.proyectosCandidato1,
                                emptyMessage = "Sin proyectos",
                                modifier = Modifier.weight(1f),
                                onItemClick = { url ->
                                    url?.let {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                        context.startActivity(intent)
                                    }
                                }
                            )
                            ItemListCard(
                                items = uiState.proyectosCandidato2,
                                emptyMessage = "Sin proyectos",
                                modifier = Modifier.weight(1f),
                                onItemClick = { url ->
                                    url?.let {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                        context.startActivity(intent)
                                    }
                                }
                            )
                        }
                    }

                    item {
                        Text(
                            text = "Denuncias",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ItemListCard(
                                items = uiState.denunciasCandidato1,
                                emptyMessage = "Sin denuncias",
                                modifier = Modifier.weight(1f),
                                onItemClick = { url ->
                                    url?.let {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                        context.startActivity(intent)
                                    }
                                }
                            )
                            ItemListCard(
                                items = uiState.denunciasCandidato2,
                                emptyMessage = "Sin denuncias",
                                modifier = Modifier.weight(1f),
                                onItemClick = { url ->
                                    url?.let {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                        context.startActivity(intent)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CandidatoCompactCard(candidato: CandidatoComparacion?, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = candidato?.fotoUrl,
                contentDescription = "Foto",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = candidato?.nombre ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (candidato?.partidoLogo != null) {
                    AsyncImage(
                        model = candidato.partidoLogo,
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(
                    text = candidato?.partido ?: "",
                    fontSize = 12.sp,
                    color = PinkPrimary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ComparisonCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            content = content
        )
    }
}

@Composable
fun ComparisonRow(label: String, value1: String?, value2: String?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = value1 ?: "-",
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value2 ?: "-",
                fontSize = 14.sp,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun ItemListCard(
    items: List<ItemComparacion>,
    emptyMessage: String,
    modifier: Modifier = Modifier,
    onItemClick: (String?) -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            if (items.isEmpty()) {
                Text(
                    text = emptyMessage,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                items.forEach { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = BackgroundLight
                        ),
                        onClick = { onItemClick(item.url) }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.titulo,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 2,
                                    modifier = Modifier.weight(1f)
                                )
                                if (item.url != null) {
                                    Icon(
                                        imageVector = Icons.Default.Link,
                                        contentDescription = null,
                                        tint = PinkPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}