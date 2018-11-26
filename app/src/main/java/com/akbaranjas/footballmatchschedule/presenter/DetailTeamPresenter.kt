package com.akbaranjas.footballmatchschedule.presenter

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.db.FavoriteTeam
import com.akbaranjas.footballmatchschedule.db.database
import com.akbaranjas.footballmatchschedule.models.Team
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.view.DetailTeamView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.delete

class DetailTeamPresenter(private val view: DetailTeamView, private val context: Context) {
    private val apiInterface by lazy {
        ApiInterface.create()
    }

    fun favoriteState(id: String) {
        context.database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs(
                    "(TEAM_ID = {id})",
                    "id" to id
                )
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) {
                view.favoriteState(true)
            } else {
                view.favoriteState(false)
            }
        }
    }

    fun getTeamDetail(teamid: String) {
        view.showLoading()
        apiInterface.getTeam(BuildConfig.TSDB_API_KEY, teamid)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    view.getTeam(result.team)
                    view.hideLoading()
                },
                { error ->
                    view.showError(error)
                }

            )

    }

    fun addToFavorite(team: Team) {
        try {
            context.database.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to team.teamId,
                    FavoriteTeam.TEAM_NAME to team.teamName,
                    FavoriteTeam.TEAM_BADGE to team.teamBadge
                )
            }
            view.showSnackbar("Added to favorite")

        } catch (e: SQLiteConstraintException) {
            view.showSnackbar(e.localizedMessage)
        }
    }

    fun removeFromFavorite(teamid: String) {
        try {
            context.database.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    "(TEAM_ID = {id})",
                    "id" to teamid
                )
            }
            view.showSnackbar("Remove from favorite")
        } catch (e: SQLiteConstraintException) {
            view.showSnackbar(e.localizedMessage)
        }
    }

}