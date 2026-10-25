package proyectoFCT.mascotasyvacunas.mascotas.ui.screen

import android.app.DatePickerDialog
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import proyectoFCT.mascotasyvacunas.mascotas.ui.model.Mascota
import proyectoFCT.mascotasyvacunas.mascotas.ui.viewmodel.MascotaViewModel
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaNuevaMascota(
    navController: NavController,
    mascotaId: Long? = null,
    mascotaViewModel: MascotaViewModel = hiltViewModel()
) {
    // Obtener el contexto de Android.
    val context = LocalContext.current

    // Obtener los estados de la pantalla.
    val mascotas by mascotaViewModel.mascotas.collectAsState()
    val showSuccessDialog by mascotaViewModel.showSuccessDialog.collectAsState()
    val subiendoFoto by mascotaViewModel.subiendoFoto.collectAsState()
    val mensaje by mascotaViewModel.message.collectAsState()

    // Obtener los datos de la mascota que se va a editar.
    val mascotaEditar = mascotas.find { it.id == mascotaId }

    // Guardar los valores de los campos del formulario.
    var nombre by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    // Guardar la fotografía seleccionada.
    var fotoSeleccionada by remember { mutableStateOf<Uri?>(null) }

    // Controlar los desplegables y los diálogos.
    var expandedGenero by remember { mutableStateOf(false) }
    var expandedEspecie by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    // Abrir la galería para seleccionar una fotografía.
    val selectorFoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        fotoSeleccionada = uri
    }

    // Definir las opciones de género.
    val generos = listOf("Macho", "Hembra")

    // Definir las opciones de especie.
    val especies = listOf(
        "Perro",
        "Gato",
        "Conejo",
        "Hurón",
        "Otro"
    )

    // Cargar los datos de la mascota cuando se está editando.
    LaunchedEffect(mascotaEditar?.id) {
        mascotaEditar?.let { mascota ->
            nombre = mascota.nombre
            genero = mascota.genero
            especie = mascota.especie
            raza = mascota.raza
            fechaNacimiento = mascota.fechaNacimiento
            observaciones = mascota.observaciones ?: ""
        }
    }

    // Cargar la fotografía seleccionada para mostrar su vista previa.
    val bitmapSeleccionado = remember(fotoSeleccionada) {
        fotoSeleccionada?.let { uri ->
            try {
                context.contentResolver.openInputStream(uri)?.use { input ->
                    BitmapFactory.decodeStream(input)
                }
            } catch (_: Exception) {
                null
            }
        }
    }

    // Mostrar una ilustración predeterminada según la especie.
    val imagenPredeterminada = when (especie.lowercase()) {
        "" -> R.drawable.mascotas
        "perro" -> R.drawable.perro
        "gato" -> R.drawable.gato
        "conejo" -> R.drawable.conejo
        "hurón", "huron" -> R.drawable.huron
        else -> R.drawable.otramascota
    }

    // Preparar el selector de fecha.
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fechaNacimiento = "%02d/%02d/%04d".format(
                dayOfMonth,
                month + 1,
                year
            )
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Impedir que se seleccione una fecha futura.
    datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF0E9FA),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (mascotaId == null) {
                            "Nueva mascota"
                        } else {
                            "Editar mascota"
                        },
                        color = Purple40,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Purple40
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF0E9FA),
                    titleContentColor = Color(0xFF25212A),
                    navigationIconContentColor = Color(0xFF25212A)
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Mostrar la fotografía seleccionada o la ilustración predeterminada.
            if (fotoSeleccionada != null && bitmapSeleccionado != null) {
                Image(
                    bitmap = bitmapSeleccionado.asImageBitmap(),
                    contentDescription = "Fotografía seleccionada",
                    modifier = Modifier
                        .size(170.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                // Quitar la fotografía seleccionada.
                TextButton(
                    onClick = { fotoSeleccionada = null }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null
                    )
                    Text("Quitar foto")
                }
            } else {
                Image(
                    painter = painterResource(id = imagenPredeterminada),
                    contentDescription = "Imagen predeterminada de la mascota",
                    modifier = Modifier.size(170.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Seleccionar una fotografía de la galería.
            Button(
                onClick = { selectorFoto.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = null
                )
                Spacer(Modifier.size(8.dp))
                Text("Elegir foto de la galería")
            }

            Spacer(Modifier.height(12.dp))

            // Campo Nombre.
            EtiquetaCampo("Nombre")
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                placeholder = { Text("Nombre de la mascota") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = coloresCampos()
            )

            Spacer(Modifier.height(8.dp))

            // Campo Especie.
            EtiquetaCampo("Especie")
            ExposedDropdownMenuBox(
                expanded = expandedEspecie,
                onExpandedChange = {
                    expandedEspecie = !expandedEspecie
                }
            ) {
                OutlinedTextField(
                    value = especie,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Selecciona una especie") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandedEspecie
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(
                            type = MenuAnchorType.PrimaryNotEditable,
                            enabled = true
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = coloresCampos()
                )

                ExposedDropdownMenu(
                    expanded = expandedEspecie,
                    onDismissRequest = { expandedEspecie = false }
                ) {
                    especies.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                especie = opcion
                                expandedEspecie = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Campo Raza.
            EtiquetaCampo("Raza")
            OutlinedTextField(
                value = raza,
                onValueChange = { raza = it },
                placeholder = { Text("Raza (opcional)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = coloresCampos()
            )

            Spacer(Modifier.height(8.dp))

            // Campo Género.
            EtiquetaCampo("Género")
            ExposedDropdownMenuBox(
                expanded = expandedGenero,
                onExpandedChange = {
                    expandedGenero = !expandedGenero
                }
            ) {
                OutlinedTextField(
                    value = genero,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Selecciona el género") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expandedGenero
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(
                            type = MenuAnchorType.PrimaryNotEditable,
                            enabled = true
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = coloresCampos()
                )

                ExposedDropdownMenu(
                    expanded = expandedGenero,
                    onDismissRequest = { expandedGenero = false }
                ) {
                    generos.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                genero = opcion
                                expandedGenero = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Campo Fecha de nacimiento.
            EtiquetaCampo("Fecha de nacimiento")
            OutlinedTextField(
                value = fechaNacimiento,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("dd/mm/aaaa") },
                trailingIcon = {
                    IconButton(
                        onClick = { datePickerDialog.show() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Seleccionar fecha"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = coloresCampos()
            )

            Spacer(Modifier.height(8.dp))

            // Campo Observaciones.
            EtiquetaCampo("Observaciones")
            OutlinedTextField(
                value = observaciones,
                onValueChange = { observaciones = it },
                placeholder = { Text("Observaciones (opcional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp),
                maxLines = 5,
                shape = RoundedCornerShape(12.dp),
                colors = coloresCampos()
            )

            Spacer(Modifier.height(16.dp))

            // Botones Cancelar y Guardar.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    enabled = !subiendoFoto,
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Purple40
                    )
                ) {
                    Text(
                        text = "Cancelar",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Button(
                    onClick = {
                        if (
                            nombre.isNotBlank() &&
                            genero.isNotBlank() &&
                            especie.isNotBlank() &&
                            fechaNacimiento.isNotBlank()
                        ) {
                            val mascota = Mascota(
                                id = mascotaId,
                                nombre = nombre,
                                especie = especie,
                                genero = genero,
                                raza = raza,
                                fechaNacimiento = fechaNacimiento,
                                observaciones = observaciones.ifBlank { null },
                                vacunas = mascotaEditar?.vacunas ?: emptyList()
                            )

                            if (mascotaId == null) {
                                mascotaViewModel.createMascota(
                                    mascota = mascota,
                                    context = context,
                                    fotoUri = fotoSeleccionada
                                )
                            } else {
                                mascotaViewModel.updateMascota(
                                    id = mascotaId,
                                    mascota = mascota,
                                    context = context,
                                    fotoUri = fotoSeleccionada
                                )
                            }
                        } else {
                            showErrorDialog = true
                        }
                    },
                    enabled = !subiendoFoto,
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple40,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = when {
                            subiendoFoto -> "Subiendo fotografía..."
                            mascotaId == null -> "Guardar"
                            else -> "Guardar cambios"
                        },
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (mensaje != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = mensaje.orEmpty(),
                    color = Purple40,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.height(8.dp))
        }
    }

    // Mostrar el diálogo de éxito.
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                mascotaViewModel.hideSuccessDialog()
                navController.popBackStack()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Operación correcta",
                    tint = Color(0xFF4A9B69),
                    modifier = Modifier.size(42.dp)
                )
            },
            title = {
                Text(
                    text = if (mascotaId == null) {
                        "¡Mascota registrada!"
                    } else {
                        "¡Cambios guardados!"
                    },
                    color = Purple40,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = if (mascotaId == null) {
                        "La mascota se ha añadido correctamente a tu lista."
                    } else {
                        "Los datos de la mascota se han actualizado correctamente."
                    },
                    color = Color(0xFF5F5965),
                    textAlign = TextAlign.Center
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mascotaViewModel.hideSuccessDialog()
                        navController.popBackStack()
                    }
                ) {
                    Text(
                        "Aceptar",
                        color = Purple40,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            containerColor = Color(0xFFF8F3FF),
            shape = RoundedCornerShape(24.dp)
        )
    }

    // Mostrar el diálogo de error de validación.
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Advertencia") },
            text = {
                Text("Debe completar todos los campos obligatorios.")
            },
            confirmButton = {
                TextButton(
                    onClick = { showErrorDialog = false }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}

// Mostrar la etiqueta de cada campo del formulario.
@Composable
private fun EtiquetaCampo(texto: String) {
    Text(
        text = texto,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, bottom = 5.dp),
        color = Color(0xFF25212A),
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
    )
}

// Definir los colores de los campos del formulario.
@Composable
private fun coloresCampos() = OutlinedTextFieldDefaults.colors(
    unfocusedContainerColor = Color.White,
    focusedContainerColor = Color.White,
    unfocusedBorderColor = Color(0xFFD6D0D9),
    focusedBorderColor = Purple40
)
