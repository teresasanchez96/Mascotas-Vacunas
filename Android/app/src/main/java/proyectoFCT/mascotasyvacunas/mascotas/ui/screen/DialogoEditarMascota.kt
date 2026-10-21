package proyectoFCT.mascotasyvacunas.mascotas.ui.screen

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import proyectoFCT.mascotasyvacunas.mascotas.ui.model.Mascota
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogoEditarMascota(
    mascota: Mascota,
    onDismiss: () -> Unit,
    onGuardar: (Mascota) -> Unit
) {

    var nombre by remember {
        mutableStateOf(mascota.nombre)
    }

    var especie by remember {
        mutableStateOf(mascota.especie)
    }

    var genero by remember {
        mutableStateOf(mascota.genero)
    }

    var raza by remember {
        mutableStateOf(mascota.raza)
    }

    var fechaNacimiento by remember {
        mutableStateOf(mascota.fechaNacimiento)
    }

    var observaciones by remember {
        mutableStateOf(
            mascota.observaciones ?: ""
        )
    }

    var especieExpandida by remember {
        mutableStateOf(false)
    }

    var generoExpandido by remember {
        mutableStateOf(false)
    }

    val especies = listOf(
        "Perro",
        "Gato",
        "Conejo",
        "Hámster",
        "Tortuga",
        "Pájaro",
        "Hurón",
        "Otra"
    )

    val generos = listOf(
        "Macho",
        "Hembra"
    )

    val context = LocalContext.current

    val calendar = Calendar.getInstance()

    AlertDialog(

        onDismissRequest = onDismiss,

        title = {
            Text(
                text = "Modificar datos",
                color = Color(0xFF4A3A68)
            )
        },

        text = {

            Column {

                OutlinedTextField(
                    value = nombre,

                    onValueChange = {
                        nombre = it
                    },

                    label = {
                        Text("Nombre")
                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = especieExpandida,

                    onExpandedChange = {
                        especieExpandida =
                            !especieExpandida
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

                            ExposedDropdownMenuDefaults
                                .TrailingIcon(
                                    expanded =
                                    especieExpandida
                                )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(
                                type = MenuAnchorType.PrimaryNotEditable,
                                enabled = true
                            )
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

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                ExposedDropdownMenuBox(
                    expanded = generoExpandido,

                    onExpandedChange = {
                        generoExpandido =
                            !generoExpandido
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

                            ExposedDropdownMenuDefaults
                                .TrailingIcon(
                                    expanded =
                                    generoExpandido
                                )
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(
                                type = MenuAnchorType.PrimaryNotEditable,
                                enabled = true
                            )
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

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = raza,

                    onValueChange = {
                        raza = it
                    },

                    label = {
                        Text("Raza (opcional)")
                    },

                    modifier = Modifier.fillMaxWidth(),

                    singleLine = true
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = fechaNacimiento,

                    onValueChange = {},

                    readOnly = true,

                    label = {
                        Text("Fecha de nacimiento")
                    },

                    trailingIcon = {

                        IconButton(
                            onClick = {

                                val datePicker =
                                    DatePickerDialog(

                                        context,

                                        { _, year,
                                          month,
                                          dayOfMonth ->

                                            fechaNacimiento =
                                                String.format(
                                                    Locale.getDefault(),
                                                    "%02d/%02d/%04d",
                                                    dayOfMonth,
                                                    month + 1,
                                                    year
                                                )
                                        },

                                        calendar.get(
                                            Calendar.YEAR
                                        ),

                                        calendar.get(
                                            Calendar.MONTH
                                        ),

                                        calendar.get(
                                            Calendar.DAY_OF_MONTH
                                        )
                                    )

                                datePicker.datePicker.maxDate =
                                    System.currentTimeMillis()

                                datePicker.show()
                            }
                        ) {

                            Icon(
                                imageVector =
                                Icons.Default.DateRange,

                                contentDescription =
                                "Seleccionar fecha"
                            )
                        }
                    },

                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                OutlinedTextField(
                    value = observaciones,

                    onValueChange = {
                        observaciones = it
                    },

                    label = {
                        Text("Observaciones")
                    },

                    modifier = Modifier.fillMaxWidth(),

                    minLines = 2,

                    maxLines = 3
                )
            }
        },

        confirmButton = {

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

                               // vacunas = mascota.vacunas
                            )
                        )
                    }
                }
            ) {

                Text("Guardar")
            }
        },

        dismissButton = {

            TextButton(
                onClick = onDismiss
            ) {

                Text("Cancelar")
            }
        }
    )
}