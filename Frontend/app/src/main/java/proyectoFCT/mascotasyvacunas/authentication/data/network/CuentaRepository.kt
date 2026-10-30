package proyectoFCT.mascotasyvacunas.authentication.data.network

import org.json.JSONObject
import proyectoFCT.mascotasyvacunas.authentication.data.remote.AuthenticationClient
import proyectoFCT.mascotasyvacunas.authentication.data.remote.CambiarPasswordRequest
import proyectoFCT.mascotasyvacunas.authentication.data.remote.UsuarioResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CuentaRepository @Inject constructor(
    private val authenticationClient: AuthenticationClient
) {

    suspend fun obtenerMiCuenta(): UsuarioResponse {
        return authenticationClient.obtenerMiCuenta()
    }

    suspend fun cambiarPassword(
        request: CambiarPasswordRequest
    ): Result<Unit> {

        return try {
            val response = authenticationClient.cambiarPassword(request)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val cuerpo = response.errorBody()?.string()

                val mensaje = try {
                    cuerpo?.let {
                        JSONObject(it).optString(
                            "Error",
                            "No se ha podido cambiar la contraseña"
                        )
                    }
                } catch (e: Exception) {
                    null
                }?.takeIf { it.isNotBlank() }
                    ?: "No se ha podido cambiar la contraseña"

                Result.failure(Exception(mensaje))
            }

        } catch (e: Exception) {
            Result.failure(
                Exception(
                    e.message ?: "Error de conexión con el servidor"
                )
            )
        }
    }
}
