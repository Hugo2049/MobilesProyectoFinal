package com.uvg.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RecipeScreen(navController: NavController) {
    // Usamos un Column para estructurar el contenido y la barra de navegación inferior
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fondo blanco para la pantalla
    ) {
        // Contenido principal de la pantalla
        Column(
            modifier = Modifier
                .weight(1f) // Permite que el contenido ocupe el espacio disponible
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Título de la receta
            Text(
                text = "Frittata with Asparagus",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Placeholder de la imagen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            )

            // Sección de ingredientes
            Text(
                text = "Ingredients",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "- 6 eggs\n" +
                        "- 1 bunch asparagus, trimmed and cut\n" +
                        "- 1/2 bell pepper, diced\n" +
                        "- Salt and pepper to taste",
                fontSize = 16.sp
            )

            // Sección de instrucciones
            Text(
                text = "Instructions",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "1. Preheat oven to 375°F (190°C).\n" +
                        "2. Beat eggs in a bowl, season with salt and pepper.\n" +
                        "3. Sauté asparagus and bell pepper over medium heat.\n" +
                        "4. Pour eggs over vegetables, cook until set.\n" +
                        "5. Transfer to oven, bake until eggs are puffed and set.",
                fontSize = 16.sp
            )

            // Sección de restricciones dietéticas
            Text(
                text = "Dietary Restrictions",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "- Gluten-free\n" +
                        "- Nut-free\n" +
                        "- Dairy-free option: Use dairy-free cheese.",
                fontSize = 16.sp
            )
        }

        // Barra de navegación inferior que siempre estará en la parte inferior de la pantalla
        BottomNavBar(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}
