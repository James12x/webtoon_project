package com.example.aldop.uas_webtoon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    ListView commentList;
    EditText textComment;
    Button btnSubmit;
    ArrayList<Comment> comments;
    int eps_id;

    static CommentActivity Instance;


    public CommentActivity GetThis(){
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Instance = this;
        Intent receiveIntent = getIntent();
        eps_id = receiveIntent.getIntExtra("eps_id", 0);

        comments = new ArrayList<>();

        commentList = (ListView)findViewById(R.id.commentList);
        commentList.setSelector(android.R.color.transparent);

        textComment = (EditText)findViewById(R.id.textComment);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendPostRequest send =new SendPostRequest();
                send.SendComment(eps_id, 1, textComment.getText()+"", GetThis());
            }
        });

        SendPostRequest send =new SendPostRequest();
        send.getComments(eps_id, this);
    }


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
            JSONObject json = new JSONObject(result);
            JSONArray json2 = json.getJSONArray("comment");

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

            commentList.setAdapter(commentAdapter);


        } catch (JSONException e) {

            e.printStackTrace();
        }
    }

    public void refreshComment(){
        comments.clear();
        SendPostRequest send =new SendPostRequest();
        send.getComments(eps_id, this);

        textComment.setText("");
    }
}
