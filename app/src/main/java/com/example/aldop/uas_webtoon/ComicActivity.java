package com.example.aldop.uas_webtoon;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ComicActivity extends AppCompatActivity {
    public ImageButton ibFav;
    public boolean fav;
    ArrayList<Episode> eps;
    public String link;
    public String bglink;
    ExpandableHeightListView lv;
    Button btnFirstEp;
    int id;
    float rating;

    RatingBar ratingBar;
    TextView textRating;

    float myRating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic2);

        //AppBarLayout abl = (AppBarLayout) findViewById(R.id.appbar);
        //abl.setBackgroundColor(Color.parseColor("#00000000"));

        Intent receiveIntent = getIntent();
        String name = receiveIntent.getStringExtra("name");
        link = receiveIntent.getStringExtra("link");
        bglink = receiveIntent.getStringExtra("bglink");
        Log.e("bglink : ", bglink);

        id = receiveIntent.getIntExtra("id", 1);
        String genre = receiveIntent.getStringExtra("genre");
        String desc = receiveIntent.getStringExtra("description");
        rating = receiveIntent.getFloatExtra("rating", 0);

        ratingBar = (RatingBar)findViewById(R.id.ratingBUAR);
        textRating = (TextView)findViewById(R.id.textRating);

        textRating.setFocusable(true);

        ratingBar.setRating(rating);
        textRating.setText(rating+"");

        TextView textTitle = (TextView)findViewById(R.id.textTitle);
        TextView textGenre = (TextView)findViewById(R.id.textGenre);
        TextView textDesc = (TextView)findViewById(R.id.textDescription);

        textTitle.setText(name);
        textGenre.setText(genre);
        textDesc.setText(desc);

        SendPostRequest send = new SendPostRequest();
        send.GetEpisodes(id, this);

        SendPostRequest send2 = new SendPostRequest();
        send2.getFavoriteAndRating(id, this);


        switch (genre) {
            case "Fantasy":
                textGenre.setTextColor(Color.MAGENTA);
                break;
            case "Action":
                textGenre.setTextColor(Color.RED);
                break;
            case "Comedy":
                textGenre.setTextColor(Color.GREEN);
                break;
            case "Scifi":
                textGenre.setTextColor(Color.BLUE);
                break;
        }

        //lv.setAdapter(new EpisodeAdapter(this, eps, link));

        btnFirstEp = (Button) findViewById(R.id.buttonFirstEp);
        btnFirstEp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReadingActivity.class);
                intent.putExtra("link", link + eps.get(0).getEpisode_link());
                intent.putExtra("id", eps.get(0).getId());
                intent.putExtra("eps_no", eps.get(0).getEpisode_no());
                intent.putExtra("pagecount", eps.get(0).getEpisode_pagecount());
                startActivity(intent);
            }
        });

        lv = (ExpandableHeightListView) findViewById(R.id.expandableHeightlist1);
        lv.setExpanded(true);
        lv.setBackgroundColor(Color.argb( 245,  86, 91, 159));

        ImageView bg = (ImageView) findViewById(R.id.imageViewBG);
        bg.setBackgroundColor(Color.argb( 255,  86, 91, 159));


        ImageView imgBg = (ImageView) findViewById(R.id.backgroundImage);
        ImageDownloader downloader = new ImageDownloader(imgBg);
        downloader.execute(Connection.Ip + bglink);

        eps = new ArrayList<>();



        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        ibFav = (ImageButton) findViewById(R.id.imageButtonFav);

        ibFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!fav){
                    SendPostRequest favorite = new SendPostRequest();
                    favorite.sendFavorite(id, getThis());
                }else{
                    SendPostRequest favorite = new SendPostRequest();
                    favorite.sendUnFavorite(id, getThis());
                }
            }
        });

        ImageView imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        LinearLayout ratingButton = (LinearLayout)findViewById(R.id.ratingButton);
        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                RatingFragment rf = new RatingFragment();
                rf.SetCA(getThis(), myRating, id);
                rf.show(fm, "Rating Fragment");
            }
        });


    }

    public ComicActivity getThis(){
        return  this;
    }

    public void refreshRating(String result){
        refreshFavorite(result);
    }

    public void refreshFavorite(String result){
        eps.clear();
        SendPostRequest send = new SendPostRequest();
        send.getFavoriteAndRating(id, this);
    }

  public void readFavorite(String result){


      try {
          JSONObject json = new JSONObject(result);
          JSONArray json2 = json.getJSONArray("favRat");

          for(int i = 0; i< json2.length(); i++) {
              JSONObject o = json2.getJSONObject(i);


              fav = o.getInt("hasFavorited") == 0 ? false : true;

              myRating = (float)o.getDouble("ratingValue");

              rating = (float)o.getDouble("avgRating");

          }

          ratingBar.setRating(rating);
          textRating.setText(rating+"");

          if (!fav){
              ibFav.setImageResource(R.drawable.fav);
          } else{
              ibFav.setImageResource(R.drawable.faved);
          }
          Log.e("FAVORITE ", fav+"");

      } catch (JSONException e) {
          //  Toast.makeText(this, eps.size()+" CATCH", Toast.LENGTH_SHORT).show();
          Log.e("CATCHH EROR ", eps.size()+"");
          e.printStackTrace();
      }




  }



    public void readDataFinish(String result){
        try {
            JSONObject json = new JSONObject(result);
            JSONArray json2 = json.getJSONArray("episodes");

            for(int i = 0; i< json2.length(); i++) {
                JSONObject o = json2.getJSONObject(i);
                int id = o.getInt("id");
                int comic_id = o.getInt("comic_id");
                int episode_no = o.getInt("episode_no");
                String episode_name = o.getString("episode_name");
                int episode_pagecount = o.getInt("episode_pagecount");
                String episode_link = o.getString("episode_link");

             //   fav = o.getInt("hasFavorited") == 0 ? false : true;

            //    myRating = (float)o.getDouble("ratingValue");

             //   rating = (float)o.getDouble("avgRating");


                Episode e = new Episode(id, comic_id, episode_no ,episode_name, episode_pagecount, episode_link);
                eps.add(e);
            }



            if (!fav){
                ibFav.setImageResource(R.drawable.fav);
            } else{
                ibFav.setImageResource(R.drawable.faved);
            }


            Log.e("FAVORITE ", fav+"");
          //  Toast.makeText(this, eps.size()+" COK", Toast.LENGTH_SHORT).show();
        //    ListView lv = (ListView) findViewById(R.id.ListEp);
        //   lv.setNestedScrollingEnabled(true);
       //     GridView lv = (GridView) findViewById(R.id.ListEp);
            lv.setAdapter(new EpisodeAdapter(this, eps, link));



            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), ReadingActivity.class);

                    Episode eps = (Episode)adapterView.getItemAtPosition(i);
                    intent.putExtra("link", link + eps.getEpisode_link());
                    intent.putExtra("id", eps.getId());
                    intent.putExtra("eps_no", eps.getEpisode_no());
                    intent.putExtra("pagecount", eps.getEpisode_pagecount());
                    startActivity(intent);
                }
            });

        } catch (JSONException e) {
          //  Toast.makeText(this, eps.size()+" CATCH", Toast.LENGTH_SHORT).show();
            Log.e("CATCHH EROR ", eps.size()+"");
            e.printStackTrace();
        }
    }



 /*   public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }*/
}
