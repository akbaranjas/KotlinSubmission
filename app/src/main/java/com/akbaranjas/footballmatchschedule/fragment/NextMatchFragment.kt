package com.akbaranjas.footballmatchschedule.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import com.akbaranjas.footballmatchschedule.DetailActivity
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
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat
import java.util.*


class NextMatchFragment : Fragment(), MatchView, BtnEventInterface {

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
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)
                        spinner = spinner()
                        listTeam = recyclerView {
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

        adapter = MatchListAdapter(match!!, "NEXT", this) {
            requireActivity().startActivity<DetailActivity>(EXTRA_MATCH to it.eventId)
        }
        listTeam.adapter = adapter

        presenter = MatchPresenter(this,apiInterface)

        presenter.getNextMatchList("4328")

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
        val datetime: String = match.date + " " + match.time?.replace("+00:00","")
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(datetime)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.HOUR, 7)
        val millisecond: Long = calendar.timeInMillis
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, millisecond)
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, millisecond + 90 * 60 * 1000)
        intent.putExtra(CalendarContract.Events.TITLE, match.homeTeam + " VS " + match.awayTeam)
        intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY")
        startActivity(intent)

    }


}
