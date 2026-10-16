package proyectoFCT.mascotasyvacunas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import proyectoFCT.mascotasyvacunas.authentication.ui.screen.LoginScreen
import proyectoFCT.mascotasyvacunas.authentication.ui.screen.PantallaRegistro
import proyectoFCT.mascotasyvacunas.home.ui.screen.PantallaBienvenida

// Gestiona la navegación entre las diferentes pantallas de la aplicación.
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,

        // Pantalla que se muestra al iniciar la aplicación.
        startDestination = Home
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
    }

}