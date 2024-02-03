package nexters.hyomk.domain.usecase

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.model.AnniversaryItem

interface GetAnniversaryListUseCase {
    suspend operator fun invoke(): Flow<List<AnniversaryItem>>
}
