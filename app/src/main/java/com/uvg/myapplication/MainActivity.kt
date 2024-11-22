package com.uvg.myapplication

import LoginViewModelFactory
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvg.myapplication.exercise.ExerciseScreen
import com.uvg.myapplication.exercise.WorkoutPlanScreen
import com.uvg.myapplication.exercise.WorkoutViewModel
import com.uvg.myapplication.exercise.WorkoutViewModelFactory
import com.uvg.myapplication.login.LoginScreenUI
import com.uvg.myapplication.login.ProfileCheckUserScreen
import com.uvg.myapplication.login.ProfileChangePasswordScreen
import com.uvg.myapplication.meals.MealsScreen
import com.uvg.myapplication.meals.MealsViewModel
import com.uvg.myapplication.meals.MealsViewModelFactory
import com.uvg.myapplication.meals.RecipeScreen
import com.uvg.myapplication.profile.ProfileInfoScreen
import com.uvg.myapplication.profile.ProfileScreen
import com.uvg.myapplication.repository.UserRepository
import com.uvg.myapplication.ui.theme.MyApplicationTheme
import com.uvg.myapplication.user_creation.SetGoalsScreen
import com.uvg.myapplication.user_creation.SignUpScreen
import com.uvg.myapplication.viewmodel.LoginViewModel

class MainActivity : ComponentActivity() {
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var mealsViewModel: MealsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa SharedPreferences
        val preferences = getSharedPreferences("app_cache", Context.MODE_PRIVATE)

        // Inicializa ViewModels con sus fábricas personalizadas
        val mealsFactory = MealsViewModelFactory(preferences)
        mealsViewModel = ViewModelProvider(this, mealsFactory)[MealsViewModel::class.java]

        val workoutFactory = WorkoutViewModelFactory(preferences)
        workoutViewModel = ViewModelProvider(this, workoutFactory)[WorkoutViewModel::class.java]

        val factory = LoginViewModelFactory(UserRepository())
        val loginViewModel: LoginViewModel = ViewModelProvider(this@MainActivity, factory)[LoginViewModel::class.java]


        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    // Pantalla de inicio de sesión
                    composable("login") {
                        LoginScreenUI(navController = navController, viewModel = loginViewModel)
                    }

                    // Pantalla principal de ejercicios
                    composable("workout_plan") {
                        WorkoutPlanScreen(
                            navController = navController,
                            viewModel = workoutViewModel
                        )
                    }

                    // Pantalla específica de ejercicio
                    composable("exercise_screen/{exerciseName}") { backStackEntry ->
                        val exerciseName = backStackEntry.arguments?.getString("exerciseName")
                            ?: "Default Exercise"
                        ExerciseScreen(navController = navController, exerciseName = exerciseName)
                    }

                    // Pantalla de creación de usuario (Sign up)
                    composable("create_user") { SignUpScreen(navController) }

                    // Pantalla de establecimiento de objetivos
                    composable("set_goals/{userId}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId") ?: ""
                        SetGoalsScreen(navController = navController, userId = userId)
                    }

                    // Pantalla de comidas
                    composable("meals") {
                        MealsScreen(
                            navController = navController,
                            mealsViewModel = mealsViewModel
                        )
                    }

                    // Pantalla de recetas
                    composable("recipes") { RecipeScreen(navController) }

                    // Pantalla de perfil principal
                    composable("main_profile") { ProfileScreen(navController) }

                    // Pantalla de verificación de usuario para recuperación de contraseña
                    composable("check_user") {
                        ProfileCheckUserScreen(navController)
                    }

                    // Pantalla de información de perfil
                    composable("profile_info") { ProfileInfoScreen(navController) }

                    // Pantalla de cambio de contraseña
                    composable("change_password/{username}") { backStackEntry ->
                        val username = backStackEntry.arguments?.getString("username")
                        if (username != null) {
                            ProfileChangePasswordScreen(navController, username)
                        }
                    }
                }
            }
        }
    }
}
