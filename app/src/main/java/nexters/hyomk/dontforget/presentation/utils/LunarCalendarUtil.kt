package nexters.hyomk.dontforget.presentation.utils

import java.text.NumberFormat

object LunarCalendarUtil {
    private val DayOfMonth = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31) // 양력 월별 일수

    // 1 : 작(29일), 2 : 큰(30일), 3 : 작작(윤달 - 29일,29일), 4 : 작큰(윤달 - 29일,30일), 5 : 큰작(윤달 - 30일,29일), 6 : 큰큰(윤달 - 30일,30일)
    private val LunarData = arrayOf(
        intArrayOf(1, 2, 1, 1, 2, 1, 2, 5, 2, 2, 1, 2, 384),
        intArrayOf(1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 354),
        intArrayOf(2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 355),
        intArrayOf(1, 2, 1, 2, 1, 3, 2, 1, 1, 2, 2, 1, 383),
        intArrayOf(2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 354),
        intArrayOf(2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 355),
        intArrayOf(1, 2, 2, 4, 1, 2, 1, 2, 1, 2, 1, 2, 384),
        intArrayOf(1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 354),
        intArrayOf(2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 355),
        intArrayOf(1, 5, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 384),
        intArrayOf(1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 354),
        intArrayOf(2, 1, 2, 1, 1, 5, 1, 2, 2, 1, 2, 2, 384),
        intArrayOf(2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 354),
        intArrayOf(2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 354),
        intArrayOf(2, 2, 1, 2, 5, 1, 2, 1, 2, 1, 1, 2, 384),
        intArrayOf(2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 355),
        intArrayOf(1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 354),
        intArrayOf(2, 3, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 384),
        intArrayOf(2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 2, 355),
        intArrayOf(1, 2, 1, 1, 2, 1, 5, 2, 2, 1, 2, 2, 384),
        intArrayOf(1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 354),
        intArrayOf(2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 354),
        intArrayOf(2, 1, 2, 2, 3, 2, 1, 1, 2, 1, 2, 2, 384),
        intArrayOf(1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 354),
        intArrayOf(2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1, 354),
        intArrayOf(2, 1, 2, 5, 2, 1, 2, 2, 1, 2, 1, 2, 385),
        intArrayOf(1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 354),
        intArrayOf(2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 355),
        intArrayOf(1, 5, 1, 2, 1, 1, 2, 2, 1, 2, 2, 2, 384),
        intArrayOf(1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 354),
        intArrayOf(1, 2, 2, 1, 1, 5, 1, 2, 1, 2, 2, 1, 383),
        intArrayOf(2, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 354),
        intArrayOf(2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 355),
        intArrayOf(1, 2, 2, 1, 6, 1, 2, 1, 2, 1, 1, 2, 384),
        intArrayOf(1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 355),
        intArrayOf(1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 354),
        intArrayOf(2, 1, 4, 1, 2, 1, 2, 1, 2, 2, 2, 1, 384),
        intArrayOf(2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 354),
        intArrayOf(2, 2, 1, 1, 2, 1, 4, 1, 2, 2, 2, 1, 384),
        intArrayOf(2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 1, 2, 354),
        intArrayOf(2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 354),
        intArrayOf(2, 2, 1, 2, 2, 4, 1, 1, 2, 1, 2, 1, 384),
        intArrayOf(2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 355),
        intArrayOf(1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 355),
        intArrayOf(1, 1, 2, 4, 1, 2, 1, 2, 2, 1, 2, 2, 384),
        intArrayOf(1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 354),
        intArrayOf(2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 354),
        intArrayOf(2, 5, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 384),
        intArrayOf(2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 354),
        intArrayOf(2, 2, 1, 2, 1, 2, 3, 2, 1, 2, 1, 2, 384),
        intArrayOf(2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 354),
        intArrayOf(2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 355),
        intArrayOf(1, 2, 1, 2, 4, 2, 1, 2, 1, 2, 1, 2, 384),
        intArrayOf(1, 2, 1, 1, 2, 2, 1, 2, 2, 1, 2, 2, 355),
        intArrayOf(1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 354),
        intArrayOf(2, 1, 4, 1, 1, 2, 1, 2, 1, 2, 2, 2, 384),
        intArrayOf(1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 354),
        intArrayOf(2, 1, 2, 1, 2, 1, 1, 5, 2, 1, 2, 2, 384),
        intArrayOf(1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 354),
        intArrayOf(1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 354),
        intArrayOf(2, 1, 2, 1, 2, 5, 2, 1, 2, 1, 2, 1, 384),
        intArrayOf(2, 1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 355),
        intArrayOf(1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 354),
        intArrayOf(2, 1, 2, 3, 2, 1, 2, 1, 2, 2, 2, 1, 384),
        intArrayOf(2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 355),
        intArrayOf(1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 353),
        intArrayOf(2, 2, 5, 2, 1, 1, 2, 1, 1, 2, 2, 1, 384),
        intArrayOf(2, 2, 1, 2, 2, 1, 1, 2, 1, 2, 1, 2, 355),
        intArrayOf(1, 2, 2, 1, 2, 1, 5, 2, 1, 2, 1, 2, 384),
        intArrayOf(1, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 354),
        intArrayOf(2, 1, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 355),
        intArrayOf(1, 2, 1, 1, 5, 2, 1, 2, 2, 2, 1, 2, 384),
        intArrayOf(1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 354),
        intArrayOf(2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 1, 354),
        intArrayOf(2, 2, 1, 5, 1, 2, 1, 1, 2, 2, 1, 2, 384),
        intArrayOf(2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 354),
        intArrayOf(2, 2, 1, 2, 1, 2, 1, 5, 2, 1, 1, 2, 384),
        intArrayOf(2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 354),
        intArrayOf(2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 355),
        intArrayOf(2, 1, 1, 2, 1, 6, 1, 2, 2, 1, 2, 1, 384),
        intArrayOf(2, 1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 355),
        intArrayOf(1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 354),
        intArrayOf(2, 1, 2, 3, 2, 1, 1, 2, 2, 1, 2, 2, 384),
        intArrayOf(2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 354),
        intArrayOf(2, 1, 2, 2, 1, 1, 2, 1, 1, 5, 2, 2, 384),
        intArrayOf(1, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 354),
        intArrayOf(1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 1, 354),
        intArrayOf(2, 1, 2, 2, 1, 5, 2, 2, 1, 2, 1, 2, 385),
        intArrayOf(1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 354),
        intArrayOf(2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 2, 355),
        intArrayOf(1, 2, 1, 1, 5, 1, 2, 1, 2, 2, 2, 2, 384),
        intArrayOf(1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 354),
        intArrayOf(1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 354),
        intArrayOf(1, 2, 5, 2, 1, 2, 1, 1, 2, 1, 2, 1, 383),
        intArrayOf(2, 2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 355),
        intArrayOf(1, 2, 2, 1, 2, 2, 1, 5, 2, 1, 1, 2, 384),
        intArrayOf(1, 2, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 355),
        intArrayOf(1, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 354),
        intArrayOf(2, 1, 1, 2, 3, 2, 2, 1, 2, 2, 2, 1, 384),
        intArrayOf(2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 354),
        intArrayOf(2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 354),
        intArrayOf(2, 2, 2, 3, 2, 1, 1, 2, 1, 2, 1, 2, 384),
        intArrayOf(2, 2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 354),
        intArrayOf(2, 2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 355),
        intArrayOf(1, 5, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 384),
        intArrayOf(1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 1, 354),
        intArrayOf(2, 1, 2, 1, 2, 1, 5, 2, 2, 1, 2, 2, 385),
        intArrayOf(1, 1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 354),
        intArrayOf(2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 354),
        intArrayOf(2, 2, 1, 1, 5, 1, 2, 1, 2, 1, 2, 2, 384),
        intArrayOf(2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 354),
        intArrayOf(2, 1, 2, 2, 1, 2, 1, 1, 2, 1, 2, 1, 354),
        intArrayOf(2, 1, 6, 2, 1, 2, 1, 1, 2, 1, 2, 1, 384),
        intArrayOf(2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 355),
        intArrayOf(1, 2, 1, 2, 1, 2, 1, 2, 5, 2, 1, 2, 384),
        intArrayOf(1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 2, 2, 355),
        intArrayOf(1, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 354),
        intArrayOf(2, 1, 1, 2, 3, 2, 1, 2, 1, 2, 2, 2, 384),
        intArrayOf(1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 354),
        intArrayOf(2, 1, 2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 354),
        intArrayOf(2, 1, 2, 5, 2, 1, 1, 2, 1, 2, 1, 2, 384),
        intArrayOf(1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 354),
        intArrayOf(2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 355),
        intArrayOf(1, 5, 2, 1, 2, 1, 2, 2, 1, 2, 1, 2, 384),
        intArrayOf(1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 354),
        intArrayOf(2, 1, 2, 1, 1, 5, 2, 1, 2, 2, 2, 1, 384),
        intArrayOf(2, 1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 355),
        intArrayOf(1, 2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 2, 354),
        intArrayOf(1, 2, 2, 1, 5, 1, 2, 1, 1, 2, 2, 1, 383),
        intArrayOf(2, 2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 2, 355),
        intArrayOf(1, 2, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 354),
        intArrayOf(2, 1, 5, 2, 1, 2, 2, 1, 2, 1, 2, 1, 384),
        intArrayOf(2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 355),
        intArrayOf(1, 2, 1, 1, 2, 1, 5, 2, 2, 2, 1, 2, 384),
        intArrayOf(1, 2, 1, 1, 2, 1, 2, 1, 2, 2, 2, 1, 354),
        intArrayOf(2, 1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 354),
        intArrayOf(2, 2, 1, 2, 1, 4, 1, 1, 2, 1, 2, 2, 384),
        intArrayOf(2, 2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 354),
        intArrayOf(2, 2, 1, 2, 1, 2, 1, 2, 1, 1, 2, 1, 354),
        intArrayOf(2, 2, 1, 2, 5, 2, 1, 2, 1, 2, 1, 1, 384),
        intArrayOf(2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 1, 355),
        intArrayOf(2, 1, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 355),
        intArrayOf(1, 5, 1, 2, 1, 2, 1, 2, 2, 1, 2, 2, 384),
        intArrayOf(1, 2, 1, 1, 2, 1, 1, 2, 2, 1, 2, 2, 354),
        intArrayOf(2, 1, 2, 1, 1, 2, 3, 2, 1, 2, 2, 2, 384),
        intArrayOf(2, 1, 2, 1, 1, 2, 1, 1, 2, 1, 2, 2, 354),
        intArrayOf(2, 1, 2, 2, 1, 1, 2, 1, 1, 2, 1, 2, 354),
        intArrayOf(2, 1, 2, 2, 4, 1, 2, 1, 1, 2, 1, 2, 384),
        intArrayOf(1, 2, 2, 1, 2, 2, 1, 2, 1, 1, 1, 1, 353),
        intArrayOf(2, 1, 2, 1, 2, 2, 1, 2, 2, 1, 2, 1, 355),
        intArrayOf(2, 1, 4, 1, 2, 1, 2, 2, 1, 2, 2, 1, 384),
    ) // 2000년
    private val LunarDataNumberDay = intArrayOf(29, 30, 58, 59, 59, 60) // 인덱스 숫자의 합

    /**
     * 해당 연도가 윤년인지 여부를 체크한다.
     *
     * @param Year
     * -
     * @return -
     */
    fun checkYunYear(Year: Int): Boolean {
        return if (Year % 4 == 0 && (Year % 100 != 0 || Year % 400 == 0)) {
            true
        } else {
            false
        }
    }

    /**
     * 음력을 양력으로 변환하는 메소드이다.
     *
     * @param TranseDay - 음력날짜
     * @param leapyes - 윤달 여부
     * @return - 변환된 양력일자
     */
    fun lunar2Solar(TranseDay: String, leapyes: Boolean): String {
        val lyear = TranseDay.substring(0, 4).toInt()
        val lmonth = TranseDay.substring(4, 6).toInt()
        val lday = TranseDay.substring(6, 8).toInt()
        val coutnAllDay = countLunarDay(lyear, lmonth, lday, leapyes)
        return countToDateForSolar(coutnAllDay)
    }

    /**
     * 양력을 음력으로 변환하는 메소드이다.
     *
     * @param TranseDay - 양력 일자
     * @return - 변환된 음력일자
     */
    fun solar2Lunar(TranseDay: String): String {
        val lyear = TranseDay.substring(0, 4).toInt()
        val lmonth = TranseDay.substring(4, 6).toInt()
        val lday = TranseDay.substring(6, 8).toInt()
        val coutnAllDay = countSolarDay(lyear, lmonth, lday)
        return countToDateForLunar(coutnAllDay)
    }

    /**
     * 음력의 총날짜수를 돌려준다. 만약 돌려줄수 없는 날이라면 0을 돌려준다
     *
     * @param Year
     * -
     * @param Month
     * -
     * @param Day
     * -
     * @param Leap
     * -
     * @return -
     */
    private fun countLunarDay(Year: Int, Month: Int, Day: Int, Leap: Boolean): Long {
        var Year = Year
        var AllCount: Long = 0
        val ResultValue: Long
        var i: Int
        Year -= 1900
        AllCount += countSolarDay(1900, 1, 30)
        if (Year >= 0) {
            i = 0
            while (i <= Year - 1) {
                AllCount += LunarData[i][12].toLong()
                i++
            }
            i = 0
            while (i <= Month - 2) {
                AllCount += LunarDataNumberDay[LunarData[Year][i] - 1].toLong()
                i++
            }
            if (!Leap) {
                AllCount += Day.toLong()
            } else {
                if (LunarData[Year][Month - 1] == 1 || LunarData[Year][Month - 1] == 2) {
                    AllCount += Day.toLong()
                } else if (LunarData[Year][Month - 1] == 3 || LunarData[Year][Month - 1] == 4) {
                    AllCount += (Day + 29).toLong()
                } else if (LunarData[Year][Month - 1] == 5 || LunarData[Year][Month - 1] == 6) {
                    AllCount += (Day + 30).toLong()
                }
            }
            ResultValue = AllCount
        } else {
            ResultValue = 0
        }
        return ResultValue
    }
    /* 특징 : 양력의 총 날짜수를 돌려준다 */
    /**
     * 양력의 총 날짜수를 돌려준다
     *
     * @param Year
     * -
     * @param Month
     * -
     * @param Day
     * -
     * @return -
     */
    private fun countSolarDay(Year: Int, Month: Int, Day: Int): Long {
        var i: Int
        var j: Int
        var AllCount: Long = 366
        i = 1
        while (i <= Year - 1) {
            AllCount += if (checkYunYear(i)) {
                366
            } else {
                365
            }
            i++
        }
        j = 1
        while (j <= Month - 1) {
            if (j == 2) {
                if (checkYunYear(Year)) {
                    AllCount += 29
                } else {
                    AllCount += DayOfMonth[j - 1].toLong()
                }
            } else {
                AllCount += DayOfMonth[j - 1].toLong()
            }
            j++
        }
        AllCount += Day.toLong()
        return AllCount
    }

    /**
     * 총날짜를 가지고 가지고 음력 날짜을 변환해서 반환한다.
     *
     * @param AllCountDay - 총일자
     * @return - 변환된 음력일자
     */
    private fun countToDateForLunar(AllCountDay: Long): String {
        var AllCount: Long
        var RepeatStop: Boolean
        var Year = 0
        var Month = 1
        var Day = 0
        RepeatStop = false
        AllCount = AllCountDay
        AllCount -= countSolarDay(1900, 1, 30)
        do {
            if (AllCount > LunarData[Year][12]) {
                AllCount -= LunarData[Year][12].toLong()
                Year += 1 // 년 계산
            } else {
                if (AllCount > LunarDataNumberDay[LunarData[Year][Month - 1] - 1]) {
                    // 월계산
                    AllCount -= LunarDataNumberDay[LunarData[Year][Month - 1] - 1].toLong()
                    Month += 1
                } else {
                    if (LunarData[Year][Month - 1] == 1 || LunarData[Year][Month - 1] == 2) {
                        Day = java.lang.Long.toString(AllCount).toInt()
                    } else if (LunarData[Year][Month - 1] == 3 || LunarData[Year][Month - 1] == 4) {
                        Day = if (AllCount <= 29) {
                            java.lang.Long.toString(AllCount).toInt()
                        } else {
                            java.lang.Long.toString(AllCount).toInt() - 29
                        }
                    } else if (LunarData[Year][Month - 1] == 5 || LunarData[Year][Month - 1] == 6) {
                        Day = if (AllCount <= 30) {
                            java.lang.Long.toString(AllCount).toInt()
                        } else {
                            java.lang.Long.toString(AllCount).toInt() - 30
                        }
                    }
                    RepeatStop = true
                }
            }
        } while (!RepeatStop)
        val nf = NumberFormat.getNumberInstance()
        nf.minimumIntegerDigits = 2
        return java.lang.Long.toString((Year + 1900).toLong()) + nf.format(
            java.lang.Long.toString(Month.toLong()).toInt(),
        ) + nf.format(java.lang.Long.toString(Day.toLong()).toInt())
    }

    /**
     * 총날짜를 가지고 가지고 양력 날짜을 변환해서 반환한다.
     *
     * @param AllCountDay - 총일자
     * @return - 변환된 양력 일자
     */
    private fun countToDateForSolar(AllCountDay: Long): String {
        var AllCountDay = AllCountDay
        var YearRepeatStop: Boolean
        var MonthRepeatStop: Boolean
        YearRepeatStop = false
        MonthRepeatStop = false
        var Year = 0
        var Month = 1
        do {
            if (checkYunYear(Year)) {
                if (AllCountDay > 366) {
                    AllCountDay -= 366
                    Year += 1
                } else {
                    YearRepeatStop = true
                }
            } else {
                if (AllCountDay > 365) {
                    AllCountDay -= 365
                    Year += 1
                } else {
                    YearRepeatStop = true
                }
            }
        } while (!YearRepeatStop)
        do {
            if (Month == 2) {
                if (checkYunYear(Year)) {
                    if (AllCountDay > 29) {
                        AllCountDay -= 29
                        Month += 1
                    } else {
                        MonthRepeatStop = true
                    }
                } else {
                    if (AllCountDay > 28) {
                        AllCountDay -= 28
                        Month += 1
                    } else {
                        MonthRepeatStop = true
                    }
                }
            } else {
                if (AllCountDay > DayOfMonth[Month - 1]) {
                    AllCountDay -= DayOfMonth[Month - 1].toLong()
                    Month += 1
                } else {
                    MonthRepeatStop = true
                }
            }
        } while (!MonthRepeatStop)
        val nf = NumberFormat.getNumberInstance()
        nf.minimumIntegerDigits = 2
        return java.lang.Long.toString(Year.toLong()) + nf.format(
            java.lang.Long.toString(Month.toLong()).toInt(),
        ) + nf.format(java.lang.Long.toString(AllCountDay).toInt())
    }
}
