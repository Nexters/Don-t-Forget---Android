package nexters.hyomk.domain.model

import java.util.Calendar

data class ModifyAnniversary(
    val title: String,
    val date: Calendar,
    val type: AnniversaryDateType,
    val alarmSchedule: List<AlarmSchedule>,
    val content: String,
)
