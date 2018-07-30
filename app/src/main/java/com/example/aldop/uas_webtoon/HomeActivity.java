package com.example.aldop.uas_webtoon;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    private ViewPager vp;
    public DrawerLayout dl;
    public NavigationView nv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        dl = (DrawerLayout) findViewById(R.id.drawerHome);
        vp = (ViewPager) findViewById(R.id.viewPagerBanner);

        ImageView iDrawer = (ImageView) findViewById(R.id.imageDrawerHome);
        iDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(GravityCompat.START);
            }
        });

        //Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarHome);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("");
        //ActionBar supportActionBar = getSupportActionBar();
        //if (supportActionBar != null) {
        //    supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        //    supportActionBar.setDisplayHomeAsUpEnabled(true);
        //}

        SlideShowVPAdapter ssAdapter = new SlideShowVPAdapter(this);
        vp.setAdapter(ssAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimer(), 2000, 4000);

        nv = (NavigationView) findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawerHome);
                dl.closeDrawers();
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.itemHome:
                      //  nv.getMenu().getItem(0).setChecked(true);
                        intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.itemNew:
                     //   nv.getMenu().getItem(1).setChecked(true);
                        intent = new Intent(getApplicationContext(),NewestActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.itemPopular:
                     //   nv.getMenu().getItem(2).setChecked(true);
                        intent = new Intent(getApplicationContext(),PopularActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.itemGenres:
                      //  nv.getMenu().getItem(3).setChecked(true);
                        intent = new Intent(getApplicationContext(),GenreActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.itemMyComics:
                      //  nv.getMenu().getItem(4).setChecked(true);
                        intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                        startActivity(intent);
                        break;
                }

                return false;
            }
        });

        vp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }*/

    public class MyTimer extends TimerTask{
        @Override
        public void run(){
            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(vp.getCurrentItem() ==0){
                        vp.setCurrentItem(1);
                    } else if (vp.getCurrentItem()==1){
                        vp.setCurrentItem(2);
                    }else {
                        vp.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
