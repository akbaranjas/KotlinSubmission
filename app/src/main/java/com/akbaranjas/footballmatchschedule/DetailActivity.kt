package com.akbaranjas.footballmatchschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.akbaranjas.footballmatchschedule.models.Match
import com.akbaranjas.footballmatchschedule.models.Team
import com.akbaranjas.footballmatchschedule.presenter.DetailPresenter
import com.akbaranjas.footballmatchschedule.util.EXTRA_MATCH
import com.akbaranjas.footballmatchschedule.util.getDateFormat
import com.akbaranjas.footballmatchschedule.util.replaceSemiColon
import com.akbaranjas.footballmatchschedule.view.DetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast

class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var matchDetail: Match
    private lateinit var presenter: DetailPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        matchDetail = intent.getParcelableExtra(EXTRA_MATCH)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Matchday " + matchDetail.round

        initData()

        presenter = DetailPresenter(this)
        presenter.getHomeTeam(matchDetail.idHomeTeam!!)
        presenter.getAwayTeam(matchDetail.idAwayTeam!!)
    }

    override fun getHomeTeam(data: List<Team>) {
        Picasso.get().load(data[0].teamBadge).fit().placeholder(R.drawable.progress_image).into(img_homeTeam)
    }

    override fun getAwayTeam(data: List<Team>) {
        Picasso.get().load(data[0].teamBadge).fit().placeholder(R.drawable.progress_image).into(img_awayTeam)
    }

    override fun showError(error: Throwable) {
        toast(error.message!!)
        Log.e("Error", error.message)
    }

    private fun initData(){

        detail_date.text = getDateFormat(matchDetail.date!!)
        txt_homeTeam.text = matchDetail.homeTeam
        txt_awayTeam.text = matchDetail.awayTeam
        txt_score.text = """${matchDetail.homeScore ?: ""} VS ${matchDetail.awayScore ?: ""}"""
        txt_goalScorerHome.text = replaceSemiColon(matchDetail.homeGoalScorer ?: "")
        txt_goalScorerAway.text = replaceSemiColon(matchDetail.homeAwayScorer ?: "")
        txt_homeShots.text = matchDetail.homeShot
        txt_awayShots.text = matchDetail.awayShot
        txt_homeGK.text = replaceSemiColon(matchDetail.homeGK?: "")
        txt_awayGK.text = replaceSemiColon(matchDetail.awayGK?: "")
        txt_homeDefense.text = replaceSemiColon(matchDetail.homeDEF?: "")
        txt_awayDefense.text = replaceSemiColon(matchDetail.awayDEF?: "-")
        txt_homeMid.text = replaceSemiColon(matchDetail.homeMID?: "-")
        txt_awayMid.text = replaceSemiColon(matchDetail.awayMID?: "")
        txt_homeForward.text = replaceSemiColon(matchDetail.homeFWD?: "")
        txt_awayForward.text = replaceSemiColon(matchDetail.awayFWD?: "")
        txt_homeSubs.text = replaceSemiColon(matchDetail.homeSUB?: "")
        txt_awaySubs.text = replaceSemiColon(matchDetail.awaySUB?: "")


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}


