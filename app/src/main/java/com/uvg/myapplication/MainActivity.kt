package com.uvg.myapplication

import ProfilePassScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
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
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { NutriFitLoginScreen(navController) }
                    composable("exercises_main") { WorkoutPlanScreen(navController) }
                    composable("change_password") { ProfilePassScreen(navController) }
                    composable("create_user") { SignUpScreen(navController) }
                    composable("set_goals") { SetGoalsScreen(navController) }
                    composable("meals") {MealsScreen(navController)  }
                    composable("exercises_specific") {ExerciseScreen() }
                    composable("profile_info") {ProfileInfoScreen() }
                    composable("main_profile") {ProfileScreen()}
                    composable("recipes") {RecipeScreen(navController)}
                }
            }
        }
    }
}