package com.akbaranjas.footballmatchschedule.view

import com.akbaranjas.footballmatchschedule.models.Player

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(data: List<Player>)
    fun showError(error: Throwable)
}