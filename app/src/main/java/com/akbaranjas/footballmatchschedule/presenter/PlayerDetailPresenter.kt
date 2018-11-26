package com.akbaranjas.footballmatchschedule.presenter

import android.content.Context
import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.view.PlayerDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PlayerDetailPresenter(private val view: PlayerDetailView, private val context: Context) {
    private val apiInterface by lazy {
        ApiInterface.create()
    }

    fun getPlayerDetail(playerID: String) {
        view.showLoading()
        apiInterface.getPlayerDetail(BuildConfig.TSDB_API_KEY, playerID)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    view.getPlayer(result.player)
                    view.hideLoading()
                },
                { error ->
                    view.showError(error)
                }

            )

    }
}