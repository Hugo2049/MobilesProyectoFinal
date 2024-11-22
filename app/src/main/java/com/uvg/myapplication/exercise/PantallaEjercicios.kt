package com.uvg.myapplication.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.uvg.myapplication.BottomNavBar
import com.uvg.myapplication.R

@Composable
fun ExerciseScreen(navController: NavController, exerciseName: String) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            val painter = rememberImagePainter(
                data = "https://example.com/your_image_url.jpg",
                builder = {
                    scale(Scale.FILL)
                    transformations(CircleCropTransformation())
                }
            )

            Image(
                painter = painter,
                contentDescription = exerciseName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.exercise_title),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.sets_reps),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Text(
                text = stringResource(id = R.string.sets_details),
                color = Color.Gray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.targeted_muscles),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                MuscleChip(text = stringResource(id = R.string.middle_deltoid))
                MuscleChip(text = stringResource(id = R.string.upper_trapezius))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.equipment),
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Text(
                text = stringResource(id = R.string.equipment_details),
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
                    containerColor = Color(0xFF00A86B)
                )
            ) {
                Text(
                    text = stringResource(id = R.string.mark_completed),
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        BottomNavBar(
            navController = navController,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        )
    }
}

@Composable
fun MuscleChip(text: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFE1E1E1),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}
