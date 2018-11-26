package com.akbaranjas.footballmatchschedule.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.akbaranjas.footballmatchschedule.DetailTeamActivity
import com.akbaranjas.footballmatchschedule.models.Team
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView


class OverviewTeamFragment : Fragment(), AnkoComponent<Context>, DetailTeamActivity.OnDescDataReceivedListener {


    private lateinit var description:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        frameLayout {
            lparams(width = matchParent, height = wrapContent)
            nestedScrollView {
                linearLayout {
                    lparams(matchParent, wrapContent)
                    orientation = LinearLayout.VERTICAL
                    topPadding = dip(16)
                    leftPadding = dip(16)
                    rightPadding = dip(16)
                    description = textView().lparams {
                        topMargin = dip(10)
                    }
                }
            }.lparams(matchParent, wrapContent)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var mActivity = activity as DetailTeamActivity?
        mActivity?.setAboutDataListener(this)
    }

    override fun onDataReceived(team: Team) {
        description.text = team.teamDescription
    }
}
