package com.akbaranjas.footballmatchschedule.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.akbaranjas.footballmatchschedule.R
import com.akbaranjas.footballmatchschedule.`interface`.BtnEventInterface
import com.akbaranjas.footballmatchschedule.models.Match
import com.akbaranjas.footballmatchschedule.util.*
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class MatchListAdapter(private val teams: List<Match>, private val type: String, private val btnListener: BtnEventInterface, private val listener: (Match) -> Unit) :
    RecyclerView.Adapter<MatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(MatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bindItem(teams[position], listener, type, btnListener)
    }


}

class MatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {
                lparams(width = matchParent, height = wrapContent) {
                    margin = dip(5)
                }
                radius = dip(4).toFloat()
                linearLayout{
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.VERTICAL
                    }

                relativeLayout {
                    lparams(matchParent, wrapContent)

                    textView {
                        id = R.id.match_date
                        this.setTypeface(null, Typeface.BOLD)
                        textSize = 16F
                        text = "20-11-2018"
                        textColor = R.color.colorDark

                    }.lparams(wrapContent, wrapContent) {
                        centerHorizontally()
                        bottomMargin = dip(5)
                    }
                    imageButton{
                        id = R.id.btn_add_event
                        imageResource = R.drawable.ic_add_alert
                        padding = dip(5)
                        backgroundColor = Color.TRANSPARENT
                        backgroundResource = R.drawable.ripple_effect

                    }.lparams(dip(50), dip(50)){
                        alignParentRight()
                        topMargin = dip(5)
                        rightOf(R.id.match_date)
                    }
                    textView {
                        id = R.id.match_time
                        this.setTypeface(null, Typeface.BOLD)
                        textSize = 16F
                        text = "20-11-2018"
                        textColor = R.color.colorDark

                    }.lparams(wrapContent, wrapContent) {
                        centerHorizontally()
                        below(R.id.match_date)
                    }
                }
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                    }
                    linearLayout {
                        lparams(matchParent, wrapContent)
                        {
                            marginEnd = dip(1)
                            weight = 1F
                            orientation = LinearLayout.VERTICAL
                        }
                        gravity = Gravity.CENTER_HORIZONTAL
                        padding = dip(16)

                        textView {
                            id = R.id.home_team
                            text = "Team A"
                            isAllCaps = true
                            this.setTypeface(null, Typeface.BOLD)
                            textSize = 16F
                            gravity = Gravity.CENTER
                        }.lparams(wrapContent, wrapContent) {
                            bottomMargin = dip(10)
                        }

                        textView {
                            id = R.id.home_score
                            text = "0"
                            textSize = 20F
                            gravity = Gravity.CENTER
                        }.lparams(wrapContent, wrapContent)


                    }
                    linearLayout {
                        lparams(height = matchParent, width = dip(1)) {
                            topMargin = dip(15)
                            bottomMargin = dip(15)
                        }
                        backgroundColor = Color.BLACK
                    }
                    linearLayout {
                        lparams(matchParent, wrapContent)
                        {
                            marginEnd = dip(1)
                            weight = 1F
                            orientation = LinearLayout.VERTICAL
                        }

                        gravity = Gravity.CENTER_HORIZONTAL
                        padding = dip(16)

                        textView {
                            id = R.id.away_team
                            text = "Team B"
                            isAllCaps = true
                            this.setTypeface(null, Typeface.BOLD)
                            textSize = 16F
                            gravity = Gravity.CENTER
                        }.lparams(wrapContent, wrapContent) {
                            bottomMargin = dip(10)
                        }

                        textView {
                            id = R.id.away_score
                            text = "0"
                            textSize = 20F
                            gravity = Gravity.CENTER
                        }.lparams(wrapContent, wrapContent)


                    }
                }


            }
            }
        }
    }

}

class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val matchDate: TextView = view.find(R.id.match_date)
    private val homeTeam: TextView = view.find(R.id.home_team)
    private val homeScore: TextView = view.find(R.id.home_score)
    private val awayTeam: TextView = view.find(R.id.away_team)
    private val awayScore: TextView = view.find(R.id.away_score)
    private val matchTime: TextView = view.find(R.id.match_time)
    private val btnEvent: ImageButton = view.findViewById(R.id.btn_add_event)
    fun bindItem(
        match: Match,
        listener: (Match) -> Unit,
        type: String,
        btnListener: BtnEventInterface
    ) {
        matchDate.text = match.date?.let { getDateFormat(it) }
        homeTeam.text = match.homeTeam?.let { getSubstringName(it) }
        homeScore.text = match.homeScore
        awayTeam.text = match.awayTeam?.let { getSubstringName(it) }
        awayScore.text = match.awayScore
        matchTime.text = match.time?.let { getTimeFormat(it.replace("+00:00","")) }
        if(type == "LAST"){
            btnEvent.invisible()
        }
        itemView.setOnClickListener {
            listener(match)
        }

        btnEvent.setOnClickListener {
            btnListener.addEvent(match)
        }
    }
}
