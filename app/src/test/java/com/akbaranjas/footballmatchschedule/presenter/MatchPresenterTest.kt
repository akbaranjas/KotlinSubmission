package com.akbaranjas.footballmatchschedule.presenter

import com.akbaranjas.footballmatchschedule.BuildConfig
import com.akbaranjas.footballmatchschedule.models.Match
import com.akbaranjas.footballmatchschedule.models.MatchResponse
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.view.MatchView
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins

class MatchPresenterTest{

    @Mock
    private
    lateinit var view: MatchView

    @Mock
    private
    lateinit var apiInterface: ApiInterface

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view,apiInterface)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }

    }
    @Test
    fun testGetMatchList(){
        val match: MutableList<Match> = mutableListOf()
        val matchresponse = MatchResponse(match)

        `when`(apiInterface.getMatchList(BuildConfig.TSDB_API_KEY, "4328")).thenReturn(Single.just(matchresponse))

        presenter.getMatchList("4328")

        Mockito.verify(view).showList(match)
    }



}