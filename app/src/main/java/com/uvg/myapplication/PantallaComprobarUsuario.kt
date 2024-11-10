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
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ProfileCheckUser(navController: NavController) {
    var username by remember { mutableStateOf("") }
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
            text = "First let's check your username",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        BasicTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .background(Color(0xFFFFFFFF))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
            decorationBox = { innerTextField ->
                if (username.isEmpty()) {
                    Text("Username", color = Color.Gray, fontSize = 16.sp)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                db.collection("users")
                    .whereEqualTo("username", username)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (!documents.isEmpty) {
                            // Navegar a `change_password` con el `username` como argumento
                            navController.navigate("change_password/$username") {
                                popUpTo("check_user") { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "Usuario no existe", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(context, "Error al verificar usuario: ${exception.message}", Toast.LENGTH_SHORT).show()
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

        Button(
            onClick = {
                navController.navigateUp()
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
