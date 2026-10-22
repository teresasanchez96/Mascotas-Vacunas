package proyectoFCT.mascotasyvacunas.vacunaciones.domain

import proyectoFCT.mascotasyvacunas.vacunaciones.data.network.VacunacionRepository
import proyectoFCT.mascotasyvacunas.vacunaciones.data.network.VacunacionResponse
import proyectoFCT.mascotasyvacunas.vacunaciones.ui.model.Vacunacion
import javax.inject.Inject

class CreateVacunacionUseCase @Inject constructor(
    private val vacunacionRepository: VacunacionRepository
) {

    suspend operator fun invoke(
        mascotaId: Long,
        vacunacion: Vacunacion
    ): Result<VacunacionResponse> {

        return try {

            val response =
                vacunacionRepository.createVacunacion(
                    mascotaId,
                    vacunacion
                )

            if (response.isSuccessful) {

                val vacunacionCreada = response.body()

                if (vacunacionCreada != null) {
                    Result.success(vacunacionCreada)
                } else {
                    Result.failure(
                        Exception(
                            "No se ha podido crear la vacunación"
                        )
                    )
                }

            } else {

                Result.failure(
                    Exception(
                        "Error al crear la vacunación: ${response.code()}"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}