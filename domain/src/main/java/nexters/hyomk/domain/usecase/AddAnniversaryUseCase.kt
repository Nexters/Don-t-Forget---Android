package nexters.hyomk.domain.usecase

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.model.CreateAnniversary

interface AddAnniversaryUseCase {
    suspend fun invoke(request: CreateAnniversary): Flow<Unit>
}
