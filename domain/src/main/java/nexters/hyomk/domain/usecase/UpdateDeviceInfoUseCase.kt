package nexters.hyomk.domain.usecase

interface UpdateDeviceInfoUseCase {
    suspend fun invoke(deviceId: String)
}
