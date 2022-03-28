package com.el3asas.utils.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class Formatter {

    fun timeToStringg(date: Date, format: String, lang: String): String =
        date.timeToString(format, lang)

    fun toDatee(time: String, format: String, lang: String) = time.toDate(format, lang)

    fun changeFormatt(
        time: String,
        fromFormat: String,
        toFormat: String,
        lang: String = Locale.ENGLISH.language
    ) = time.changeFormat(fromFormat, toFormat, lang)

    fun diffBetweenTwoTimess(time1: Calendar, time2: Calendar): Long =
        diffBetweenTwoTimes(time1, time2)

    fun formatTimee(
        calendar: Calendar,
        timeFormat: String,
        lang: String = Locale.ENGLISH.language
    ) = calendar.formatTime(timeFormat, lang)

    fun getTimerr(time: Long): String = time.getTimer()

}

@SuppressLint("SimpleDateFormat")
fun String.changeFormat(
    fromFormat: String,
    toFormat: String,
    lang: String = Locale.ENGLISH.language
): String {
    val to = SimpleDateFormat(toFormat, Locale(lang))
    val from = SimpleDateFormat(fromFormat, Locale(lang))
    val date: Date? = from.parse(this)
    return date?.let { to.format(it) } ?: "Time Not Format"
}

fun Calendar.formatTime(timeFormat: String, lang: String = Locale.ENGLISH.language): String {
    val formatter = SimpleDateFormat(timeFormat, Locale(lang))
    return formatter.format(this.time)
}

fun Date.timeToString(format: String, lang: String): String =
    SimpleDateFormat(format, Locale(lang)).format(this)

fun String.toDate(format: String, lang: String = Locale.ENGLISH.language): Date {
    val formatter = SimpleDateFormat(format, Locale(lang))
    return formatter.parse(this) ?: Date()
}

fun diffBetweenTwoTimes(time1: Calendar, time2: Calendar): Long {
    return if (time1.before(time2))
        time2.timeInMillis - time1.timeInMillis
    else
        time1.timeInMillis - time2.timeInMillis
}

/**
 * get timer string from long 01:00:00
 * */
fun Long.getTimer(): String {
    val hours = (this / (1000 * 60 * 60)).toInt()
    val min = (this - 1000 * 60 * 60 * hours).toInt() / (1000 * 60)
    val second = (this - (1000 * 60 * 60 * hours + min * 60 * 1000)).toInt() / 1000
    return String.format(
        Locale.ENGLISH,
        "%02d : %02d : %02d",
        if (hours > 0) hours else -hours,
        if (min > 0) min else -min,
        second
    )
}