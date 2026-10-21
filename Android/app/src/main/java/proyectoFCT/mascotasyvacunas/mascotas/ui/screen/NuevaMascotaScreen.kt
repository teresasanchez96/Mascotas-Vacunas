package proyectoFCT.mascotasyvacunas.mascotas.ui.screen

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.DateRange
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
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaNuevaMascota(
    navController: NavController,
    mascotaId: Long? = null,
    mascotaViewModel: MascotaViewModel = hiltViewModel()
) {

    val mascotas by mascotaViewModel.mascotas.collectAsState()
    val showSuccessDialog by mascotaViewModel.showSuccessDialog.collectAsState()

    val mascotaEditar = mascotas.find {
        it.id == mascotaId
    }

    var nombre by remember {
        mutableStateOf("")
    }

    var genero by remember {
        mutableStateOf("")
    }

    var especie by remember {
        mutableStateOf("")
    }

    var raza by remember {
        mutableStateOf("")
    }

    var fechaNacimiento by remember {
        mutableStateOf("")
    }

    var observaciones by remember {
        mutableStateOf("")
    }

    var expandedGenero by remember {
        mutableStateOf(false)
    }

    var expandedEspecie by remember {
        mutableStateOf(false)
    }

    var showErrorDialog by remember {
        mutableStateOf(false)
    }

    val generos = listOf(
        "Macho",
        "Hembra"
    )

    val especies = listOf(
        "Perro",
        "Gato",
        "Conejo",
        "Pájaro",
        "Hámster",
        "Tortuga",
        "Hurón",
        "Otro"
    )

    /*
     * Cargamos los datos de la mascota cuando estamos editando.
     */
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

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        navController.context,

        { _, year, month, dayOfMonth ->

            fechaNacimiento =
                "%02d/%02d/%04d".format(
                    dayOfMonth,
                    month + 1,
                    year
                )
        },

        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.datePicker.maxDate =
        System.currentTimeMillis()

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
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector =
                            Icons.AutoMirrored.Filled.ArrowBack,

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
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    horizontal = 20.dp,
                    vertical = 4.dp
                ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Imagen
            Image(
                painter = painterResource(
                    id = R.drawable.mascotas
                ),

                contentDescription = "Mascotas",

                modifier = Modifier
                    .height(150.dp),

                contentScale = ContentScale.Fit
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            // Campo Nombre
            EtiquetaCampo(
                texto = "Nombre"
            )

            OutlinedTextField(
                value = nombre,

                onValueChange = {
                    nombre = it
                },

                placeholder = {
                    Text("Nombre de la mascota")
                },

                singleLine = true,

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(12.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFD6D0D9),
                    focusedBorderColor = Purple40
                )
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Campo Especie
            EtiquetaCampo(
                texto = "Especie"
            )

            ExposedDropdownMenuBox(
                expanded = expandedEspecie,

                onExpandedChange = {
                    expandedEspecie =
                        !expandedEspecie
                }
            ) {

                OutlinedTextField(
                    value = especie,

                    onValueChange = {},

                    readOnly = true,

                    placeholder = {
                        Text("Selecciona una especie")
                    },

                    trailingIcon = {

                        ExposedDropdownMenuDefaults
                            .TrailingIcon(
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

                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedBorderColor = Color(0xFFD6D0D9),
                        focusedBorderColor = Purple40
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandedEspecie,

                    onDismissRequest = {
                        expandedEspecie = false
                    }
                ) {

                    especies.forEach { opcion ->

                        DropdownMenuItem(
                            text = {
                                Text(opcion)
                            },

                            onClick = {

                                especie = opcion
                                expandedEspecie = false
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Campo Raza
            EtiquetaCampo(
                texto = "Raza"
            )

            OutlinedTextField(
                value = raza,

                onValueChange = {
                    raza = it
                },

                placeholder = {
                    Text("Raza (opcional)")
                },

                singleLine = true,

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(12.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFD6D0D9),
                    focusedBorderColor = Purple40
                )
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Campo Género
            EtiquetaCampo(
                texto = "Género"
            )

            ExposedDropdownMenuBox(
                expanded = expandedGenero,

                onExpandedChange = {
                    expandedGenero =
                        !expandedGenero
                }
            ) {

                OutlinedTextField(
                    value = genero,

                    onValueChange = {},

                    readOnly = true,

                    placeholder = {
                        Text("Selecciona el género")
                    },

                    trailingIcon = {

                        ExposedDropdownMenuDefaults
                            .TrailingIcon(
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

                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        unfocusedBorderColor = Color(0xFFD6D0D9),
                        focusedBorderColor = Purple40
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandedGenero,

                    onDismissRequest = {
                        expandedGenero = false
                    }
                ) {

                    generos.forEach { opcion ->

                        DropdownMenuItem(
                            text = {
                                Text(opcion)
                            },

                            onClick = {

                                genero = opcion
                                expandedGenero = false
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Campo Fecha de nacimiento
            EtiquetaCampo(
                texto = "Fecha de nacimiento"
            )

            OutlinedTextField(
                value = fechaNacimiento,

                onValueChange = {},

                readOnly = true,

                placeholder = {
                    Text("dd/mm/aaaa")
                },

                trailingIcon = {

                    IconButton(
                        onClick = {
                            datePickerDialog.show()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Seleccionar fecha"
                        )
                    }
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(12.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFD6D0D9),
                    focusedBorderColor = Purple40
                )
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // Campo Observaciones
            EtiquetaCampo(
                texto = "Observaciones"
            )

            OutlinedTextField(
                value = observaciones,

                onValueChange = {
                    observaciones = it
                },

                placeholder = {
                    Text("Observaciones (opcional)")
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp),

                maxLines = 5,

                shape = RoundedCornerShape(12.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color(0xFFD6D0D9),
                    focusedBorderColor = Purple40
                )
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Botón guardar mascota
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

                            fechaNacimiento =
                            fechaNacimiento,

                            observaciones =
                            observaciones.ifBlank {
                                null
                            },

                            vacunas =
                            mascotaEditar?.vacunas
                                ?: emptyList()
                        )

                        if (mascotaId == null) {

                            mascotaViewModel
                                .createMascota(
                                    mascota
                                )

                        } else {

                            mascotaViewModel
                                .updateMascota(
                                    id = mascotaId,
                                    mascota = mascota
                                )
                        }

                    } else {

                        showErrorDialog = true
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                shape = RoundedCornerShape(28.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = if (mascotaId == null) {
                        "Guardar"
                    } else {
                        "Guardar cambios"
                    },

                    fontSize = 16.sp,

                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(4.dp)
            )
        }
    }

    // Diálogo de éxito al registrar una mascota.
    if (showSuccessDialog) {

        AlertDialog(
            onDismissRequest = {
                mascotaViewModel.hideSuccessDialog()
                navController.popBackStack()
            },

            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Registro correcto",
                    tint = Color(0xFF4A9B69),
                    modifier = Modifier.size(42.dp)
                )
            },

            title = {
                Text(
                    text = "¡Mascota registrada!",
                    color = Purple40,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },

            text = {
                Text(
                    text = "La mascota se ha añadido correctamente a tu lista.",
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
                        text = "Aceptar",
                        color = Purple40,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },

            containerColor = Color(0xFFF8F3FF),
            shape = RoundedCornerShape(24.dp)
        )
    }

    // Diálogo de error
    if (showErrorDialog) {

        AlertDialog(

            onDismissRequest = {
                showErrorDialog = false
            },

            title = {
                Text("Advertencia")
            },

            text = {
                Text(
                    "Debe completar todos los campos obligatorios."
                )
            },

            confirmButton = {

                TextButton(
                    onClick = {
                        showErrorDialog = false
                    }
                ) {

                    Text("Aceptar")
                }
            }
        )
    }
}

@Composable
private fun EtiquetaCampo(
    texto: String
) {

    Text(
        text = texto,

        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 4.dp,
                bottom = 5.dp
            ),

        color = Color(0xFF25212A),

        fontSize = 15.sp,

        fontWeight = FontWeight.Bold
    )
}