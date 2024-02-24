package nexters.hyomk.domain.usecaseImpl

import kotlinx.coroutines.flow.Flow
import nexters.hyomk.domain.model.ModifyAnniversary
import nexters.hyomk.domain.repository.AnniversaryRepository
import nexters.hyomk.domain.usecase.ModifyAnniversaryUserCase
import javax.inject.Inject

class ModifyAnniversaryUseCaseImpl @Inject constructor(
    private val anniversaryRepository: AnniversaryRepository,
) : ModifyAnniversaryUserCase {
    override suspend operator fun invoke(eventId: Long, request: ModifyAnniversary): Flow<*> =
        anniversaryRepository.modifyAnniversary(eventId, request)
}
