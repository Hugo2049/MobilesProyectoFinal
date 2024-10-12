package com.uvg.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SetGoalsScreen() {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var workoutFrequency by remember { mutableStateOf("") }
    var dietaryRestriction by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }

    var selectedFrequency by remember { mutableStateOf("") }
    val frequencies = listOf("Never", "Rarely", "Sometimes", "Often", "Very Often")

    var selectedDietaryRestriction by remember { mutableStateOf("None") }
    val dietaryOptions = listOf("None", "Vegetarian", "Vegan", "Pescatarian")

    var selectedGoal by remember { mutableStateOf("") }
    val goalOptions = listOf("Lose Weight", "Gain Muscle", "Maintain")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo para el nombre
        CustomTextField(value = name, onValueChange = { name = it }, label = "Nombre")

        // Campo para el nombre de usuario
        CustomTextField(value = username, onValueChange = { username = it }, label = "Usuario")

        // Campo para la contraseña (con opción de mostrar/ocultar)
        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            isPassword = true,
            showPassword = showPassword,
            onShowPasswordChange = { showPassword = !showPassword }
        )

        // Campo para la edad
        CustomTextField(value = age, onValueChange = { age = it }, label = "Edad")

        // Campo para el peso
        CustomTextField(value = weight, onValueChange = { weight = it }, label = "Peso")

        // Campo para la altura
        CustomTextField(value = height, onValueChange = { height = it }, label = "Altura")

        // Selección de la frecuencia de ejercicio
        Text("¿Con qué frecuencia haces ejercicio?", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            frequencies.forEach { freq ->
                FrequencyChip(
                    text = freq,
                    isSelected = selectedFrequency == freq,
                    onSelected = { selectedFrequency = freq }
                )
            }
        }

        // Restricciones dietéticas
        Text("¿Tienes alguna restricción dietética?", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            dietaryOptions.forEach { option ->
                FrequencyChip(
                    text = option,
                    isSelected = selectedDietaryRestriction == option,
                    onSelected = { selectedDietaryRestriction = option }
                )
            }
        }

        // Selección de objetivo
        Text("¿Cuál es tu objetivo?", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            goalOptions.forEach { g ->
                FrequencyChip(
                    text = g,
                    isSelected = selectedGoal == g,
                    onSelected = { selectedGoal = g }
                )
            }
        }

        // Botón de continuar
        Button(
            onClick = { /* Manejar acción de continuar */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00AA00))
        ) {
            Text("Continuar", color = Color.White)
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onShowPasswordChange: (() -> Unit)? = null
) {
    Column {
        Text(label, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .background(Color(0xFFF0F0F0))
                .padding(8.dp),
            visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (isPassword && onShowPasswordChange != null) {
            Text(
                text = if (showPassword) "Ocultar" else "Mostrar",
                modifier = Modifier
                    .clickable { onShowPasswordChange() }
                    .padding(4.dp),
                color = Color.Blue
            )
        }
    }
}

@Composable
fun FrequencyChip(text: String, isSelected: Boolean, onSelected: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                if (isSelected) Color(0xFFEEE0BB) else Color(0xFFF0F0F0),
                shape = MaterialTheme.shapes.small
            )
            .clickable { onSelected() }
            .padding(8.dp)
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun SetGoalsScreenPreview() {
    SetGoalsScreen()
}