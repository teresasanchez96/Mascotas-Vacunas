package proyectoFCT.mascotasyvacunas.vacunaciones.data.network

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
import retrofit2.http.Streaming

interface VacunacionClient {

    // Obtiene todas las vacunaciones asociadas a una mascota.
    @GET("/api/vacunaciones/mascota/{mascotaId}")
    suspend fun getVacunacionesByMascota(
        @Path("mascotaId") mascotaId: Long
    ): Response<List<VacunacionResponse>>

    // Registra una nueva vacunación para una mascota.
    @POST("/api/vacunaciones/mascota/{mascotaId}")
    suspend fun createVacunacion(
        @Path("mascotaId") mascotaId: Long,
        @Body vacunacion: VacunacionResponse
    ): Response<VacunacionResponse>

    // Actualiza los datos de una vacunación.
    @PUT("/api/vacunaciones/{id}")
    suspend fun updateVacunacion(
        @Path("id") id: Long,
        @Body vacunacion: VacunacionResponse
    ): Response<VacunacionResponse>

    // Elimina una vacunación por su identificador.
    @DELETE("/api/vacunaciones/{id}")
    suspend fun deleteVacunacion(
        @Path("id") id: Long
    ): Response<Unit>

    // Sube la fotografía de la etiqueta de una vacunación.
    @Multipart
    @POST("/api/vacunaciones/{id}/foto-etiqueta")
    suspend fun subirFotoEtiqueta(
        @Path("id") id: Long,
        @Part archivo: MultipartBody.Part
    ): Response<Unit>

    // Descarga la fotografía de la etiqueta de una vacunación.
    @Streaming
    @GET("/api/vacunaciones/{id}/foto-etiqueta")
    suspend fun obtenerFotoEtiqueta(
        @Path("id") id: Long
    ): Response<ResponseBody>
}