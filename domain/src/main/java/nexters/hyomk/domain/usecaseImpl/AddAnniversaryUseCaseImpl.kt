package nexters.hyomk.domain.usecaseImpl

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.model.CreateAnniversary
import nexters.hyomk.domain.repository.AnniversaryRepository
import nexters.hyomk.domain.usecase.AddAnniversaryUseCase
import javax.inject.Inject

class AddAnniversaryUseCaseImpl @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository,
) : AddAnniversaryUseCase {
    override suspend fun invoke(request: CreateAnniversary): Flow<Unit> = anniversaryRepository.postAnniversary(request)
}
