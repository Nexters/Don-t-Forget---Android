package nexters.hyomk.domain.usecase

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.model.FcmInfo

interface UpdateAnniversaryAlarmStateUseCase {
    suspend fun invoke(request: FcmInfo): Flow<Unit>
}
