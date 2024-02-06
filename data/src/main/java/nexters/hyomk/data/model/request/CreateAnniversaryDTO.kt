package nexters.hyomk.data.model.request

import com.google.gson.annotations.SerializedName
import nexters.hyomk.domain.model.CreateAnniversary
import nexters.hyomk.domain.utils.toFormatString
import java.text.SimpleDateFormat

data class CreateAnniversaryDTO(
    @SerializedName("title")
    val title: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("calendarType")
    val calendarType: String,
    @SerializedName("cardType")
    val cardType: String,
    @SerializedName("alarmSchedule")
    val alarmSchedule: List<String>,
    @SerializedName("content")
    val content: String,
)

fun CreateAnniversary.toRequestDTO(): CreateAnniversaryDTO {
    return CreateAnniversaryDTO(
        title = this.title,
        date = this.date.toFormatString(SimpleDateFormat("yyyy-MM-dd")),
        calendarType = this.calendarType.value,
        alarmSchedule = this.alarmSchedule.map { it.value },
        content = this.content,
        cardType = this.cardType.name,
    )
}
