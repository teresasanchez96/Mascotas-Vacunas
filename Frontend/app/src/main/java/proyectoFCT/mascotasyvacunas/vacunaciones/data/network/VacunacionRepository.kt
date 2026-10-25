package proyectoFCT.mascotasyvacunas.vacunaciones.data.network

import proyectoFCT.mascotasyvacunas.vacunaciones.ui.model.Vacunacion
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VacunacionRepository @Inject constructor(
    private val vacunacionClient: VacunacionClient
) {

    suspend fun getVacunacionesByMascota(
        mascotaId: Long
    ): Response<List<VacunacionResponse>> {
        return vacunacionClient.getVacunacionesByMascota(mascotaId)
    }

    suspend fun createVacunacion(
        mascotaId: Long,
        vacunacion: Vacunacion
    ): Response<VacunacionResponse> {
        return vacunacionClient.createVacunacion(
            mascotaId,
            vacunacion.toVacunacionResponse()
        )
    }

    suspend fun updateVacunacion(
        id: Long,
        vacunacion: Vacunacion
    ): Response<VacunacionResponse> {
        return vacunacionClient.updateVacunacion(
            id,
            vacunacion.toVacunacionResponse()
        )
    }

    suspend fun deleteVacunacion(
        id: Long
    ): Response<Unit> {
        return vacunacionClient.deleteVacunacion(id)
    }
}