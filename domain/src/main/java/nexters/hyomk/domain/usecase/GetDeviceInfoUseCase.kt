package nexters.hyomk.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetDeviceInfoUseCase {
    suspend fun invoke(): Flow<String?>
}
