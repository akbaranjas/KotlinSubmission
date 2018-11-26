package com.akbaranjas.footballmatchschedule.models

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("event")
    val event: List<Match>
)