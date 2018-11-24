package com.akbaranjas.footballmatchschedule.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.akbaranjas.footballmatchschedule.DetailActivity
import com.akbaranjas.footballmatchschedule.R
import com.akbaranjas.footballmatchschedule.`interface`.BtnEventInterface
import com.akbaranjas.footballmatchschedule.adapter.MatchListAdapter
import com.akbaranjas.footballmatchschedule.models.Match
import com.akbaranjas.footballmatchschedule.presenter.MatchPresenter
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.util.EXTRA_MATCH
import com.akbaranjas.footballmatchschedule.util.invisible
import com.akbaranjas.footballmatchschedule.util.visible
import com.akbaranjas.footballmatchschedule.view.MatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast

class LastMatchFragment : Fragment(), MatchView, BtnEventInterface {

    private var match: MutableList<Match>? = mutableListOf()
    private lateinit var presenter: MatchPresenter
    private lateinit var adapter: MatchListAdapter
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var spinner: Spinner
    private val apiInterface by lazy {
        ApiInterface.create()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val ui = UI {
            frameLayout {
                lparams(width = matchParent, height = wrapContent)
                linearLayout {

                    lparams(matchParent, wrapContent)
                    orientation = LinearLayout.VERTICAL
                    topPadding = dip(16)
                    leftPadding = dip(16)
                    rightPadding = dip(16)
                    spinner = spinner()
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listTeam = recyclerView {
                            id = R.id.last_match_list
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

        val spinnerItems = resources.getStringArray(R.array.league)
        val leagueID = resources.getStringArray(R.array.league_id)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        adapter = MatchListAdapter(match!!,"LAST", this) {
            requireActivity().startActivity<DetailActivity>(EXTRA_MATCH to it.eventId)

        }
        listTeam.adapter = adapter

        presenter = MatchPresenter(this,apiInterface)

        presenter.getMatchList("4328")

        return ui.view
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

    override fun showList(data: List<Match>) {
        match?.clear()
        match?.addAll(data)
        adapter.notifyDataSetChanged()

    }

    override fun addEvent(match: Match) {

    }

}
