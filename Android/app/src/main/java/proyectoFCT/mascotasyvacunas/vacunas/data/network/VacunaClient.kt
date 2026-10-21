package proyectoFCT.mascotasyvacunas.vacunas.data.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VacunaClient {

    @GET("/api/vacunas")
    suspend fun getAllVacunas():
            Response<List<VacunaResponse>>

    @POST("/api/vacunas")
    suspend fun createVacuna(
        @Body vacuna: VacunaResponse
    ): Response<VacunaResponse>


    @DELETE("/api/vacunas/{id}")
    suspend fun deleteVacuna(
        @Path("id") id: Long
    ): Response<Unit>

}