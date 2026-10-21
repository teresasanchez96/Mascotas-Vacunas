package proyectoFCT.mascotasyvacunas.vacunas.ui.screen

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import proyectoFCT.mascotasyvacunas.R
import proyectoFCT.mascotasyvacunas.navigation.BarraNavegacion
import proyectoFCT.mascotasyvacunas.navigation.ListaMascotas
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40
import proyectoFCT.mascotasyvacunas.vacunas.ui.model.Vacuna
import proyectoFCT.mascotasyvacunas.vacunas.ui.viewmodel.VacunaViewModel

@Composable
fun PantallaListaVacunas(
    navController: NavController,
    vacunaViewModel: VacunaViewModel = hiltViewModel()
) {

    val vacunas by vacunaViewModel.vacunas.collectAsState()
    val showDialog by vacunaViewModel.showDialog.collectAsState()
    val showSuccessDialog by vacunaViewModel.showSuccessDialog.collectAsState()
    val message by vacunaViewModel.message.collectAsState()

    var showNewVaccineDialog by remember {
        mutableStateOf(false)
    }

    var nombreVacuna by remember {
        mutableStateOf("")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF0E9FA),

        bottomBar = {
            BarraNavegacion(
                pantallaActual = "vacunas",

                onMascotasClick = {
                    navController.navigate(ListaMascotas)
                },

                onVacunasClick = {
                    // Ya estamos en Vacunas.
                },

                onPerfilClick = {
                    // La navegación al perfil se añadirá más adelante.
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

            // Cabecera.
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Vacunas registradas",
                    color = Purple40,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                FloatingActionButton(
                    onClick = {
                        nombreVacuna = ""
                        showNewVaccineDialog = true
                    },
                    modifier = Modifier.size(52.dp),
                    containerColor = Purple40,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {

                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Nueva vacuna"
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            // Lista de vacunas registradas.
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(vacunas) { vacuna ->

                    VacunaItem(
                        vacuna = vacuna,
                        vacunaViewModel = vacunaViewModel
                    )
                }
            }
        }
    }

    // Diálogo para registrar una nueva vacuna.
    if (showNewVaccineDialog) {

        Dialog(
            onDismissRequest = {
                showNewVaccineDialog = false
            }
        ) {

            Column(
                modifier = Modifier
                    .widthIn(max = 360.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color(0xFFF8F3FF)
                    )
            ) {

                // Imagen de cabecera del formulario de nueva vacuna.
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(165.dp)
                ) {

                    Image(
                        painter = painterResource(
                            id = R.drawable.imagencabecera_vacuna
                        ),
                        contentDescription = "Imagen de cabecera del formulario de nueva vacuna",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Botón para cerrar el diálogo.
                    IconButton(
                        onClick = {
                            showNewVaccineDialog = false
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .size(38.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = Color(0xFF51436F)
                        )
                    }
                }

                // Contenido del diálogo.
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 22.dp,
                            vertical = 20.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Título.
                    Text(
                        text = "Nueva vacuna",
                        color = Purple40,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    // Texto explicativo.
                    Text(
                        text = "Añade una vacuna a tu listado para utilizarla en las vacunaciones de tus mascotas.",
                        color = Color(0xFF6F6680),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 21.sp
                    )

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    // Nombre de la vacuna.
                    OutlinedTextField(
                        value = nombreVacuna,
                        onValueChange = {
                            nombreVacuna = it
                        },
                        label = {
                            Text("Nombre de la vacuna")
                        },
                        placeholder = {
                            Text("Ej. Rabia")
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    // Botones de cancelar y registrar.
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Button(
                            onClick = {
                                showNewVaccineDialog = false
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEDE1FF),
                                contentColor = Purple40
                            )
                        ) {

                            Text("Cancelar")
                        }

                        Button(
                            onClick = {

                                if (nombreVacuna.isNotBlank()) {

                                    vacunaViewModel.createVacuna(
                                        Vacuna(
                                            id = null,
                                            nombreVacuna = nombreVacuna.trim()
                                        )
                                    )

                                    showNewVaccineDialog = false
                                }
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Purple40,
                                contentColor = Color.White
                            )
                        ) {

                            Text("Registrar")
                        }
                    }
                }
            }
        }
    }

    // Diálogo de confirmación para eliminar una vacuna.
    DialogoEliminarVacuna(
        show = showDialog,

        onDismiss = {
            vacunaViewModel.changeShowDialog()
        },

        onConfirm = {
            vacunaViewModel.deleteVacuna()
        }
    )

    // Mensaje después de registrar correctamente una vacuna.
    if (showSuccessDialog) {

        AlertDialog(
            onDismissRequest = {
                vacunaViewModel.hideSuccessDialog()
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
                    text = "¡Vacuna registrada!",
                    color = Purple40,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },

            text = {
                Text(
                    text = "La vacuna se ha añadido correctamente a tu listado.",
                    color = Color(0xFF5F5965),
                    textAlign = TextAlign.Center
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        vacunaViewModel.hideSuccessDialog()
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

    // Mensaje cuando ocurre un error.
    if (message != null) {

        AlertDialog(
            onDismissRequest = {
                vacunaViewModel.clearMessage()
            },

            title = {
                Text(
                    text = "No se puede eliminar",
                    color = Purple40,
                    fontWeight = FontWeight.Bold
                )
            },

            text = {
                Text(
                    text = message ?: ""
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        vacunaViewModel.clearMessage()
                    }
                ) {

                    Text(
                        text = "Aceptar",
                        color = Purple40
                    )
                }
            }
        )
    }
}

@Composable
fun VacunaItem(
    vacuna: Vacuna,
    vacunaViewModel: VacunaViewModel
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),

            verticalAlignment = Alignment.CenterVertically
        ) {

            // Imagen de la vacuna.
            Image(
                painter = painterResource(
                    id = R.drawable.vacuna
                ),

                contentDescription = "Vacuna",

                contentScale = ContentScale.Fit,

                modifier = Modifier.size(58.dp)
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            // Nombre de la vacuna.
            Text(
                text = vacuna.nombreVacuna,

                modifier = Modifier.weight(1f),

                color = Color(0xFF25212A),

                fontSize = 18.sp,

                fontWeight = FontWeight.SemiBold
            )

            // Eliminar vacuna.
            IconButton(
                onClick = {
                    vacuna.id?.let {
                        vacunaViewModel.setIdVacuna(it)
                        vacunaViewModel.changeShowDialog()
                    }
                }
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,

                    contentDescription = "Eliminar vacuna",

                    tint = Color(0xFFC45C6C)
                )
            }
        }
    }
}

@Composable
fun DialogoEliminarVacuna(
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
                        contentDescription = "Eliminar vacuna",
                        tint = Color(0xFFC45C6C),
                        modifier = Modifier.size(30.dp)
                    )
                }
            },

            title = {
                Text(
                    text = "¿Eliminar vacuna?",
                    color = Purple40,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            },

            text = {
                Text(
                    text = "¿Está seguro de que desea eliminar esta vacuna? Esta acción no se puede deshacer.",
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