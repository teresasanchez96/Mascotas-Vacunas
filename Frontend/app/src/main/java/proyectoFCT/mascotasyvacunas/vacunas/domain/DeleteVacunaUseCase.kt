
package proyectoFCT.mascotasyvacunas.vacunas.domain

import org.json.JSONObject
import proyectoFCT.mascotasyvacunas.vacunas.data.network.VacunaRepository
import javax.inject.Inject

class DeleteVacunaUseCase @Inject constructor(
    private val vacunaRepository: VacunaRepository
) {

    suspend operator fun invoke(id: Long): Result<Unit> {
        return try {
            val response = vacunaRepository.deleteVacuna(id)

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()

                val mensaje = try {
                    JSONObject(errorBody ?: "")
                        .optString(
                            "Error",
                            "No se ha podido eliminar la vacuna."
                        )
                } catch (e: Exception) {
                    "No se ha podido eliminar la vacuna."
                }

                Result.failure(Exception(mensaje))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
