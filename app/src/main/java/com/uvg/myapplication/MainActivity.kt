package com.uvg.myapplication

import ProfileInfoScreen
import ProfilePassScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvg.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                // Configuración del NavHost para manejar las rutas de navegación
                NavHost(navController = navController, startDestination = "login") {//
                    composable("login") { NutriFitLoginScreen(navController) }//
                    composable("exercises_main") { WorkoutPlanScreen(navController) }//
                    composable("change_password") { ProfilePassScreen(navController) }//
                    composable("create_user") { SignUpScreen(navController) }//
                    composable("set_goals") { SetGoalsScreen(navController) }//
                    composable("meals") {MealsScreen(navController)  }//
                    composable("exercises_specific") {ExerciseScreen(navController) }//
                    composable("profile_info") {ProfileInfoScreen(navController) }
                    composable("main_profile") {ProfileScreen(navController)} // COPIAS ESTE
                    composable("recipes") {RecipeScreen(navController)}//
                }
            }
        }
    }
}