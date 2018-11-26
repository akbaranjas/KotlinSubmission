package com.akbaranjas.footballmatchschedule.view

import com.akbaranjas.footballmatchschedule.models.Player

interface PlayerDetailView{
    fun getPlayer(data: List<Player>)
    fun showLoading()
    fun hideLoading()
    fun showError(error: Throwable)
}