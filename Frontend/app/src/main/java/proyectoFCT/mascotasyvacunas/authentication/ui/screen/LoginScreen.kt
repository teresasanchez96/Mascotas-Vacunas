package proyectoFCT.mascotasyvacunas.authentication.ui.screen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import proyectoFCT.mascotasyvacunas.authentication.ui.viewmodel.AuthenticationViewModel
import proyectoFCT.mascotasyvacunas.navigation.ListaMascotas
import proyectoFCT.mascotasyvacunas.navigation.Registro
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var mostrarPassword by remember {
        mutableStateOf(false)
    }

    var mensajeError by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF9F6FF),
                        Color(0xFFF0E9FA)
                    )
                )
            )
            .padding(
                horizontal = 28.dp,
                vertical = 20.dp
            )
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Icono de huella
            Box(
                modifier = Modifier
                    .size(82.dp)
                    .background(
                        color = Color(0xFFE8DDF8),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Pets,
                    contentDescription = "Mascotas",
                    tint = Purple40,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // Título
            Text(
                text = "Iniciar sesión",
                color = Purple40,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            // Campo de email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text("Correo electrónico")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Correo electrónico",
                        tint = Purple40
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Campo de contraseña
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text("Contraseña")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Contraseña",
                        tint = Purple40
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            mostrarPassword = !mostrarPassword
                        }
                    ) {
                        Icon(
                            imageVector = if (mostrarPassword) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            },
                            contentDescription = if (mostrarPassword) {
                                "Ocultar contraseña"
                            } else {
                                "Mostrar contraseña"
                            },
                            tint = Purple40
                        )
                    }
                },
                visualTransformation = if (mostrarPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            // Botón de inicio de sesión
            Button(
                onClick = {

                    if (email.isBlank() || password.isBlank()) {
                        mensajeError = "Introduce tu email y contraseña."
                        return@Button
                    }

                    viewModel.login(
                        email = email,
                        password = password,
                        onSuccess = {
                            navController.navigate(ListaMascotas) {
                                popUpTo(proyectoFCT.mascotasyvacunas.navigation.Login) {
                                    inclusive = true
                                }
                            }
                        },
                        onError = {
                            mensajeError =
                                "No se ha podido iniciar sesión. Comprueba que tu email y contraseña son correctos."
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40
                )
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            // Enlace de registro
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿No estás registrado?",
                    fontSize = 16.sp,
                    color = Color(0xFF302B35)
                )

                TextButton(
                    onClick = {
                        navController.navigate(Registro)
                    }
                ) {
                    Text(
                        text = "Registrate",
                        color = Purple40,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Diálogo para mostrar errores durante el inicio de sesión
    if (mensajeError != null) {
        AlertDialog(
            onDismissRequest = {
                mensajeError = null
            },
            icon = null,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = Color(0xFFE8DDF8),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = null,
                            tint = Purple40,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Text(
                        text = "Error",
                        color = Purple40,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            text = {
                Text(
                    text = mensajeError ?: "",
                    color = Color(0xFF625D68),
                    modifier = Modifier.padding(top = 0.dp)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mensajeError = null
                    }
                ) {
                    Text(
                        text = "Aceptar",
                        color = Purple40,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color(0xFFF9F6FF),
            tonalElevation = 6.dp
        )
    }
}