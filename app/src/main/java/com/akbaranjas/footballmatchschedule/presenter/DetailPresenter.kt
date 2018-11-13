package com.akbaranjas.footballmatchschedule.presenter

import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.view.DetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailPresenter(private val view: DetailView){
    private val apiInterface by lazy {
        ApiInterface.create()
    }

    fun getHomeTeam(teamid: String) {

        apiInterface.getTeam(BuildConfig.TSDB_API_KEY,teamid)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        result -> view.getHomeTeam(result.team)
                },
                {
                        error -> view.showError(error)
                }

            )

    }

    fun getAwayTeam(teamid: String) {

        apiInterface.getTeam(BuildConfig.TSDB_API_KEY,teamid)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        result -> view.getAwayTeam(result.team)
                },
                {
                        error -> view.showError(error)
                }

            )

    }

}