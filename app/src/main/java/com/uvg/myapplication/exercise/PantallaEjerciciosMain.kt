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
import com.google.firebase.firestore.FirebaseFirestore
import com.uvg.myapplication.BottomNavBar
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun WorkoutPlanScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val selectedDayNumber = remember { mutableStateOf<Int?>(null) }
    val exercises = remember { mutableStateOf<List<String>>(emptyList()) } // Lista para ejercicios

    val db = FirebaseFirestore.getInstance()

    // Cargar datos desde Firestore cuando se selecciona un día
    LaunchedEffect(selectedDayNumber.value) {
        selectedDayNumber.value?.let { day ->
            db.collection("users")
                .document("rZ3FtLee4lvwfgZVCnbu")
                .collection("entries")
                .document("día_$day")
                .collection("exercises")
                .get()
                .addOnSuccessListener { documents ->
                    val loadedExercises = documents.map { it.getString("name") ?: "Unknown Exercise" }
                    exercises.value = loadedExercises // Actualiza la lista de ejercicios
                }
                .addOnFailureListener {
                    exercises.value = listOf("Error loading exercises")
                }
        }
    }

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
            WorkoutCalendar(selectedDayNumber)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Exercises for Day ${selectedDayNumber.value ?: "None"}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20) // Verde oscuro
            )

            // Muestra los ejercicios en los 5 Box
            for (i in 1..5) {
                val exerciseName = exercises.value.getOrNull(i - 1) ?: "No exercise"
                WorkoutDay("Exercise $i: $exerciseName")
            }
        }

        // Barra de navegación inferior
        BottomNavBar(
            navController = navController,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
@Composable
fun WorkoutDay(dayText: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .border(1.dp, Color(0xFF66BB6A), RoundedCornerShape(12.dp)) // Borde verde claro
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
fun WorkoutCalendar(selectedDayNumber: MutableState<Int?>) {
    val today = LocalDate.now()
    val month = YearMonth.now()
    val firstDayOfMonth = month.atDay(1)
    val lastDayOfMonth = month.atEndOfMonth()
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
                onClick = { /* Navegar al mes anterior */ },
                modifier = Modifier.padding(8.dp),
                style = androidx.compose.ui.text.TextStyle(color = Color(0xFF1B5E20)) // Verde oscuro
            )

            Text(
                text = month.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + month.year,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF1B5E20) // Verde oscuro
            )

            ClickableText(
                text = AnnotatedString(">"),
                onClick = { /* Navegar al mes siguiente */ },
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

        val startOffset = firstDayOfMonth.dayOfWeek.value % 7
        var currentDay = firstDayOfMonth.minusDays(startOffset.toLong())

        for (week in 1..6) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (day in 1..7) {
                    if (currentDay.isAfter(lastDayOfMonth)) break

                    val dayOfMonth = currentDay.dayOfMonth
                    Text(
                        text = if (currentDay.monthValue == month.monthValue) dayOfMonth.toString() else "",
                        fontSize = 16.sp,
                        color = if (selectedDayNumber.value == dayOfMonth) Color(0xFF66BB6A) else Color(0xFF1B5E20),
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                selectedDayNumber.value = dayOfMonth
                            }
                    )
                    currentDay = currentDay.plusDays(1)
                }
            }
            if (currentDay.isAfter(lastDayOfMonth)) break
        }
    }
}
