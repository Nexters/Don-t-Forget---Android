package nexters.hyomk.domain.model

import java.util.Calendar

data class DetailAnniversary(
    val eventId: Long,
    val title: String,
    val lunarDate: Calendar,
    val solarDate: Calendar,
    val alarmSchedule: List<AlarmSchedule>,
    val content: String,
    val type: AnniversaryDateType,
)
