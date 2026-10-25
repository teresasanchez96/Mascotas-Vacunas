package proyectoFCT.mascotasyvacunas.vacunaciones.ui.screen

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import proyectoFCT.mascotasyvacunas.R
import proyectoFCT.mascotasyvacunas.mascotas.ui.viewmodel.MascotaViewModel
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40
import proyectoFCT.mascotasyvacunas.vacunaciones.ui.model.Vacunacion
import proyectoFCT.mascotasyvacunas.vacunaciones.ui.viewmodel.VacunacionViewModel
import proyectoFCT.mascotasyvacunas.vacunas.ui.viewmodel.VacunaViewModel
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Composable
fun PantallaVacunacionesMascota(
    navController: NavController,
    mascotaId: Long,
    vacunacionViewModel: VacunacionViewModel = hiltViewModel(),
    vacunaViewModel: VacunaViewModel = hiltViewModel(),
    mascotaViewModel: MascotaViewModel = hiltViewModel()
) {

    val vacunaciones by vacunacionViewModel.vacunaciones.collectAsState()
    val vacunas by vacunaViewModel.vacunas.collectAsState()
    val mascotas by mascotaViewModel.mascotas.collectAsState()
    val fotosMascotas by mascotaViewModel.fotosMascotas.collectAsState()

    val fotosMascotasCargadas by mascotaViewModel.fotosMascotasCargadas.collectAsState()

    val mascota = mascotas.find {
        it.id == mascotaId
    }

    var mostrarFormulario by remember {
        mutableStateOf(false)
    }

    var vacunacionEditar by remember {
        mutableStateOf<Vacunacion?>(null)
    }

    var mostrarDialogoEliminar by remember {
        mutableStateOf(false)
    }

    var vacunacionEliminar by remember {
        mutableStateOf<Vacunacion?>(null)
    }

    // Pestaña seleccionada: 0 = Todas, 1 = Administradas, 2 = Pendientes.
    var indiceSeleccionado by remember {
        mutableIntStateOf(0)
    }

    // Lista según la pestaña seleccionada.
    val vacunacionesFiltradas = when (indiceSeleccionado) {
        1 -> vacunaciones.filter { it.vacunaAdministrada }
        2 -> vacunaciones.filter { !it.vacunaAdministrada }
        else -> vacunaciones
    }

    // Cargar las mascotas, vacunaciones y vacunas.
    LaunchedEffect(mascotaId) {
        mascotaViewModel.cargarMascotas()
        vacunacionViewModel.cargarVacunaciones(mascotaId)
        vacunaViewModel.cargarVacunas()
    }

    Scaffold(
        containerColor = Color(0xFFF0E9FA)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {

            // Cabecera.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Purple40,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.width(4.dp)
                )

                Text(
                    text = "Vacunaciones",
                    color = Purple40,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Información de la mascota.
            if (mascota != null) {

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

                // Obtener la fotografía real de la mascota, si existe.
                val fotoMascota = mascota.id?.let { id ->
                    fotosMascotas[id]
                }

                // Foto y datos de la mascota en horizontal.
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier.size(100.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(
                                    color = Color(0xFFE3D8F5),
                                    shape = CircleShape
                                )
                        )

                        // Mostrar la foto real; si no existe, mostrar el dibujo.

                        if (fotoMascota != null) {
                            Image(
                                bitmap = fotoMascota.asImageBitmap(),
                                contentDescription = "Foto de ${mascota.nombre}",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(94.dp)
                                    .clip(CircleShape)
                            )
                        } else if (mascotaId in fotosMascotasCargadas) {
                            Image(
                                painter = painterResource(id = imagenMascota),
                                contentDescription = mascota.nombre,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(80.dp)
                            )
                        }

                    }


                    Spacer(
                        modifier = Modifier.width(18.dp)
                    )

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = mascota.nombre,
                            color = Color(0xFF25212A),
                            fontSize = 27.sp,
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
                            fontSize = 16.sp
                        )

                        if (edad.isNotEmpty()) {
                            Spacer(
                                modifier = Modifier.height(2.dp)
                            )

                            Text(
                                text = edad,
                                color = Color(0xFF5F5965),
                                fontSize = 15.sp
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                // Botón para añadir una vacunación.
                Button(
                    onClick = {
                        vacunacionEditar = null
                        mostrarFormulario = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40,
                        contentColor = Color.White
                    )
                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "Añadir vacunación",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // Barra para filtrar las vacunaciones.
            TabRow(
                selectedTabIndex = indiceSeleccionado,
                containerColor = Color.Transparent,
                contentColor = Purple40
            ) {
                Tab(
                    selected = indiceSeleccionado == 0,
                    onClick = { indiceSeleccionado = 0 },
                    text = { Text("Todas", fontSize = 14.sp) }
                )

                Tab(
                    selected = indiceSeleccionado == 1,
                    onClick = { indiceSeleccionado = 1 },
                    text = { Text("Administradas", fontSize = 14.sp) }
                )

                Tab(
                    selected = indiceSeleccionado == 2,
                    onClick = { indiceSeleccionado = 2 },
                    text = { Text("Pendientes", fontSize = 14.sp) }
                )
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // Lista de vacunaciones según el filtro seleccionado.
            if (vacunacionesFiltradas.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 100.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "💉",
                            fontSize = 48.sp
                        )

                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )

                        Text(
                            text = if (vacunaciones.isEmpty()) {
                                "No hay vacunaciones registradas"
                            } else {
                                "No hay vacunaciones en esta categoría"
                            },
                            color = Color(0xFF25212A),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = if (vacunaciones.isEmpty()) {
                                "Pulsa \"Añadir vacunación\" para añadir una"
                            } else {
                                "Prueba a seleccionar otra pestaña"
                            },
                            color = Color(0xFF5F5965),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    items(vacunacionesFiltradas) { vacunacion ->

                        TarjetaVacunacion(
                            vacunacion = vacunacion,

                            onEditar = {
                                vacunacionEditar = vacunacion
                                mostrarFormulario = true
                            },

                            onEliminar = {
                                vacunacionEliminar = vacunacion
                                mostrarDialogoEliminar = true
                            }
                        )
                    }

                    item {
                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )
                    }
                }
            }
        }
    }

    // Formulario para crear o editar una vacunación.
    if (mostrarFormulario) {

        FormularioVacunacion(
            vacunacion = vacunacionEditar,
            vacunas = vacunas,
            vacunacionesRegistradas = vacunaciones,

            onDismiss = {
                mostrarFormulario = false
            },

            onGuardar = { vacunacion ->

                if (vacunacionEditar == null) {

                    vacunacionViewModel.createVacunacion(
                        mascotaId = mascotaId,
                        vacunacion = vacunacion
                    )

                } else {

                    vacunacionEditar?.id?.let { id ->

                        vacunacionViewModel.updateVacunacion(
                            mascotaId = mascotaId,
                            id = id,
                            vacunacion = vacunacion
                        )
                    }
                }

                mostrarFormulario = false
            }
        )
    }

    // Diálogo para confirmar la eliminación
    if (mostrarDialogoEliminar && vacunacionEliminar != null) {
        Dialog(
            onDismissRequest = {
                mostrarDialogoEliminar = false
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.82f)
                    .widthIn(max = 360.dp),
                shape = RoundedCornerShape(30.dp),
                color = Color(0xFFF7F2FF),
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .background(
                                color = Color(0xFFFCE7EA),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Eliminar vacunación",
                            tint = Color(0xFFC65A70),
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "¿Eliminar vacunación?",
                        fontSize = 22.sp,
                        color = Color(0xFF6B50A0),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "¿Está seguro de que desea eliminar esta vacunación? Esta acción no se puede deshacer.",
                        fontSize = 16.sp,
                        color = Color(0xFF625D69),
                        textAlign = TextAlign.Center,
                        lineHeight = 21.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = {
                                mostrarDialogoEliminar = false
                            }
                        ) {
                            Text(
                                text = "Cancelar",
                                color = Color(0xFF6B50A0),
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        TextButton(
                            onClick = {
                                vacunacionEliminar?.id?.let { id ->
                                    vacunacionViewModel.deleteVacunacion(
                                        mascotaId = mascotaId,
                                        id = id
                                    )
                                }
                                mostrarDialogoEliminar = false
                                vacunacionEliminar = null
                            }
                        ) {
                            Text(
                                text = "Eliminar",
                                color = Color(0xFFD65368),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}


/**
 * Tarjeta que muestra la información de una vacunación.
 */
@Composable
private fun TarjetaVacunacion(
    vacunacion: Vacunacion,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {

    val colorEstado = if (vacunacion.vacunaAdministrada) {

        Color(0xFF2E7D32)

    } else {

        Color(0xFFEF6C00)
    }

    Card(

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(18.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            // Cabecera de la tarjeta.
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Icono de vacuna.
                Box(

                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(14.dp)
                        ),

                    contentAlignment = Alignment.Center

                ) {

                    Text(
                        text = "💉",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = vacunacion.vacuna.nombreVacuna,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    // Estado de la vacunación.
                    Box(

                        modifier = Modifier
                            .background(
                                color = colorEstado.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(50)
                            )
                            .padding(
                                horizontal = 10.dp,
                                vertical = 5.dp
                            )

                    ) {

                        Text(

                            text = if (vacunacion.vacunaAdministrada) {
                                "ADMINISTRADA"
                            } else {
                                "PENDIENTE"
                            },

                            color = colorEstado,

                            style = MaterialTheme.typography.labelMedium,

                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(
                    onClick = onEditar
                ) {

                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar" ,
                        tint = Purple40
                    )
                }

                IconButton(
                    onClick = onEliminar
                ) {

                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color(0xFFD96C78)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // Fecha prevista.
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Purple40
                )

                Spacer(
                    modifier = Modifier.width(10.dp)
                )

                Column {

                    Text(
                        text = "Fecha prevista",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = vacunacion.fechaPrevista,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Fecha de administración.
            if (
                vacunacion.vacunaAdministrada &&
                vacunacion.fechaAdministracion != null
            ) {

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = colorEstado
                    )

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    Column {

                        Text(
                            text = "Administrada el",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Text(
                            text = vacunacion.fechaAdministracion,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}


/**
 * Calcula la edad a partir de la fecha de nacimiento.
 */
private fun calcularEdad(
    fechaNacimiento: String
): String {

    return try {

        val fecha = try {

            LocalDate.parse(
                fechaNacimiento,
                DateTimeFormatter.ISO_LOCAL_DATE
            )

        } catch (e: Exception) {

            LocalDate.parse(
                fechaNacimiento,
                DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy"
                )
            )
        }

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


