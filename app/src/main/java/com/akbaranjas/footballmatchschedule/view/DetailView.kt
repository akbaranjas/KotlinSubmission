package com.akbaranjas.footballmatchschedule.view

import com.akbaranjas.footballmatchschedule.models.Team

interface DetailView {
    fun getHomeTeam(data: List<Team>)
    fun getAwayTeam(data: List<Team>)
    fun showError(error: Throwable)
}