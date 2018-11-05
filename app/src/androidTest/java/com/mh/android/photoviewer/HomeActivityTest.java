package com.mh.android.photoviewer;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.mh.android.photoviewer.adapter.AlbumListAdapter;
import com.mh.android.photoviewer.ui.FullScreenActivity;
import com.mh.android.photoviewer.ui.HomeActivity;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by @author Mubarak Hussain.
 */
@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    private RecyclerView recyclerView;
    private AlbumListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Solo solo;
    @Rule
    public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    @Before
    public void setUp() {
        recyclerView = activityTestRule.getActivity().findViewById(R.id.recycler_view);

        adapter = (AlbumListAdapter) recyclerView.getAdapter();
        swipeRefreshLayout = activityTestRule.getActivity().findViewById(R.id.swipe_refresh_layout);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        solo = new Solo(instrumentation, activityTestRule.getActivity());
    }

    /**
     * Test views visibility
     */
    @Test
    public void testViewVisibility() {
        solo.waitForActivity(HomeActivity.class);
        solo.assertCurrentActivity("MainActivity is not displayed", HomeActivity.class);
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));

        Assert.assertNotNull("Initially Adapter should not be null", adapter);
        Assert.assertEquals("Initially Adapter item count is zero",0, adapter.getItemCount());
    }

    /**
     * Test recycler view click item and recycler view displayed all the value
     */
    @Test
    public void testClickItem() {
        solo.waitForActivity(HomeActivity.class);
        solo.assertCurrentActivity("MainActivity is not displayed", HomeActivity.class);
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));

        Assert.assertNotNull("Initially adapter should not be null", adapter);
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return !swipeRefreshLayout.isRefreshing();
            }
        }, 10000);
        Assert.assertFalse(swipeRefreshLayout.isRefreshing());
        adapter = (AlbumListAdapter) recyclerView.getAdapter();

        Assert.assertNotNull("Adapter should not be null", adapter);
        Assert.assertTrue("Adapter count should be greater then 0", adapter.getItemCount() > 0);

        solo.clickInRecyclerView(1);

        Assert.assertTrue("Full screen activity should launch", solo.waitForActivity(FullScreenActivity.class));
    }
}
