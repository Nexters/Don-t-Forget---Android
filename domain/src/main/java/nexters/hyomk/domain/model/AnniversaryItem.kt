package nexters.hyomk.domain.model

import java.util.Calendar

data class AnniversaryItem(
    val eventId: Long,
    val title: String,
    val lunarDate: Calendar,
    val solarDate: Calendar,
    val cardType: AnniversaryCardType,
)
