package proyectoFCT.mascotasyvacunas.mascotas.domain

import proyectoFCT.mascotasyvacunas.core.network.MascotaRepository
import javax.inject.Inject

class DeleteMascotaUseCase @Inject constructor(
    private val mascotaRepository: MascotaRepository
) {

    suspend operator fun invoke(
        id: Long
    ): Result<Unit> {

        return try {

            val response =
                mascotaRepository.deleteMascota(id)

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