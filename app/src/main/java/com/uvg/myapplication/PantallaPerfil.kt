package com.uvg.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults

@Composable
fun ProfileScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5EEDC))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 64.dp), // Espacio para la barra de navegación
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título "Your Profile"
            Text(
                text = "Your Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
            )

            // Fila 1: Fitness Level
            ProfileItem(text = "Fitness Level", Modifier.padding(bottom = 32.dp))

            // Fila 2: Dietary Preferences
            ProfileItem(text = "Dietary Preferences", Modifier.padding(bottom = 32.dp))

            // Botón de Update Profile
            Button(
                onClick = { navController.navigate("profile_info") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEFEFEF) // Gris claro
                )
            ) {
                Text(text = "Update Profile", fontSize = 16.sp, color = Color.Black)
            }

            // Botón de Logout
            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFEFEFEF) // Gris claro
                )
            ) {
                Text(text = "Log out", fontSize = 16.sp, color = Color.Black)
            }
        }

        // Barra de navegación inferior en un Box para anclarla al fondo
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter) // Ubica la barra en la parte inferior
                .fillMaxWidth()
        ) {
            BottomNavBar(navController = navController)
        }
    }
}

@Composable
fun ProfileItem(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /* Acción al hacer clic */ }
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        // Icono de flecha para navegación
        Text(
            text = ">",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}
