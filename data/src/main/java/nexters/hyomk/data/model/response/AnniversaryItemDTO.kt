package nexters.hyomk.data.model.response

import com.google.gson.annotations.SerializedName
import nexters.hyomk.domain.model.AnniversaryCardType
import nexters.hyomk.domain.model.AnniversaryItem
import nexters.hyomk.domain.utils.toCalendarFormat
import java.text.SimpleDateFormat

data class AnniversaryItemDTO(
    @SerializedName("anniversaryId") val eventId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("lunarDate") val lunarDate: String,
    @SerializedName("solarDate") val solarDate: String,
    @SerializedName("cardType") val cardType: String,
)

@Suppress("SimpleDateFormat")
fun AnniversaryItemDTO.toDomain(): AnniversaryItem {
    return AnniversaryItem(
        eventId = eventId,
        title = title,
        lunarDate = this.lunarDate.toCalendarFormat(format = SimpleDateFormat("yyyy-MM-dd")),
        solarDate = this.solarDate.toCalendarFormat(format = SimpleDateFormat("yyyy-MM-dd")),
        cardType = AnniversaryCardType.valueOf(cardType),
    )
}
