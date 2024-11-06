package com.uvg.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RecipeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F5DC), Color(0xFFDDFFDD)) // Fondo degradado suave
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Frittata con Espárragos",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF00796B),
                modifier = Modifier.fillMaxWidth()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFFB0EFC1), Color(0xFF88D89D)) // Verde pastel suave
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Imagen de receta",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "Ingredientes",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF00796B)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.verticalGradient(
                        colors = listOf(Color(0xFFE0F5DC), Color(0xFFCDE9C8)) // Beige y verde claro
                    ))
            ) {
                Text(
                    text = "- 6 huevos\n- 1 manojo de espárragos, cortados\n- 1/2 pimiento, picado\n- Sal y pimienta al gusto",
                    fontSize = 16.sp,
                    color = Color(0xFF004D40)
                )
            }

            Text(
                text = "Instrucciones",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF00796B)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Brush.verticalGradient(
                        colors = listOf(Color(0xFFE0F5DC), Color(0xFFCDE9C8)) // Beige y verde claro
                    ))
            ) {
                Text(
                    text = "1. Precalienta el horno a 190°C.\n2. Bate los huevos, sazona con sal y pimienta.\n3. Saltea los espárragos y el pimiento a fuego medio.\n4. Vierte los huevos sobre las verduras y cocina hasta que cuajen.\n5. Lleva al horno hasta que esté esponjoso y cocido.",
                    fontSize = 16.sp,
                    color = Color(0xFF5D4037)
                )
            }

            Text(
                text = "Restricciones Dietéticas",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF00796B)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE9F7EF), shape = RoundedCornerShape(8.dp)) // Verde claro pastel
                    .padding(12.dp)
            ) {
                Text(
                    text = "- Sin gluten\n- Sin nueces\n- Opción sin lácteos: Usa queso sin lácteos.",
                    fontSize = 16.sp,
                    color = Color(0xFF33691E)
                )
            }
        }

        BottomNavBar(
            navController = navController
        )
    }
}
