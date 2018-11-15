package com.akbaranjas.footballmatchschedule.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.akbaranjas.footballmatchschedule.DetailActivity
import com.akbaranjas.footballmatchschedule.R.color.colorAccent
import com.akbaranjas.footballmatchschedule.adapter.FavoriteMatchAdapter
import com.akbaranjas.footballmatchschedule.db.FavoriteMatch
import com.akbaranjas.footballmatchschedule.presenter.FavoriteMatchPresenter
import com.akbaranjas.footballmatchschedule.util.EXTRA_MATCH
import com.akbaranjas.footballmatchschedule.util.invisible
import com.akbaranjas.footballmatchschedule.util.visible
import com.akbaranjas.footballmatchschedule.view.FavoriteMatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavouriteMatchFragment : Fragment(), AnkoComponent<Context>, FavoriteMatchView {

    private var favorites: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: FavoriteMatchAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var favoriteMatchPresenter: FavoriteMatchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteMatchAdapter(favorites) {
            ctx.startActivity<DetailActivity>(EXTRA_MATCH to "${it.eventId}")
        }

        listEvent.adapter = adapter
        favoriteMatchPresenter = FavoriteMatchPresenter(this, context!!)
        favoriteMatchPresenter.showFavoriteList()
        swipeRefresh.setOnRefreshListener {
            favoriteMatchPresenter.showFavoriteList()
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteMatchPresenter.showFavoriteList()
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            progressBar = progressBar {
            }.lparams {
                gravity = Gravity.CENTER_HORIZONTAL
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(
                    colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                listEvent = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showList(data: List<FavoriteMatch>) {
        favorites.clear()
        swipeRefresh.isRefreshing = false
        favorites.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
