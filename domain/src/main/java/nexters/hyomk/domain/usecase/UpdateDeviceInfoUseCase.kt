package nexters.hyomk.domain.usecase

interface UpdateDeviceInfoUseCase {
    suspend operator fun invoke(deviceId: String)
}
