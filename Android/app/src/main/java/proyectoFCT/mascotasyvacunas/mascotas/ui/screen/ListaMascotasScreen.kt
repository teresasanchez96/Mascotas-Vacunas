package proyectoFCT.mascotasyvacunas.mascotas.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import proyectoFCT.mascotasyvacunas.R
import proyectoFCT.mascotasyvacunas.mascotas.ui.model.Mascota
import proyectoFCT.mascotasyvacunas.mascotas.ui.viewmodel.MascotaViewModel
import proyectoFCT.mascotasyvacunas.navigation.BarraNavegacion
import proyectoFCT.mascotasyvacunas.navigation.ListaVacunas
import proyectoFCT.mascotasyvacunas.navigation.NuevaMascota
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter


@Composable
fun PantallaListaMascotas(
    navController: NavController,
    mascotaViewModel: MascotaViewModel = hiltViewModel()
) {

    val mascotas by mascotaViewModel.mascotas.collectAsState()
    val showDialog by mascotaViewModel.showDialog.collectAsState()

    LaunchedEffect(Unit) {
        mascotaViewModel.cargarMascotas()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF0E9FA),
        bottomBar = {
            BarraNavegacion(
                pantallaActual = "mascotas",
                onMascotasClick = {
                    // Ya estamos en mascotas
                },
                onVacunasClick = {
                    navController.navigate(ListaVacunas)
                },
                onPerfilClick = {
                    //navController.navigate(Perfil)
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
                )
        ) {

            // Encabezado
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Mis mascotas",
                    color = Purple40,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                FloatingActionButton(
                    onClick = {
                       navController.navigate(NuevaMascota)
                    },
                    modifier = Modifier.size(52.dp),
                    containerColor = Purple40,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Nueva mascota"
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(mascotas) { mascota ->

                    MascotaItem(
                        mascota = mascota,
                        mascotaViewModel = mascotaViewModel,
                        navController = navController
                    )
                }
            }
        }
    }

    MyDialog(
        show = showDialog,
        description = "¿Está seguro de que desea eliminar la mascota?",
        onDismiss = {
            mascotaViewModel.changeShowDialog()
        },
        onConfirm = {
            mascotaViewModel.deleteMascota()
        }
    )
}

@Composable
fun MascotaItem(
    mascota: Mascota,
    mascotaViewModel: MascotaViewModel,
    navController: NavController
) {


    val imagenMascota = when (mascota.especie.lowercase()) {
        "perro" -> R.drawable.perro
        "gato" -> R.drawable.gato
        "conejo" -> R.drawable.conejo
        "pájaro", "pajaro" -> R.drawable.pajaro
        "hámster", "hamster" -> R.drawable.hamster
        "tortuga" -> R.drawable.tortuga
        "hurón", "huron" -> R.drawable.huron
        else -> R.drawable.otramascota
    }

    val edad = calcularEdad(mascota.fechaNacimiento)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                mascota.id?.let { id ->
                    navController.navigate(
                        "detalle_mascota/$id"
                    )
                }
            },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    )  {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 14.dp,
                    vertical = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Imagen completa de la mascota
            Image(
                painter = painterResource(
                    id = imagenMascota
                ),
                contentDescription = mascota.nombre,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(78.dp)
            )

            Spacer(
                modifier = Modifier.width(14.dp)
            )

            // Información de la mascota
            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = mascota.nombre,
                    color = Color(0xFF25212A),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = if (mascota.raza.isNotBlank()) {
                        "${mascota.especie} · ${mascota.raza}"
                    } else {
                        mascota.especie
                    },
                    color = Color(0xFF5F5965),
                    fontSize = 14.sp
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                if (edad.isNotEmpty()) {
                    Text(
                        text = edad,
                        color = Color(0xFF5F5965),
                        fontSize = 14.sp
                    )
                }
            }


            // Eliminar mascota
            IconButton(
                onClick = {

                    mascota.id?.let {
                        mascotaViewModel.setIdMascota(it)
                        mascotaViewModel.changeShowDialog()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar mascota",
                    tint = Color(0xFFD96C78)
                )
            }
        }
    }
}

private fun calcularEdad(fechaNacimiento: String): String {

    return try {

        val fecha = LocalDate.parse(
            fechaNacimiento,
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        )

        val hoy = LocalDate.now()
        val periodo = Period.between(fecha, hoy)

        when {
            periodo.years > 0 -> {
                if (periodo.years == 1) {
                    "1 año"
                } else {
                    "${periodo.years} años"
                }
            }

            periodo.months > 0 -> {
                if (periodo.months == 1) {
                    "1 mes"
                } else {
                    "${periodo.months} meses"
                }
            }

            else -> {
                "Menos de un mes"
            }
        }

    } catch (e: Exception) {
        ""
    }
}

@Composable
fun MyDialog(
    show: Boolean,
    description: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    if (show) {

        AlertDialog(
            onDismissRequest = onDismiss,

            icon = {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = Color(0xFFFCE7EB),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar mascota",
                        tint = Color(0xFFC45C6C),
                        modifier = Modifier.size(30.dp)
                    )
                }
            },

            title = {
                Text(
                    text = "¿Eliminar mascota?",
                    color = Purple40,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },

            text = {
                Text(
                    text = description,
                    color = Color(0xFF5F5965),
                    textAlign = TextAlign.Center
                )
            },

            confirmButton = {
                TextButton(
                    onClick = onConfirm
                ) {
                    Text(
                        text = "Eliminar",
                        color = Color(0xFFC45C6C),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },

            dismissButton = {
                TextButton(
                    onClick = onDismiss
                ) {
                    Text(
                        text = "Cancelar",
                        color = Purple40,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },

            containerColor = Color(0xFFF8F3FF),
            shape = RoundedCornerShape(24.dp)
        )
    }
}