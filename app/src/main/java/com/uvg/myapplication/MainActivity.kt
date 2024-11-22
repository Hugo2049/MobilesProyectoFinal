package com.uvg.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvg.myapplication.exercise.WorkoutPlanScreen
import com.uvg.myapplication.exercise.WorkoutViewModel
import com.uvg.myapplication.exercise.WorkoutViewModelFactory
import com.uvg.myapplication.login.NutriFitLoginScreen
import com.uvg.myapplication.login.ProfileCheckUser
import com.uvg.myapplication.login.ProfilePassScreen
import com.uvg.myapplication.meals.MealsScreen
import com.uvg.myapplication.meals.RecipeScreen
import com.uvg.myapplication.profile.ProfileInfoScreen
import com.uvg.myapplication.profile.ProfileScreen
import com.uvg.myapplication.ui.theme.MyApplicationTheme
import com.uvg.myapplication.user_creation.SetGoalsScreen
import com.uvg.myapplication.user_creation.SignUpScreen
import com.uvg.myapplication.exercise.ExerciseScreen

class MainActivity : ComponentActivity() {
    private lateinit var workoutViewModel: WorkoutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa SharedPreferences
        val preferences = getSharedPreferences("workout_cache", Context.MODE_PRIVATE)

        // Inicializa el ViewModel con la fábrica personalizada
        val factory = WorkoutViewModelFactory(preferences)
        workoutViewModel = ViewModelProvider(this, factory)[WorkoutViewModel::class.java]

        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { NutriFitLoginScreen(navController) }
                    composable("exercises_main") {
                        WorkoutPlanScreen(navController, workoutViewModel)
                    }
                    composable("create_user") { SignUpScreen(navController) }
                    composable("set_goals/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId") ?: ""
                        SetGoalsScreen(navController = navController, userId = userId)
                    }
                    composable("meals") { MealsScreen(navController) }
                    composable("exercises_specific") { ExerciseScreen(navController) }
                    composable("main_profile") { ProfileScreen(navController) }
                    composable("recipes") { RecipeScreen(navController) }
                    composable("check_user") { ProfileCheckUser(navController) }
                    composable("profile_info") { ProfileInfoScreen(navController) }
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
