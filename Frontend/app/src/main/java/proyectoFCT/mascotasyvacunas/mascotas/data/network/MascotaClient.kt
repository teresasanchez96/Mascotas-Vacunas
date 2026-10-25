package proyectoFCT.mascotasyvacunas.mascotas.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MascotaClient {

    // Obtener todas las mascotas
    @GET("/api/mascotas")
    suspend fun getAllMascotas():
            Response<List<MascotaResponse>>

    // Crear nueva mascota
    @POST("/api/mascotas")
    suspend fun createMascota(
        @Body mascota: MascotaResponse
    ): Response<MascotaResponse>

    // Actualizar mascota
    @PUT("/api/mascotas/{id}")
    suspend fun updateMascota(
        @Path("id") id: Long,
        @Body mascota: MascotaResponse
    ): Response<MascotaResponse>

    // Eliminar mascota
    @DELETE("/api/mascotas/{id}")
    suspend fun deleteMascota(
        @Path("id") id: Long
    ): Response<Unit>

}