package com.akbaranjas.footballmatchschedule.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.akbaranjas.footballmatchschedule.DetailTeamActivity
import com.akbaranjas.footballmatchschedule.PlayerDetailActivity
import com.akbaranjas.footballmatchschedule.R
import com.akbaranjas.footballmatchschedule.adapter.PlayerListAdapter
import com.akbaranjas.footballmatchschedule.models.Player
import com.akbaranjas.footballmatchschedule.models.Team
import com.akbaranjas.footballmatchschedule.presenter.PlayerPresenter
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.util.EXTRA_PLAYER
import com.akbaranjas.footballmatchschedule.util.invisible
import com.akbaranjas.footballmatchschedule.util.visible
import com.akbaranjas.footballmatchschedule.view.PlayerView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.toast

class PlayerListFragment : Fragment(), AnkoComponent<Context>, PlayerView, DetailTeamActivity.OnTeamIDReceivedListener {

    private lateinit var listPlayer: RecyclerView
    private var player: MutableList<Player>? = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerListAdapter
    private lateinit var progressBar: ProgressBar
    private val apiInterface by lazy {
        ApiInterface.create()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        frameLayout {
            lparams(width = matchParent, height = wrapContent)
            linearLayout {

                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL
                topPadding = dip(16)
                leftPadding = dip(16)
                rightPadding = dip(16)
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listPlayer = recyclerView {
                        id = R.id.player_list
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mActivity = activity as DetailTeamActivity?
        mActivity?.setOnTeamIDListener(this)
        adapter = PlayerListAdapter(player!!) {
            requireActivity().startActivity<PlayerDetailActivity>(EXTRA_PLAYER to it.playerId)

        }
        listPlayer.adapter = adapter

        presenter = PlayerPresenter(this,apiInterface)


    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }


    override fun showError(error: Throwable) {
        progressBar.invisible()
        toast(error.message!!)
        Log.e("Error", error.message)
    }

    override fun showPlayerList(data: List<Player>) {
        player?.clear()
        player?.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onTeamIDReceived(teamID: String) {
            presenter.getPlayerList(teamID)
    }
}
