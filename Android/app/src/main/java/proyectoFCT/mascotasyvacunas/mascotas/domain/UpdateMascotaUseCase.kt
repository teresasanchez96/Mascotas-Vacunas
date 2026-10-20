package proyectoFCT.mascotasyvacunas.mascotas.domain

import proyectoFCT.mascotasyvacunas.core.network.MascotaRepository
import proyectoFCT.mascotasyvacunas.core.network.MascotaResponse
import proyectoFCT.mascotasyvacunas.mascotas.ui.model.Mascota
import javax.inject.Inject

class UpdateMascotaUseCase @Inject constructor(
    private val mascotaRepository: MascotaRepository
) {

    suspend operator fun invoke(
        id: Long,
        mascota: Mascota
    ): Result<MascotaResponse> {

        return try {

            val response = mascotaRepository.updateMascota(
                id = id,
                mascota = mascota
            )

            if (response.isSuccessful) {

                val mascotaActualizada = response.body()

                if (mascotaActualizada != null) {
                    Result.success(mascotaActualizada)
                } else {
                    Result.failure(
                        Exception("No se pudo actualizar la mascota")
                    )
                }

            } else {

                Result.failure(
                    Exception(
                        "Error al actualizar la mascota: ${response.code()}"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(
                e
            )
        }
    }
}