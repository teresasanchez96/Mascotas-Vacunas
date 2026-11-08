package proyectoFCT.mascotasyvacunas.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import proyectoFCT.mascotasyvacunas.authentication.ui.screen.LoginScreen
import proyectoFCT.mascotasyvacunas.authentication.ui.screen.PantallaPerfil
import proyectoFCT.mascotasyvacunas.authentication.ui.screen.PantallaRegistro
import proyectoFCT.mascotasyvacunas.home.ui.screen.PantallaBienvenida
import proyectoFCT.mascotasyvacunas.mascotas.ui.screen.PantallaDetalleMascota
import proyectoFCT.mascotasyvacunas.mascotas.ui.screen.PantallaListaMascotas
import proyectoFCT.mascotasyvacunas.mascotas.ui.screen.PantallaNuevaMascota
import proyectoFCT.mascotasyvacunas.vacunaciones.ui.screen.PantallaVacunacionesMascota
import proyectoFCT.mascotasyvacunas.vacunas.ui.screen.PantallaListaVacunas

// Gestiona la navegación y las transiciones entre las diferentes pantallas de la aplicación.
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,

        // Pantalla que se muestra al iniciar la aplicación.
        startDestination = Home,
        enterTransition = {
            fadeIn(
                animationSpec = tween(400)
            ) + scaleIn(
                initialScale = 0.95f,
                animationSpec = tween(400)
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(300)
            ) + scaleOut(
                targetScale = 1.02f,
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            fadeIn(
                animationSpec = tween(400)
            ) + scaleIn(
                initialScale = 0.95f,
                animationSpec = tween(400)
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(300)
            ) + scaleOut(
                targetScale = 1.02f,
                animationSpec = tween(300)
            )
        }
    ) {

        // Pantalla de bienvenida.
        composable<Home> {
            PantallaBienvenida(navController)
        }

        // Pantalla de inicio de sesión.
        composable<Login> {
            LoginScreen(navController)
        }

       //  Pantalla de registro de nuevos usuarios.
        composable<Registro> {
            PantallaRegistro(navController)
        }

        // Pantalla lista de mascotas del usuario.
        composable<ListaMascotas> {
            PantallaListaMascotas(navController)
        }

        // Pantalla para añadir una mascota nueva.
        composable<NuevaMascota> {
            PantallaNuevaMascota(navController)
        }

        // Pantalla de detalle de una mascota.
        composable(
            route = "detalle_mascota/{mascotaId}",
            arguments = listOf(
                navArgument("mascotaId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val mascotaId = backStackEntry
                .arguments
                ?.getLong("mascotaId")

            if (mascotaId != null) {

                PantallaDetalleMascota(
                    navController = navController,
                    mascotaId = mascotaId
                )
            }
        }

        // Lista de vacunas disponibles.
        composable<ListaVacunas> {
            PantallaListaVacunas(navController)
        }


        // Muestra las vacunaciones asociadas a una mascota.
        composable(
            route = "vacunaciones_mascota/{mascotaId}",
            arguments = listOf(
                navArgument("mascotaId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->

            val mascotaId =
                backStackEntry.arguments?.getLong("mascotaId")

            if (mascotaId != null) {

                PantallaVacunacionesMascota(
                    navController = navController,
                    mascotaId = mascotaId
                )
            }
        }

        // Pantalla de perfil del usuario.
        composable<Perfil> {
            PantallaPerfil(navController)
        }


    }

}