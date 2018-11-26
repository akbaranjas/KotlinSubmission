package com.akbaranjas.footballmatchschedule.models

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("player")
    val player: List<Player>
)