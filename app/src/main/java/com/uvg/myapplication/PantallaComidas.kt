package com.uvg.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun MealsScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE0F7FA), Color(0xFFB2EBF2)) // Fondo en tonos azulados
                )
            )
    ){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // Parte superior con el selector de fechas
            DateSelector()

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de comidas
            MealItem("Frittata with Asparagus")
            MealItem("Baked Chicken")
            MealItem("Pasta with Spinach")

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para ver la receta completa
            Button(
                onClick = { navController.navigate("recipes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
            ) {
                Text("View Full Recipe", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(125.dp))


        }
        // Barra de navegación inferior
        BottomNavBar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter) // Alinea la barra de navegación en la parte inferior
        )
    }
}

@Composable
fun DateSelector() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val buttonModifier = Modifier
            .clip(RoundedCornerShape(24.dp)) // Botones redondeados

        Button(
            onClick = { /* Acción para hoy */ },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Today", color = Color.Black)
        }
        Button(
            onClick = { /* Acción para mañana */ },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))
        ) {
            Text("Tomorrow", color = Color.Black)
        }
        Button(
            onClick = { /* Acción para fecha personalizada */ },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD7CCC8))
        ) {
            Text("Mon, 2/24", color = Color.Black)
        }
    }
}

@Composable
fun MealItem(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp)) // Bordes más redondeados
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFB2EBF2), Color(0xFFE0F7FA)) // Gradiente suave en tonos azulados
                )
            )
            .padding(16.dp) // Espacio interno
             // Sombra sutil
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Información de la comida
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Serving: 1",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }

            // Botón de configuración
            IconButton(onClick = { /* Acción para ajustes */ }) {
                Icon(
                    Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = Color(0xFF00796B) // Color acorde con el botón "View Full Recipe"
                )
            }
        }
    }
}