package com.tecsup.candidatoinfo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tecsup.candidatoinfo.presentation.theme.PinkPrimary

@Composable
fun CandidatoPresidencialCard(
    nombrePresidente: String,
    apellidosPresidente: String,
    profesion: String?,
    partidoSiglas: String?,
    partidoLogo: String?,
    fotoUrl: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
            AsyncImage(
                model = fotoUrl ?: "",
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.2f)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "$nombrePresidente $apellidosPresidente",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                if (profesion != null) {
                    Text(
                        text = profesion,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                if (partidoSiglas != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = partidoSiglas,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PinkPrimary
                    )
                }
            }

            if (partidoLogo != null) {
                AsyncImage(
                    model = partidoLogo,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun CandidatoSimpleCard(
    nombre: String,
    apellidos: String,
    profesion: String?,
    partidoSiglas: String?,
    partidoLogo: String?,
    fotoUrl: String?,
    numeroPreferencial: Int?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
            AsyncImage(
                model = fotoUrl ?: "",
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.2f)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "$nombre $apellidos",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.weight(1f)
                    )

                    if (numeroPreferencial != null) {
                        Text(
                            text = "#$numeroPreferencial",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PinkPrimary
                        )
                    }
                }

                if (profesion != null) {
                    Text(
                        text = profesion,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                if (partidoSiglas != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = partidoSiglas,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = PinkPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (partidoLogo != null) {
                AsyncImage(
                    model = partidoLogo,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}