package com.el3asas.utils.utils

import android.annotation.SuppressLint
import android.content.Context
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

const val TIME_FORMAT_12_H = "hh:MM a"
const val TIME_FORMAT_24_H = "HH:MM"

const val DATE_FORMAT_WITH_TIME = "yyyy-MM-DD hh:mm:ss"

const val DATE_FORMAT_WITHOUT_TIME = "yyyy-MM-DD"
const val NOTIFICATION_DATE_FORMAT_en = "dd/M/yyyy   hh:mm:ss a"
const val NOTIFICATION_DATE_FORMAT_ar = "hh:mm:ss a   yyyy:M:d"

const val DATE_FORMAT_SALARY_REPORT = "yyyy-d"

class Formatter(context: Context? = null) {
    private val pref by lazy { context?.applicationContext?.getSharedPreferences("appSetting", 0) }

    @SuppressLint("SimpleDateFormat")
    fun formatTime(time: String): String {
        return time.formatTime()
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateOnly(str: String): String {
        val stringFormatter12 = SimpleDateFormat(DATE_FORMAT_WITHOUT_TIME, Locale.ENGLISH)
        val stringFormatter24 = SimpleDateFormat(DATE_FORMAT_WITH_TIME, Locale.ENGLISH)
        val date: Date? = stringFormatter24.parse(str)
        return stringFormatter12.format(date!!)
    }

    fun Date.formatNotificationTime(): String =
        this.formatTimeNoti(
            pref?.getString("lang", Locale.getDefault().language) ?: Locale.getDefault().language
        )

}

fun Calendar.formatSalaryMonthYear(locale: Locale) =
    SimpleDateFormat(DATE_FORMAT_SALARY_REPORT, locale).format(this.time)

@SuppressLint("SimpleDateFormat")
fun String.getDateOnly(): String {
    val stringFormatter12 = SimpleDateFormat(DATE_FORMAT_WITHOUT_TIME, Locale.ENGLISH)
    val stringFormatter24 = SimpleDateFormat(DATE_FORMAT_WITH_TIME, Locale.ENGLISH)
    val date: Date? = stringFormatter24.parse(this)
    return stringFormatter12.format(date!!)
}

fun formatTime(calendar: Calendar, timeFormat: String): String {
    val formatter = SimpleDateFormat(timeFormat, Locale.ENGLISH)
    return formatter.format(calendar.time)
}

fun formatTime(timeInMills: Long, timeFormat: String): String {
    val calendar=Calendar.getInstance()
    calendar.time.time=timeInMills
    val formatter = SimpleDateFormat(timeFormat, Locale.ENGLISH)
    return formatter.format(calendar.time)
}

@SuppressLint("SimpleDateFormat")
fun String.formatTime(): String {
    val stringFormatter12 = SimpleDateFormat(TIME_FORMAT_12_H)
    val stringFormatter24 = SimpleDateFormat(TIME_FORMAT_24_H)
    val date: Date? = stringFormatter24.parse(this)
    return stringFormatter12.format(date!!)
}

fun Date.formatTimeNoti(lang: String): String =
    if (lang == "ar")
        SimpleDateFormat(NOTIFICATION_DATE_FORMAT_ar, Locale(lang)).format(this)
    else
        SimpleDateFormat(NOTIFICATION_DATE_FORMAT_en, Locale(lang)).format(this)

/***
 *take start and get defference time
 */
@SuppressLint("SimpleDateFormat")
fun String.differenceBetweenCurrAndMyTime(): Long {
    val current = Calendar.getInstance()
    val myTime = this.getTime()

    Timber.d("--------------- ${myTime.time}  ------- ${current.time}")

    return if (current.before(myTime))
        myTime.timeInMillis - current.timeInMillis
    else
        current.timeInMillis - myTime.timeInMillis
}


fun diffBetweenTwoTimes(t1: String, t2: String): Long {
    val time1 = t1.getTime()
    val time2 = t2.getTime()

    return if (time1.before(time2))
        time2.timeInMillis - time1.timeInMillis
    else
        time1.timeInMillis - time2.timeInMillis
}

/**
 * get value of inc or dec for timer
 */
@SuppressLint("SimpleDateFormat")
fun String.incDecTime(): Long {
    val current = Calendar.getInstance()
    val start = Calendar.getInstance()

    val stringFormatter24 = SimpleDateFormat(TIME_FORMAT_24_H)
    val date: Date = stringFormatter24.parse(this)!!

    start.time = date
    start.set(Calendar.DATE, current.get(Calendar.DATE))
    start.set(Calendar.YEAR, current.get(Calendar.YEAR))
    start.set(Calendar.MONTH, current.get(Calendar.MONTH))

    return if (current.before(start))
        -1000L
    else
        1000L
}

fun timeDownToStr(difference: Long): String {
    val hours = (difference / (1000 * 60 * 60)).toInt()
    val min = (difference - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)
    val second = (difference - (1000 * 60 * 60 * hours + min * 60 * 1000)).toInt() / 1000
    return String.format(
        Locale.ENGLISH,
        "%02d : %02d : %02d",
        if (hours > 0) hours else -hours,
        if (min > 0) min else -min,
        second
    )
}

@SuppressLint("SimpleDateFormat")
fun String.getTime(): Calendar {
    val calendar = Calendar.getInstance()
    val current = Calendar.getInstance()

    val stringFormatter24 = SimpleDateFormat(TIME_FORMAT_24_H)
    val date: Date = stringFormatter24.parse(this)!!

    calendar.time = date
    calendar.set(Calendar.DATE, current.get(Calendar.DATE))
    calendar.set(Calendar.YEAR, current.get(Calendar.YEAR))
    calendar.set(Calendar.MONTH, current.get(Calendar.MONTH))

    Timber.d("------------ ${calendar.time}")

    return calendar
}