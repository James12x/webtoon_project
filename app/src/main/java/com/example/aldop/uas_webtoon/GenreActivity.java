package com.example.aldop.uas_webtoon;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GenreActivity extends AppCompatActivity {
    public static GenreActivity instance;

    private ViewPager vp;
    public TabLayout tabs;
    public NavigationView nv;
    public DrawerLayout dl;

    ArrayList<Comic> comsAction;
    ArrayList<Comic> comsComedy;
    ArrayList<Comic> comsScifi;
    ArrayList<Comic> comsFantasy;

    ActionFragment actionFragment;

    Adapter adapter;

    public GenreActivity getThis(){
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

        instance = this;
        dl = (DrawerLayout) findViewById(R.id.drawer);
        vp = (ViewPager) findViewById(R.id.viewpager);
      //  setupViewPager(vp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Genres");

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        comsAction = new ArrayList<Comic>();
        comsComedy = new ArrayList<Comic>();
        comsScifi = new ArrayList<Comic>();
        comsFantasy = new ArrayList<Comic>();

        SendPostRequest send = new SendPostRequest();
        send.getComicBasedOnGenre("Action", this);

        SendPostRequest send1 = new SendPostRequest();
        send1.getComicBasedOnGenre("Scifi", this);

        SendPostRequest send2 = new SendPostRequest();
        send2.getComicBasedOnGenre("Comedy", this);

        SendPostRequest send3 = new SendPostRequest();
        send3.getComicBasedOnGenre("Fantasy", this);
      //  setupViewPager(vp);
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {




             /*   coms.clear();
                coms = new ArrayList<Comic>();

                comsAction.clear();
                comsComedy.clear();
                comsScifi.clear();
                comsFantasy.clear();
                SendPostRequest send = new SendPostRequest();
                String tempGenre = tab.getText()+"";
                send.getComicBasedOnGenre(tempGenre, getThis());*/

                vp.setCurrentItem(tab.getPosition());
             /*   if(tempGenre.equals("Action")){
                    actionFragment.Test(getThis(), coms);
                    adapter.setItem(0, actionFragment);
                }else if(tempGenre.equals("Comedy")){
                    comedyFragment.Test(getThis(), coms);
                    adapter.setItem(1, new ComedyFragment());
                }else if(tempGenre.equals("Scifi")){
                    scifiFragment.Test(getThis(), coms);
                    adapter.setItem(2, new ScifiFragment());
                }else if(tempGenre.equals("Fantasy")){
                    fantasyFragment.Test(getThis(), coms);
                    adapter.setItem(3, new ComedyFragment());
                }*/




                //Toast.makeText(GenreActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();
                 //tab.getText();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TabLayout.Tab tab = tabs.getTabAt(position);
                tab.select();


            /*    coms.clear();
                coms = new ArrayList<Comic>();
                SendPostRequest send = new SendPostRequest();
                String tempGenre = tab.getText()+"";
                send.getComicBasedOnGenre(tempGenre, getThis());*/
                //Toast.makeText(GenreActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(state == 0){

                }else if(state == 1){

                }
            }
        });

        nv = (NavigationView) findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawer);
                dl.closeDrawers();
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.itemHome:
                      //  nv.getMenu().getItem(0).setChecked(true);
                        intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.itemNew:
                      //  nv.getMenu().getItem(1).setChecked(true);
                        intent = new Intent(getApplicationContext(),NewestActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.itemPopular:
                      //  nv.getMenu().getItem(2).setChecked(true);
                        intent = new Intent(getApplicationContext(),PopularActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.itemGenres:
                      //  nv.getMenu().getItem(3).setChecked(true);
                        intent = new Intent(getApplicationContext(),GenreActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.itemMyComics:
                       // nv.getMenu().getItem(4).setChecked(true);
                        intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                        startActivity(intent);
                        break;
                }

                return false;
            }
        });

        //ReadData rd = new ReadData(this);
       // rd.execute(Connection.Ip + "loadComic.php");
    }

    public void readDataFinish(String result) {
        try {
            JSONObject json = new JSONObject(result);
            JSONArray json2 = json.getJSONArray("comic");
            String tempGenre = "Action";
            for(int i = 0; i< json2.length(); i++) {
                JSONObject o = json2.getJSONObject(i);
                int id = o.getInt("id");
                String genre = o.getString("genre");
                String name = o.getString("name");

                String description = o.getString ("description");
                String comic_link = o.getString("comic_link");
                String comic_preview = o.getString("comic_preview");
                String comic_background = o.getString("comic_background");
                String date = o.getString("datetime");
                float rating = (float)o.getDouble("rating");
             //   Toast.makeText(this, comic_preview, Toast.LENGTH_SHORT).show();

                Comic c = new Comic(id, genre, name ,description, comic_link, comic_preview, comic_background, date, rating);
                //coms.add(c);

                tempGenre = genre;
                if (genre.equals("Action")) {
                    comsAction.add(c);
                   // ActionFragment.Test(this, comsAction);
                }else if(genre.equals("Comedy")){
                    comsComedy.add(c);
                   // ScifiFragment.Test(this,comsComedy);
                }else if(genre.equals("Scifi")){
                    comsScifi.add(c);
                  //  ComedyFragment.Test(this,comsScifi);
                }else if(genre.equals("Fantasy")){
                    comsFantasy.add(c);
                  //  FantasyFragment.Test(this,comsFantasy);
                }
            }
            setupViewPager(vp);
        /*    if(tempGenre.equals("Action")){
                actionFragment.Test(getThis(), coms);
                adapter.setItem(0, actionFragment);
            }else if(tempGenre.equals("Comedy")){
                comedyFragment.Test(getThis(), coms);
                adapter.setItem(1, new ComedyFragment());
            }else if(tempGenre.equals("Scifi")){
                scifiFragment.Test(getThis(), coms);
                adapter.setItem(2, new ScifiFragment());
            }else if(tempGenre.equals("Fantasy")){
                fantasyFragment.Test(getThis(), coms);
                adapter.setItem(3, new ComedyFragment());
            }*/




          //  Toast.makeText(this, "WEW, " + coms.size()+"", Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager(ViewPager vp) {
        adapter = new Adapter(getSupportFragmentManager());
       // ActionFragment asd = new ActionFragment();
     //   ActionFragment.Test(this, coms);
        actionFragment = new ActionFragment();
        actionFragment.Test(this, comsAction);
        adapter.addFragment(actionFragment);
     //   ScifiFragment.Test(this,coms);
        actionFragment = new ActionFragment();
        actionFragment.Test(this, comsScifi);
        adapter.addFragment(actionFragment);
      //  ComedyFragment.Test(this,coms)
        actionFragment = new ActionFragment();
        actionFragment.Test(this, comsComedy);
        adapter.addFragment(actionFragment);
     //   FantasyFragment.Test(this,coms);
        actionFragment = new ActionFragment();
        actionFragment.Test(this, comsFantasy);
        adapter.addFragment(actionFragment);
        vp.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }



}
