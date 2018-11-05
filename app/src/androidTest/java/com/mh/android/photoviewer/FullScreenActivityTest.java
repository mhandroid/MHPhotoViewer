package com.mh.android.photoviewer;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mh.android.photoviewer.ui.FullScreenActivity;
import com.mh.android.photoviewer.utils.Utils;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by @author Mubarak Hussain.
 */
@RunWith(AndroidJUnit4.class)
public class FullScreenActivityTest {

    @Rule
    public ActivityTestRule<FullScreenActivity> activityRule = new ActivityTestRule<>(
            FullScreenActivity.class,
            true,     // initialTouchMode
            false); // launchActivity. False so we can customize the intent per test method
    private Solo solo;

    @Before
    public void setUp() {

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        solo = new Solo(instrumentation, activityRule.getActivity());
    }

    /**
     * Test image is getting loaded properly in image view
     */
    @Test
    public void intent_imageLoading() {
        Intent intent = new Intent();
        intent.putExtra(Utils.ALBUM_URL, "https://via.placeholder.com/600/6dd9cb");

        activityRule.launchActivity(intent);

        solo.waitForActivity(FullScreenActivity.class);
        onView(withId(R.id.progress_circular)).check(matches(ViewMatchers.isDisplayed()));

        ProgressBar progressBar = activityRule.getActivity().findViewById(R.id.progress_circular);
        ImageView imageView = activityRule.getActivity().findViewById(R.id.image);

        Assert.assertNotNull(imageView);
        Assert.assertNotNull(progressBar);
        Assert.assertNull(imageView.getDrawable());
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return progressBar.getVisibility() == View.GONE;
            }
        }, 10000);

        Assert.assertEquals(View.GONE, progressBar.getVisibility());
        Assert.assertNotNull(imageView.getDrawable());

    }

    /**
     * Test image is not getting loaded properly in image view because  url is wrong
     */
    @Test
    public void intent_imageNotLoaded() {
        Intent intent = new Intent();
        intent.putExtra(Utils.ALBUM_URL, "https://via.placeholder.com");

        activityRule.launchActivity(intent);

        solo.waitForActivity(FullScreenActivity.class);
        onView(withId(R.id.progress_circular)).check(matches(ViewMatchers.isDisplayed()));

        ProgressBar progressBar = activityRule.getActivity().findViewById(R.id.progress_circular);
        ImageView imageView = activityRule.getActivity().findViewById(R.id.image);

        Assert.assertNotNull(imageView);
        Assert.assertNotNull(progressBar);
        Assert.assertNull(imageView.getDrawable());
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                return progressBar.getVisibility() == View.GONE;
            }
        }, 10000);

        Assert.assertEquals(View.GONE, progressBar.getVisibility());
        Assert.assertNull(imageView.getDrawable());

    }

    /**
     * Test image is not getting loaded properly in image view because  url is empty
     */
    @Test
    public void intent_when_urlIsEmpty() {
        Intent intent = new Intent();
        intent.putExtra(Utils.ALBUM_URL, "");

        activityRule.launchActivity(intent);

        solo.waitForActivity(FullScreenActivity.class);

        ImageView imageView = activityRule.getActivity().findViewById(R.id.image);

        Assert.assertNull(imageView.getDrawable());

    }
}
