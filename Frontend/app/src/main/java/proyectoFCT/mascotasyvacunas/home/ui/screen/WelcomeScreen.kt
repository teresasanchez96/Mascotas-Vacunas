package proyectoFCT.mascotasyvacunas.home.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import proyectoFCT.mascotasyvacunas.R
import proyectoFCT.mascotasyvacunas.navigation.Login
import proyectoFCT.mascotasyvacunas.navigation.Registro
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40

@Composable
fun PantallaBienvenida(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF9F6FF),
                        Color(0xFFF0E9FA)
                    )
                )
            )
            .padding(horizontal = 28.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Imagen principal
        Image(
            painter = painterResource(
                id = R.drawable.portada
            ),
            contentDescription = "Mascotas",
            modifier = Modifier.size(300.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Título de la aplicación
        Text(
            text = "Mascotas\ny Vacunas",
            color = Purple40,
            fontSize = 50.sp,
            lineHeight = 52.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Descripción
        Text(
            text = "Cuida de los que\nsiempre están a tu lado",
            color = Color(0xFF302B35),
            fontSize = 18.sp,
            lineHeight = 27.sp,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(50.dp)
        )

        // Botón iniciar sesión
        Button(
            onClick = {
                navController.navigate(Login)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple40
            )
        ) {
            Text(
                text = "Iniciar sesión",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        // Botón registrarse
        OutlinedButton(
            onClick = {
                navController.navigate(Registro)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Transparent,
                contentColor = Purple40
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Purple40
            )
        ) {
            Text(
                text = "Registrarse",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}