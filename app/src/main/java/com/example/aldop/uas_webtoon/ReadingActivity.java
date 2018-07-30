package com.example.aldop.uas_webtoon;

import android.app.ActionBar;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReadingActivity extends AppCompatActivity {
    int eps_no;
    int pagecount;
    int eps_id;
    ExpandableHeightListView topComments;
    LinearLayout pictures;

    Button btnViewAllComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pictures = (LinearLayout)findViewById(R.id.pictures);
        //pictures.setFocusable(true);



        topComments = (ExpandableHeightListView)findViewById(R.id.expandableHeightlist1);
        topComments.setExpanded(true);
      //  topComments.setNestedScrollingEnabled(true);

        btnViewAllComment = (Button)findViewById(R.id.btnViewComment);
        btnViewAllComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                intent.putExtra("eps_id", eps_id);
                startActivity(intent);
            }
        });

        Intent receiveIntent = getIntent();
        String tempLink = receiveIntent.getStringExtra("link");
        eps_id = receiveIntent.getIntExtra("id", 0);
        eps_no = receiveIntent.getIntExtra("eps_no", 0);
        pagecount = receiveIntent.getIntExtra("pagecount", 0);

        String link = tempLink+eps_no+"/";

       // Toast.makeText(this, "eps no : " + ", pagecount : " + pagecount + ", LINK : " + link , Toast.LENGTH_LONG).show();
        Log.e("CHECK DOANK : " , "eps no : " + ", pagecount : " + pagecount + ", LINK : " + link);
        populateImages(link);

        SendPostRequest send = new SendPostRequest();
        send.getTopComments(eps_id,this);
    }

    public void refreshComment(){
        comments.clear();
        SendPostRequest send =new SendPostRequest();
        send.getTopComments(eps_id, this);
    }

    void populateImages(String link){

        for(int i = 0; i <= pagecount; i++){
            ImageView image = new ImageView(this);

            String superLink = Connection.Ip+link+i+".jpg";
            ImageDownloader downloader = new ImageDownloader(image);
            downloader.execute(superLink);
            pictures.addView(image, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            image.setScaleType(ImageView.ScaleType.FIT_XY);

          //  Toast.makeText(this, asd, Toast.LENGTH_SHORT).show();

        }

    }

    ArrayList<Comment> comments = new ArrayList<>();

    public void LikeComment(int comment_id, int user_id){
        SendPostRequest send =new SendPostRequest();
        send.LikeComment(comment_id, user_id, this);
    }

    public void UnlikeComment(int comment_id, int user_id){
        SendPostRequest send =new SendPostRequest();
        send.UnlikeComment(comment_id, user_id, this);
    }

    public void readDataFinish(String result){
        try {
            Log.e("RESULT TOP COMMENT : " , result);

            JSONObject json = new JSONObject(result);
            JSONArray json2 = json.getJSONArray("topComment");

            for(int i = 0; i< json2.length(); i++) {
                JSONObject o = json2.getJSONObject(i);

                int id = o.getInt("id");
                int episode_id = o.getInt("episode_id");
                int user_id = o.getInt("user_id");
                String username = o.getString("username");
                String text = o.getString("content");
                String date = o.getString("datetime");
                int like = o.getInt("commentLikes");

                int hasLikeNumber = o.getInt("hasLiked");

                boolean hasLiked = hasLikeNumber < 1 ? false : true;


                Comment comment = new Comment(id, episode_id, user_id, username, text, date, like, hasLiked);
                comments.add(comment);
            }

            CommentAdapter commentAdapter = new CommentAdapter(this, comments);

            topComments.setAdapter(commentAdapter);


        } catch (JSONException e) {
            Log.e("ERROR COMMENT : " , e.getMessage());
            e.printStackTrace();
        }
    }
}
