package proyectoFCT.mascotasyvacunas.vacunas.data.network

import proyectoFCT.mascotasyvacunas.vacunas.ui.model.Vacuna
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VacunaRepository  @Inject constructor(
    private val vacunaClient: VacunaClient
) {

    suspend fun getAllVacunas(): Response<List<VacunaResponse>> {
        return vacunaClient.getAllVacunas()
    }

    suspend fun createVacuna(
        vacuna: Vacuna
    ): Response<VacunaResponse> {

        return vacunaClient.createVacuna(
            vacuna.toVacunaResponse()
        )
    }

    suspend fun deleteVacuna(
        id: Long
    ): Response<Unit> {

        return vacunaClient.deleteVacuna(id)
    }
}