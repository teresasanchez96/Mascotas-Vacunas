package proyectoFCT.mascotasyvacunas.mascotas.data.network

import proyectoFCT.mascotasyvacunas.mascotas.ui.model.Mascota
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MascotaRepository @Inject constructor(
    private val mascotaClient: MascotaClient
) {

    suspend fun getAllMascotas(): Response<List<MascotaResponse>> {
        return mascotaClient.getAllMascotas()
    }

    suspend fun createMascota(
        mascota: Mascota
    ): Response<MascotaResponse> {

        return mascotaClient.createMascota(
            mascota.toMascotaResponse()
        )
    }

    suspend fun updateMascota(
        id: Long,
        mascota: Mascota
    ): Response<MascotaResponse> {

        return mascotaClient.updateMascota(
            id,
            mascota.toMascotaResponse()
        )
    }

    suspend fun deleteMascota(
        id: Long
    ): Response<Unit> {

        return mascotaClient.deleteMascota(id)
    }
}