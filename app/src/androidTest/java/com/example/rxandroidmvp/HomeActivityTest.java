package com.example.rxandroidmvp;


import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.rxandroidmvp.deps.Deps;
import com.example.rxandroidmvp.home.HomeActivity;
import com.example.rxandroidmvp.models.NewsModel;
import com.example.rxandroidmvp.networking.NetworkModule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.LinkedList;
import java.util.List;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest {

    private static final int ANY_NUMBERS_OF_NEWSITEM = 10;
    @Mock NewsModel newsModel;



    @Rule
    public DaggerMockRule<Deps> daggerRule =new DaggerMockRule<>(Deps.class,new NetworkModule()).set(new DaggerMockRule.ComponentSetter<Deps>() {
        @Override
        public void setComponent(Deps component) {

            RxAndroidMvpApplication app =
                    (RxAndroidMvpApplication) InstrumentationRegistry.getInstrumentation()
                            .getTargetContext()
                            .getApplicationContext();
            app.setDeps(component);

        }
    });



    @Rule public ActivityTestRule<HomeActivity> activityRule =
            new ActivityTestRule<>(HomeActivity.class, true, true);

    @Test
    public void showNewslistIfThere() {
        List<NewsModel.Row> newslist = givenIfThereAreNewlist(ANY_NUMBERS_OF_NEWSITEM);

      //  startActivity();

       /* RecyclerViewInteraction.<String>onRecyclerView(withId(R.id.list))
                .withItems(Arrays.asList(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}))
                        .check(new RecyclerViewInteraction.ItemViewAssertion<String>() {
                    @Override
                    public void check(String item, View view, NoMatchingViewException e) {
                        matches(hasDescendant(withText(item))).check(view, e);
                    }
                });*/

      /*  RecyclerViewInteraction.<NewsModel.Row>onRecyclerView(withId(R.id.list))
                .withItems(newslist)
                .check(new RecyclerViewInteraction.ItemViewAssertion<NewsModel.Row>() {
                    @Override
                    public void check(NewsModel.Row item, View view, NoMatchingViewException e) {

                        matches(hasDescendant(withText(item.getTitle()))).check(view,e);
                    }
                });*/

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
