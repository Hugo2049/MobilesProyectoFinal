package com.uvg.myapplication.user_creation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SetGoalsScreen(navController: NavController, userId: String) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

    // Opciones para categorías con scroll horizontal
    var selectedFrequency by remember { mutableStateOf("Never") }
    val frequencies = listOf("Never", "Rarely", "Sometimes", "Often", "Very Often")

    var selectedDietaryRestriction by remember { mutableStateOf("None") }
    val dietaryOptions = listOf("None", "Vegetarian", "Vegan", "Pescatarian")

    var selectedGoal by remember { mutableStateOf("Lose Weight") }
    val goalOptions = listOf("Lose Weight", "Gain Muscle", "Maintain")

    // Habilitar scroll vertical en toda la pantalla
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // Habilitar scroll vertical
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text fields para ingresar edad, peso y altura
        CustomTextField(
            value = age,
            onValueChange = { age = it },
            label = "Age"
        )
        CustomTextField(
            value = weight,
            onValueChange = { weight = it },
            label = "Weight"
        )
        CustomTextField(
            value = height,
            onValueChange = { height = it },
            label = "Height"
        )

        // Ejercicio: frecuencia (scroll horizontal)
        Text(
            "How often do you exercise?",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()), // Habilitar scroll horizontal
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            frequencies.forEach { freq ->
                FrequencyChip(
                    text = freq,
                    isSelected = selectedFrequency == freq,
                    onSelected = { selectedFrequency = freq }
                )
            }
        }

        // Restricciones dietéticas (scroll horizontal)
        Text(
            "Do you have any dietary restrictions?",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()), // Habilitar scroll horizontal
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            dietaryOptions.forEach { option ->
                FrequencyChip(
                    text = option,
                    isSelected = selectedDietaryRestriction == option,
                    onSelected = { selectedDietaryRestriction = option }
                )
            }
        }

        // Objetivo principal (scroll horizontal)
        Text(
            "What is your goal?",
            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()), // Habilitar scroll horizontal
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            goalOptions.forEach { g ->
                FrequencyChip(
                    text = g,
                    isSelected = selectedGoal == g,
                    onSelected = { selectedGoal = g }
                )
            }
        }

        Button(
            onClick = {
                val goalData = hashMapOf(
                    "userId" to userId,
                    "age" to age,
                    "weight" to weight,
                    "height" to height,
                    "selectedFrequency" to selectedFrequency,
                    "selectedDietaryRestriction" to selectedDietaryRestriction,
                    "selectedGoal" to selectedGoal
                )

                db.collection("users")
                    .document(userId)
                    .collection("goals")
                    .add(goalData)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Goals updated successfully!", Toast.LENGTH_SHORT).show()
                        navController.navigate("Exercises_main")
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Failed to update goals: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A86B)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Continue", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}


@Composable
fun FrequencyChip(text: String, isSelected: Boolean, onSelected: () -> Unit) {
    Box(
        modifier = Modifier
            .widthIn(min = 80.dp)
            .background(
                color = if (isSelected) Color(0xFFEEE0BB) else Color(0xFFF0F0F0),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onSelected() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.Black else Color.Gray,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
    }
}