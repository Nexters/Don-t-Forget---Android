package nexters.hyomk.domain.usecaseImpl

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.model.DetailAnniversary
import nexters.hyomk.domain.repository.AnniversaryRepository
import nexters.hyomk.domain.usecase.GetDetailAnniversaryUseCase
import javax.inject.Inject

class GetDetailAnniversaryUseCaseImpl @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository,
) : GetDetailAnniversaryUseCase {
    override suspend fun invoke(eventId: Long): Flow<DetailAnniversary> = anniversaryRepository.getAnniversary(eventId)
}
