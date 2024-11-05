package com.uvg.myapplication

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun WorkoutPlanScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5EEDC))
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        WorkoutCalendar()

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Personalized Plan",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Get Lean in 2 Weeks",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        Text(
            text = "We've created a plan to help you get lean. It's designed for beginners, and it's easy to follow.",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de ejercicios
        WorkoutDay("Day 1: Monday Chest & Triceps")
        WorkoutDay("Day 2: Tuesday Back & Biceps")
        WorkoutDay("Day 3: Wednesday Rest")
        WorkoutDay("Day 4: Thursday Shoulders")
        WorkoutDay("Day 5: Friday Legs")
        WorkoutDay("Day 6: Saturday Arms")
        WorkoutDay("Day 7: Sunday Rest")

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para iniciar el plan
        Button(
            onClick = { navController.navigate("exercises_specific") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50))
        ) {
            Text(
                text = "Start Plan",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de navegación inferior
        BottomNavBar(navController = navController)
    }
}


@Composable
fun WorkoutDay(dayText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = dayText, fontSize = 16.sp)

        Checkbox(
            checked = false,
            onCheckedChange = { /* Handle checkbox change */ }
        )
    }
}

@Composable
fun WorkoutCalendar() {
    val today = LocalDate.now()
    val selectedDate = remember { mutableStateOf<LocalDate?>(null) }
    val month = YearMonth.of(2023, 4) // Abril 2023
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
            // Mes anterior
            ClickableText(
                text = AnnotatedString("<"),
                onClick = { /* Navegar al mes anterior */ }
            )

            // Mes y año
            Text(
                text = month.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + month.year,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            // Mes siguiente
            ClickableText(
                text = AnnotatedString(">"),
                onClick = { /* Navegar al mes siguiente */ }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Días de la semana
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach { day ->
                Text(text = day, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Días del mes
        val startOffset = firstDayOfMonth.dayOfWeek.value % 7
        var currentDay = firstDayOfMonth.minusDays(startOffset.toLong())

        for (week in 1..6) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (day in 1..7) {
                    if (currentDay.isAfter(lastDayOfMonth)) {
                        break
                    }

                    Text(
                        text = if (currentDay.monthValue == month.monthValue) currentDay.dayOfMonth.toString() else "",
                        fontSize = 16.sp,
                        color = if (selectedDate.value == currentDay) Color.Green else Color.Black,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                selectedDate.value = currentDay
                            }
                    )
                    currentDay = currentDay.plusDays(1)
                }
            }
            if (currentDay.isAfter(lastDayOfMonth)) {
                break
            }
        }
    }
}
