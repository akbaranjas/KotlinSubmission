package com.akbaranjas.footballmatchschedule.presenter

import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.view.TeamView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TeamPresenter(private val view: TeamView, private val apiInterface: ApiInterface) {

    fun getTeamList(league: String) {
        view.showLoading()
        apiInterface.getTeamList(BuildConfig.TSDB_API_KEY, league)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    view.showTeamList(result.team)
                    view.hideLoading()
                },
                { error ->
                    view.showError(error)
                }

            )

    }
}