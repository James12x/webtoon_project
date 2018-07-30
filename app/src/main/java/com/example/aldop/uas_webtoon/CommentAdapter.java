package com.example.aldop.uas_webtoon;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by aldop on 12/10/2017.
 */

public class CommentAdapter extends BaseAdapter implements ListAdapter{
    ArrayList<Comment> comments;
   ReadingActivity ra;
   CommentActivity ca;

    public enum DataType {
        Reading,
        Comment
    }
    public DataType data;

    public CommentAdapter(ReadingActivity ra, ArrayList<Comment> comments){
        this.comments = comments;
        this.ra = ra;
        data = DataType.Reading;
    }

    public CommentAdapter(CommentActivity ca, ArrayList<Comment> comments){
        this.comments = comments;
        this.ca = ca;
        data = DataType.Comment;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return comments.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater;
        if(data == DataType.Reading){
            layoutInflater = (LayoutInflater) ra.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        else {
            layoutInflater = (LayoutInflater) ca.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        View rowView = layoutInflater.inflate(R.layout.comment_layout, null);

        TextView un =(TextView)rowView.findViewById(R.id.textName);
        TextView date = (TextView) rowView.findViewById(R.id.textDate);
        TextView comment = (TextView) rowView.findViewById(R.id.textComment);

        TextView like = (TextView) rowView.findViewById(R.id.textNumberLike);

        final int index = i;

        final Comment com = comments.get(i);



        LinearLayout likeButtonLayout = (LinearLayout)rowView.findViewById(R.id.likeButtonLayout) ;

        if(com.isHasLiked()){
            likeButtonLayout.setBackgroundColor(Color.TRANSPARENT);
        }else {
            likeButtonLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        likeButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data == DataType.Comment){
                    if(comments.get(index).isHasLiked()){
                        ca.UnlikeComment(comments.get(index).getId(), 1);
                    }else {
                        ca.LikeComment(comments.get(index).getId(), 1);
                    }

                }else {
                    if(comments.get(index).isHasLiked()){
                        ra.UnlikeComment(comments.get(index).getId(), 1);
                    }else {
                        ra.LikeComment(comments.get(index).getId(), 1);
                    }
                }
            }
        });

        un.setText(comments.get(i).getUsername());

        date.setText(comments.get(i).getDate());

        comment.setText(comments.get(i).getText());
        like.setText(comments.get(i).getLike()+"");

        return rowView;

    }
}
