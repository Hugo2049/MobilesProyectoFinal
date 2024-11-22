package com.uvg.myapplication.meals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uvg.myapplication.BottomNavBar
import java.time.LocalDate

@Composable
fun MealsScreen(
    navController: NavController,
    mealsViewModel: MealsViewModel = viewModel()
) {
    // Observar el estado del ViewModel
    val meals = mealsViewModel.meals.collectAsState()
    val selectedDay = mealsViewModel.selectedDay.collectAsState()

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD)) // Fondo degradado verde claro
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // Selector de fechas
            DateSelector(
                selectedDate = selectedDay.value,
                onTodaySelected = { mealsViewModel.updateSelectedDay(LocalDate.now()) },
                onTomorrowSelected = { mealsViewModel.loadMealsForTomorrow() },
                onDayAfterTomorrowSelected = { mealsViewModel.loadMealsForDayAfterTomorrow() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de comidas
            meals.value.forEach { meal ->
                MealItem(meal)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para ver la receta completa
            Button(
                onClick = { navController.navigate("recipes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(50)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00796B))
            ) {
                Text("View Full Recipe", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(125.dp))
        }

        // Barra de navegación inferior
        BottomNavBar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter) // Alinea la barra de navegación en la parte inferior
        )
    }
}

@Composable
fun DateSelector(
    selectedDate: LocalDate,
    onTodaySelected: () -> Unit,
    onTomorrowSelected: () -> Unit,
    onDayAfterTomorrowSelected: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val buttonModifier = Modifier
            .clip(RoundedCornerShape(24.dp)) // Botones redondeados

        Button(
            onClick = onTodaySelected,
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedDate == LocalDate.now()) Color.Gray else Color.White
            )
        ) {
            Text("Today", color = Color.Black)
        }
        Button(
            onClick = onTomorrowSelected,
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedDate == LocalDate.now().plusDays(1)) Color.Gray else Color(0xFFE0E0E0)
            )
        ) {
            Text("22/11", color = Color.Black)
        }
        Button(
            onClick = onDayAfterTomorrowSelected,
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedDate == LocalDate.now().plusDays(2)) Color.Gray else Color(0xFFD7CCC8)
            )
        ) {
            Text("23/11", color = Color.Black)
        }
    }
}

@Composable
fun MealItem(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp)) // Bordes redondeados
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFDEEDC0), Color(0xFFB4D8A6)) // Gradiente en tonos verde suave y beige
                )
            )
            .padding(16.dp) // Espaciado interno
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}
