package nexters.hyomk.dontforget.ui.language

import nexters.hyomk.domain.model.AlarmSchedule

abstract class TransGuide() {
    abstract val appName: String
    abstract val complete: String
    abstract val cancel: String

    abstract val createTitle: String
    abstract val save: String

    abstract val anniversaryTitle: String
    abstract val createHint: String
    abstract val dateTitle: String
    abstract val solarTabTitle: String
    abstract val lunarTabTitle: String

    abstract val year: String
    abstract val month: String
    abstract val day: String

    abstract val notificationTitle: String
    abstract fun transNotificationPeriod(alarmSchedule: AlarmSchedule): String
    abstract val memoTitle: String
    abstract val memoHint: String
}
