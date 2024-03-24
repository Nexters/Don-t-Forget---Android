package nexters.hyomk.domain.model

data class FcmInfo(
    val token: String,
    val deviceId: String,
    val status: AlarmStatus,
)
