package proyectoFCT.mascotasyvacunas.authentication.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import proyectoFCT.mascotasyvacunas.authentication.ui.viewmodel.CuentaViewModel
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
    perfilViewModel: PerfilViewModel = hiltViewModel(),
    cuentaViewModel: CuentaViewModel = hiltViewModel()
) {

    val sesionCerrada by perfilViewModel.sesionCerrada.collectAsState()

    val usuario by cuentaViewModel.usuario.collectAsState()
    val cargandoCuenta by cuentaViewModel.cargando.collectAsState()
    val errorCuenta by cuentaViewModel.error.collectAsState()
    val cambiandoPassword by cuentaViewModel.cambiandoPassword.collectAsState()
    val mensajeExitoPassword by cuentaViewModel.mensajeExito.collectAsState()

    var mostrarInformacion by remember { mutableStateOf(false) }
    var mostrarMiCuenta by remember { mutableStateOf(false) }
    var mostrarCambiarPassword by remember { mutableStateOf(false) }

    var passwordActual by remember { mutableStateOf("") }
    var nuevaPassword by remember { mutableStateOf("") }
    var confirmarNuevaPassword by remember { mutableStateOf("") }

    var mostrarPasswordActual by remember { mutableStateOf(false) }
    var mostrarNuevaPassword by remember { mutableStateOf(false) }
    var mostrarConfirmarPassword by remember { mutableStateOf(false) }


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

    LaunchedEffect(mostrarMiCuenta) {
        if (mostrarMiCuenta) {
            cuentaViewModel.cargarMiCuenta()
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

            // Mi cuenta
            OpcionPerfil(
                icon = Icons.Default.Person,
                texto = "Mi cuenta",
                descripcion = "Consultar los datos de tu cuenta",
                onClick = { mostrarMiCuenta = true }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Cambiar contraseña
            OpcionPerfil(
                icon = Icons.Default.Lock,
                texto = "Cambiar contraseña",
                descripcion = "Actualizar tu contraseña de forma segura",
                onClick = {
                    passwordActual = ""
                    nuevaPassword = ""
                    confirmarNuevaPassword = ""

                    mostrarPasswordActual = false
                    mostrarNuevaPassword = false
                    mostrarConfirmarPassword = false

                    cuentaViewModel.limpiarMensajes()
                    mostrarCambiarPassword = true
                }
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            // Sobre la aplicación
            OpcionPerfil(
                icon = Icons.Default.Info,
                texto = "Sobre la aplicación",
                descripcion = "Información de Mascotas y Vacunas",
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
                descripcion = "Salir de tu cuenta de forma segura",
                colorIcono = Color(0xFFD96C78),
                onClick = {
                    perfilViewModel.cerrarSesion()
                }
            )
        }
    }

    // Diálogo: Mi cuenta
    if (mostrarMiCuenta) {
        AlertDialog(
            onDismissRequest = { mostrarMiCuenta = false },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color(0xFFFCFAFF),
            title = {
                Text(
                    text = "Mi cuenta",
                    color = Purple40,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                when {
                    cargandoCuenta -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Purple40)
                        }
                    }

                    errorCuenta != null -> {
                        Text(
                            text = errorCuenta.orEmpty(),
                            color = Color(0xFFB3261E)
                        )
                    }

                    usuario != null -> {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            DatoMiCuenta(
                                icono = Icons.Default.Person,
                                titulo = "Nombre",
                                valor = usuario!!.nombre
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            DatoMiCuenta(
                                icono = Icons.Default.Email,
                                titulo = "Correo electrónico",
                                valor = usuario!!.email
                            )
                        }
                    }

                    else -> {
                        Text(
                            text = "No hay datos de la cuenta disponibles.",
                            color = Color(0xFF655780)
                        )
                    }
                }
            },
            confirmButton = {
                BotonDialogo(
                    texto = "Cerrar",
                    onClick = { mostrarMiCuenta = false }
                )
            }
        )
    }

    // Diálogo Cambiar contraseña
    if (mostrarCambiarPassword) {
        Dialog(
            onDismissRequest = {
                if (!cambiandoPassword) {
                    mostrarCambiarPassword = false
                    cuentaViewModel.limpiarMensajes()
                }
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = true
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.88f)
                    .heightIn(max = 640.dp),
                shape = RoundedCornerShape(26.dp),
                color = Color(0xFFFCFAFF)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = CircleShape,
                            color = Color(0xFFECE2FF)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = null,
                                    tint = Purple40,
                                    modifier = Modifier.size(25.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            text = "Cambiar contraseña",
                            color = Color(0xFF482A87),
                            fontSize = 20.sp,
                            lineHeight = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Introduce tu contraseña actual y elige una nueva.",
                        color = Color(0xFF655780),
                        fontSize = 15.sp,
                        lineHeight = 21.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CampoPassword(
                        valor = passwordActual,
                        onValorChange = { passwordActual = it },
                        etiqueta = "Contraseña actual",
                        visible = mostrarPasswordActual,
                        onCambiarVisibilidad = {
                            mostrarPasswordActual = !mostrarPasswordActual
                        },
                        habilitado = !cambiandoPassword &&
                                mensajeExitoPassword == null
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CampoPassword(
                        valor = nuevaPassword,
                        onValorChange = { nuevaPassword = it },
                        etiqueta = "Nueva contraseña",
                        visible = mostrarNuevaPassword,
                        onCambiarVisibilidad = {
                            mostrarNuevaPassword = !mostrarNuevaPassword
                        },
                        habilitado = !cambiandoPassword &&
                                mensajeExitoPassword == null
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CampoPassword(
                        valor = confirmarNuevaPassword,
                        onValorChange = { confirmarNuevaPassword = it },
                        etiqueta = "Confirmar nueva contraseña",
                        visible = mostrarConfirmarPassword,
                        onCambiarVisibilidad = {
                            mostrarConfirmarPassword =
                                !mostrarConfirmarPassword
                        },
                        habilitado = !cambiandoPassword &&
                                mensajeExitoPassword == null
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF0E6FF)
                    ) {
                        Row(
                            modifier = Modifier.padding(11.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Default.VerifiedUser,
                                contentDescription = null,
                                tint = Purple40,
                                modifier = Modifier.size(21.dp)
                            )

                            Spacer(modifier = Modifier.width(9.dp))

                            Column {
                                Text(
                                    text = "Requisitos de seguridad",
                                    color = Color(0xFF482A87),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = "• Al menos 8 caracteres\n" +
                                            "• Diferente de la contraseña actual\n" +
                                            "• Las nuevas contraseñas deben coincidir",
                                    color = Color(0xFF655780),
                                    fontSize = 13.sp,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }

                    if (cambiandoPassword) {
                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = Purple40,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    if (errorCuenta != null) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = errorCuenta.orEmpty(),
                            color = Color(0xFFB3261E),
                            fontSize = 12.sp
                        )
                    }

                    if (mensajeExitoPassword != null) {
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = mensajeExitoPassword.orEmpty(),
                            color = Color(0xFF206B3B),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            if (mensajeExitoPassword != null) {
                                mostrarCambiarPassword = false
                                cuentaViewModel.limpiarMensajes()
                            } else {
                                when {
                                    passwordActual.isBlank() -> {
                                        cuentaViewModel.mostrarError(
                                            "Introduce tu contraseña actual."
                                        )
                                    }

                                    nuevaPassword.length < 8 -> {
                                        cuentaViewModel.mostrarError(
                                            "La nueva contraseña debe tener al menos 8 caracteres."
                                        )
                                    }

                                    nuevaPassword == passwordActual -> {
                                        cuentaViewModel.mostrarError(
                                            "La nueva contraseña debe ser diferente de la actual."
                                        )
                                    }

                                    nuevaPassword != confirmarNuevaPassword -> {
                                        cuentaViewModel.mostrarError(
                                            "Las nuevas contraseñas no coinciden."
                                        )
                                    }

                                    else -> {
                                        cuentaViewModel.cambiarPassword(
                                            passwordActual = passwordActual,
                                            nuevaPassword = nuevaPassword,
                                            confirmarNuevaPassword =
                                            confirmarNuevaPassword
                                        )
                                    }
                                }
                            }
                        },
                        enabled = !cambiandoPassword,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Purple40
                        )
                    ) {
                        if (cambiandoPassword) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(19.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                text = if (mensajeExitoPassword != null) {
                                    "Aceptar"
                                } else {
                                    "Guardar contraseña"
                                },
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (mensajeExitoPassword == null) {
                        OutlinedButton(
                            onClick = {
                                mostrarCambiarPassword = false
                                cuentaViewModel.limpiarMensajes()
                            },
                            enabled = !cambiandoPassword,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp),
                            shape = RoundedCornerShape(24.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Purple40
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = Color(0xFFD8CBEF)
                            )
                        ) {
                            Text(
                                text = "Cancelar",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
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

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .width(32.dp)
                            .height(2.dp)
                            .background(Purple40)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Tus mascotas
                    InformacionApp(
                        icono = Icons.Default.Pets,
                        titulo = "Tus mascotas",
                        descripcion = "Toda su información"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Sus vacunas
                    InformacionApp(
                        icono = Icons.Default.Vaccines,
                        titulo = "Sus vacunas",
                        descripcion = "Historial organizado"
                    )

                    Spacer(modifier = Modifier.height(14.dp))

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
private fun DatoMiCuenta(
    icono: ImageVector,
    titulo: String,
    valor: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = Purple40,
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = titulo,
                color = Color(0xFF756A83),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = valor,
                color = Color(0xFF25212A),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun BotonDialogo(
    texto: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    cargando: Boolean = false
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple40
        )
    ) {
        if (cargando) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = texto,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun CampoPassword(
    valor: String,
    onValorChange: (String) -> Unit,
    etiqueta: String,
    visible: Boolean,
    onCambiarVisibilidad: () -> Unit,
    habilitado: Boolean,
    altura: Dp = 56.dp
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(altura),
        enabled = habilitado,
        singleLine = true,
        label = {
            Text(
                text = etiqueta,
                fontSize = 14.sp,
                maxLines = 1
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = Color(0xFF72569D),
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = {
            IconButton(
                onClick = onCambiarVisibilidad,
                enabled = habilitado
            ) {
                Icon(
                    imageVector = if (visible) {
                        Icons.Default.VisibilityOff
                    } else {
                        Icons.Default.Visibility
                    },
                    contentDescription = if (visible) {
                        "Ocultar contraseña"
                    } else {
                        "Mostrar contraseña"
                    },
                    tint = Color(0xFF655780),
                    modifier = Modifier.size(21.dp)
                )
            }
        },
        visualTransformation = if (visible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Purple40,
            unfocusedBorderColor = Color(0xFFD8CBEF),
            focusedLabelColor = Purple40,
            cursorColor = Purple40
        )
    )
}
@Composable
private fun OpcionPerfil(
    icon: ImageVector,
    texto: String,
    descripcion: String,
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

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = texto,
                    color = Color(0xFF25212A),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = descripcion,
                    color = Color(0xFF756A83),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

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