package com.uvg.myapplication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileInfoScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    val username = sharedPreferences.getString("username", "User") ?: "User"  // Obtener el nombre de usuario de SharedPreferences

    val newPasswordState = remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD))
                )
            )
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título mostrando el username
        Text(
            text = "Hi, $username! Let's change your password",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "New Password",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        BasicTextField(
            value = newPasswordState.value,
            onValueChange = { newPasswordState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            decorationBox = { innerTextField ->
                if (newPasswordState.value.isEmpty()) {
                    Text("Enter your new password", color = Color.Gray, fontSize = 16.sp)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                val newPassword = newPasswordState.value
                if (newPassword.isNotEmpty()) {
                    // Actualizar la contraseña en Firestore
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
                                        navController.navigate("main_profile") {
                                            popUpTo("main_profile") { inclusive = true }
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
                .padding(horizontal = 16.dp)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A86B),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Save Changes",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("main_profile") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color(0xFF333333)
            )
        ) {
            Text(
                text = "Cancel",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
