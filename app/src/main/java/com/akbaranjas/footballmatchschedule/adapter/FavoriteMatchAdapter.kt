package com.akbaranjas.footballmatchschedule.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.akbaranjas.footballmatchschedule.R
import com.akbaranjas.footballmatchschedule.db.FavoriteMatch
import com.akbaranjas.footballmatchschedule.util.getDateFormat
import com.akbaranjas.footballmatchschedule.util.getSubstringName
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView


class FavoriteMatchAdapter(private val teams: List<FavoriteMatch>, private val listener: (FavoriteMatch) -> Unit) :
    RecyclerView.Adapter<FavoriteMatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMatchViewHolder {
        return FavoriteMatchViewHolder(FavoriteMatchUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: FavoriteMatchViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

}

class FavoriteMatchUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {
                lparams(width = matchParent, height = wrapContent) {
                    margin = dip(5)
                }
                radius = dip(4).toFloat()
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.VERTICAL
                        bottomMargin = dip(50)
                    }

                    textView {
                        id = R.id.match_date
                        this.setTypeface(null, Typeface.BOLD)
                        textSize = 16F
                        text = "20-11-2018"
                        textColor = R.color.colorDark

                    }.lparams(wrapContent, wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL

                    }
                }
                linearLayout {
                    lparams(matchParent, wrapContent) {
                        orientation = LinearLayout.HORIZONTAL
                        topMargin = dip(20)
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

class FavoriteMatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val matchDate: TextView = view.find(R.id.match_date)
    private val homeTeam: TextView = view.find(R.id.home_team)
    private val homeScore: TextView = view.find(R.id.home_score)
    private val awayTeam: TextView = view.find(R.id.away_team)
    private val awayScore: TextView = view.find(R.id.away_score)
    fun bindItem(
        match: FavoriteMatch,
        listener: (FavoriteMatch) -> Unit
    ) {
        matchDate.text = match.eventDate?.let { getDateFormat(it) }
        homeTeam.text = getSubstringName(match.homeTeam!!)
        homeScore.text = match.homeScore
        awayTeam.text = getSubstringName(match.awayTeam!!)
        awayScore.text = match.awayScore
        itemView.setOnClickListener {
            listener(match)
        }
    }
}