package com.example.rxandroidmvp.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.rxandroidmvp.BaseApp;
import com.example.rxandroidmvp.R;
import com.example.rxandroidmvp.RxAndroidMvpApplication;
import com.example.rxandroidmvp.models.NewsModel;
import com.example.rxandroidmvp.networking.Service;
import com.example.rxandroidmvp.utils.ConnectivityReceiver;
import com.example.rxandroidmvp.utils.RecyclerItemClickListener;
import com.example.rxandroidmvp.utils.SharedPreferenceSingleton;

import java.util.ArrayList;

import javax.inject.Inject;

public class HomeActivity extends BaseApp implements HomeView,ConnectivityReceiver.ConnectivityReceiverListener {

    private RecyclerView list;
    private RelativeLayout nodatalay;
    @Inject
    public  Service service;
    ProgressBar progressBar;
    HomePresenter presenter;


    @Override
    public void onResume() {
        super.onResume();

        // register connection status listener
        RxAndroidMvpApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RxAndroidMvpApplication app = (RxAndroidMvpApplication) getApplication();
        app.getDeps().inject(this);

        renderView();
        init();

        presenter= new HomePresenter(service, this);
        presenter.getNewslist(HomeActivity.this);
    }

    public  void renderView(){
        setContentView(R.layout.activity_home);
        list = findViewById(R.id.list);
        nodatalay=findViewById(R.id.rl_nodata);
        progressBar = findViewById(R.id.progress);

        //progressBar.getIndeterminateDrawable().setColorFilter(0x459EDB, android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    public void init(){
        list.setLayoutManager(new LinearLayoutManager(this));
    }


    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbarmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btn_refresh) {
            // do something here
            list.setVisibility(View.VISIBLE);
            nodatalay.setVisibility(View.GONE);
            presenter.getNewslist(HomeActivity.this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    /**
     * RecyclerItem click event listener
     * */
    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(NewsModel.Row notice) {

            Toast.makeText(HomeActivity.this,
                    "List title:  " + notice.getTitle(),
                    Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void getNewslistSuccess(NewsModel cityListResponse) {


        SharedPreferenceSingleton.getInstance().init(this);
        String titletext=SharedPreferenceSingleton.getInstance().getStringPreference("title");

        if(titletext!=null){

            getSupportActionBar().setTitle(titletext);
        }


        if(ConnectivityReceiver.isConnected()){

            list.setVisibility(View.VISIBLE);
            nodatalay.setVisibility(View.GONE);
            list.setAdapter(new HomeAdapter(getApplicationContext(), (ArrayList<NewsModel.Row>) cityListResponse.getRows(),
                    recyclerItemClickListener));

        }else if(!ConnectivityReceiver.isConnected() && cityListResponse.getRows().size()==0){

            //Toast.makeText(getActivity(),""+ConnectivityReceiver.isConnected(),Toast.LENGTH_SHORT).show();

            list.setVisibility(View.GONE);
            nodatalay.setVisibility(View.VISIBLE);

        }else {

            list.setVisibility(View.VISIBLE);
            nodatalay.setVisibility(View.GONE);

            list.setAdapter(new HomeAdapter(getApplicationContext(), (ArrayList<NewsModel.Row>) cityListResponse.getRows(),
                    recyclerItemClickListener));

        }

       /* HomeAdapter adapter = new HomeAdapter(getApplicationContext(), (ArrayList<NewsModel.Row>) cityListResponse.getRows(),
               recyclerItemClickListener);

        list.setAdapter(adapter);*/

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {


        if(isConnected){

            Toast.makeText(this, "Internet available.Kindly pull down to refresh layout",Toast.LENGTH_SHORT).show();
        }else {

            Toast.makeText(this, "Internet not available.Kindly connect internet to see updated news.",Toast.LENGTH_SHORT).show();
        }

    }
}
