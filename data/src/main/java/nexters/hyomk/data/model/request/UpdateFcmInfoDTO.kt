package nexters.hyomk.data.model.request

import com.google.gson.annotations.SerializedName
import nexters.hyomk.domain.model.FcmInfo

data class UpdateFcmInfoDTO(
    @SerializedName("token") val token: String,
    @SerializedName("deviceUuid") val deviceUuid: String,
    @SerializedName("status") val status: String,
)

fun FcmInfo.toRequestDTO(): UpdateFcmInfoDTO {
    return UpdateFcmInfoDTO(
        token = this.token,
        deviceUuid = this.deviceId,
        status = this.status.name,
    )
}
