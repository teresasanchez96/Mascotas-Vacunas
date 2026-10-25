package proyectoFCT.mascotasyvacunas.mascotas.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import proyectoFCT.mascotasyvacunas.R
import proyectoFCT.mascotasyvacunas.mascotas.ui.viewmodel.MascotaViewModel
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Composable
fun PantallaDetalleMascota(
    navController: NavController,
    mascotaId: Long,
    mascotaViewModel: MascotaViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val mascotas by mascotaViewModel.mascotas.collectAsState()
    val fotosMascotas by mascotaViewModel.fotosMascotas.collectAsState()
    val fotosMascotasCargadas by mascotaViewModel.fotosMascotasCargadas.collectAsState()

    LaunchedEffect(mascotaId) {
        mascotaViewModel.cargarFotoMascota(mascotaId)
    }

    val showDialog by mascotaViewModel.showDialog.collectAsState()

    val mascota = mascotas.find {
        it.id == mascotaId
    }

    var mostrarDialogoEditar by remember {
        mutableStateOf(false)
    }

    var mostrarMenu by remember {
        mutableStateOf(false)
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF0E9FA)
    ) { innerPadding ->

        if (mascota == null) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Cargando información...",
                    color = Color(0xFF25212A),
                    fontSize = 18.sp
                )
            }

        } else {

            val imagenMascota = when (
                mascota.especie.lowercase()
            ) {

                "perro" -> R.drawable.perro

                "gato" -> R.drawable.gato

                "conejo" -> R.drawable.conejo

                "hurón",
                "huron" -> R.drawable.huron

                else -> R.drawable.otramascota
            }

            val edad = calcularEdad(
                mascota.fechaNacimiento
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        horizontal = 20.dp,
                        vertical = 8.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Cabecera con botón volver y menú.
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Purple40,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Box {

                        IconButton(
                            onClick = {
                                mostrarMenu = true
                            }
                        ) {

                            Icon(
                                imageVector =
                                Icons.Default.MoreVert,
                                contentDescription = "Opciones",
                                tint = Purple40,
                                modifier = Modifier.size(30.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = mostrarMenu,
                            onDismissRequest = {
                                mostrarMenu = false
                            },
                            containerColor = Color(0xFFF0E9FA)
                        ) {

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Editar mascota",
                                        color = Purple40
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = null,
                                        tint = Purple40
                                    )
                                },
                                onClick = {
                                    mostrarMenu = false
                                    mostrarDialogoEditar = true
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Eliminar mascota",
                                        color = Purple40
                                    )
                                },
                                leadingIcon = {

                                    Icon(
                                        imageVector =
                                        Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = Purple40
                                    )
                                },
                                onClick = {

                                    mostrarMenu = false

                                    mascota.id?.let { id ->

                                        mascotaViewModel.setIdMascota(
                                            id
                                        )

                                        mascotaViewModel.changeShowDialog()
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                // Imagen de la mascota.
                Box(
                    modifier = Modifier.size(155.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Círculo lila de fondo.
                    Box(
                        modifier = Modifier
                            .size(155.dp)
                            .background(
                                color = Color(0xFFE3D8F5),
                                shape = CircleShape
                            )
                    )

                    // Fotografía real, indicador de carga o ilustración.
                    val fotoMascota = fotosMascotas[mascotaId]
                    val fotoCargada = mascotaId in fotosMascotasCargadas

                    when {
                        fotoMascota != null -> {
                            Image(
                                bitmap = fotoMascota.asImageBitmap(),
                                contentDescription = "Fotografía de ${mascota.nombre}",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(145.dp)
                                    .clip(CircleShape)
                            )
                        }

                        !fotoCargada -> {
                            CircularProgressIndicator(
                                modifier = Modifier.size(40.dp),
                                color = Purple40
                            )
                        }

                        else -> {
                            Image(
                                painter = painterResource(id = imagenMascota),
                                contentDescription = mascota.nombre,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(125.dp)
                            )
                        }
                    }

                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // Nombre.
                Text(
                    text = mascota.nombre,
                    color = Color(0xFF25212A),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                // Especie y raza.
                Text(
                    text = if (
                        mascota.raza.isNotBlank()
                    ) {
                        "${mascota.especie} · ${mascota.raza}"
                    } else {
                        mascota.especie
                    },
                    color = Color(0xFF5F5965),
                    fontSize = 18.sp
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                // Edad.
                if (edad.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Cake,
                            contentDescription = "Edad",
                            tint = Color(0xFF5F5965),
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = edad,
                            color = Color(0xFF5F5965),
                            fontSize = 17.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                // Tarjeta de información.
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 20.dp,
                                vertical = 20.dp
                            )
                    ) {

                        Text(
                            text = "Información",
                            color = Color(0xFF25212A),
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )

                        DatoMascota(
                            icon = Icons.Default.Pets,
                            titulo = "Nombre",
                            valor = mascota.nombre
                        )

                        DatoMascota(
                            icon = Icons.Default.Pets,
                            titulo = "Especie",
                            valor = mascota.especie
                        )

                        if (
                            mascota.raza.isNotBlank()
                        ) {

                            DatoMascota(
                                icon = Icons.Default.Pets,
                                titulo = "Raza",
                                valor = mascota.raza
                            )
                        }

                        DatoMascota(
                            icon = if (
                                mascota.genero.equals(
                                    "Hembra",
                                    ignoreCase = true
                                )
                            ) {
                                Icons.Default.Female
                            } else {
                                Icons.Default.Male
                            },
                            titulo = "Género",
                            valor = mascota.genero
                        )

                        DatoMascota(
                            icon = Icons.Default.DateRange,
                            titulo = "Fecha de nacimiento",
                            valor = mascota.fechaNacimiento
                        )

                        if (
                            !mascota.observaciones.isNullOrBlank()
                        ) {

                            DatoMascota(
                                icon =
                                Icons.Default.Description,
                                titulo = "Observaciones",
                                valor =
                                mascota.observaciones
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // Botón de vacunaciones.
                Button(
                    onClick = {

                        navController.navigate(
                            "vacunaciones_mascota/${mascota.id}"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40,
                        contentColor = Color.White
                    )
                ) {

                    Text(
                        text = "Ver vacunaciones",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }
    }

    // Diálogo para editar la mascota.
    if (mostrarDialogoEditar && mascota != null) {
        DialogoEditarMascota(
            mascota = mascota,
            onDismiss = {
                mostrarDialogoEditar = false
            },
            onGuardar = { mascotaEditada, fotoUri ->
                mascotaViewModel.updateMascota(
                    id = mascotaId,
                    mascota = mascotaEditada,
                    context = context,
                    fotoUri = fotoUri,
                    onFotoActualizada = {
                        navController.previousBackStackEntry
                            ?.savedStateHandle
                            ?.set("foto_actualizada_id", mascotaId)
                    }
                )

                mostrarDialogoEditar = false
            }
        )
    }

    // Diálogo de confirmación para eliminar.
    DialogoEliminarMascota(
        show = showDialog,
        onDismiss = {
            mascotaViewModel.changeShowDialog()
        },
        onConfirm = {

            mascotaViewModel.deleteMascota()

            mascotaViewModel.changeShowDialog()

            navController.popBackStack()
        }
    )
}

/*
 * Muestra un dato de la mascota dentro de la tarjeta.
 */
@Composable
private fun DatoMascota(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    titulo: String,
    valor: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Purple40,
            modifier = Modifier.size(24.dp)
        )

        Spacer(
            modifier = Modifier.width(14.dp)
        )

        Text(
            text = titulo,
            modifier = Modifier.width(125.dp),
            color = Color(0xFF5F5965),
            fontSize = 15.sp
        )

        Text(
            text = valor,
            modifier = Modifier.weight(1f),
            color = Color(0xFF25212A),
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/*
 * Diálogo de confirmación para eliminar una mascota.
 */
@Composable
private fun DialogoEliminarMascota(
    show: Boolean,
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
                    text = "¿Está seguro de que desea eliminar esta mascota? Esta acción no se puede deshacer.",
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

/*
 * Calcula la edad a partir de la fecha de nacimiento.
 */
private fun calcularEdad(
    fechaNacimiento: String
): String {

    return try {

        val fecha = LocalDate.parse(
            fechaNacimiento,
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        )

        val hoy = LocalDate.now()

        val periodo = Period.between(
            fecha,
            hoy
        )

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
