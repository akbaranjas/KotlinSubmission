package com.akbaranjas.footballmatchschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.akbaranjas.footballmatchschedule.models.Player
import com.akbaranjas.footballmatchschedule.presenter.PlayerDetailPresenter
import com.akbaranjas.footballmatchschedule.util.EXTRA_PLAYER
import com.akbaranjas.footballmatchschedule.util.formatHeight
import com.akbaranjas.footballmatchschedule.util.invisible
import com.akbaranjas.footballmatchschedule.util.visible
import com.akbaranjas.footballmatchschedule.view.PlayerDetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*
import org.jetbrains.anko.ctx

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailView {

    private lateinit var presenter: PlayerDetailPresenter
    private lateinit var playerId: String
    private lateinit var player: Player
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)
        playerId = intent.getStringExtra(EXTRA_PLAYER)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = PlayerDetailPresenter(this, ctx)
        presenter.getPlayerDetail(playerId)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getPlayer(data: List<Player>) {
        player = data[0]
        Picasso.get().load(data[0].playerThumb).placeholder(R.drawable.progress_image).into(player_banner)
        supportActionBar?.title = player.playerName
        txt_player_height.text = player.playerHeight?.let { formatHeight(it) }
        txt_player_weight.text = player.playerWeight
        player_position.text = player.playerPos
        player_desc.text = player.playerDesc
    }

    override fun showLoading() {
        progress_bar_player_detail.visible()
    }

    override fun hideLoading() {
        progress_bar_player_detail.invisible()
    }

    override fun showError(error: Throwable) {
        Log.e("Error", error.message)
    }
}
