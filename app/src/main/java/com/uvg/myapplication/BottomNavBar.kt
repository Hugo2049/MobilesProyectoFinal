package com.uvg.myapplication


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavBar(
    navController: NavController,
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF8C6E4D),
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_fitness), contentDescription = "Fitness") },
            label = { Text("Fitness") },
            selected = false,
            onClick = { navController.navigate("exercises_main") } // Navega a WorkoutPlanScreen
        )

        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_meals), contentDescription = "Meals") },
            label = { Text("Meals") },
            selected = false,
            onClick = { navController.navigate("meals") } // Navega a MealsScreen
        )

        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_profile), contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = false,
            onClick = { navController.navigate("main_profile") } // Navega a ProfileScreen
        )
    }
}