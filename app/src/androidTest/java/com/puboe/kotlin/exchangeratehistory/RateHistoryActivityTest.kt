package com.puboe.kotlin.exchangeratehistory

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.puboe.kotlin.exchangeratehistory.ratehistory.RateHistoryActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RateHistoryActivityTest {

    @get:Rule
    val activityActivityTestRule = ActivityTestRule<RateHistoryActivity>(RateHistoryActivity::class.java)

    @Before
    fun setup() {
    }

    @Test
    fun testComponentsVisibility() {
        onView(withId(R.id.start_date_label))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.start_date_label)))
        onView(withId(R.id.end_date_label))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.end_date_label)))
        onView(withId(R.id.start_date)).check(matches(isDisplayed()))
        onView(withId(R.id.end_date)).check(matches(isDisplayed()))
        onView(withId(R.id.button))
            .check(matches(isDisplayed()))
            .check(matches(withText(R.string.button_label)))
        onView(withId(R.id.graph)).check(matches(isDisplayed()))
        onView(withId(R.id.loading_view)).check(doesNotExist())
    }

    @Test
    fun testButtonClickShowsLoading() {
        onView(withId(R.id.loading_view)).check(doesNotExist())
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.loading_view)).check(matches(isDisplayed()))
    }
}