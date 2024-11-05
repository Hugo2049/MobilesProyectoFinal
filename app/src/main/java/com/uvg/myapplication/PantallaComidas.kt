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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun MealsScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5EEDC))
            .padding(16.dp)
            .verticalScroll(scrollState) // Habilitar el scroll vertical
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
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A651))
        ) {
            Text("View Full Recipe", color = Color.White)
        }

        Spacer(modifier = Modifier.height(125.dp))

        // Barra de navegación inferior
        BottomNavBar(navController = navController)
    }
}

@Composable
fun DateSelector() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = { /* Acción para hoy */ }, colors = ButtonDefaults.buttonColors(containerColor = Color.White)) {
            Text("Today", color = Color.Black)
        }
        Button(onClick = { /* Acción para mañana */ }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E0E0))) {
            Text("Tomorrow", color = Color.Black)
        }
        Button(onClick = { /* Acción para fecha personalizada */ }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0D7D1))) {
            Text("Mon, 2/24", color = Color.Black)
        }
    }
}

@Composable
fun MealItem(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Información de la comida
        Column(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        ) {
            Text(text = title, fontSize = 16.sp, color = Color.Black)
            Text(text = "Serving: 1", fontSize = 12.sp, color = Color.Gray)
        }

        // Botón de configuración
        IconButton(onClick = { /* Acción para ajustes */ }) {
            Icon(Icons.Filled.Settings, contentDescription = "Settings")
        }
    }
}
