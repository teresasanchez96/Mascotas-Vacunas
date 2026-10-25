package proyectoFCT.mascotasyvacunas.mascotas.data.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import proyectoFCT.mascotasyvacunas.mascotas.ui.model.Mascota
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

// Repositorio encargado de gestionar las peticiones de las mascotas a la API.
@Singleton
class MascotaRepository @Inject constructor(
    private val mascotaClient: MascotaClient
) {

    // Obtener todas las mascotas
    suspend fun getAllMascotas(): Response<List<MascotaResponse>> {
        return mascotaClient.getAllMascotas()
    }

    // Crear una nueva mascota
    suspend fun createMascota(
        mascota: Mascota
    ): Response<MascotaResponse> {
        return mascotaClient.createMascota(
            mascota.toMascotaResponse()
        )
    }

    // Actualizar los datos de una mascota
    suspend fun updateMascota(
        id: Long,
        mascota: Mascota
    ): Response<MascotaResponse> {
        return mascotaClient.updateMascota(
            id,
            mascota.toMascotaResponse()
        )
    }

    // Eliminar una mascota
    suspend fun deleteMascota(
        id: Long
    ): Response<Unit> {
        return mascotaClient.deleteMascota(id)
    }

    // Subir la fotografía de una mascota
    suspend fun subirFotoMascota(
        id: Long,
        archivo: MultipartBody.Part
    ): Response<MascotaResponse> {
        return mascotaClient.subirFotoMascota(id, archivo)
    }

    // Obtener la fotografía de una mascota
    suspend fun obtenerFotoMascota(
        id: Long
    ): Response<ResponseBody> {
        return mascotaClient.obtenerFotoMascota(id)
    }
}
