package com.akbaranjas.footballmatchschedule.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match(
    @SerializedName("idEvent")
    var eventId: String? = null,

    @SerializedName("strEvent")
    var eventName: String? = null,

    @SerializedName("intRound")
    var round: String? = null,

    @SerializedName("strHomeTeam")
    var homeTeam: String? = null,

    @SerializedName("strAwayTeam")
    var awayTeam: String? = null,

    @SerializedName("intHomeScore")
    var homeScore: String? = null,

    @SerializedName("intAwayScore")
    var awayScore: String? = null,

    @SerializedName("strHomeGoalDetails")
    var homeGoalScorer: String? = null,

    @SerializedName("strHomeRedCards")
    var homeRedCards: String? = null,

    @SerializedName("strHomeYellowCards")
    var homeYellowCard: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    var homeGK: String? = null,

    @SerializedName("strHomeLineupDefense")
    var homeDEF: String? = null,

    @SerializedName("strHomeLineupMidfield")
    var homeMID: String? = null,

    @SerializedName("strHomeLineupForward")
    var homeFWD: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    var homeSUB: String? = null,

    @SerializedName("strAwayGoalDetails")
    var homeAwayScorer: String? = null,

    @SerializedName("strAwayRedCards")
    var awayRedCards: String? = null,

    @SerializedName("strAwayYellowCards")
    var awayYellowCard: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    var awayGK: String? = null,

    @SerializedName("strAwayLineupDefense")
    var awayDEF: String? = null,

    @SerializedName("strAwayLineupMidfield")
    var awayMID: String? = null,

    @SerializedName("strAwayLineupForward")
    var awayFWD: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    var awaySUB: String? = null,

    @SerializedName("intHomeShots")
    var homeShot : String? = null,

    @SerializedName("intAwayShots")
    var awayShot : String? = null,

    @SerializedName("dateEvent")
    var date : String? ,

    @SerializedName("idHomeTeam")
    var idHomeTeam : String? = null,

    @SerializedName("idAwayTeam")
    var idAwayTeam : String? = null


) : Parcelable