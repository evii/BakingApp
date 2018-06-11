package com.example.android.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by evi on 11. 6. 2018.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityBasicIdlingTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    CountingIdlingResource mainActivityIdlingResource;

    @Before
    public void registerIdlingResource() {
        mainActivityIdlingResource = mActivityRule.getActivity().getEspressoIdlingResourceForMainActivity();
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(mainActivityIdlingResource);
    }


    @Test
    public void useAppContext() throws Exception {

        onView(withId(R.id.recipes_rv))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.recipe_detail_container))
                .check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mainActivityIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mainActivityIdlingResource);
        }
    }
}
