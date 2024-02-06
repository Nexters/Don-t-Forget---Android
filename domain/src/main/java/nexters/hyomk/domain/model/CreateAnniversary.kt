package nexters.hyomk.domain.model

import java.util.Calendar

data class CreateAnniversary(
    val title: String,
    val date: Calendar,
    val calendarType: AnniversaryDateType,
    val cardType: AnniversaryCardType,
    val alarmSchedule: List<AlarmSchedule>,
    val content: String,
)
