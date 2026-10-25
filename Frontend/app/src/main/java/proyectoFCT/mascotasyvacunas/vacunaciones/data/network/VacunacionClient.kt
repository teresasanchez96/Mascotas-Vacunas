package proyectoFCT.mascotasyvacunas.vacunaciones.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VacunacionClient {

    @GET("/api/vacunaciones/mascota/{mascotaId}")
    suspend fun getVacunacionesByMascota(
        @Path("mascotaId") mascotaId: Long
    ): Response<List<VacunacionResponse>>

    @POST("/api/vacunaciones/mascota/{mascotaId}")
    suspend fun createVacunacion(
        @Path("mascotaId") mascotaId: Long,
        @Body vacunacion: VacunacionResponse
    ): Response<VacunacionResponse>

    @PUT("/api/vacunaciones/{id}")
    suspend fun updateVacunacion(
        @Path("id") id: Long,
        @Body vacunacion: VacunacionResponse
    ): Response<VacunacionResponse>

    @DELETE("/api/vacunaciones/{id}")
    suspend fun deleteVacunacion(
        @Path("id") id: Long
    ): Response<Unit>
}