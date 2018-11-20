package com.akbaranjas.footballmatchschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.akbaranjas.footballmatchschedule.R.id.*
import com.akbaranjas.footballmatchschedule.fragment.FavouriteMatchFragment
import com.akbaranjas.footballmatchschedule.fragment.LastMatchFragment
import com.akbaranjas.footballmatchschedule.fragment.MatchFragment
import com.akbaranjas.footballmatchschedule.fragment.NextMatchFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                match_menu -> {
                    loadMatchFragment(savedInstanceState)
                }
                teams_menu -> {
                    loadNextMatchFragment(savedInstanceState)
                }

                favourites_menu -> {
                    loadFavoritesFragment(savedInstanceState)
                }
            }

            true
        }
        bottom_navigation.selectedItemId = match_menu
    }

    private fun loadMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, MatchFragment(), MatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadLastMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, LastMatchFragment(), LastMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadNextMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, NextMatchFragment(), NextMatchFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, FavouriteMatchFragment(), FavouriteMatchFragment::class.java.simpleName)
                .commit()
        }
    }
}