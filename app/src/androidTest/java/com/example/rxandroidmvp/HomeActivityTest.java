package com.example.rxandroidmvp;


import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.rxandroidmvp.deps.Deps;
import com.example.rxandroidmvp.home.HomeActivity;
import com.example.rxandroidmvp.models.NewsModel;
import com.example.rxandroidmvp.networking.NetworkModule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.LinkedList;
import java.util.List;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static java.util.regex.Pattern.matches;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest {

    private static final int ANY_NUMBERS_OF_NEWSITEM = 10;
    @Mock
    NewsModel newsModel;


    @Rule
    public DaggerMockRule<Deps> daggerRule = new DaggerMockRule<>(Deps.class, new NetworkModule()).set(new DaggerMockRule.ComponentSetter<Deps>() {
        @Override
        public void setComponent(Deps component) {

            RxAndroidMvpApplication app =
                    (RxAndroidMvpApplication) InstrumentationRegistry.getInstrumentation()
                            .getTargetContext()
                            .getApplicationContext();
            app.setDeps(component);

        }
    });


    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class, true, true);


    // Convenience helper
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Test
    public void showNewslistIfThere() {
        List<NewsModel.Row> newslist = givenIfThereAreNewlist(ANY_NUMBERS_OF_NEWSITEM);




        //startActivity();

// Click item at position 3
      // onView(withRecyclerView(R.id.list).atPosition(3)).perform(click());


    }


    private List<NewsModel.Row> givenIfThereAreNewlist(int anyNumbersOfNewsitem) {

        List<NewsModel.Row> newslist = new LinkedList<>();
        for (int i = 0; i < anyNumbersOfNewsitem; i++) {
            String newstitle = "News - " + i;
            String newsImage = "https://i.annihil.us/u/prod/marvel/i/mg/c/60/55b6a28ef24fa.jpg";
            String newsDescription = "Description News - " + i;
            NewsModel.Row row = new NewsModel().new Row(newstitle, newsDescription, newsImage);
            newslist.add(row);
            when(newsModel.getByName(newstitle)).thenReturn(row);
        }
        when(newsModel.getRows()).thenReturn(newslist);
        return newslist;
    }


    private HomeActivity startActivity() {
        return activityRule.launchActivity(null);
    }


}
