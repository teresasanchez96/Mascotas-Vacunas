package proyectoFCT.mascotasyvacunas.vacunaciones.domain

import proyectoFCT.mascotasyvacunas.vacunaciones.data.network.VacunacionRepository
import proyectoFCT.mascotasyvacunas.vacunaciones.data.network.VacunacionResponse
import proyectoFCT.mascotasyvacunas.vacunaciones.ui.model.Vacunacion
import javax.inject.Inject

class UpdateVacunacionUseCase @Inject constructor(
    private val vacunacionRepository: VacunacionRepository
) {

    suspend operator fun invoke(
        id: Long,
        vacunacion: Vacunacion
    ): Result<VacunacionResponse> {

        return try {
            val response =
                vacunacionRepository.updateVacunacion(id, vacunacion)

            if (response.isSuccessful) {
                val vacunacionActualizada = response.body()

                if (vacunacionActualizada != null) {
                    Result.success(vacunacionActualizada)
                } else {
                    Result.failure(
                        Exception("No se ha podido actualizar la vacunación")
                    )
                }
            } else {
                Result.failure(
                    Exception(
                        "Error al actualizar la vacunación: ${response.code()}"
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}