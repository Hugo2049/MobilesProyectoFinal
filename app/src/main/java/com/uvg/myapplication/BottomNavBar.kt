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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp)
        ) {

        }

        BottomNavBar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationBar(

        containerColor = Color(0xFF00796B ),
        contentColor = Color.White,
        tonalElevation = 8.dp,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_fitness), contentDescription = "Fitness") },
            label = { Text("Fitness") },
            selected = false,
            onClick = { navController.navigate("workout_plan") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color(0xFFDDFFDD),
                selectedTextColor = Color.White,
                unselectedTextColor = Color(0xFF1B5E20)
            )
        )

        NavigationBarItem(
            icon = { Icon(painter = painterResource(R.drawable.ic_meals), contentDescription = "Meals") },
            label = { Text("Meals") },
            selected = false,
            onClick = { navController.navigate("meals") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color(0xFFDDFFDD),
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
                unselectedIconColor = Color(0xFFDDFFDD),
                selectedTextColor = Color.White,
                unselectedTextColor = Color(0xFF1B5E20)
            )
        )
    }
}
