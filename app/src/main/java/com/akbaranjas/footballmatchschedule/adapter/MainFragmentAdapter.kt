package com.akbaranjas.footballmatchschedule.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.akbaranjas.footballmatchschedule.R
import com.akbaranjas.footballmatchschedule.fragment.LastMatchFragment
import com.akbaranjas.footballmatchschedule.fragment.NextMatchFragment

class MainFragmentAdapter(fm : FragmentManager, context : Context) : FragmentPagerAdapter(fm){
    private val ctx = context
    private val pages = listOf(
        LastMatchFragment(),
        NextMatchFragment()
    )
    override fun getItem(position: Int): Fragment {
        return pages[position] as Fragment
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {

        return when(position){
            0 ->  ctx.getString(R.string.str_last_match)
            1 -> ctx.getString(R.string.str_next_match)
            else -> ""
        }
    }

}