package proyectoFCT.mascotasyvacunas.authentication.domain

import proyectoFCT.mascotasyvacunas.authentication.data.local.TokenManager
import proyectoFCT.mascotasyvacunas.authentication.data.remote.AuthenticationClient
import proyectoFCT.mascotasyvacunas.authentication.data.remote.LoginRequest

class LoginUseCase(
    private val authenticationClient: AuthenticationClient,
    private val tokenManager: TokenManager
) {

    suspend operator fun invoke(
        email: String,
        password: String
    ): Boolean {

        val request = LoginRequest(
            email = email,
            password = password
        )

        val response = authenticationClient.login(request)

        tokenManager.guardarToken(response.token)

        return true
    }
}