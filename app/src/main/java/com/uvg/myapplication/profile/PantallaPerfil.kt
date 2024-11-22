package com.uvg.myapplication.profile

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uvg.myapplication.BottomNavBar

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD)) // Fondo degradado verde suave
                )
            )
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
                color = Color(0xFF1B5E20), // Verde oscuro
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
            )

            // Fila 1: Fitness Level
            ProfileItem(text = "Fitness Level", Modifier.padding(bottom = 16.dp))

            // Fila 2: Dietary Preferences
            ProfileItem(text = "Dietary Preferences", Modifier.padding(bottom = 16.dp))

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de Update Profile
            Button(
                onClick = { navController.navigate("profile_info") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF66BB6A))
            ) {
                Text(text = "Update Profile", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón de Logout
            Button(
                onClick = {
                    // Eliminar el estado de autenticación
                    sharedPreferences.edit().clear().apply() // Limpiar SharedPreferences
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true } // Eliminar el historial para evitar volver atrás
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFE8F5E9)) // Verde suave
            ) {
                Text(text = "Log out", fontSize = 16.sp, color = Color(0xFF1B5E20)) // Verde oscuro
            }
        }

        // Barra de navegación inferior en la parte inferior con fondo degradado
        BottomNavBar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun ProfileItem(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { /* Acción al hacer clic */ }
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF1B5E20), // Verde oscuro
                modifier = Modifier.weight(1f)
            )

            // Icono de flecha para navegación
            Text(
                text = ">",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    }
}
