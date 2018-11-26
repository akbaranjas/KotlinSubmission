package com.akbaranjas.footballmatchschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.akbaranjas.footballmatchschedule.adapter.TeamDetailTabAdapter
import com.akbaranjas.footballmatchschedule.models.Team
import com.akbaranjas.footballmatchschedule.presenter.DetailTeamPresenter
import com.akbaranjas.footballmatchschedule.util.EXTRA_TEAM
import com.akbaranjas.footballmatchschedule.util.invisible
import com.akbaranjas.footballmatchschedule.util.visible
import com.akbaranjas.footballmatchschedule.view.DetailTeamView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.design.snackbar

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {


    private lateinit var presenter: DetailTeamPresenter
    private lateinit var teamID: String
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var team: Team
    private var mDescDataListener: OnDescDataReceivedListener? = null
    private var mTeamIDListener: OnTeamIDReceivedListener? = null

    interface OnDescDataReceivedListener {
        fun onDataReceived(team: Team)
    }

    fun setAboutDataListener(listener: OnDescDataReceivedListener) {
        this.mDescDataListener = listener
    }

    interface OnTeamIDReceivedListener {
        fun onTeamIDReceived(teamID: String)
    }

    fun setOnTeamIDListener(listener: OnTeamIDReceivedListener) {
        this.mTeamIDListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        container_detail.adapter = TeamDetailTabAdapter(supportFragmentManager,ctx)
        tabs_detail.setupWithViewPager(container_detail)

        teamID = intent.getStringExtra(EXTRA_TEAM)
        setSupportActionBar(toolbar_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        presenter = DetailTeamPresenter(this, ctx)
        presenter.getTeamDetail(teamID)
        presenter.favoriteState(teamID)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) presenter.removeFromFavorite(teamID) else presenter.addToFavorite(team)
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    override fun getTeam(data: List<Team>) {
        team = data[0]
        Picasso.get().load(data[0].teamBadge).fit().placeholder(R.drawable.progress_image).into(badge_team_detail)
        team_name_detail.text = data[0].teamName
        team_year_build.text = data[0].teamFormedYear
        team_stadium.text = data[0].teamStadium
        mDescDataListener?.onDataReceived(team)
        mTeamIDListener?.onTeamIDReceived(teamID)
    }

    override fun favoriteState(isFav: Boolean) {
        isFavorite = isFav
    }

    override fun showLoading() {
        progress_detail_team.visible()
    }

    override fun hideLoading() {
        progress_detail_team.invisible()
    }

    override fun showError(error: Throwable) {
        Log.e("Error", error.message)
    }

    override fun showSnackbar(msg: String) {
        snackbar(backdrop, msg).show()
    }

}
