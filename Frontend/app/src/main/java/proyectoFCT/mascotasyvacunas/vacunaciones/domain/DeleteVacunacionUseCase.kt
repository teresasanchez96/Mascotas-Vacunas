package proyectoFCT.mascotasyvacunas.vacunaciones.domain

import proyectoFCT.mascotasyvacunas.vacunaciones.data.network.VacunacionRepository
import javax.inject.Inject

class DeleteVacunacionUseCase @Inject constructor(
    private val vacunacionRepository: VacunacionRepository
) {

    suspend operator fun invoke(id: Long): Result<Unit> {

        return try {
            val response =
                vacunacionRepository.deleteVacunacion(id)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(
                    Exception(
                        "Error al eliminar la vacunación: ${response.code()}"
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}