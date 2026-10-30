package proyectoFCT.mascotasyvacunas.authentication.data.remote

data class CambiarPasswordRequest(
    val passwordActual: String,
    val nuevaPassword: String,
    val confirmarNuevaPassword: String
)