package com.akbaranjas.footballmatchschedule.util

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

const val EXTRA_MATCH = "match_extra"

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.GONE
}

fun getSubstringName(name: String): String {
    return if (name.length > 10) {
        name.substring(0, 9) + "..."
    } else {
        name
    }
}

fun getDateFormat(name: String): String {
    val newFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.US)
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(name)
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.HOUR, 7)
    val newdate = newFormat.format(calendar.time)

    return newdate.toString()
}

fun getTimeFormat(time: String): String{
    val newFormat = SimpleDateFormat("HH:mm", Locale.US)
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.US).parse(time)
    val calendar = Calendar.getInstance()

    calendar.time = sdf
    calendar.add(Calendar.HOUR, 7)
    val newtime = newFormat.format(calendar.time)

    return newtime.toString()

}

fun replaceSemiColon(name: String): String {
    return if (name.contains(";")) {
        name.replace(";", "\n")
    } else {
        "-"
    }
}

