package com.uvg.myapplication.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uvg.myapplication.viewmodel.ChangePasswordViewModel
import com.uvg.myapplication.repository.UserRepository

@Composable
fun ProfileChangePasswordScreen(navController: NavController, username: String) {
    val repository = remember { UserRepository() }
    val viewModel = remember { ChangePasswordViewModel(repository) }

    ChangePasswordUI(navController, viewModel, username)
}

@Composable
fun ChangePasswordUI(navController: NavController, viewModel: ChangePasswordViewModel, username: String) {
    var newPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    val passwordUpdated by viewModel.passwordUpdated.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(passwordUpdated) {
        if (passwordUpdated) {
            Toast.makeText(context, "Password updated successfully", Toast.LENGTH_SHORT).show()
            navController.navigate("login")
        }
    }

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
        Text("Change Password", fontSize = 28.sp, color = Color(0xFF333333))

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (newPassword.isBlank()) {
                Toast.makeText(context, "Password is required", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.changePassword(username, newPassword)
            }
        }) {
            Text("Change Password")
        }

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
