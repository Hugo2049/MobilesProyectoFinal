package com.uvg.myapplication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfilePassScreen(navController: NavController, username: String) {
    var newPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD))
                )
            )
            .padding(horizontal = 24.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Now let's change your password",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo de texto para la nueva contraseña
        BasicTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFFFFFFF))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            decorationBox = { innerTextField ->
                if (newPassword.isEmpty()) {
                    Text("New Password", color = Color.Gray, fontSize = 16.sp)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (newPassword.isNotEmpty()) {
                    db.collection("users")
                        .whereEqualTo("username", username)
                        .get()
                        .addOnSuccessListener { documents ->
                            if (!documents.isEmpty) {
                                val userId = documents.documents[0].id
                                db.collection("users").document(userId)
                                    .update("password", newPassword)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Password updated successfully!", Toast.LENGTH_SHORT).show()
                                        navController.navigate("login") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(context, "Error updating password: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error fetching user: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(context, "Please enter a new password", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A86B),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Save Changes",
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón "Cancel" para regresar a la pantalla de inicio de sesión
        Button(
            onClick = {
                navController.navigate("login")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFFCCCCCC),
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
