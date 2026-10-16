package proyectoFCT.mascotasyvacunas.authentication.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationClient {

    @POST("api/auth/registro")
    suspend fun registrar(
        @Body request: RegistroRequest
    )

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}