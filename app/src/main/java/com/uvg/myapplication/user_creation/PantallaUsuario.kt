package com.uvg.myapplication.user_creation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import android.content.Context
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SetGoalsScreen(navController: NavController, userId: String) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var selectedFrequency by remember { mutableStateOf("Never") }
    val frequencies = listOf("Never", "Rarely", "Sometimes", "Often", "Very Often")

    var selectedDietaryRestriction by remember { mutableStateOf("None") }
    val dietaryOptions = listOf("None", "Vegetarian", "Vegan", "Pescatarian")

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF5F5DC), // Beige claro
                        Color(0xFFDDFFDD)  // Verde pastel
                    )
                )
            )
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(value = age, onValueChange = { age = it }, label = "Age")
        CustomTextField(value = weight, onValueChange = { weight = it }, label = "Weight")
        CustomTextField(value = height, onValueChange = { height = it }, label = "Height")

        Text("Exercise Frequency:")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
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

        Text("Dietary Restriction:")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
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

        Button(
            onClick = {
                if (age.isBlank() || weight.isBlank() || height.isBlank()) {
                    Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                } else {
                    val goalData = hashMapOf(
                        "userId" to userId,
                        "age" to age,
                        "weight" to weight,
                        "height" to height,
                        "selectedFrequency" to selectedFrequency,
                        "selectedDietaryRestriction" to selectedDietaryRestriction
                    )

                    db.collection("users")
                        .document(userId)
                        .collection("goals")
                        .add(goalData)
                        .addOnSuccessListener {
                            assignExercises(
                                db = db,
                                userId = userId,
                                selectedFrequency = selectedFrequency,
                                context = context
                            )

                            assignMeals(
                                db = db,
                                userId = userId,
                                selectedDietaryRestriction = selectedDietaryRestriction,
                                context = context
                            )

                            navController.navigate("Exercises_main")
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Failed to save goals: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A86B) // Verde vibrante
            )
        ) {
            Text(
                text = "Continue",
                color = Color.White // Color del texto en contraste
            )
        }
    }
}

fun assignExercises(
    db: FirebaseFirestore,
    userId: String,
    selectedFrequency: String,
    context: Context
) {
    val userDocument = db.collection("users").document(userId)

    db.collection("exercise")
        .get()
        .addOnSuccessListener { exercisesSnapshot ->
            val filteredExercises = exercisesSnapshot.documents.filter { doc ->
                val difficulty = doc.getString("difficulty") ?: ""
                when (selectedFrequency) {
                    "Very Often", "Often" -> true
                    "Sometimes" -> difficulty in listOf("Easy", "Medium")
                    "Rarely", "Never" -> difficulty == "Easy"
                    else -> false
                }
            }

            filteredExercises.forEach { exercise ->
                val exerciseData = exercise.data
                    ?.filterValues { it != null }
                    ?.mapValues { it.value as Any }

                userDocument.collection("exercises")
                    .add(exerciseData ?: emptyMap<String, Any>())
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Error al guardar ejercicio: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }

            Toast.makeText(context, "Ejercicios asignados correctamente!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Error al obtener ejercicios: ${e.message}", Toast.LENGTH_SHORT).show()
        }
}

fun assignMeals(
    db: FirebaseFirestore,
    userId: String,
    selectedDietaryRestriction: String,
    context: Context
) {
    val userDocument = db.collection("users").document(userId)

    db.collection("meals")
        .get()
        .addOnSuccessListener { mealsSnapshot ->
            val filteredMeals = mealsSnapshot.documents.filter { doc ->
                val dietaryRestriction = doc.getString("dietaryRestrictions") ?: "None"

                // Filtrar según la restricción dietética seleccionada
                when (selectedDietaryRestriction) {
                    "None" -> true // Incluir todo si es "None"
                    "Pescatarian" -> dietaryRestriction != "pescatarian"
                    "Vegan" -> dietaryRestriction != "vegan"
                    "Vegetarian" -> dietaryRestriction != "vegetarian"
                    else -> true // En caso de un valor inesperado, incluir todo
                }
            }

            if (filteredMeals.isEmpty()) {
                Toast.makeText(context, "No se encontraron comidas para esta configuración", Toast.LENGTH_SHORT).show()
                return@addOnSuccessListener
            }

            // Agregar comidas filtradas a la subcolección
            filteredMeals.forEach { meal ->
                val mealData = meal.data
                    ?.filterValues { it != null }
                    ?.mapValues { it.value as Any }

                userDocument.collection("meals")
                    .add(mealData ?: emptyMap<String, Any>())
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Error al guardar comida: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }

            Toast.makeText(context, "Comidas asignadas correctamente!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { e ->
            Toast.makeText(context, "Error al obtener comidas: ${e.message}", Toast.LENGTH_SHORT).show()
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