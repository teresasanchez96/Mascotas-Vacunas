package proyectoFCT.mascotasyvacunas.core.network

import proyectoFCT.mascotasyvacunas.mascotas.ui.model.Mascota
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MascotaResponse(
    val id: Long?,
    val nombre: String,
    val especie: String,
    val genero: String,
    val raza: String,
    val fechaNacimiento: String,
    val observaciones: String?
){
    fun toModel(): Mascota {
        return Mascota(
            id = id,
            nombre = nombre,
            especie = especie,
            genero = genero,
            raza = raza,
            fechaNacimiento = formatDate(fechaNacimiento),
            observaciones = observaciones
        )
    }

    private fun formatDate(fecha: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formatterES = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        val date = LocalDate.parse(fecha, formatter)

        return date.format(formatterES)
    }
}
