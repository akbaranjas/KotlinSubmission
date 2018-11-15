package com.akbaranjas.footballmatchschedule.presenter

import android.content.Context
import com.akbaranjas.footballmatchschedule.db.FavoriteMatch
import com.akbaranjas.footballmatchschedule.db.database
import com.akbaranjas.footballmatchschedule.view.FavoriteMatchView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteMatchPresenter(private val favoriteMatchView: FavoriteMatchView, private val context: Context) {
    fun showFavoriteList() {
        favoriteMatchView.showLoading()
        context.database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favoriteMatchView.hideLoading()
            favoriteMatchView.showList(favorite)
        }
    }
}