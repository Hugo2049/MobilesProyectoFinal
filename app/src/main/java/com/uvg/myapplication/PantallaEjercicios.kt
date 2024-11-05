package com.uvg.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation

@Composable
fun ExerciseScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5EEDC))
            .padding(16.dp)
    ) {
        // Contenido principal de la pantalla
        val painter = rememberImagePainter(
            data = "PlaceHolder",
            builder = {
                scale(Scale.FILL)
                transformations(CircleCropTransformation())
            }
        )

        Image(
            painter = painter,
            contentDescription = "Dumbbell Lateral Raise",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Dumbbell Lateral Raise",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Sets x Reps",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
        Text(
            text = "4x12-15\nRest 60s",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Targeted Muscles",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            MuscleChip("Middle Deltoid")
            MuscleChip("Upper Trapezius")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Equipment",
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
        Text(
            text = "Dumbbells",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* TODO: Acción al hacer clic */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF007BFF)
            )
        ) {
            Text(
                text = "Mark as Completed",
                color = Color.White,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Spacer para empujar el contenido hacia arriba

        // Barra de navegación en la parte inferior
        BottomNavBar(navController)
    }
}

@Composable
fun MuscleChip(muscle: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.LightGray,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = muscle,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 14.sp
        )
    }
}