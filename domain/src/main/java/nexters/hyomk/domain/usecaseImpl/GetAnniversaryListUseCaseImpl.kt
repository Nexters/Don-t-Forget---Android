package nexters.hyomk.domain.usecaseImpl

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.model.AnniversaryItem
import nexters.hyomk.domain.repository.AnniversaryRepository
import nexters.hyomk.domain.usecase.GetAnniversaryListUseCase
import javax.inject.Inject

class GetAnniversaryListUseCaseImpl @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository
) : GetAnniversaryListUseCase {
    override suspend fun invoke(): Flow<List<AnniversaryItem>> = anniversaryRepository.getAnniversaryHistory()
}
