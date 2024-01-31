package nexters.hyomk.dontforget.ui.language

import nexters.hyomk.domain.model.AlarmSchedule

class KorGuide() : TransGuide() {
    override val appName: String = "챙겨챙겨"
    override val complete: String = "완료"
    override val close: String = "닫기"
    override val cancel: String = "취소"
    override val anniversaryTitle: String = "기념일 이름"
    override val createTitle: String = "기념일 만들기"
    override val save: String = "저장"
    override val createHint: String = "사랑하는 엄마 생일"
    override val dateTitle: String = "날짜"
    override val solarTabTitle: String = "양력으로 입력"
    override val lunarTabTitle: String = "음력으로 입력"
    override val year: String = "년"
    override val month: String = "월"
    override val day: String = "일"
    override val notificationTitle: String = "미리 알림"
    override val createDialogContent: String = "만들고 있던 기념일은\n" + "저장되지 않고, 사라집니다."
    override val createDialogTitle: String = "기념일 만들기를 취소할까요?"
    override val check: String = "확인"
    override val next: String = "다음"

    override fun transNotificationPeriod(alarmSchedule: AlarmSchedule): String {
        return when (alarmSchedule) {
            AlarmSchedule.Month -> "1달 전"
            AlarmSchedule.BinaryWeek -> "2주 전"
            AlarmSchedule.Week -> "1주 전"
            AlarmSchedule.ThreeDay -> "3일 전"
            AlarmSchedule.DDay -> "하루 전"
        }
    }

    override val memoTitle: String = "간단 메모"
    override val memoHint: String = "가족 여행 미리 계획하기"
}
