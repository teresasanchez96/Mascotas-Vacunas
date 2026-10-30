package proyectoFCT.mascotasyvacunas.authentication.data.remote

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthenticationClient {

    @POST("api/auth/registro")
    suspend fun registrar(
        @Body request: RegistroRequest
    )

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("api/auth/mi-cuenta")
    suspend fun obtenerMiCuenta(): UsuarioResponse

    @PUT("api/auth/cambiar-password")
    suspend fun cambiarPassword(
        @Body request: CambiarPasswordRequest
    ): retrofit2.Response<ResponseBody>

}