package nexters.hyomk.data.model.response

import com.google.gson.annotations.SerializedName
import nexters.hyomk.domain.model.AnniversaryItem
import nexters.hyomk.domain.utils.toCalendarFormat

data class AnniversaryItemDTO(
    @SerializedName("eventId") val eventId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("lunarDate") val lunarDate: String,
    @SerializedName("solarDate") val solarDate: String,
)

fun AnniversaryItemDTO.toDomain(): AnniversaryItem {
    return AnniversaryItem(
        eventId = eventId,
        title = title,
        lunarDate = this.lunarDate.toCalendarFormat(),
        solarDate = this.solarDate.toCalendarFormat(),
    )
}
