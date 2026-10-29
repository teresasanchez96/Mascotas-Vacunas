package proyectoFCT.mascotasyvacunas.vacunaciones.ui.model

import proyectoFCT.mascotasyvacunas.vacunaciones.data.network.VacunacionResponse
import proyectoFCT.mascotasyvacunas.vacunas.ui.model.Vacuna
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Vacunacion(
    val id: Long?,
    val fechaPrevista: String,
    val vacunaAdministrada: Boolean,
    val fechaAdministracion: String?,
    val fotoEtiqueta: String? = null,
    val vacuna: Vacuna
) {

    fun toVacunacionResponse(): VacunacionResponse {
        return VacunacionResponse(
            id = id,
            fechaPrevista = formatDate(fechaPrevista),
            vacunaAdministrada = vacunaAdministrada,
            fechaAdministracion = fechaAdministracion?.let {
                formatDate(it)
            },
            fotoEtiqueta = fotoEtiqueta,
            vacuna = vacuna.toVacunaResponse()
        )
    }

    fun formatDate(fecha: String): String {
        val formatterES = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val date = LocalDate.parse(fecha, formatterES)

        return date.format(formatter)
    }
}