package nexters.hyomk.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetDeviceInfoUseCase {
    suspend operator fun invoke(): Flow<String?>
}
