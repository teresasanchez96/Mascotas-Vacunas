package proyectoFCT.mascotasyvacunas.vacunas.data.network

import proyectoFCT.mascotasyvacunas.vacunas.ui.model.Vacuna

data class VacunaResponse(
    val id: Long?,
    val nombreVacuna: String
) {
    fun toModel(): Vacuna {
        return Vacuna(
            id = id,
            nombreVacuna = nombreVacuna
        )
    }
}