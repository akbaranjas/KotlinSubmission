package com.akbaranjas.footballmatchschedule.view

import com.akbaranjas.footballmatchschedule.models.Team

interface SearchTeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeam(data: List<Team>?)
    fun showError(error: Throwable)
}