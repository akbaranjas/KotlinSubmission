package com.akbaranjas.footballmatchschedule.models

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("events")
    val events: List<Match>
    )