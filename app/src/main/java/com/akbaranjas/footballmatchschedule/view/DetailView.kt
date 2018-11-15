package com.akbaranjas.footballmatchschedule.view

import com.akbaranjas.footballmatchschedule.models.Match
import com.akbaranjas.footballmatchschedule.models.Team

interface DetailView {
    fun getDetailMatch(data: List<Match>)
    fun getHomeTeam(data: List<Team>)
    fun getAwayTeam(data: List<Team>)
    fun favoriteState(isFav: Boolean)
    fun showLoading()
    fun hideLoading()
    fun showError(error: Throwable)
    fun showSnackbar(msg: String)
}