package com.uvg.myapplication.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uvg.myapplication.BottomNavBar
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun WorkoutPlanScreen(
    navController: NavController,
    viewModel: WorkoutViewModel
) {
    val scrollState = rememberScrollState()

    // Observar el estado del ViewModel
    val selectedDay by viewModel.selectedDay.collectAsState()
    val exercises by viewModel.exercises.collectAsState()
    val currentMonth by viewModel.currentMonth.collectAsState()
    val daysInMonth = viewModel.daysInMonth

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD)) // Fondo verde suave
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp) // Deja espacio para la barra de navegación inferior
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            WorkoutCalendar(
                daysInMonth = daysInMonth,
                selectedDay = selectedDay,
                currentMonth = currentMonth,
                onDayClick = { day -> viewModel.updateSelectedDay(day) },
                onNextMonth = { viewModel.goToNextMonth() },
                onPreviousMonth = { viewModel.goToPreviousMonth() }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Exercises for Day ${selectedDay?.dayOfMonth ?: "None"}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20) // Verde oscuro
            )

            for (i in 1..5) {
                val exerciseName = exercises.getOrNull(i - 1) ?: "No exercise"
                WorkoutDay(
                    dayText = "Exercise $i: $exerciseName",
                    onClick = {
                        // Navegar a ExerciseScreen con el ID del ejercicio
                        navController.navigate("exercise_screen/${exerciseName}")
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Barra de navegación inferior
        BottomNavBar(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun WorkoutDay(dayText: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(1.dp, Color(0xFF66BB6A), RoundedCornerShape(12.dp)) // Borde verde claro
            .clickable { onClick() } // Hacer clickeable el Box
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dayText,
                fontSize = 16.sp,
                color = Color(0xFF1B5E20) // Verde oscuro
            )

            Checkbox(
                checked = false,
                onCheckedChange = { /* Handle checkbox change */ },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF66BB6A), // Verde claro
                    uncheckedColor = Color(0xFF2E7D32) // Verde medio
                )
            )
        }
    }
}

@Composable
fun WorkoutCalendar(
    daysInMonth: List<LocalDate>,
    selectedDay: LocalDate?,
    currentMonth: YearMonth,
    onDayClick: (LocalDate) -> Unit,
    onNextMonth: () -> Unit,
    onPreviousMonth: () -> Unit
) {
    val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ClickableText(
                text = AnnotatedString("<"),
                onClick = { onPreviousMonth() },
                modifier = Modifier.padding(8.dp),
                style = androidx.compose.ui.text.TextStyle(color = Color(0xFF1B5E20)) // Verde oscuro
            )

            Text(
                text = currentMonth.month.name.lowercase().capitalize() + " " + currentMonth.year,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF1B5E20) // Verde oscuro
            )

            ClickableText(
                text = AnnotatedString(">"),
                onClick = { onNextMonth() },
                modifier = Modifier.padding(8.dp),
                style = androidx.compose.ui.text.TextStyle(color = Color(0xFF1B5E20)) // Verde oscuro
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach { day ->
                Text(text = day, fontSize = 16.sp, color = Color(0xFF33691E)) // Verde oscuro
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        daysInMonth.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                week.forEach { day ->
                    Text(
                        text = if (day.month == currentMonth.month) day.dayOfMonth.toString() else "",
                        fontSize = 16.sp,
                        color = if (day == selectedDay) Color(0xFF66BB6A) else Color(0xFF1B5E20),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onDayClick(day) }
                    )
                }
            }
        }
    }
}
