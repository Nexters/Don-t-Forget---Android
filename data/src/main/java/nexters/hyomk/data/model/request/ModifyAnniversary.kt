package nexters.hyomk.data.model.request

import com.google.gson.annotations.SerializedName

data class ModifyAnniversary(
    @SerializedName("title")
    val title: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("alarmSchedule")
    val alarmSchedule: List<String>,
    @SerializedName("content")
    val content: String,
)
