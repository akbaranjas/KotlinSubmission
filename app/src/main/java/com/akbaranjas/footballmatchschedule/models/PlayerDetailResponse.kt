package com.akbaranjas.footballmatchschedule.models

import com.google.gson.annotations.SerializedName

data class PlayerDetailResponse(
    @SerializedName("players")
    val player: List<Player>
)