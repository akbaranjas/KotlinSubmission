package com.akbaranjas.footballmatchschedule.presenter

import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.view.SearchEventView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchPresenter(private val view: SearchEventView, private val apiInterface: ApiInterface) {

    fun getSearchResult(search: String) {
        view.showLoading()
        apiInterface.searchEvent(BuildConfig.TSDB_API_KEY, search)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        result -> view.showList(result?.event)
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