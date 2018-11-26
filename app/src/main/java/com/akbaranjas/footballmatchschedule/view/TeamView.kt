package com.akbaranjas.footballmatchschedule.view

import com.akbaranjas.footballmatchschedule.models.Team

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Team>)
    fun showError(error: Throwable)
}