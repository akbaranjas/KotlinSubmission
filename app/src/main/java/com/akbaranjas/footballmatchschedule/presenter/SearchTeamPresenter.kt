package com.akbaranjas.footballmatchschedule.presenter

import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.view.SearchTeamView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchTeamPresenter(private val view: SearchTeamView, private val apiInterface: ApiInterface) {

    fun getSearchTeamResult(search: String) {
        view.showLoading()
        apiInterface.searchTeam(BuildConfig.TSDB_API_KEY, search)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        result -> view.showTeam(result?.team)
                },
                {
                        error -> view.showError(error)
                },
                {
                    view.hideLoading()
                }

            )


    }
}