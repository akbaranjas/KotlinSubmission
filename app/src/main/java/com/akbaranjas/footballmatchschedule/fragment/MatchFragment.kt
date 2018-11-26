package com.akbaranjas.footballmatchschedule.fragment


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.akbaranjas.footballmatchschedule.DetailActivity
import com.akbaranjas.footballmatchschedule.R
import com.akbaranjas.footballmatchschedule.`interface`.BtnEventInterface
import com.akbaranjas.footballmatchschedule.adapter.MatchListAdapter
import com.akbaranjas.footballmatchschedule.adapter.MatchTabAdapter
import com.akbaranjas.footballmatchschedule.models.Match
import com.akbaranjas.footballmatchschedule.presenter.SearchPresenter
import com.akbaranjas.footballmatchschedule.util.ApiInterface
import com.akbaranjas.footballmatchschedule.util.EXTRA_MATCH
import com.akbaranjas.footballmatchschedule.util.invisible
import com.akbaranjas.footballmatchschedule.util.visible
import com.akbaranjas.footballmatchschedule.view.SearchEventView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.toast


class MatchFragment : Fragment(), SearchEventView, BtnEventInterface {

    private var match: MutableList<Match>? = mutableListOf()
    private lateinit var viewPager: ViewPager
    private lateinit var tab: TabLayout
    private lateinit var listSearch:RecyclerView
    private lateinit var searchLayout:RelativeLayout
    private lateinit var progressBar:ProgressBar
    private lateinit var searchPresenter:SearchPresenter
    private lateinit var adapter:MatchListAdapter
    private val apiInterface by lazy {
        ApiInterface.create()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_match, container, false)
        viewPager = view.findViewById(R.id.viewpager_main)
        tab = view.findViewById(R.id.tabs_main)
        listSearch = view.findViewById(R.id.list_search)
        searchLayout = view.findViewById(R.id.searchLayout)
        progressBar = view.findViewById(R.id.search_progress)
        viewPager.adapter = MatchTabAdapter(childFragmentManager, requireActivity())
        tab.setupWithViewPager(viewPager)
        setHasOptionsMenu(true)

        adapter = MatchListAdapter(match!!,"SEARCH", this) {
            requireActivity().startActivity<DetailActivity>(EXTRA_MATCH to it.eventId)
        }
        listSearch.layoutManager = LinearLayoutManager(ctx)
        listSearch.adapter = adapter

        searchPresenter = SearchPresenter(this,apiInterface)
        searchLayout.invisible()
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.search_menu,menu)
        val searchItem : MenuItem = menu.findItem(R.id.searchMenu)
        val searchView : SearchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.isNullOrEmpty()) {
                    viewPager.visible()
                    return false
                }
                searchLayout.visible()
                viewPager.invisible()
                searchPresenter.getSearchResult(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewPager.visible()
                    searchLayout.invisible()
                    return false
                }
                searchLayout.visible()
                viewPager.invisible()
                searchPresenter.getSearchResult(newText.toString())
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

    override fun showList(data: List<Match>?) {
        data?.let {
            match?.clear()
            match?.addAll(it)
            adapter.notifyDataSetChanged()
        }

    }

    override fun showError(error: Throwable) {
        progressBar.invisible()
        toast(error.message!!)
        Log.e("Error", error.message)
    }

    override fun addEvent(match: Match) {

    }
}
