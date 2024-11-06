package com.uvg.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Contenido principal de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp) // Ajuste para dar espacio a la BottomNavBar
        ) {
            // Aquí va el contenido principal de la pantalla, como WorkoutPlanScreen()
        }

        // Barra de navegación inferior fija
        BottomNavBar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter) // Alinea la barra de navegación en la parte inferior
        )
    }
}

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier // Añadimos un parámetro de Modifier para la alineación
) {
    NavigationBar(
        containerColor = Color(0xFF66BB6A), // Fondo verde claro para la barra de navegación
        contentColor = Color.White, // Color blanco para el contenido (iconos y texto)
        tonalElevation = 8.dp,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_fitness), contentDescription = "Fitness") },
            label = { Text("Fitness") },
            selected = false,
            onClick = { navController.navigate("exercises_main") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White, // Blanco para icono seleccionado
                unselectedIconColor = Color(0xFF1B5E20), // Verde oscuro para icono no seleccionado
                selectedTextColor = Color.White, // Blanco para texto seleccionado
                unselectedTextColor = Color(0xFF1B5E20) // Verde oscuro para texto no seleccionado
            )
        )

        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_meals), contentDescription = "Meals") },
            label = { Text("Meals") },
            selected = false,
            onClick = { navController.navigate("meals") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color(0xFF1B5E20),
                selectedTextColor = Color.White,
                unselectedTextColor = Color(0xFF1B5E20)
            )
        )

        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_profile), contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { navController.navigate("main_profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color(0xFF1B5E20),
                selectedTextColor = Color.White,
                unselectedTextColor = Color(0xFF1B5E20)
            )
        )
    }
}
