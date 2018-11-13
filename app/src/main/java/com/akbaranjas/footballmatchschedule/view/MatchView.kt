package com.akbaranjas.footballmatchschedule.view

import com.akbaranjas.footballmatchschedule.models.Match

interface MatchView {
    fun showLoading()
    fun hideLoading()
    fun showList(data: List<Match>)
    fun showError(error: Throwable)
}