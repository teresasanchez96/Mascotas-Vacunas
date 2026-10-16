package proyectoFCT.mascotasyvacunas.authentication.ui.screen

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import proyectoFCT.mascotasyvacunas.authentication.ui.viewmodel.AuthenticationViewModel
import proyectoFCT.mascotasyvacunas.navigation.Login
import proyectoFCT.mascotasyvacunas.ui.theme.Purple40

@Composable
fun PantallaRegistro(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {

    var nombre by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmarPassword by remember {
        mutableStateOf("")
    }

    var mostrarPassword by remember {
        mutableStateOf(false)
    }

    var mostrarConfirmarPassword by remember {
        mutableStateOf(false)
    }

    var mostrarRegistroExitoso by remember {
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
                vertical = 16.dp
            )
    ) {

        Spacer(
            modifier = Modifier.height(90.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Huella
            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = "Mascotas",
                tint = Purple40,
                modifier = Modifier.size(58.dp)
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // Título
            Text(
                text = "Crear cuenta",
                color = Purple40,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            // Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                },
                label = {
                    Text("Nombre")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Nombre",
                        tint = Purple40
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text("Email")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = Purple40
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // Contraseña
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
                modifier = Modifier.height(14.dp)
            )

            // Confirmar contraseña
            OutlinedTextField(
                value = confirmarPassword,
                onValueChange = {
                    confirmarPassword = it
                },
                label = {
                    Text("Confirmar contraseña")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Confirmar contraseña",
                        tint = Purple40
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            mostrarConfirmarPassword = !mostrarConfirmarPassword
                        }
                    ) {
                        Icon(
                            imageVector = if (mostrarConfirmarPassword) {
                                Icons.Default.VisibilityOff
                            } else {
                                Icons.Default.Visibility
                            },
                            contentDescription = if (mostrarConfirmarPassword) {
                                "Ocultar contraseña"
                            } else {
                                "Mostrar contraseña"
                            },
                            tint = Purple40
                        )
                    }
                },
                visualTransformation = if (mostrarConfirmarPassword) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            // Botón de crear cuenta
            Button(
                onClick = {
                    if (nombre.isBlank() || email.isBlank() ||
                        password.isBlank() || confirmarPassword.isBlank()
                    ) {
                        mensajeError = "Completa todos los campos."
                    } else if (!Patterns.EMAIL_ADDRESS
                            .matcher(email.trim()).matches()
                    ) {
                        mensajeError = "Introduce un correo electrónico válido."
                    } else if (password.length < 8) {
                        mensajeError = "La contraseña debe tener al menos 8 caracteres."
                    } else if (!password.any { it.isLowerCase() } ||
                        !password.any { it.isUpperCase() } ||
                        !password.any { it.isDigit() } ||
                        !password.any { it in "@#$%^&+=!" }
                    ) {
                        mensajeError =
                            "La contraseña debe incluir mayúscula, minúscula, número y carácter especial."
                    } else if (password != confirmarPassword) {
                        mensajeError = "Las contraseñas no coinciden."
                    } else {
                        mensajeError = null

                        viewModel.registrar(
                            nombre = nombre.trim(),
                            email = email.trim(),
                            password = password,
                            onSuccess = {
                                mostrarRegistroExitoso = true
                            },
                            onError = { error ->
                                mensajeError = error
                            }
                        )
                    }
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
                    text = "Crear cuenta",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // Enlace para iniciar sesión
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Ya tienes una cuenta?",
                    color = Color(0xFF302B35),
                    fontSize = 16.sp
                )

                TextButton(
                    onClick = {
                        navController.navigate(Login)
                    }
                ) {
                    Text(
                        text = "Iniciar sesión",
                        color = Purple40,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Diálogo de registro correcto
    if (mostrarRegistroExitoso) {
        AlertDialog(
            onDismissRequest = {
                mostrarRegistroExitoso = false
            },
            title = {
                Text("Cuenta creada")
            },
            text = {
                Text("Tu cuenta se ha creado correctamente.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarRegistroExitoso = false
                        navController.navigate(Login)
                    }
                ) {
                    Text(
                        text = "Iniciar sesión",
                        color = Purple40,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }

    // Diálogo de error
    if (mensajeError != null) {
        AlertDialog(
            onDismissRequest = {
                mensajeError = null
            },
            title = {
                Text("Error")
            },
            text = {
                Text(mensajeError ?: "")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mensajeError = null
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}