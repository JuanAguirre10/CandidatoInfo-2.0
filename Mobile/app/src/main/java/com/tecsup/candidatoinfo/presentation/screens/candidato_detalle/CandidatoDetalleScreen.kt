package com.tecsup.candidatoinfo.presentation.screens.candidato_detalle

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.candidatoinfo.presentation.theme.BackgroundLight
import com.tecsup.candidatoinfo.presentation.theme.PinkPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidatoDetalleScreen(
    viewModel: CandidatoDetalleViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToPartidos: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Información del Candidato",
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
                    }
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
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = uiState.fotoUrl,
                                    contentDescription = "Foto candidato",
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = uiState.nombreCompleto,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    if (uiState.partidoLogo != null) {
                                        AsyncImage(
                                            model = uiState.partidoLogo,
                                            contentDescription = "Logo partido",
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                    Text(
                                        text = uiState.partidoNombre ?: "Sin partido",
                                        fontSize = 16.sp,
                                        color = PinkPrimary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Divider()

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "Información Personal",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Start)
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                uiState.dni?.let { dni ->
                                    InfoRow(label = "DNI", value = dni)
                                }

                                uiState.genero?.let { genero ->
                                    InfoRow(label = "Género", value = genero)
                                }

                                // Edad
                                uiState.edad?.let { edad ->
                                    InfoRow(label = "Edad", value = "$edad años")
                                }

                                uiState.fechaNacimiento?.let { fecha ->
                                    InfoRow(label = "Fecha de Nacimiento", value = fecha)
                                }

                                uiState.profesion?.let { profesion ->
                                    InfoRow(label = "Profesión", value = profesion)
                                }


                                uiState.experienciaPolitica?.let { experiencia ->
                                    InfoRow(label = "Experiencia Política", value = experiencia)
                                }

                                uiState.numeroLista?.let { numero ->
                                    InfoRow(label = "Número de Lista", value = "N° $numero")
                                }

                                if (uiState.posicionLista != null) {
                                    InfoRow(label = "Posición en Lista", value = "${uiState.posicionLista}")
                                }

                                uiState.posicionLista?.let { posicion ->
                                    InfoRow(label = "Posición en Lista", value = "$posicion")
                                }

                                uiState.numeroPreferencial?.let { numero ->
                                    InfoRow(label = "Número Preferencial", value = "$numero")
                                }

                                uiState.circunscripcion?.let { circ ->
                                    InfoRow(label = "Circunscripción", value = circ)
                                }

                                uiState.esNaturalCircunscripcion?.let { esNatural ->
                                    InfoRow(
                                        label = "Natural de la Circunscripción",
                                        value = if (esNatural) "Sí" else "No"
                                    )
                                }

                                uiState.biografia?.let { bio ->
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Biografía",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.align(Alignment.Start)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = bio,
                                        fontSize = 14.sp,
                                        color = Color.Gray,
                                        modifier = Modifier.align(Alignment.Start)
                                    )
                                }

                                uiState.vicepresidente1?.let { vp1 ->
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Divider()
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Primer Vicepresidente",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.align(Alignment.Start)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    InfoRow(label = "Nombre", value = vp1)
                                    uiState.vicepresidente1Profesion?.let { prof ->
                                        InfoRow(label = "Profesión", value = prof)
                                    }
                                }

                                uiState.vicepresidente2?.let { vp2 ->
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Segundo Vicepresidente",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.align(Alignment.Start)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    InfoRow(label = "Nombre", value = vp2)
                                    uiState.vicepresidente2Profesion?.let { prof ->
                                        InfoRow(label = "Profesión", value = prof)
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Button(
                                    onClick = {},
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = PinkPrimary
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CompareArrows,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Comparar candidato")
                                }
                            }
                        }
                    }

                    item {
                        SectionCard(
                            title = "Propuestas",
                            icon = Icons.Default.Lightbulb,
                            iconColor = Color(0xFF4CAF50),
                            items = uiState.propuestas,
                            onItemClick = { url ->
                                url?.let {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                    context.startActivity(intent)
                                }
                            },
                            onViewAll = {},
                            renderItem = { propuesta ->
                                PropuestaItem(propuesta = propuesta)
                            }
                        )
                    }

                    item {
                        SectionCard(
                            title = "Proyectos Realizados",
                            icon = Icons.Default.Work,
                            iconColor = Color(0xFF2196F3),
                            items = uiState.proyectos,
                            onItemClick = { url ->
                                url?.let {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                    context.startActivity(intent)
                                }
                            },
                            onViewAll = {},
                            renderItem = { proyecto ->
                                ProyectoItem(proyecto = proyecto)
                            }
                        )
                    }

                    item {
                        SectionCard(
                            title = "Denuncias",
                            icon = Icons.Default.Warning,
                            iconColor = Color(0xFFF44336),
                            items = uiState.denuncias,
                            onItemClick = { url ->
                                url?.let {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                                    context.startActivity(intent)
                                }
                            },
                            onViewAll = {},
                            renderItem = { denuncia ->
                                DenunciaItem(denuncia = denuncia)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(0.6f)
        )
    }
}

@Composable
fun <T> SectionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    items: List<T>,
    onItemClick: (String?) -> Unit,
    onViewAll: () -> Unit,
    renderItem: @Composable (T) -> ItemData
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }

                Icon(
                    imageVector = Icons.Default.OpenInNew,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (items.isEmpty()) {
                Text(
                    text = "No hay información disponible",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                items.take(3).forEach { item ->
                    val itemData = renderItem(item)
                    ItemCard(
                        title = itemData.title,
                        description = itemData.description,
                        url = itemData.url,
                        onClick = { onItemClick(itemData.url) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                if (items.size > 3) {
                    TextButton(
                        onClick = onViewAll,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = "Ver ${if (title == "Propuestas") "propuestas" else if (title == "Proyectos Realizados") "documentación" else "expedientes"} completos",
                            color = PinkPrimary,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
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

data class ItemData(
    val title: String,
    val description: String,
    val url: String?
)

@Composable
fun PropuestaItem(propuesta: PropuestaUI): ItemData {
    return ItemData(
        title = propuesta.titulo,
        description = propuesta.descripcion,
        url = propuesta.archivoUrl
    )
}

@Composable
fun ProyectoItem(proyecto: ProyectoUI): ItemData {
    return ItemData(
        title = proyecto.titulo,
        description = proyecto.descripcion,
        url = proyecto.evidenciaUrl
    )
}

@Composable
fun DenunciaItem(denuncia: DenunciaUI): ItemData {
    return ItemData(
        title = denuncia.titulo,
        description = denuncia.descripcion,
        url = denuncia.urlFuente
    )
}

@Composable
fun ItemCard(
    title: String,
    description: String,
    url: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = url != null) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = BackgroundLight
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (url != null) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Link,
                    contentDescription = null,
                    tint = PinkPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}