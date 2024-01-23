package nexters.hyomk.data.model.request

import com.google.gson.annotations.SerializedName
import nexters.hyomk.domain.model.CreateAnniversary

data class CreateAnniversaryDTO(
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

fun CreateAnniversary.toRequestDTO(): CreateAnniversaryDTO {
    return CreateAnniversaryDTO(
        title = this.title,
        date = this.date.toString(),
        type = this.type.value,
        alarmSchedule = this.alarmSchedule.map { it.value },
        content = this.content,
    )
}
