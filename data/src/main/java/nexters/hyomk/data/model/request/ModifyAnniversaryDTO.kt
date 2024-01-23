package nexters.hyomk.data.model.request

import com.google.gson.annotations.SerializedName
import nexters.hyomk.domain.model.ModifyAnniversary

data class ModifyAnniversaryDTO(
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

fun ModifyAnniversary.toRequestDTO(): ModifyAnniversaryDTO {
    return ModifyAnniversaryDTO(
        title = this.title,
        date = this.date.toString(),
        type = this.type.value,
        alarmSchedule = this.alarmSchedule.map { it.value },
        content = this.content,
    )
}
