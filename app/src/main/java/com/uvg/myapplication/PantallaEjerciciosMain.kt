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
import androidx.compose.ui.graphics.Brush
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
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9)) // Verde suave para el fondo
                )
            )
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        WorkoutCalendar()

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Personalized Plan",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20) // Verde oscuro
        )

        Text(
            text = "Get Lean in 2 Weeks",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF2E7D32), // Verde medio
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        Text(
            text = "We've created a plan to help you get lean. It's designed for beginners, and it's easy to follow.",
            fontSize = 14.sp,
            color = Color(0xFF33691E) // Verde oscuro
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
            colors = ButtonDefaults.buttonColors(Color(0xFF66BB6A)) // Verde claro para el botón
        ) {
            Text(
                text = "Start Plan",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Spacer flexible para empujar la barra de navegación al fondo de la pantalla
        Spacer(modifier = Modifier.weight(1f))

        // Barra de navegación inferior que ocupa todo el ancho y está fijada en la parte inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF81C784)) // Verde claro para la barra de navegación
                .padding(vertical = 8.dp)
        ) {
            BottomNavBar(navController = navController)
        }
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
                onClick = { /* Navegar al mes anterior */ },
                modifier = Modifier.padding(8.dp),
                style = androidx.compose.ui.text.TextStyle(color = Color(0xFF1B5E20)) // Verde oscuro
            )

            // Mes y año
            Text(
                text = month.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + month.year,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color(0xFF1B5E20) // Verde oscuro
            )

            // Mes siguiente
            ClickableText(
                text = AnnotatedString(">"),
                onClick = { /* Navegar al mes siguiente */ },
                modifier = Modifier.padding(8.dp),
                style = androidx.compose.ui.text.TextStyle(color = Color(0xFF1B5E20)) // Verde oscuro
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Días de la semana
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            daysOfWeek.forEach { day ->
                Text(text = day, fontSize = 16.sp, color = Color(0xFF33691E)) // Verde oscuro
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
                        color = if (selectedDate.value == currentDay) Color(0xFF66BB6A) else Color(0xFF1B5E20), // Verde claro para la fecha seleccionada
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
