package com.akbaranjas.footballmatchschedule.presenter

import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.view.PlayerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PlayerPresenter(private val view: PlayerView, private val apiInterface: ApiInterface) {

    fun getPlayerList(teamId: String) {
        view.showLoading()
        apiInterface.getPlayerbyTeam(BuildConfig.TSDB_API_KEY, teamId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    view.showPlayerList(result.player)
                    view.hideLoading()
                },
                { error ->
                    view.showError(error)
                }

            )

    }
}