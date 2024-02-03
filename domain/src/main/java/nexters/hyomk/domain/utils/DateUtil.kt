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

fun calculateDDay(date: Date, base: Calendar = Calendar.getInstance()): Long {
    val dday = (base.time.time - date.time) / (60 * 60 * 24 * 1000)
    return dday
}
