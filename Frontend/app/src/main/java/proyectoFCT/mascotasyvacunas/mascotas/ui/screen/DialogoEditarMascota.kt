package proyectoFCT.mascotasyvacunas.mascotas.ui.screen

import android.app.DatePickerDialog
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import proyectoFCT.mascotasyvacunas.mascotas.ui.model.Mascota
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogoEditarMascota(
    mascota: Mascota,
    onDismiss: () -> Unit,
    onGuardar: (Mascota, Uri?) -> Unit
) {

    var nombre by remember(mascota.id) {
        mutableStateOf(mascota.nombre)
    }

    var especie by remember(mascota.id) {
        mutableStateOf(mascota.especie)
    }

    var genero by remember(mascota.id) {
        mutableStateOf(mascota.genero)
    }

    var raza by remember(mascota.id) {
        mutableStateOf(mascota.raza)
    }

    var fechaNacimiento by remember(mascota.id) {
        mutableStateOf(mascota.fechaNacimiento)
    }

    var observaciones by remember(mascota.id) {
        mutableStateOf(mascota.observaciones ?: "")
    }

    var especieExpandida by remember {
        mutableStateOf(false)
    }

    var generoExpandido by remember {
        mutableStateOf(false)
    }

    var mostrarError by remember {
        mutableStateOf(false)
    }

    val especies = listOf(
        "Perro",
        "Gato",
        "Conejo",
        "Hurón",
        "Otra"
    )

    val generos = listOf(
        "Macho",
        "Hembra"
    )

    val context = LocalContext.current
    var fotoSeleccionada by remember(mascota.id) { mutableStateOf<Uri?>(null) }

    val selectorFoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        fotoSeleccionada = uri
    }

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

    val calendar = Calendar.getInstance()

    val coloresCampos = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = Color.White,
        focusedContainerColor = Color.White,
        unfocusedBorderColor = Color(0xFFD6D0D9),
        focusedBorderColor = Purple40,
        focusedLabelColor = Purple40,
        cursorColor = Purple40
    )

    Dialog(
        onDismissRequest = onDismiss
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 720.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF8F3FF)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {

                // Cabecera del diálogo.
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .background(
                                    color = Color(0xFFE8DDF8),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = Purple40,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.padding(start = 12.dp)
                        ) {

                            Text(
                                text = "Editar mascota",
                                color = Purple40,
                                fontSize = 21.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "Modifica sus datos",
                                color = Color(0xFF6F6680),
                                fontSize = 13.sp
                            )
                        }
                    }

                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // Campos del formulario.
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 430.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Fotografía de la mascota.
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (bitmapSeleccionado != null) {
                            Image(
                                bitmap = bitmapSeleccionado.asImageBitmap(),
                                contentDescription = "Nueva fotografía de ${mascota.nombre}",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        } else {
                            Text(
                                text = "Puedes cambiar la fotografía de esta mascota",
                                color = Color(0xFF6F6680),
                                fontSize = 13.sp
                            )
                        }

                        Button(
                            onClick = { selectorFoto.launch("image/*") },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEDE1FF),
                                contentColor = Purple40
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddAPhoto,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (fotoSeleccionada == null) {
                                    "Cambiar fotografía"
                                } else {
                                    "Elegir otra fotografía"
                                },
                                fontSize = 13.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Nombre.
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = {
                            nombre = it
                            mostrarError = false
                        },
                        label = {
                            Text("Nombre")
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = coloresCampos
                    )

                    // Especie.
                    ExposedDropdownMenuBox(
                        expanded = especieExpandida,
                        onExpandedChange = {
                            especieExpandida = !especieExpandida
                        }
                    ) {

                        OutlinedTextField(
                            value = especie,
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text("Especie")
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = especieExpandida
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(
                                    type = MenuAnchorType.PrimaryNotEditable,
                                    enabled = true
                                ),
                            shape = RoundedCornerShape(12.dp),
                            colors = coloresCampos
                        )

                        ExposedDropdownMenu(
                            expanded = especieExpandida,
                            onDismissRequest = {
                                especieExpandida = false
                            }
                        ) {

                            especies.forEach { opcion ->

                                DropdownMenuItem(
                                    text = {
                                        Text(opcion)
                                    },
                                    onClick = {
                                        especie = opcion
                                        especieExpandida = false
                                    }
                                )
                            }
                        }
                    }

                    // Género.
                    ExposedDropdownMenuBox(
                        expanded = generoExpandido,
                        onExpandedChange = {
                            generoExpandido = !generoExpandido
                        }
                    ) {

                        OutlinedTextField(
                            value = genero,
                            onValueChange = {},
                            readOnly = true,
                            label = {
                                Text("Género")
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = generoExpandido
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(
                                    type = MenuAnchorType.PrimaryNotEditable,
                                    enabled = true
                                ),
                            shape = RoundedCornerShape(12.dp),
                            colors = coloresCampos
                        )

                        ExposedDropdownMenu(
                            expanded = generoExpandido,
                            onDismissRequest = {
                                generoExpandido = false
                            }
                        ) {

                            generos.forEach { opcion ->

                                DropdownMenuItem(
                                    text = {
                                        Text(opcion)
                                    },
                                    onClick = {
                                        genero = opcion
                                        generoExpandido = false
                                    }
                                )
                            }
                        }
                    }

                    // Raza.
                    OutlinedTextField(
                        value = raza,
                        onValueChange = {
                            raza = it
                        },
                        label = {
                            Text("Raza (opcional)")
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = coloresCampos
                    )

                    // Fecha de nacimiento.
                    OutlinedTextField(
                        value = fechaNacimiento,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Fecha de nacimiento")
                        },
                        placeholder = {
                            Text("dd/MM/aaaa")
                        },
                        trailingIcon = {

                            IconButton(
                                onClick = {

                                    val datePicker = DatePickerDialog(
                                        context,
                                        { _, year, month, dayOfMonth ->

                                            fechaNacimiento = String.format(
                                                Locale.getDefault(),
                                                "%02d/%02d/%04d",
                                                dayOfMonth,
                                                month + 1,
                                                year
                                            )

                                            mostrarError = false
                                        },
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH)
                                    )

                                    datePicker.datePicker.maxDate =
                                        System.currentTimeMillis()

                                    datePicker.show()
                                }
                            ) {

                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Seleccionar fecha",
                                    tint = Purple40
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = coloresCampos
                    )

                    // Observaciones.
                    OutlinedTextField(
                        value = observaciones,
                        onValueChange = {
                            observaciones = it
                        },
                        label = {
                            Text("Observaciones (opcional)")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 3,
                        shape = RoundedCornerShape(12.dp),
                        colors = coloresCampos
                    )

                    // Mensaje de validación.
                    if (mostrarError) {

                        Text(
                            text = "Completa el nombre, la especie, el género y la fecha de nacimiento.",
                            color = Color(0xFFC45C6C),
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                // Botones.
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Botón cancelar.
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        contentPadding = PaddingValues(
                            horizontal = 6.dp,
                            vertical = 0.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEDE1FF),
                            contentColor = Purple40
                        )
                    ) {
                        Text(
                            text = "Cancelar",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1
                        )
                    }

                    // Botón guardar.
                    Button(
                        onClick = {
                            if (
                                nombre.isNotBlank() &&
                                especie.isNotBlank() &&
                                genero.isNotBlank() &&
                                fechaNacimiento.isNotBlank()
                            ) {
                                onGuardar(
                                    Mascota(
                                        id = mascota.id,
                                        nombre = nombre.trim(),
                                        especie = especie,
                                        genero = genero,
                                        raza = raza.trim(),
                                        fechaNacimiento = fechaNacimiento,
                                        observaciones = observaciones.ifBlank {
                                            null
                                        },
                                        vacunas = mascota.vacunas
                                    ),
                                    fotoSeleccionada
                                )
                            } else {
                                mostrarError = true
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        contentPadding = PaddingValues(
                            horizontal = 6.dp,
                            vertical = 0.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Purple40,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Guardar cambios",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            softWrap = false
                        )
                    }
                }
            }
        }
    }
}
