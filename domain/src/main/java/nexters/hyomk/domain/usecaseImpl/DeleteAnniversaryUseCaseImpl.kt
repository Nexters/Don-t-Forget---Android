package nexters.hyomk.domain.usecaseImpl

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.repository.AnniversaryRepository
import nexters.hyomk.domain.usecase.DeleteAnniversaryUseCase
import javax.inject.Inject

class DeleteAnniversaryUseCaseImpl @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository,
) : DeleteAnniversaryUseCase {
    override suspend fun invoke(eventId: Long): Flow<*> = anniversaryRepository.deleteAnniversary(eventId)
}
