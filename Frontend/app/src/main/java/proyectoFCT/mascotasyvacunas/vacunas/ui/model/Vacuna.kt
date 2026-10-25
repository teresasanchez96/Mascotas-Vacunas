package proyectoFCT.mascotasyvacunas.vacunas.ui.model

import proyectoFCT.mascotasyvacunas.vacunas.data.network.VacunaResponse

data class Vacuna(
    val id: Long?,
    val nombreVacuna: String
) {
    fun toVacunaResponse(): VacunaResponse {
        return VacunaResponse(
            id = id,
            nombreVacuna = nombreVacuna
        )
    }
}