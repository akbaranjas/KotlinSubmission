package com.akbaranjas.footballmatchschedule.view

import com.akbaranjas.footballmatchschedule.db.FavoriteMatch

interface FavoriteMatchView {
    fun showLoading()
    fun hideLoading()
    fun showList(data: List<FavoriteMatch>)
}