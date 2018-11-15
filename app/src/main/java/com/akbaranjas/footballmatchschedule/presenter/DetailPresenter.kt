package com.akbaranjas.footballmatchschedule.presenter

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.db.FavoriteMatch
import com.akbaranjas.footballmatchschedule.db.database
import com.akbaranjas.footballmatchschedule.models.Match
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.view.DetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailPresenter(private val view: DetailView, private val context: Context) {
    private val apiInterface by lazy {
        ApiInterface.create()
    }

    fun favoriteState(id: String) {
        context.database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE)
                .whereArgs(
                    "(EVENT_ID = {id})",
                    "id" to id
                )
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) {
                view.favoriteState(true)
            } else {
                view.favoriteState(false)
            }
        }
    }

    fun getMatchDetail(eventid: String) {
        view.showLoading()
        apiInterface.getDetailMatch(BuildConfig.TSDB_API_KEY, eventid)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    view.getDetailMatch(result.events)
                    view.hideLoading()
                },
                { error ->
                    view.showError(error)
                }

            )

    }

    fun getHomeTeam(teamid: String) {

        apiInterface.getTeam(BuildConfig.TSDB_API_KEY, teamid)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    view.getHomeTeam(result.team)
                },
                { error ->
                    view.showError(error)
                }

            )

    }

    fun getAwayTeam(teamid: String) {

        apiInterface.getTeam(BuildConfig.TSDB_API_KEY, teamid)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    view.getAwayTeam(result.team)
                },
                { error ->
                    view.showError(error)
                }

            )

    }

    fun addToFavorite(match: Match) {
        try {
            context.database.use {
                insert(
                    FavoriteMatch.TABLE_FAVORITE,
                    FavoriteMatch.EVENT_ID to match.eventId,
                    FavoriteMatch.AWAY_TEAM to match.awayTeam,
                    FavoriteMatch.HOME_TEAM to match.homeTeam,
                    FavoriteMatch.AWAY_TEAM_SCORE to match.awayScore,
                    FavoriteMatch.HOME_TEAM_SCORE to match.homeScore,
                    FavoriteMatch.EVENT_DATE to match.date
                )
            }
            view.showSnackbar("Added to favorite")

        } catch (e: SQLiteConstraintException) {
            view.showSnackbar(e.localizedMessage)
        }
    }

    fun removeFromFavorite(eventid: String) {
        try {
            context.database.use {
                delete(
                    FavoriteMatch.TABLE_FAVORITE,
                    "(EVENT_ID = {id})",
                    "id" to eventid
                )
            }
            view.showSnackbar("Remove from favorite")
        } catch (e: SQLiteConstraintException) {
            view.showSnackbar(e.localizedMessage)
        }
    }

}