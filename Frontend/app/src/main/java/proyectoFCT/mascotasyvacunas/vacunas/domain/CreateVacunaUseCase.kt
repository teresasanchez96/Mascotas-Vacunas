package proyectoFCT.mascotasyvacunas.vacunas.domain

import proyectoFCT.mascotasyvacunas.vacunas.data.network.VacunaRepository
import proyectoFCT.mascotasyvacunas.vacunas.data.network.VacunaResponse
import proyectoFCT.mascotasyvacunas.vacunas.ui.model.Vacuna
import javax.inject.Inject

class CreateVacunaUseCase @Inject constructor(
    private val vacunaRepository: VacunaRepository
) {

    suspend operator fun invoke(
        vacuna: Vacuna
    ): Result<VacunaResponse> {

        return try {

            val response =
                vacunaRepository.createVacuna(vacuna)

            if (response.isSuccessful) {

                Result.success(response.body()!!)

            } else {

                Result.failure(
                    Exception(
                        "Error en la petición: ${response.code()}"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}