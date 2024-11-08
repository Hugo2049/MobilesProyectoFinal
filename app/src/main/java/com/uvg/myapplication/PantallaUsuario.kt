package com.uvg.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun SetGoalsScreen(navController: NavController) {

    var age by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var selectedFrequency by remember { mutableStateOf("") }
    val frequencies = listOf("Never", "Rarely", "Sometimes", "Often", "Very Often")

    var selectedDietaryRestriction by remember { mutableStateOf("None") }
    val dietaryOptions = listOf("None", "Vegetarian", "Vegan", "Pescatarian")

    var selectedGoal by remember { mutableStateOf("") }
    val goalOptions = listOf("Lose Weight", "Gain Muscle", "Maintain")

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD))
                )
            )
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text fields for age, weight, and height
        CustomTextField(value = age, onValueChange = { age = it }, label = "Age")
        CustomTextField(value = weight, onValueChange = { weight = it }, label = "Weight")
        CustomTextField(value = height, onValueChange = { height = it }, label = "Height")

        // Exercise frequency selection with horizontal scrolling
        Text("How often do you exercise?", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            frequencies.forEach { freq ->
                FrequencyChip(
                    text = freq,
                    isSelected = selectedFrequency == freq,
                    onSelected = { selectedFrequency = freq }
                )
            }
        }

        // Dietary restrictions selection
        Text("Do you have any dietary restrictions?", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            dietaryOptions.forEach { option ->
                FrequencyChip(
                    text = option,
                    isSelected = selectedDietaryRestriction == option,
                    onSelected = { selectedDietaryRestriction = option }
                )
            }
        }

        // Goal selection
        Text("What is your goal?", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            goalOptions.forEach { g ->
                FrequencyChip(
                    text = g,
                    isSelected = selectedGoal == g,
                    onSelected = { selectedGoal = g }
                )
            }
        }

        // Continue button
        Button(
            onClick = { navController.navigate("Exercises_main") },
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
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    showPassword: Boolean = false,
    onShowPasswordChange: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .background(Color(0xFFF0F0F0), RoundedCornerShape(12.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            cursorBrush = SolidColor(Color.Black),
            visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None
        )
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
