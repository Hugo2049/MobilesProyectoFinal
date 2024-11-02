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
                text = "Frittata con Espárragos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Placeholder de la imagen centrado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Gray)
            ) {
                // Este Box interno centra el texto dentro del Box exterior
                Box(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center) // Centra el contenido dentro del Box
                ) {
                    Text(
                        text = "Imagen de receta",
                        color = Color.White
                    )
                }
            }

            // Sección de ingredientes
            Text(
                text = "Ingredientes",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "- 6 huevos\n" +
                        "- 1 manojo de espárragos, cortados\n" +
                        "- 1/2 pimiento, picado\n" +
                        "- Sal y pimienta al gusto",
                fontSize = 16.sp
            )

            // Sección de instrucciones
            Text(
                text = "Instrucciones",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "1. Precalienta el horno a 190°C.\n" +
                        "2. Bate los huevos, sazona con sal y pimienta.\n" +
                        "3. Saltea los espárragos y el pimiento a fuego medio.\n" +
                        "4. Vierte los huevos sobre las verduras y cocina hasta que cuajen.\n" +
                        "5. Lleva al horno hasta que esté esponjoso y cocido.",
                fontSize = 16.sp
            )

            // Sección de restricciones dietéticas
            Text(
                text = "Restricciones Dietéticas",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "- Sin gluten\n" +
                        "- Sin nueces\n" +
                        "- Opción sin lácteos: Usa queso sin lácteos.",
                fontSize = 16.sp
            )
        }

        // Barra de navegación inferior que siempre estará en la parte inferior de la pantalla
        BottomNavBar(
            navController = navController
        )
    }
}
