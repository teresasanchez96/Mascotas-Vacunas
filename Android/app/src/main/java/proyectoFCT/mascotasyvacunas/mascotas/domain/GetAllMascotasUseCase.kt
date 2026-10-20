package proyectoFCT.mascotasyvacunas.mascotas.domain

import proyectoFCT.mascotasyvacunas.core.network.MascotaRepository
import proyectoFCT.mascotasyvacunas.core.network.MascotaResponse
import javax.inject.Inject

class GetAllMascotasUseCase @Inject constructor(
    private val mascotaRepository: MascotaRepository
) {

    suspend operator fun invoke():
            Result<List<MascotaResponse>> {

        return try {

            val response =
                mascotaRepository.getAllMascotas()

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