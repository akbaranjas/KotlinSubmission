package com.akbaranjas.footballmatchschedule.util

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

const val EXTRA_MATCH = "match_extra"

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun getSubstringName(name : String): String{
    return if(name.length > 10){
        name.substring(0,9)+"..."
    }else{
        name
    }
}

fun getDateFormat(name : String): String{
    val newFormat = SimpleDateFormat("EEE, dd MMM yyyy", Locale.US)
    var date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(name)
    val newdate = newFormat.format(date)
    return newdate.toString()
}

fun replaceSemiColon(name: String): String{
    return if(name.contains(";")) {
        name.replace(";", "\n")
    }else{
        "-"
    }
}
