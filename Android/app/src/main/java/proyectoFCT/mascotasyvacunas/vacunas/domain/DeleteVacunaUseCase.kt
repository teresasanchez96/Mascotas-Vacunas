package proyectoFCT.mascotasyvacunas.vacunas.domain

import proyectoFCT.mascotasyvacunas.vacunas.data.network.VacunaRepository
import javax.inject.Inject

class DeleteVacunaUseCase @Inject constructor(
    private val vacunaRepository: VacunaRepository
) {

    suspend operator fun invoke(
        id: Long
    ): Result<Unit> {

        return try {

            val response =
                vacunaRepository.deleteVacuna(id)

            if (response.isSuccessful) {

                Result.success(Unit)

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