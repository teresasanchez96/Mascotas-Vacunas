package proyectoFCT.mascotasyvacunas.vacunaciones.data.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import proyectoFCT.mascotasyvacunas.vacunaciones.ui.model.Vacunacion
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VacunacionRepository @Inject constructor(
    private val vacunacionClient: VacunacionClient
) {

    // Obtiene las vacunaciones asociadas a una mascota.
    suspend fun getVacunacionesByMascota(
        mascotaId: Long
    ): Response<List<VacunacionResponse>> {
        return vacunacionClient.getVacunacionesByMascota(mascotaId)
    }

    // Registra una nueva vacunación para una mascota.
    suspend fun createVacunacion(
        mascotaId: Long,
        vacunacion: Vacunacion
    ): Response<VacunacionResponse> {
        return vacunacionClient.createVacunacion(
            mascotaId,
            vacunacion.toVacunacionResponse()
        )
    }

    // Actualiza los datos de una vacunación.
    suspend fun updateVacunacion(
        id: Long,
        vacunacion: Vacunacion
    ): Response<VacunacionResponse> {
        return vacunacionClient.updateVacunacion(
            id,
            vacunacion.toVacunacionResponse()
        )
    }

    // Elimina una vacunación por su identificador.
    suspend fun deleteVacunacion(
        id: Long
    ): Response<Unit> {
        return vacunacionClient.deleteVacunacion(id)
    }

    // Sube la fotografía de la etiqueta de una vacunación.
    suspend fun subirFotoEtiqueta(
        id: Long,
        archivo: MultipartBody.Part
    ): Response<Unit> {
        return vacunacionClient.subirFotoEtiqueta(id, archivo)
    }

    // Obtiene la fotografía de la etiqueta de una vacunación.
    suspend fun obtenerFotoEtiqueta(
        id: Long
    ): Response<ResponseBody> {
        return vacunacionClient.obtenerFotoEtiqueta(id)
    }
}