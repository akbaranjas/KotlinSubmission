package com.akbaranjas.footballmatchschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.akbaranjas.footballmatchschedule.adapter.MainFragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewpager_main.adapter = MainFragmentAdapter(supportFragmentManager,this)
        tabs_main.setupWithViewPager(viewpager_main)

    }
}
