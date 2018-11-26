package com.akbaranjas.footballmatchschedule.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.*
import com.akbaranjas.footballmatchschedule.R
import com.akbaranjas.footballmatchschedule.R.array.league
import com.akbaranjas.footballmatchschedule.R.color.colorAccent
import com.akbaranjas.footballmatchschedule.adapter.TeamsAdapter
import com.akbaranjas.footballmatchschedule.models.Team
import com.akbaranjas.footballmatchschedule.presenter.SearchTeamPresenter
import com.akbaranjas.footballmatchschedule.presenter.TeamPresenter
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.util.invisible
import com.akbaranjas.footballmatchschedule.util.visible
import com.akbaranjas.footballmatchschedule.view.SearchTeamView
import com.akbaranjas.footballmatchschedule.view.TeamView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast
import android.support.v7.widget.SearchView
import com.akbaranjas.footballmatchschedule.DetailTeamActivity
import com.akbaranjas.footballmatchschedule.util.EXTRA_TEAM

class TeamFragment : Fragment(), AnkoComponent<Context>, TeamView , SearchTeamView{

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var searchPresenter: SearchTeamPresenter
    private lateinit var adapter: TeamsAdapter
    private lateinit var spinner: Spinner
    private lateinit var listTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String
    private val apiInterface by lazy {
        ApiInterface.create()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = TeamsAdapter(teams) {
            context?.startActivity<DetailTeamActivity>(EXTRA_TEAM to "${it.teamId}")
        }
        listTeam.adapter = adapter

        presenter = TeamPresenter(this, apiInterface)
        searchPresenter = SearchTeamPresenter(this,apiInterface)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.setOnRefreshListener {
            presenter.getTeamList(leagueName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.spinner_team
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listTeam = recyclerView {
                        id = R.id.list_team
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.search_menu,menu)
        val searchItem : MenuItem = menu.findItem(R.id.searchMenu)
        val searchView : SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    spinner.visible()
                    if (::leagueName.isInitialized) {
                        presenter.getTeamList(leagueName)
                    }
                    return false
                }
                spinner.invisible()
                searchPresenter.getSearchTeamResult(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    spinner.visible()
                    if (::leagueName.isInitialized) {
                        presenter.getTeamList(leagueName)
                    }
                    return false
                }
                spinner.invisible()
                searchPresenter.getSearchTeamResult(newText.toString())
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showError(error: Throwable) {
        progressBar.invisible()
        toast(error.message!!)
        Log.e("Error", error.message)
    }

    override fun showTeam(data: List<Team>?) {
        data?.let {
            teams?.clear()
            teams?.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

}