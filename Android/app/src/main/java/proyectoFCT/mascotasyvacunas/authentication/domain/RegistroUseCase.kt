package proyectoFCT.mascotasyvacunas.authentication.domain

import proyectoFCT.mascotasyvacunas.authentication.data.remote.AuthenticationClient
import proyectoFCT.mascotasyvacunas.authentication.data.remote.RegistroRequest

class RegistroUseCase(
    private val authenticationClient: AuthenticationClient
) {

    suspend operator fun invoke(
        nombre: String,
        email: String,
        password: String
    ) {

        val request = RegistroRequest(
            nombre = nombre,
            email = email,
            password = password
        )

        authenticationClient.registrar(request)
    }
}