package com.uvg.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NutriFitLoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome Back!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        // Campo de nombre de usuario
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            BasicTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                decorationBox = { innerTextField ->
                    if (username.isEmpty()) {
                        Text("Username", color = Color.Gray)
                    }
                    innerTextField()
                }
            )
        }

        // Campo de contraseña
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            BasicTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                decorationBox = { innerTextField ->
                    if (password.isEmpty()) {
                        Text("Password", color = Color.Gray)
                    }
                    innerTextField()
                }
            )
        }

        // Texto de "Olvidó su contraseña" con navegación
        Text(
            text = "Forgot your password?",
            color = Color(0xFF00A86B),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("change_password")
                },
            textAlign = TextAlign.End
        )

        // Botón de "Iniciar sesión" con navegación
        Button(
            onClick = { navController.navigate("exercises_main") },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A86B)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Sign In", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }

        // Texto de "Registrarse" con navegación
        Text(
            text = "New user? Sign Up",
            color = Color(0xFF333333),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("create_user")
                },
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
    }
}
