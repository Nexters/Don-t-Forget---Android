package nexters.hyomk.data.model.response

import com.google.gson.annotations.SerializedName

data class DetailAnniversary(
    @SerializedName("eventId")
    val eventId: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("lunarDate")
    val lunarDate: String,
    @SerializedName("solarDate")
    val solarDate: String,
    @SerializedName("alarmSchedule")
    val alarmSchedule: List<String>,
    @SerializedName("content")
    val content: String,
)
