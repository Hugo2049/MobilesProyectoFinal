package com.uvg.myapplication.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.uvg.myapplication.BottomNavBar

@Composable
fun ExerciseScreen(navController: NavController, exerciseId: String?, viewModel: WorkoutViewModel) {
    val scrollState = rememberScrollState()
    val exerciseDetails by viewModel.exerciseDetails.collectAsState()

    // Cargar detalles del ejercicio cuando se abre la pantalla
    LaunchedEffect(exerciseId) {
        if (exerciseId != null) {
            viewModel.loadExerciseDetails(exerciseId)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            if (exerciseDetails == null) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                val name = exerciseDetails?.get("name") as? String ?: "Unknown Exercise"
                val difficulty = exerciseDetails?.get("difficulty") as? String ?: "Unknown"
                val equipment = exerciseDetails?.get("equipment") as? String ?: "Unknown"
                val goal = exerciseDetails?.get("goal") as? String ?: "Unknown"
                val reps = exerciseDetails?.get("reps") as? String ?: "Unknown"
                val rest = exerciseDetails?.get("rest") as? String ?: "Unknown"
                val targetMuscles = exerciseDetails?.get("targetMuscles") as? String ?: "Unknown"

                val painter = rememberImagePainter(
                    data = "https://via.placeholder.com/300", // Cambiar a URL real si aplica
                    builder = {
                        scale(Scale.FILL)
                        transformations(CircleCropTransformation())
                    }
                )

                Image(
                    painter = painter,
                    contentDescription = name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Difficulty: $difficulty", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text(text = "Equipment: $equipment", fontSize = 18.sp)
                Text(text = "Goal: $goal", fontSize = 18.sp)
                Text(text = "Reps: $reps", fontSize = 18.sp)
                Text(text = "Rest: $rest", fontSize = 18.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Targeted Muscles:",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                MuscleChip(targetMuscles)
            }
        }

        BottomNavBar(navController = navController, modifier = Modifier.align(Alignment.BottomCenter))
    }
}


@Composable
fun MuscleChip(muscle: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFE1E1E1), // Gris claro para los chips
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = muscle,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 14.sp,
            color = Color.Black // Texto negro para los chips
        )
    }
}


