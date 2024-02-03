package nexters.hyomk.data.model.response

import com.google.gson.annotations.SerializedName
import nexters.hyomk.domain.model.AlarmSchedule
import nexters.hyomk.domain.model.AnniversaryDateType
import nexters.hyomk.domain.model.DetailAnniversary
import nexters.hyomk.domain.utils.toCalendarFormat
import java.text.SimpleDateFormat

data class DetailAnniversaryDTO(
    @SerializedName("eventId") val eventId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("lunarDate") val lunarDate: String,
    @SerializedName("solarDate") val solarDate: String,
    @SerializedName("alarmSchedule") val alarmSchedule: List<String>,
    @SerializedName("content") val content: String,
    @SerializedName("type") val type: String,
)

@Suppress("SimpleDateFormat")
fun DetailAnniversaryDTO.toDomain(): DetailAnniversary {
    return DetailAnniversary(
        eventId = eventId,
        title = title,
        lunarDate = this.lunarDate.toCalendarFormat(SimpleDateFormat("yyyy-MM-dd")),
        solarDate = this.solarDate.toCalendarFormat(SimpleDateFormat("yyyy-MM-dd")),
        alarmSchedule = this.alarmSchedule.map { AlarmSchedule.valueOf(it) },
        content = content,
        type = AnniversaryDateType.valueOf(type),
    )
}
