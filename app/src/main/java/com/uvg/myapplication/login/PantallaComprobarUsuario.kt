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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.uvg.myapplication.viewmodel.CheckUserViewModel
import com.uvg.myapplication.repository.UserRepository

@Composable
fun ProfileCheckUserScreen(navController: NavController) {
    val repository = remember { UserRepository() }
    val viewModel = remember { CheckUserViewModel(repository) }

    CheckUserUI(navController, viewModel)
}

@Composable
fun CheckUserUI(navController: NavController, viewModel: CheckUserViewModel) {
    var username by remember { mutableStateOf("") }
    val context = LocalContext.current

    val userExists by viewModel.userExists.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(userExists) {
        if (userExists) {
            navController.navigate("change_password/$username")
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
        Text("Check Username", fontSize = 28.sp, color = Color(0xFF333333))

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(color = Color.Black, fontSize = 16.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (username.isBlank()) {
                Toast.makeText(context, "Username is required", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.checkUser(username)
            }
        }) {
            Text("Check User")
        }

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }
    }
}