package nexters.hyomk.domain.model

enum class AlarmSchedule(val value: String) {
    DDay("D_DAY"),
    OneDay("ONE_DAYS"),
    ThreeDay("THREE_DAYS"),
    Week("ONE_WEEKS"),
    BinaryWeek("TWO_WEEKS"),
    Month("ONE_MONTH"),
}
