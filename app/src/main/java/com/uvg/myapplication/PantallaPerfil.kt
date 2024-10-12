package com.uvg.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally // Centra la columna horizontalmente
    ) {
        // "X" para cerrar la pantalla
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clickable { /* Acción al hacer clic en la "X" */ }
            ) {
                Text(
                    text = "X",
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        // Título "Your Profile"
        Text(
            text = "Your Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        )

        // Fila 1: Health Goals
        ProfileItem(text = "Health Goals", Modifier.padding(bottom = 24.dp))

        // Fila 2: Fitness Level
        ProfileItem(text = "Fitness Level", Modifier.padding(bottom = 24.dp))

        // Fila 3: Dietary Preferences
        ProfileItem(text = "Dietary Preferences", Modifier.padding(bottom = 32.dp))

        // Botón de Update Profile
        Button(
            onClick = { /* Acción de actualizar */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(8.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEFEFEF) // Gris claro
            )
        ) {
            Text(text = "Update Profile", fontSize = 16.sp, color = Color.Black)
        }
    }
}

@Composable
fun ProfileItem(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /* Acción al hacer clic */ }
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        // Icono de flecha para navegación
        Text(
            text = ">",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}