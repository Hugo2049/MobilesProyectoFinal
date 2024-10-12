package com.uvg.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Definimos colores en tonos grises
val LightGrayBackground = Color(0xFFF5F5F5)
val DarkerGrayText = Color(0xFF333333)
val GrayButton = Color(0xFFCCCCCC)

@Composable
fun ProfileInfoScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(LightGrayBackground), // Fondo gris claro
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la página
        Text(
            text = "Profile",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DarkerGrayText,
            modifier = Modifier.padding(bottom = 32.dp) // Más espacio
        )

        // Sección de objetivos de salud
        TextSection(
            title = "Health Goals",
            items = listOf("Maintain Weight", "Eat Healthier", "Get Fitter")
        )

        Spacer(modifier = Modifier.height(24.dp)) // Más espacio entre secciones

        // Sección de nivel de condición física
        TextSection(
            title = "Fitness Level",
            items = listOf("Intermediate")
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sección de preferencias alimentarias
        TextSection(
            title = "Dietary Preferences",
            items = listOf("High Protein", "Low Carb")
        )

        Spacer(modifier = Modifier.height(40.dp)) // Más espacio antes del botón

        // Botón "Edit Profile" sin contorno
        Button(
            onClick = { /* Acción de editar perfil */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),  // Esquinas redondeadas
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = GrayButton,  // Fondo gris claro
                contentColor = DarkerGrayText // Texto gris oscuro
            )
        ) {
            Text(
                text = "Edit Profile",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun TextSection(title: String, items: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)  // Espacio entre títulos y descripciones
    ) {
        Text(
            text = title,
            fontSize = 22.sp,  // Tamaño de texto un poco más grande
            fontWeight = FontWeight.Bold,
            color = DarkerGrayText,
            modifier = Modifier.padding(bottom = 12.dp)  // Más espacio bajo el título
        )

        items.forEach { item ->
            Text(
                text = item,
                fontSize = 18.sp,
                color = DarkerGrayText,
                modifier = Modifier.padding(bottom = 8.dp)  // Más espacio entre líneas
            )
        }
    }
}
