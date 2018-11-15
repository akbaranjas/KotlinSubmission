package com.akbaranjas.footballmatchschedule.presenter

import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.view.MatchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MatchPresenter(private val view: MatchView) {

    private val apiInterface by lazy {
        ApiInterface.create()
    }

    fun getMatchList(league: String) {
        view.showLoading()
        apiInterface.getMatchList(BuildConfig.TSDB_API_KEY, league)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    view.showList(result.events)
                    view.hideLoading()
                },
                { error ->
                    view.showError(error)
                }

            )

    }

    fun getNextMatchList(league: String) {
        view.showLoading()
        apiInterface.getNextMatchList(BuildConfig.TSDB_API_KEY, league)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    view.showList(result.events)
                    view.hideLoading()
                },
                { error ->
                    view.showError(error)
                }

            )

    }


}

