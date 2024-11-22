package com.uvg.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.uvg.myapplication.exercise.ExerciseScreen
import com.uvg.myapplication.exercise.WorkoutPlanScreen
import com.uvg.myapplication.exercise.WorkoutViewModel
import com.uvg.myapplication.exercise.WorkoutViewModelFactory
import com.uvg.myapplication.login.NutriFitLoginScreen
import com.uvg.myapplication.login.ProfileCheckUser
import com.uvg.myapplication.login.ProfilePassScreen
import com.uvg.myapplication.meals.MealsScreen
import com.uvg.myapplication.meals.MealsViewModel
import com.uvg.myapplication.meals.MealsViewModelFactory
import com.uvg.myapplication.meals.RecipeScreen
import com.uvg.myapplication.profile.ProfileInfoScreen
import com.uvg.myapplication.profile.ProfileScreen
import com.uvg.myapplication.ui.theme.MyApplicationTheme
import com.uvg.myapplication.user_creation.SetGoalsScreen
import com.uvg.myapplication.user_creation.SignUpScreen

class MainActivity : ComponentActivity() {
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var mealsViewModel: MealsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa SharedPreferences
        val preferences = getSharedPreferences("app_cache", Context.MODE_PRIVATE)

        val mealsFactory = MealsViewModelFactory(preferences)
        mealsViewModel = ViewModelProvider(this, mealsFactory)[MealsViewModel::class.java]

        // Inicializa ViewModels con sus fábricas personalizadas
        val workoutFactory = WorkoutViewModelFactory(preferences)
        workoutViewModel = ViewModelProvider(this, workoutFactory)[WorkoutViewModel::class.java]

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    // Pantalla de inicio de sesión
                    composable("login") { NutriFitLoginScreen(navController) }

                    // Pantalla principal de ejercicios
                    composable("exercises_main") {
                        WorkoutPlanScreen(navController, workoutViewModel)
                    }

                    // Pantalla específica de ejercicios con parámetros
                    composable(
                        route = "exercise_screen/{exerciseId}",
                        arguments = listOf(navArgument("exerciseId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val exerciseId = backStackEntry.arguments?.getString("exerciseId")
                        ExerciseScreen(navController, exerciseId, workoutViewModel)
                    }

                    // Pantalla de creación de usuario
                    composable("create_user") { SignUpScreen(navController) }

                    // Pantalla de establecimiento de objetivos
                    composable("set_goals/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId") ?: ""
                        SetGoalsScreen(navController = navController, userId = userId)
                    }

                    // Pantalla de comidas
                    composable("meals") {
                        MealsScreen(navController = navController, mealsViewModel = mealsViewModel)
                    }

                    // Pantalla de recetas
                    composable("recipes") { RecipeScreen(navController) }

                    // Pantalla de perfil principal
                    composable("main_profile") { ProfileScreen(navController) }

                    // Pantalla de verificación de usuario en perfil
                    composable("check_user") { ProfileCheckUser(navController) }

                    // Pantalla de información de perfil
                    composable("profile_info") { ProfileInfoScreen(navController) }

                    // Pantalla de cambio de contraseña
                    composable("change_password/{username}") { backStackEntry ->
                        val username = backStackEntry.arguments?.getString("username")
                        if (username != null) {
                            ProfilePassScreen(navController, username)
                        }
                    }
                }
            }
        }
    }
}
