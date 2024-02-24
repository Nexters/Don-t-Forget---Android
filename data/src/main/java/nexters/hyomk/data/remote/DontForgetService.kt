package nexters.hyomk.data.remote

import nexters.hyomk.data.model.request.CreateAnniversaryDTO
import nexters.hyomk.data.model.request.ModifyAnniversaryDTO
import nexters.hyomk.data.model.response.AnniversaryItemDTO
import nexters.hyomk.data.model.response.DetailAnniversaryDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DontForgetService {

    @GET("/api/anniversary")
    suspend fun getAnniversaryHistory(): Response<List<AnniversaryItemDTO>>

    @GET("/api/anniversary/{eventId}")
    suspend fun getAnniversary(@Path("eventId") eventId: Long): Response<DetailAnniversaryDTO>

    @POST("/api/anniversary")
    suspend fun postAnniversary(@Body request: CreateAnniversaryDTO): Response<*>

    @PUT("/api/anniversary/{eventId}")
    suspend fun modifyAnniversary(@Path("eventId") eventId: Long, @Body request: ModifyAnniversaryDTO): Response<Unit>

    @DELETE("/api/anniversary/{eventId}")
    suspend fun deleteAnniversary(@Path("eventId") eventId: Long): Response<*>

    @PUT("/api/device")
    suspend fun modifyAlarmState(): Response<Unit>
}
