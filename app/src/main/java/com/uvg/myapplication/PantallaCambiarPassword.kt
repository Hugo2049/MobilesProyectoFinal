package com.uvg.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LightGrayBackground1 = Color(0xFFF5F5F5)
val DarkerGrayText1 = Color(0xFF333333)
val GrayButton1 = Color(0xFFCCCCCC)

@Composable
fun ProfilePassScreen() {
    // Estados para el username y password
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(LightGrayBackground1),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la página
        Text(
            text = "Profile",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DarkerGrayText1,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Sección para cambiar Password
        Text(
            text = "Password",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = DarkerGrayText1,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            placeholder = { Text(text = "Enter your password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(40.dp)) // Más espacio antes del botón

        // Botón "Save Changes"
        Button(
            onClick = { /* Acción de guardar cambios */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = GrayButton1,
                contentColor = DarkerGrayText1
            )
        ) {
            Text(
                text = "Save Changes",
                fontSize = 16.sp
            )
        }
    }
}