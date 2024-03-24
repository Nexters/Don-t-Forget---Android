package nexters.hyomk.dontforget.presentation.utils

fun Long.dayFormat(): String {
    return if (this == 0L) {
        "D-DAY"
    } else if (this > 0) {
        "D+$this"
    } else {
        "D$this"
    }
}
