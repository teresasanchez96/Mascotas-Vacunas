package proyectoFCT.mascotasyvacunas.authentication.data.remote

data class RegistroRequest(
    val nombre: String,
    val email: String,
    val password: String
)