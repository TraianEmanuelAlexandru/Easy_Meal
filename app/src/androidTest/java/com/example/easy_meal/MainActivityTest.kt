package com.example.easy_meal

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.easy_meal.activities.LoginActivity
import com.example.easy_meal.activities.MainActivity
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{


    @Test
    fun test() {

        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.fragment_container)).check(matches(isDisplayed()))

        }

    @Test
    fun test2(){

        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
        onView(withId(R.id.loginEmailEt)).check(matches(isDisplayed()))
        onView(withId(R.id.loginPasswordEt)).check(matches(isDisplayed()))
        onView(withId(R.id.loginBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.loginForgotPassTV)).check(matches(isDisplayed()))
        onView(withId(R.id.loginForgotPassTV)).perform(click())
        onView(withId(R.id.cardView2)).check(matches(isDisplayed()))


    }
}