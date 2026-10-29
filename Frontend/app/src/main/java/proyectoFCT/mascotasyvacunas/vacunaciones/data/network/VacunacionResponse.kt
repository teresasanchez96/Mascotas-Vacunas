package proyectoFCT.mascotasyvacunas.vacunaciones.data.network

import proyectoFCT.mascotasyvacunas.vacunaciones.ui.model.Vacunacion
import proyectoFCT.mascotasyvacunas.vacunas.data.network.VacunaResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class VacunacionResponse(
    val id: Long?,
    val fechaPrevista: String,
    val vacunaAdministrada: Boolean,
    val fechaAdministracion: String?,
    val fotoEtiqueta: String? = null,
    val vacuna: VacunaResponse
) {
    fun toModel(): Vacunacion {
        return Vacunacion(
            id = id,
            fechaPrevista = formatDate(fechaPrevista),
            vacunaAdministrada = vacunaAdministrada,
            fechaAdministracion = fechaAdministracion?.let { formatDate(it) },
            fotoEtiqueta = fotoEtiqueta,
            vacuna = vacuna.toModel()
        )
    }

    private fun formatDate(fecha: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatterES = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val date = LocalDate.parse(fecha, formatter)

        return date.format(formatterES)
    }
}