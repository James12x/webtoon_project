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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewestActivity extends AppCompatActivity {
    public static NewestActivity instance;
    private ViewPager vp;
    public TabLayout tabs;
    public NavigationView nv;
    public DrawerLayout dl;

    public NewestActivity getThis() {
        return this;
    }

    ArrayList<Comic> coms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newest);

        dl = (DrawerLayout) findViewById(R.id.drawerNewest);
        vp = (ViewPager) findViewById(R.id.viewpager);
        //  setupViewPager(vp);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarNewest);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Newest");

        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        coms = new ArrayList<Comic>();
        SendPostRequest send = new SendPostRequest();
        send.getRecentComic(this);

        nv = (NavigationView) findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawer);
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
                     //   nv.getMenu().getItem(3).setChecked(true);
                        intent = new Intent(getApplicationContext(),GenreActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.itemMyComics:
                     //   nv.getMenu().getItem(4).setChecked(true);
                        intent = new Intent(getApplicationContext(), FavoriteActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    public void readDataFinish(String result) {
        try {
            JSONObject json = new JSONObject(result);
            JSONArray json2 = json.getJSONArray("comic");

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
                coms.add(c);
            }
            //  Toast.makeText(this, "WEW, " + coms.size()+"", Toast.LENGTH_SHORT).show();
            GridView grid = (GridView) findViewById(R.id.gridItem) ;
            grid.setAdapter(new HotCardAdapter(this, coms));

            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Comic c = coms.get(i);
                    Intent intent = new Intent(getThis(), ComicActivity.class);
                    intent.putExtra("name", c.getName());
                    intent.putExtra("link", c.getComic_link());
                    intent.putExtra("bglink", c.getImageBackground());
                    intent.putExtra("id", c.getId());
                    intent.putExtra("genre", c.getGenre());
                    intent.putExtra("description", c.getDescription());
                    intent.putExtra("rating", c.getRating());
                    startActivity(intent);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            dl.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}
