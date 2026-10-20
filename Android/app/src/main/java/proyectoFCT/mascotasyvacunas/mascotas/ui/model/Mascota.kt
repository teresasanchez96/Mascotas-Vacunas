package proyectoFCT.mascotasyvacunas.mascotas.ui.model

import proyectoFCT.mascotasyvacunas.core.network.MascotaResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Mascota(
    val id: Long?,
    val nombre: String,
    val especie: String,
    val genero: String,
    val raza: String,
    val fechaNacimiento: String,
    val observaciones: String?,
){
    fun toMascotaResponse(): MascotaResponse {
        return MascotaResponse(
            id = id,
            nombre = nombre,
            especie = especie,
            genero = genero,
            raza = raza,
            fechaNacimiento = formatDate(fechaNacimiento),
            observaciones = observaciones,

        )
    }

    private fun formatDate(fecha: String): String {
        val formatterES = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val date = LocalDate.parse(fecha, formatterES)

        return date.format(formatter)
    }
}