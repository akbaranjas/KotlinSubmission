package com.akbaranjas.footballmatchschedule.view

import com.akbaranjas.footballmatchschedule.models.Team

interface DetailTeamView {
    fun getTeam(data: List<Team>)
    fun favoriteState(isFav: Boolean)
    fun showLoading()
    fun hideLoading()
    fun showError(error: Throwable)
    fun showSnackbar(msg: String)
}