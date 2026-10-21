package proyectoFCT.mascotasyvacunas.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40

@Composable
fun BarraNavegacion(
    pantallaActual: String,
    onMascotasClick: () -> Unit,
    onVacunasClick: () -> Unit,
    onPerfilClick: () -> Unit
) {

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {

        // Mascotas
        NavigationBarItem(
            selected = pantallaActual == "mascotas",
            onClick = onMascotasClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Pets,
                    contentDescription = "Mascotas",
                    modifier = Modifier.size(30.dp)
                )
            },
            label = {
                Text(
                    text = "Mascotas",
                    fontSize = 14.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Purple40,
                selectedTextColor = Purple40,
                unselectedIconColor = Color(0xFF8A858C),
                unselectedTextColor = Color(0xFF8A858C),
                indicatorColor = Color.Transparent
            )
        )

        // Vacunas
        NavigationBarItem(
            selected = pantallaActual == "vacunas",
            onClick = onVacunasClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.MedicalServices,
                    contentDescription = "Vacunas",
                    modifier = Modifier.size(30.dp)
                )
            },
            label = {
                Text(
                    text = "Vacunas",
                    fontSize = 14.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Purple40,
                selectedTextColor = Purple40,
                unselectedIconColor = Color(0xFF8A858C),
                unselectedTextColor = Color(0xFF8A858C),
                indicatorColor = Color.Transparent
            )
        )

        // Perfil
        NavigationBarItem(
            selected = pantallaActual == "perfil",
            onClick = onPerfilClick,
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    modifier = Modifier.size(30.dp)
                )
            },
            label = {
                Text(
                    text = "Perfil",
                    fontSize = 14.sp
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Purple40,
                selectedTextColor = Purple40,
                unselectedIconColor = Color(0xFF8A858C),
                unselectedTextColor = Color(0xFF8A858C),
                indicatorColor = Color.Transparent
            )
        )
    }
}