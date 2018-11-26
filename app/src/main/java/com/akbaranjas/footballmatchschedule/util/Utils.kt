package com.akbaranjas.footballmatchschedule.util

import android.os.Build
import android.text.Html
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

const val EXTRA_MATCH = "match_extra"
const val EXTRA_TEAM = "team_extra"
const val EXTRA_PLAYER = "player_extra"

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

fun getSubstringPlayerName(name: String): String {
    var decoded = ""
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        decoded = Html.fromHtml(name, Html.FROM_HTML_MODE_COMPACT).toString()
    } else {
        decoded
    }
    return if (decoded.length > 20) {
        decoded.substring(0, 19) + ".."
    } else {
        decoded
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
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.US)
    val calendar = Calendar.getInstance()
    if(time.length == 5)
        calendar.time = newFormat.parse(time)
    else
        calendar.time = sdf.parse(time)
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

fun formatHeight(height:String): String{
    val index = height.indexOf("m")

    val h: String = if (index == -1) height else height.substring(0, index)
    return h
}

