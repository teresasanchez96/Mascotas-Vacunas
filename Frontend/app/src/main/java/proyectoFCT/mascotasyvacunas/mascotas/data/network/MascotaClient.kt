package proyectoFCT.mascotasyvacunas.mascotas.data.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    // Subir la fotografía de una mascota
    @Multipart
    @POST("/api/mascotas/{id}/foto")
    suspend fun subirFotoMascota(
        @Path("id") id: Long,
        @Part archivo: MultipartBody.Part
    ): Response<MascotaResponse>

    // Consultar la fotografía de una mascota
    @GET("/api/mascotas/{id}/foto")
    suspend fun obtenerFotoMascota(
        @Path("id") id: Long
    ): Response<ResponseBody>
}
