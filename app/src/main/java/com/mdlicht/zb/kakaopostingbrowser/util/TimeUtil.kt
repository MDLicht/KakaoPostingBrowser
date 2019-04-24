package com.mdlicht.zb.kakaopostingbrowser.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


object TimeUtil {
    const val YYYY_MM_DD = "yyyy년 MM월 dd일"
    const val YYYY_MM_DD_a_hh_mm = "yyyy년 MM월 dd일 a hh시 mm분"
    const val YYYY_MM_DD_HH_mm_ss = "yyyy-MM-dd'T'HH:mm:ss."
    const val ONE_DAY_MILLISECONDS = 24 * 60 * 60 * 1000

    /**
     * @brief 전달된 날짜를 포맷에 맞춰 변환
     * @param date 변환하려는 날짜
     * @param fromFormat 변환하려는 날짜의 현재 포맷
     * @param toFormat 변환하려는 포맷
     * @return 변환된 날짜
     */
    fun convertDateFormat(date: String, fromFormat: String, toFormat: String): String {
        val fF: DateFormat = SimpleDateFormat(fromFormat, Locale.KOREA)
        val tF: DateFormat = SimpleDateFormat(toFormat, Locale.KOREA)

        return tF.format(fF.parse(date))
    }

    /**
     * @brief 현재 시간 정보를 포함한 Calendar 객체를 반환
     * @return 현재 시간 정보를 포함한 Calendar 객체
     */
    fun getCurrent(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        return calendar
    }

    /**
     * @brief 현재 시간 정보(Long 타입) 반환
     * @return 현재 시간 정보(Long 타입)
     */
    fun getCurrentTime(): Long {
        val calendar = getCurrent()

        return calendar.time.time
    }

    /**
     * @brief 전달된 포맷으로 변환한 현재 날짜 반환
     * @param format 변환하려는 포맷
     * @return 변환된 현재 날짜
     */
    fun getCurrentDate(format: String): String {
        val calendar: Calendar = getCurrent()
        val tF: DateFormat = SimpleDateFormat(format, Locale.KOREA)

        return tF.format(calendar.time)
    }

    /**
     * @brief 현재 날짜로부터 특정 필드에 값을 더한 후 전달된 포맷으로 변환한 현재 날짜 반환
     * @param format 변환하려는 포맷
     * @param dateType Calenar.YEAR Calendar.MONTH 등의 수치를 변경할 필드값
     * @param offset 전달한 dateType에 더할 값
     * @return 수치를 더한 후 변환된 날짜
     */
    fun getCalculatedFormatDate(format: String, dateType: Int, offset: Int): String {
        val calendar: Calendar = getCurrent()
        calendar.add(dateType, offset)
        val tF = SimpleDateFormat(format, Locale.KOREA)

        return tF.format(calendar.time)
    }

    /**
     * @brief 전달된 날짜가 현재보다 이전인지 여부 반환
     * @param date 확인할 날짜
     * @param format 비교할 날짜 포맷
     * @return 현재보다 이전인지 여부
     */
    fun isOldDate(date: String, format: String): Boolean {
        val now = Calendar.getInstance()
        now.timeInMillis = System.currentTimeMillis()

        val f = SimpleDateFormat(format, Locale.KOREA)
        val calendar = Calendar.getInstance()
        calendar.clear()
        calendar.time = f.parse(date)
        return now.after(calendar) // calendar(dday) 가 now 보다 이전(true)인지 아닌지(false)
    }

    fun getDateDiff(date: String, fromFormat: String, toFormat: String): Int {
        val today = Calendar.getInstance(Locale.KOREA)
        today.apply {
            clear()
            timeInMillis = System.currentTimeMillis()
        }
        val tf = SimpleDateFormat(toFormat, Locale.KOREA)
        val tDate = tf.parse(tf.format(today.time))

        val ff = SimpleDateFormat(toFormat, Locale.KOREA)
        val fDate = ff.parse(convertDateFormat(date, fromFormat, toFormat))

        val diff = tDate.time - fDate.time
        return (diff / ONE_DAY_MILLISECONDS).toInt()
    }
}