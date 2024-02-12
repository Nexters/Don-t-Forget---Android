package nexters.hyomk.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import nexters.hyomk.data.model.request.toRequestDTO
import nexters.hyomk.data.model.response.toDomain
import nexters.hyomk.data.remote.DontForgetService
import nexters.hyomk.data.util.SafeApiCall
import nexters.hyomk.domain.model.AnniversaryItem
import nexters.hyomk.domain.model.CreateAnniversary
import nexters.hyomk.domain.model.DetailAnniversary
import nexters.hyomk.domain.model.ModifyAnniversary
import nexters.hyomk.domain.repository.AnniversaryRepository
import javax.inject.Inject

class AnniversaryRepositoryImpl @Inject constructor(
    private val service: DontForgetService,
) : AnniversaryRepository {
    override suspend fun getAnniversaryHistory(): Flow<List<AnniversaryItem>> {
        return SafeApiCall.call(service.getAnniversaryHistory()).catch {
            throw it
        }.map { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun getAnniversary(eventId: Long): Flow<DetailAnniversary> = SafeApiCall.call(service.getAnniversary(eventId)).catch {
        throw it
    }.map { it.toDomain() }

    override suspend fun postAnniversary(request: CreateAnniversary): Flow<*> =
        SafeApiCall.call(service.postAnniversary(request.toRequestDTO()))

    override suspend fun modifyAnniversary(eventId: Long, request: ModifyAnniversary): Flow<*> = SafeApiCall.call(
        service.modifyAnniversary(
            eventId,
            request
                .toRequestDTO
                (),
        ),
    ).catch {
        throw it
    }.map { it }

    override suspend fun deleteAnniversary(eventId: Long): Flow<*> = SafeApiCall.call(service.deleteAnniversary(eventId))
}
