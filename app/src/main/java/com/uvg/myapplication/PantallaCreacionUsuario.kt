package com.uvg.myapplication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SignUpScreen(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5EEDC))
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Get started with NutriFit",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Create your account",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomTextField(value = firstName, onValueChange = { firstName = it }, label = "First Name")
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(value = username, onValueChange = { username = it }, label = "Username")
        Spacer(modifier = Modifier.height(16.dp))
        CustomTextField(value = password, onValueChange = { password = it }, label = "Password", isPassword = true)
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // Guardar datos en Firestore
                val user = hashMapOf(
                    "firstName" to firstName,
                    "username" to username,
                    "password" to password
                )

                db.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Account created successfully!", Toast.LENGTH_SHORT).show()
                        navController.navigate("set_goals") // Navega a la pantalla de SetGoalsScreen
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Failed to create account: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00E676)
            )
        ) {
            Text(text = "Continue", fontSize = 18.sp, color = Color.White)
        }
    }
}
