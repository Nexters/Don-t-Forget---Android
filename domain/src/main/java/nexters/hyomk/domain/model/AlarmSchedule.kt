package nexters.hyomk.domain.model

enum class AlarmSchedule(val value: String) {
    Month("1month"),
    BinaryWeek("2week"),
    Week("1week"),
    ThreeDay("3day"),
    DDay("dday"),
    OneDay("oneday"),
}
