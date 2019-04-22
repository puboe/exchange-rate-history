package com.puboe.kotlin.exchangeratehistory

import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.puboe.kotlin.exchangeratehistory.core.Callback
import com.puboe.kotlin.exchangeratehistory.core.NetworkFailure
import com.puboe.kotlin.exchangeratehistory.core.Success
import com.puboe.kotlin.exchangeratehistory.core.di.DaggerApplicationComponent
import com.puboe.kotlin.exchangeratehistory.ratehistory.RateHistoryActivity
import com.puboe.kotlin.exchangeratehistory.ratehistory.data.DataRepository
import com.puboe.kotlin.exchangeratehistory.ratehistory.data.RateHistory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RateHistoryActivityTest {

    @get:Rule
    val activityActivityTestRule = ActivityTestRule<RateHistoryActivity>(RateHistoryActivity::class.java, false, false)

    @Test
    fun testComponentsVisibility() {
        activityActivityTestRule.launchActivity(null)

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
        activityActivityTestRule.launchActivity(null)

        onView(withId(R.id.loading_view)).check(doesNotExist())
        onView(withId(R.id.button)).perform(click())
        onView(withId(R.id.loading_view)).check(matches(isDisplayed()))
    }

    @Test
    fun testSuccessShouldDisplayData() {
        val repositoryMock = SuccessMockDataRepository()
        val mockComponent = DaggerApplicationComponent.builder().appModule(TestAppModule(repositoryMock)).build()
        (InstrumentationRegistry.getTargetContext().applicationContext as MainApplication).appComponent = mockComponent

        activityActivityTestRule.launchActivity(null)

        onView(withId(R.id.loading_view)).check(doesNotExist())
        onView(withId(R.id.button)).perform(click())
        onView(withText(R.string.network_error_message)).check(doesNotExist())
        onView(withText(R.string.server_error_message)).check(doesNotExist())
        onView(withText(R.string.client_error_message)).check(doesNotExist())
    }

    @Test
    fun testFailureShouldDisplayError() {
        val repositoryMock = FailureMockDataRepository()
        val mockComponent = DaggerApplicationComponent.builder().appModule(TestAppModule(repositoryMock)).build()
        (InstrumentationRegistry.getTargetContext().applicationContext as MainApplication).appComponent = mockComponent

        activityActivityTestRule.launchActivity(null)

        onView(withText(R.string.network_error_message)).check(doesNotExist())
        onView(withId(R.id.loading_view)).check(doesNotExist())
        onView(withId(R.id.button)).perform(click())
        onView(withText(R.string.network_error_message)).check(matches(isDisplayed()))
    }

    class SuccessMockDataRepository : DataRepository {
        override fun getRateHistory(startDate: String, endDate: String): Callback {
            val map = mapOf("2019-03-10" to mapOf("USD" to 1.13), "2019-03-11" to mapOf("USD" to 1.14))
            val history = RateHistory("EUR", "2019-03-10", "2019-03-12", map)
            return Success(history)
        }
    }

    class FailureMockDataRepository : DataRepository {
        override fun getRateHistory(startDate: String, endDate: String): Callback {
            return NetworkFailure()
        }
    }
}