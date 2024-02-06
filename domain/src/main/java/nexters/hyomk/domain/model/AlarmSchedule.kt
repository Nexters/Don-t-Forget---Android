package nexters.hyomk.domain.model

enum class AlarmSchedule(val value: String) {
    Month("ONE_MONTH"),
    BinaryWeek("TWO_WEEKS"),
    Week("ONE_WEEKS"),
    ThreeDay("THREE_DAYS"),
    OneDay("ONE_DAYS"),
    DDay("D_DAY"),
}
