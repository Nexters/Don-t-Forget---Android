package nexters.hyomk.domain.usecase

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.model.ModifyAnniversary

interface ModifyAnniversaryUserCase {
    suspend operator fun invoke(eventId: Long, request: ModifyAnniversary): Flow<*>
}
