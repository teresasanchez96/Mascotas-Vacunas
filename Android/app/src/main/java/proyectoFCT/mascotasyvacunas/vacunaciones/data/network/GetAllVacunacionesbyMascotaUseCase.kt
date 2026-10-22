package proyectoFCT.mascotasyvacunas.vacunaciones.data.network

import javax.inject.Inject

class GetAllVacunacionesbyMascotaUseCase @Inject constructor(
    private val vacunacionRepository: VacunacionRepository
) {

    suspend operator fun invoke(
        mascotaId: Long
    ): Result<List<VacunacionResponse>> {

        return try {
            val response =
                vacunacionRepository.getVacunacionesByMascota(mascotaId)

            if (response.isSuccessful) {

                val vacunaciones = response.body()

                if (vacunaciones != null) {
                    Result.success(vacunaciones)
                } else {
                    Result.failure(
                        Exception("No se han podido cargar las vacunaciones")
                    )
                }

            } else {
                Result.failure(
                    Exception(
                        "Error al obtener las vacunaciones: ${response.code()}"
                    )
                )
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}