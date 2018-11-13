package com.akbaranjas.footballmatchschedule.models

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("strTeamBadge")
        var teamBadge: String?
    )