package com.akbaranjas.footballmatchschedule

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.akbaranjas.footballmatchschedule.R.color.colorAccent
import com.akbaranjas.footballmatchschedule.R.drawable.ic_add_to_favorites
import com.akbaranjas.footballmatchschedule.R.drawable.ic_added_to_favorites
import com.akbaranjas.footballmatchschedule.R.id.add_to_favorite
import com.akbaranjas.footballmatchschedule.R.menu.detail_menu
import com.akbaranjas.footballmatchschedule.models.Match
import com.akbaranjas.footballmatchschedule.models.Team
import com.akbaranjas.footballmatchschedule.presenter.DetailPresenter
import com.akbaranjas.footballmatchschedule.util.EXTRA_MATCH
import com.akbaranjas.footballmatchschedule.util.getDateFormat
import com.akbaranjas.footballmatchschedule.util.replaceSemiColon
import com.akbaranjas.footballmatchschedule.view.DetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.design.snackbar

class DetailActivity : AppCompatActivity(), DetailView {

    private lateinit var presenter: DetailPresenter
    private lateinit var eventId: String
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var match: Match

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        eventId = intent.getStringExtra(EXTRA_MATCH)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = DetailPresenter(this, ctx)
        presenter.getMatchDetail(eventId)
        swipe_refresh.setColorSchemeResources(
            colorAccent,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
        swipe_refresh.setOnRefreshListener {
            presenter.getMatchDetail(eventId)
        }
        presenter.favoriteState(eventId)

    }

    override fun getHomeTeam(data: List<Team>) {
        Picasso.get().load(data[0].teamBadge).fit().placeholder(R.drawable.progress_image).into(img_homeTeam)
    }

    override fun getAwayTeam(data: List<Team>) {
        Picasso.get().load(data[0].teamBadge).fit().placeholder(R.drawable.progress_image).into(img_awayTeam)
    }

    override fun showError(error: Throwable) {
        Log.e("Error", error.message)
    }

    private fun initData(matchDetail: Match) {

        detail_date.text = getDateFormat(matchDetail.date!!)
        txt_homeTeam.text = matchDetail.homeTeam
        txt_awayTeam.text = matchDetail.awayTeam
        txt_score.text = """${matchDetail.homeScore ?: ""} VS ${matchDetail.awayScore ?: ""}"""
        txt_goalScorerHome.text = replaceSemiColon(matchDetail.homeGoalScorer ?: "")
        txt_goalScorerAway.text = replaceSemiColon(matchDetail.homeAwayScorer ?: "")
        txt_homeShots.text = matchDetail.homeShot
        txt_awayShots.text = matchDetail.awayShot
        txt_homeGK.text = replaceSemiColon(matchDetail.homeGK ?: "")
        txt_awayGK.text = replaceSemiColon(matchDetail.awayGK ?: "")
        txt_homeDefense.text = replaceSemiColon(matchDetail.homeDEF ?: "")
        txt_awayDefense.text = replaceSemiColon(matchDetail.awayDEF ?: "-")
        txt_homeMid.text = replaceSemiColon(matchDetail.homeMID ?: "-")
        txt_awayMid.text = replaceSemiColon(matchDetail.awayMID ?: "")
        txt_homeForward.text = replaceSemiColon(matchDetail.homeFWD ?: "")
        txt_awayForward.text = replaceSemiColon(matchDetail.awayFWD ?: "")
        txt_homeSubs.text = replaceSemiColon(matchDetail.homeSUB ?: "")
        txt_awaySubs.text = replaceSemiColon(matchDetail.awaySUB ?: "")
        supportActionBar?.title = "Match Day " + matchDetail.round
        matchDetail.idHomeTeam?.let { presenter.getHomeTeam(it) }
        matchDetail.idAwayTeam?.let { presenter.getAwayTeam(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) presenter.removeFromFavorite(eventId) else presenter.addToFavorite(match)
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getDetailMatch(data: List<Match>) {
        match = data[0]
        swipe_refresh.isRefreshing = false
        initData(match)
    }

    override fun showLoading() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress_bar.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun favoriteState(isFav: Boolean) {
        isFavorite = isFav
    }

    override fun showSnackbar(msg: String) {
        snackbar(swipe_refresh, msg).show()
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }
}


