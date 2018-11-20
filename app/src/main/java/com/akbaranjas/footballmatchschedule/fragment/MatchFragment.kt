package com.akbaranjas.footballmatchschedule.fragment


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akbaranjas.footballmatchschedule.R
import com.akbaranjas.footballmatchschedule.adapter.MatchTabAdapter


class MatchFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tab: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_match, container, false)
        viewPager = view.findViewById(R.id.viewpager_main)
        tab = view.findViewById(R.id.tabs_main)
        viewPager.adapter = MatchTabAdapter(childFragmentManager, requireActivity())
        tab.setupWithViewPager(viewPager)
        return view
    }


}
