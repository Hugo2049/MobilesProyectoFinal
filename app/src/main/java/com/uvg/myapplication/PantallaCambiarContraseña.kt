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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfilePassScreen(navController: NavController) {
    // Estado para la contraseña
    val passwordState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD)) // Fondo con gradiente similar al de login
                )
            )
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(
            text = "Change Password",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo de texto para nueva contraseña
        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            placeholder = { Text(text = "Enter new password", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.Black, fontSize = 16.sp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(40.dp)) // Espacio antes del botón

        // Botón "Save Changes"
        Button(
            onClick = {
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true } // Limpiar el stack de navegación
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A86B), // Botón verde similar al de login
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Save Changes",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Cancel"
        Button(
            onClick = {
                navController.navigateUp() // Vuelve a la pantalla anterior
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFFCCCCCC), // Color de botón cancel gris claro
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Cancel",
                fontSize = 16.sp
            )
        }
    }
}
