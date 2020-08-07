package com.almoon.foundation_lib.utils

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

/**
 * Date Util
 */
open class DateUtil {
    var locale: Locale = Locale.CHINA

    /**
     *  Turn time to string
     */
    fun timeSlashData(time: Long): String? {
        val sdr = SimpleDateFormat("yyyy-MM-dd", locale)
        return sdr.format(Date(time))
    }

    /**
     *  Get current time string
     */
    fun getPresentTime(): String? {
        val simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", locale)
        return simpleDateFormat.format(System.currentTimeMillis())
    }

    /**
     *  Get time string from int
     */
    fun getDateFormat(year: Int, month: Int, day: Int): String? {
        return year.toString() + "-" + getDisplayNumber(month) + "-" + getDisplayNumber(day)
    }

    /**
     *  Get date format
     */
    fun getDateFormat(calendar: Calendar): String? {
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        return year.toString() + "-" + getDisplayNumber(month) + "-" + getDisplayNumber(day)
    }

    /**
     *  Get display number
     *  Example, 9 -> 09
     */
    fun getDisplayNumber(num: Int): String {
        return if (num < 10) "0$num" else "" + num
    }

    /**
     *  Get time string by
     */
    fun getDateTimeByMillisecond(time: Long, type: String?): String {
        val date = Date(time)
        val format = SimpleDateFormat(type, locale)
        return format.format(date)
    }

    fun getDateAndWeek(
        time: Long,
        type: String?,
        separator: String
    ): String? {
        return getDateTimeByMillisecond(time, type) + separator + changeDataToWeek(time)
    }

    fun changeDataToWeek(time: Long): String? {
        val sdr = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", locale)
        val times = sdr.format(Date(time)) ?: return null
        val date: Date
        var myDate = 0
        var week: String? = null
        try {
            date = sdr.parse(times) ?: return null
            val cd = Calendar.getInstance()
            cd.time = date
            myDate = cd[Calendar.DAY_OF_WEEK]
        } catch (e: Exception) {
            e.printStackTrace()
        }
        when (myDate) {
            1 -> {
                week = "星期日"
            }
            2 -> {
                week = "星期一"
            }
            3 -> {
                week = "星期二"
            }
            4 -> {
                week = "星期三"
            }
            5 -> {
                week = "星期四"
            }
            6 -> {
                week = "星期五"
            }
            7 -> {
                week = "星期六"
            }
        }
        return week
    }

    fun dateToTimestamp(time: String, type: String): Long? {
        val format = SimpleDateFormat(type, locale)
        val pos = ParsePosition(0)
        val timestamp = format.parse(time, pos) ?: return null
        return timestamp.time
    }


    fun timeStampToDate(timeStamp: Long): String {
        val date = Date(timeStamp)
        val simpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)
        return simpleDateFormat.format(date)
    }


    fun getYearByTimeStamp(timeStamp: Long): Int {
        val date = timeStampToDate(timeStamp)
        val year = date.substring(0, 4)
        return year.toInt()
    }

    fun getMonthByTimeStamp(timeStamp: Long): Int {
        val date = timeStampToDate(timeStamp)
        val month = date.substring(5, 7)
        return month.toInt()
    }

    fun getDayByTimeStamp(timeStamp: Long): Int {
        val date = timeStampToDate(timeStamp)
        val day = date.substring(8, 10)
        return day.toInt()
    }

    fun getDateTipFromTimestamp(timestamp: Long): String? {
        // 获得当前时间并向下取整
        val today = floor((System.currentTimeMillis() / (1000 * 60 * 60 * 24)).toDouble()).toFloat()
        val target = timestamp.toFloat() / (1000 * 60 * 60 * 24)

        //Log.e("test", today + "===" + target);
        val day = today - target
        val date: String
        date = if (day <= 0f) {
            "今天"
        } else if (day > 0f && day <= 1f) {
            "昨天"
        } else if (day > 1f && day <= 2f) {
            "前天"
        } else {
            getDateTimeByMillisecond(timestamp, "MM-dd") + " " + changeDataToWeek(timestamp)
        }
        return date
    }

    fun getDateTipFromTimestamp(timestamp: Long, type: String?): String? {
        val today = floor((System.currentTimeMillis() / (1000 * 60 * 60 * 24)).toDouble()).toFloat()
        val target = timestamp.toFloat() / (1000 * 60 * 60 * 24)
        val day = today - target
        val date: String
        date = if (day <= 0f) {
            "今天"
        } else if (day > 0f && day <= 1f) {
            "昨天"
        } else if (day > 1f && day <= 2f) {
            "前天"
        } else {
            getDateTimeByMillisecond(timestamp, type)
        }
        return date
    }

    fun getTimeFormatHMMD(timestamp: Long): String? {
        return getDateTimeByMillisecond(timestamp, "HH:mm") + " " + getDateTimeByMillisecond(
            timestamp,
            "MM/dd"
        )
    }


    fun getTimeQuantumFromTimestamp(
        startTime: Long,
        endTime: Long
    ): String? {
        return getDateTimeByMillisecond(
            startTime,
            "HH:mm"
        ) + "-" + getDateTimeByMillisecond(endTime, "HH:mm")
    }


    fun getMonth(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar[Calendar.MONTH] + 1
    }

    fun getDay(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar[Calendar.DAY_OF_MONTH]
    }

    fun getYear(timestamp: Long): Int {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar[Calendar.YEAR]
    }


    fun getHour(second: Long): Long {
        return second / 3600
    }

    fun getMinute(second: Long): Long {
        return second % 3600 / 60
    }


    fun getChineseMonth(timestamp: Long): String? {
        return when (getMonth(timestamp)) {
            1 -> "一月"
            2 -> "二月"
            3 -> "三月"
            4 -> "四月"
            5 -> "五月"
            6 -> "六月"
            7 -> "七月"
            8 -> "八月"
            9 -> "九月"
            10 -> "十月"
            11 -> "十一月"
            12 -> "十二月"
            else -> "未知"
        }
    }
}