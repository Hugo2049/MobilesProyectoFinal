package com.uvg.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvg.myapplication.creacion_usuario.SetGoalsScreen
import com.uvg.myapplication.creacion_usuario.SignUpScreen
import com.uvg.myapplication.exercise.ExerciseScreen
import com.uvg.myapplication.exercise.WorkoutPlanScreen
import com.uvg.myapplication.login.NutriFitLoginScreen
import com.uvg.myapplication.login.ProfileCheckUser
import com.uvg.myapplication.login.ProfilePassScreen
import com.uvg.myapplication.meals.MealsScreen
import com.uvg.myapplication.meals.RecipeScreen
import com.uvg.myapplication.profile.ProfileInfoScreen
import com.uvg.myapplication.profile.ProfileScreen
import com.uvg.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { NutriFitLoginScreen(navController) }
                    composable("exercises_main") { WorkoutPlanScreen(navController) }
                    composable("create_user") { SignUpScreen(navController) }
                    composable("set_goals") { SetGoalsScreen(navController) }
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
