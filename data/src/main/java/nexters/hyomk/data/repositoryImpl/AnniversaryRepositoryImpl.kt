package nexters.hyomk.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
//        return channelFlow {
//            val flowContext = currentCoroutineContext()
//            coroutineScope {
//                launch(flowContext) {
//                    SafeApiCall.call(service.getAnniversaryHistory()).catch {
//                        throw it
//                    }.collectLatest {
//                        val list = it.map { dto -> dto.toDomain() }
//                        send(list)
//                    }
//                }
//            }
//        }

        return SafeApiCall.call(service.getAnniversaryHistory())
            .catch {
                throw it
            }.map { it.map { dto -> dto.toDomain() } }
    }

    override suspend fun getAnniversary(eventId: Long): Flow<DetailAnniversary> = flow {
        SafeApiCall.call(service.getAnniversary(eventId)).catch {
            throw it
        }.map { it.toDomain() }
    }

    override suspend fun postAnniversary(request: CreateAnniversary): Flow<Unit> = flow {
        SafeApiCall.call(service.postAnniversary(request.toRequestDTO())).onEach {
            emit(it)
        }.catch {
            throw it
        }
    }

    override suspend fun modifyAnniversary(request: ModifyAnniversary): Flow<Unit> = flow {
        SafeApiCall.call(service.modifyAnniversary(request.toRequestDTO())).onEach {
            emit(it)
        }.catch {
            throw it
        }
    }

    override suspend fun deleteAnniversary(eventId: Long): Flow<Unit> = flow {
        SafeApiCall.call(service.deleteAnniversary(eventId)).onEach {
            emit(it)
        }.catch {
            throw it
        }
    }
}
