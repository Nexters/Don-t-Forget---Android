package nexters.hyomk.domain.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun String.toCalendarFormat(format: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm")): Calendar {
    var date: Date? = null
    try {
        date = format.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar
}

fun Calendar.toFormatString(format: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")): String {
    return format.format(this.time)
}
