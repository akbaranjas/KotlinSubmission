package com.akbaranjas.footballmatchschedule.`interface`

import com.akbaranjas.footballmatchschedule.models.Match

interface BtnEventInterface {
    fun addEvent(match: Match)
}