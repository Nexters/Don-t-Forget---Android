package nexters.hyomk.data.remote

import nexters.hyomk.data.model.request.CreateAnniversary
import nexters.hyomk.data.model.request.ModifyAnniversary
import nexters.hyomk.data.model.response.AnniversaryItem
import nexters.hyomk.data.model.response.DetailAnniversary
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DontForgetService {

    @GET("/api/anniversary")
    suspend fun getAnniversaryHistory(): Response<List<AnniversaryItem>>

    @GET("/api/anniversary/{eventId}")
    suspend fun getAnniversary(@Path("eventId") eventId: Long): Response<DetailAnniversary>

    @POST("/api/anniversary")
    suspend fun postAnniversary(@Body request: CreateAnniversary): Response<Unit>

    @PUT("/api/anniversary/{eventId}")
    suspend fun modifyAnniversary(@Path("eventId") request: ModifyAnniversary): Response<Unit>

    @DELETE("/api/anniversary/{eventId}")
    suspend fun deleteAnniversary(@Path("eventId") eventId: Long): Response<Unit>

    @PUT("/api/device")
    suspend fun modifyAlarmState(): Response<Unit>
}
