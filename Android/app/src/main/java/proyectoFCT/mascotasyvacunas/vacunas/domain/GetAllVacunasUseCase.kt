package proyectoFCT.mascotasyvacunas.vacunas.domain

import proyectoFCT.mascotasyvacunas.vacunas.data.network.VacunaRepository
import proyectoFCT.mascotasyvacunas.vacunas.data.network.VacunaResponse
import javax.inject.Inject

class GetAllVacunasUseCase @Inject constructor(
    private val vacunaRepository: VacunaRepository
) {

    suspend operator fun invoke():
            Result<List<VacunaResponse>> {

        return try {

            val response =
                vacunaRepository.getAllVacunas()

            if (response.isSuccessful) {

                Result.success(
                    response.body() ?: emptyList()
                )

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