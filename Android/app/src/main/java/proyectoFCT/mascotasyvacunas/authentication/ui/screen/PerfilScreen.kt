package proyectoFCT.mascotasyvacunas.authentication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import proyectoFCT.mascotasyvacunas.authentication.ui.viewmodel.PerfilViewModel
import proyectoFCT.mascotasyvacunas.navigation.BarraNavegacion
import proyectoFCT.mascotasyvacunas.navigation.Home
import proyectoFCT.mascotasyvacunas.navigation.ListaMascotas
import proyectoFCT.mascotasyvacunas.navigation.ListaVacunas
import proyectoFCT.mascotasyvacunas.navigation.Login
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40

@Composable
fun PantallaPerfil(
    navController: NavController,
    perfilViewModel: PerfilViewModel = hiltViewModel()
) {

    val sesionCerrada by perfilViewModel.sesionCerrada.collectAsState()

    var mostrarInformacion by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(sesionCerrada) {

        if (sesionCerrada) {

            navController.navigate(Login) {

                popUpTo(Home) {
                    inclusive = true
                }

                launchSingleTop = true
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF0E9FA),

        bottomBar = {
            BarraNavegacion(
                pantallaActual = "perfil",

                onMascotasClick = {
                    navController.navigate(ListaMascotas)
                },

                onVacunasClick = {
                    navController.navigate(ListaVacunas)
                },

                onPerfilClick = {
                    // Ya estamos en Perfil
                }
            )
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    horizontal = 20.dp,
                    vertical = 18.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Título
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Mi perfil",
                    color = Purple40,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // Icono de la aplicación
            Surface(
                modifier = Modifier.size(150.dp),
                shape = CircleShape,
                color = Color(0xFFE3D8F5)
            ) {

                Icon(
                    imageVector = Icons.Default.Pets,
                    contentDescription = "Mascotas y Vacunas",
                    tint = Purple40,
                    modifier = Modifier.padding(22.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            // Mis mascotas
            OpcionPerfil(
                icon = Icons.Default.Pets,
                texto = "Mis mascotas",
                onClick = {
                    navController.navigate(ListaMascotas)
                }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Catálogo de vacunas
            OpcionPerfil(
                icon = Icons.Default.MedicalServices,
                texto = "Catálogo de vacunas",
                onClick = {
                    navController.navigate(ListaVacunas)
                }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Sobre la aplicación
            OpcionPerfil(
                icon = Icons.Default.Info,
                texto = "Sobre la aplicación",
                onClick = {
                    mostrarInformacion = true
                }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Cerrar sesión
            OpcionPerfil(
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                texto = "Cerrar sesión",
                colorIcono = Color(0xFFD96C78),
                onClick = {
                    perfilViewModel.cerrarSesion()
                }
            )
        }
    }

    // Información de la aplicación
    if (mostrarInformacion) {
        AlertDialog(
            onDismissRequest = {
                mostrarInformacion = false
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color(0xFFFAF7FF),
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        modifier = Modifier.size(84.dp),
                        shape = CircleShape,
                        color = Color(0xFFEAE1FA)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Pets,
                                contentDescription = null,
                                tint = Purple40,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Mascotas y Vacunas",
                        color = Purple40,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Aplicación para gestionar la información de tus mascotas y mantener organizado su historial de vacunación.",
                        color = Color(0xFF454052),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Box(
                        modifier = Modifier
                            .width(32.dp)
                            .height(2.dp)
                            .background(Purple40)
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    // Tus mascotas
                    InformacionApp(
                        icono = Icons.Default.Pets,
                        titulo = "Tus mascotas",
                        descripcion = "Toda su información"
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Sus vacunas
                    InformacionApp(
                        icono = Icons.Default.Vaccines,
                        titulo = "Sus vacunas",
                        descripcion = "Historial organizado"
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Fácil y segura
                    InformacionApp(
                        icono = Icons.Default.VerifiedUser,
                        titulo = "Fácil y segura",
                        descripcion = "Pensada para el cuidado de tu mascota"
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        mostrarInformacion = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40
                    )
                ) {
                    Text(
                        text = "Cerrar",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )
    }
}

@Composable
private fun OpcionPerfil(
    icon: ImageVector,
    texto: String,
    colorIcono: Color = Purple40,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        onClick = onClick
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp,
                    vertical = 17.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = texto,
                tint = colorIcono,
                modifier = Modifier.size(30.dp)
            )

            Spacer(
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = texto,
                modifier = Modifier.weight(1f),
                color = Color(0xFF25212A),
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF6A6170),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun InformacionApp(
    icono: ImageVector,
    titulo: String,
    descripcion: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = Purple40,
            modifier = Modifier.size(30.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = titulo,
                color = Color(0xFF302940),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Text(
                text = descripcion,
                color = Color(0xFF777181),
                fontSize = 12.sp
            )
        }
    }
}