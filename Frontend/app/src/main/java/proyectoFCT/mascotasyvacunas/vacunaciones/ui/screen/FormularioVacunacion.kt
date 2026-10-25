package proyectoFCT.mascotasyvacunas.vacunaciones.ui.screen

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import proyectoFCT.mascotasyvacunas.R
import proyectoFCT.mascotasyvacunas.vacunaciones.ui.model.Vacunacion
import proyectoFCT.mascotasyvacunas.vacunas.ui.model.Vacuna
import java.util.Calendar
import java.util.Locale

// Colores del formulario.
private val MoradoVacunacion = Color(0xFF6B50A0)
private val FondoVacunacion = Color(0xFFF7F2FF)
private val LavandaVacunacion = Color(0xFFE9DDFB)
private val BordeVacunacion = Color(0xFFD8CDE5)
private val TextoSecundarioVacunacion = Color(0xFF756982)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioVacunacion(
    vacunacion: Vacunacion?,
    vacunas: List<Vacuna>,
    vacunacionesRegistradas: List<Vacunacion>,
    onDismiss: () -> Unit,
    onGuardar: (Vacunacion) -> Unit
) {
    val context = LocalContext.current

    // En edición se conserva la vacuna de esta vacunación.
    // Al crear, solo se ofrecen vacunas que aún no están registradas.
    val vacunasDisponibles = vacunas.filter { vacuna ->
        vacunacion?.vacuna?.id == vacuna.id ||
                vacunacionesRegistradas.none { registrada ->
                    registrada.vacuna.id == vacuna.id
                }
    }

    var vacunaSeleccionada by remember(vacunacion) {
        mutableStateOf(vacunacion?.vacuna)
    }

    var fechaPrevista by remember(vacunacion) {
        mutableStateOf(vacunacion?.fechaPrevista ?: "")
    }

    var vacunaAdministrada by remember(vacunacion) {
        mutableStateOf(vacunacion?.vacunaAdministrada ?: false)
    }

    var fechaAdministracion by remember(vacunacion) {
        mutableStateOf(vacunacion?.fechaAdministracion ?: "")
    }

    var mostrarMenuVacunas by remember {
        mutableStateOf(false)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .widthIn(max = 480.dp)
                .heightIn(max = 760.dp),
            shape = RoundedCornerShape(30.dp),
            color = FondoVacunacion,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 22.dp
                    )
            ) {

                // Cabecera: ilustración, título, subtítulo
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 110.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(96.dp),
                        shape = CircleShape,
                        color = LavandaVacunacion
                    ) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.vacuna
                            ),
                            contentDescription = "Ilustración de una vacuna",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = if (vacunacion == null) {
                                "Nueva vacunación"
                            } else {
                                "Editar vacunación"
                            },
                            color = MoradoVacunacion,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 25.sp
                        )

                        Text(
                            text = "Organiza las vacunas de tu mascota",
                            color = TextoSecundarioVacunacion,
                            fontSize = 14.sp,
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 430.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    // Selector de vacuna.
                    Column(
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "Vacuna",
                            color = Color(0xFF382C49),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        if (vacunasDisponibles.isEmpty() && vacunacion == null) {
                            Text(
                                text = "Esta mascota ya tiene todas las vacunas registradas.",
                                color = TextoSecundarioVacunacion,
                                fontSize = 13.sp
                            )
                        }

                        ExposedDropdownMenuBox(
                            expanded = mostrarMenuVacunas,
                            onExpandedChange = {
                                mostrarMenuVacunas = !mostrarMenuVacunas
                            }
                        ) {
                            OutlinedTextField(
                                value = vacunaSeleccionada?.nombreVacuna ?: "",
                                onValueChange = {},
                                readOnly = true,
                                singleLine = true,
                                placeholder = {
                                    Text("Selecciona una vacuna")
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = mostrarMenuVacunas
                                    )
                                },
                                shape = RoundedCornerShape(14.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MoradoVacunacion,
                                    unfocusedBorderColor = BordeVacunacion,
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(
                                        type = MenuAnchorType.PrimaryNotEditable,
                                        enabled = true
                                    )
                            )

                            ExposedDropdownMenu(
                                expanded = mostrarMenuVacunas,
                                onDismissRequest = {
                                    mostrarMenuVacunas = false
                                }
                            ) {
                                vacunasDisponibles.forEach { vacuna ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(vacuna.nombreVacuna)
                                        },
                                        onClick = {
                                            vacunaSeleccionada = vacuna
                                            mostrarMenuVacunas = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Fecha prevista.
                    CampoFechaVacunacion(
                        titulo = "Fecha prevista",
                        fecha = fechaPrevista,
                        seleccionHabilitada = true,
                        onClick = {
                            mostrarSelectorFecha(
                                context = context,
                                fechaActual = fechaPrevista
                            ) {
                                fechaPrevista = it
                            }
                        }
                    )

                    // Tarjeta de estado.
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        color = LavandaVacunacion
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 12.dp,
                                    vertical = 12.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Surface(
                                modifier = Modifier.size(48.dp),
                                shape = CircleShape,
                                color = Color.White
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "💉",
                                        fontSize = 24.sp
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Vacuna administrada",
                                    color = Color(0xFF382C49),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    text = if (vacunaAdministrada) {
                                        "Ya se ha administrado"
                                    } else {
                                        "Todavía está pendiente"
                                    },
                                    color = if (vacunaAdministrada) {
                                        Color(0xFF2E7D32) // Verde: administrada
                                    } else {
                                        Color(0xFFB26A00) // Naranja: pendiente
                                    },
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Switch(
                                checked = vacunaAdministrada,
                                onCheckedChange = {
                                    vacunaAdministrada = it

                                    if (!it) {
                                        fechaAdministracion = ""
                                    }
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = MoradoVacunacion,
                                    checkedBorderColor = MoradoVacunacion
                                )
                            )
                        }
                    }

                    // Fecha de administración.
                    CampoFechaVacunacion(
                        titulo = "Fecha de administración",
                        fecha = fechaAdministracion,
                        seleccionHabilitada = vacunaAdministrada,
                        onClick = {
                            mostrarSelectorFecha(
                                context = context,
                                fechaActual = fechaAdministracion
                            ) {
                                fechaAdministracion = it
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = LavandaVacunacion,
                            contentColor = MoradoVacunacion
                        )
                    ) {
                        Text(
                            text = "Cancelar",
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Button(
                        onClick = {
                            val vacuna = vacunaSeleccionada

                            if (
                                vacuna != null &&
                                fechaPrevista.isNotBlank()
                            ) {
                                onGuardar(
                                    Vacunacion(
                                        id = vacunacion?.id,
                                        fechaPrevista = fechaPrevista,
                                        vacunaAdministrada = vacunaAdministrada,
                                        fechaAdministracion =
                                        if (vacunaAdministrada) {
                                            fechaAdministracion.ifBlank {
                                                null
                                            }
                                        } else {
                                            null
                                        },
                                        vacuna = vacuna
                                    )
                                )
                            }
                        },
                        enabled = vacunaSeleccionada != null &&
                                fechaPrevista.isNotBlank(),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MoradoVacunacion,
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFFC9BCD9),
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Guardar",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CampoFechaVacunacion(
    titulo: String,
    fecha: String,
    seleccionHabilitada: Boolean,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = titulo,
            color = if (seleccionHabilitada) {
                Color(0xFF382C49)
            } else {
                TextoSecundarioVacunacion
            },
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )

        OutlinedTextField(
            value = fecha,
            onValueChange = {},
            readOnly = true,
            enabled = seleccionHabilitada,
            singleLine = true,
            placeholder = {
                Text("Selecciona la fecha")
            },
            trailingIcon = {
                IconButton(
                    onClick = onClick,
                    enabled = seleccionHabilitada
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Seleccionar fecha",
                        tint = if (seleccionHabilitada) {
                            MoradoVacunacion
                        } else {
                            TextoSecundarioVacunacion
                        }
                    )
                }
            },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MoradoVacunacion,
                unfocusedBorderColor = BordeVacunacion,
                disabledBorderColor = BordeVacunacion,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color(0xFFF0EBF5)
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun mostrarSelectorFecha(
    context: Context,
    fechaActual: String,
    onFechaSeleccionada: (String) -> Unit
) {
    val calendario = Calendar.getInstance()
    val partes = fechaActual.split("/")

    if (partes.size == 3) {
        val dia = partes[0].toIntOrNull()
        val mes = partes[1].toIntOrNull()
        val anio = partes[2].toIntOrNull()

        if (dia != null && mes != null && anio != null) {
            calendario.set(anio, mes - 1, dia)
        }
    }

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val fecha = String.format(
                Locale.getDefault(),
                "%02d/%02d/%04d",
                dayOfMonth,
                month + 1,
                year
            )

            onFechaSeleccionada(fecha)
        },
        calendario.get(Calendar.YEAR),
        calendario.get(Calendar.MONTH),
        calendario.get(Calendar.DAY_OF_MONTH)
    ).show()
}
