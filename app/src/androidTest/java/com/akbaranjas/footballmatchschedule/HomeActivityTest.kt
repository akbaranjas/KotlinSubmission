package com.akbaranjas.footballmatchschedule

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.akbaranjas.footballmatchschedule.R.id.add_to_favorite
import com.akbaranjas.footballmatchschedule.R.id.bottom_navigation
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Test
    fun testBottomNavigationViewBehaviour() {
        onView(withId(bottom_navigation))
            .check(matches(isDisplayed()))
        onView(withId(R.id.last_match_menu)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.next_match_menu)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.favourites_menu)).perform(click())
    }

    @Test
    fun testAppBehaviour() {

        onView(withId(bottom_navigation))
            .check(matches(isDisplayed()))

        onView(withId(R.id.last_match_menu)).perform(click())
        Thread.sleep(5000)
        onView(withId(R.id.last_match_list))
            .check(matches(isDisplayed()))

        onView(withText(CoreMatchers.containsString("Arsenal"))).check(matches(isDisplayed()))
        onView(withText(CoreMatchers.containsString("Arsenal"))).perform(click())
        Thread.sleep(5000)
        onView(withId(add_to_favorite))
            .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
            .check(matches(isDisplayed()))
        pressBack()

        onView(withId(bottom_navigation))
            .check(matches(isDisplayed()))
        onView(withId(R.id.favourites_menu)).perform(click())
    }



}