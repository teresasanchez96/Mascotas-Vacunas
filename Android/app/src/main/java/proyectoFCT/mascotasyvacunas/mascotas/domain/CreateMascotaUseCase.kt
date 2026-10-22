package proyectoFCT.mascotasyvacunas.mascotas.domain

import proyectoFCT.mascotasyvacunas.mascotas.data.network.MascotaRepository
import proyectoFCT.mascotasyvacunas.mascotas.data.network.MascotaResponse
import proyectoFCT.mascotasyvacunas.mascotas.ui.model.Mascota
import javax.inject.Inject

class CreateMascotaUseCase @Inject constructor(
    private val mascotaRepository: MascotaRepository
) {

    suspend operator fun invoke(
        mascota: Mascota
    ): Result<MascotaResponse> {

        return try {

            val response =
                mascotaRepository.createMascota(mascota)

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