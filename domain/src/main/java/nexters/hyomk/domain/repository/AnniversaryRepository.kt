package nexters.hyomk.domain.repository

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.model.AnniversaryItem
import nexters.hyomk.domain.model.CreateAnniversary
import nexters.hyomk.domain.model.DetailAnniversary
import nexters.hyomk.domain.model.ModifyAnniversary
import retrofit2.http.Body
import retrofit2.http.Path

interface AnniversaryRepository {
    suspend fun getAnniversaryHistory(): Flow<List<AnniversaryItem>>

    suspend fun getAnniversary(eventId: Long): Flow<DetailAnniversary>

    suspend fun postAnniversary(@Body request: CreateAnniversary): Flow<Unit>

    suspend fun modifyAnniversary(@Path("eventId") request: ModifyAnniversary): Flow<Unit>

    suspend fun deleteAnniversary(@Path("eventId") eventId: Long): Flow<Unit>
}