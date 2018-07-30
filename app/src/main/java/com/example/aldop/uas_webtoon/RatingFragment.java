package com.example.aldop.uas_webtoon;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends DialogFragment {
    RatingBar rating;
    ComicActivity ca;
    float startingRating;
    int comic_id;


    public RatingFragment() {
        // Required empty public constructor
    }

    public void SetCA(ComicActivity ca, float startingRating, int comic_id){
        this.ca = ca;
        this.startingRating = startingRating;
        this.comic_id = comic_id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rating, container, false);
        getDialog().setTitle("Rate this Comic");

        final TextView textRating = (TextView) v.findViewById(R.id.textRatingDialog);
        TextView hint = (TextView) v.findViewById(R.id.textHint);
        rating = (RatingBar) v.findViewById(R.id.ratingStar);

        rating.setRating(startingRating);

        Button bSend = (Button) v.findViewById(R.id.buttonSend);
        Button bCancel = (Button) v.findViewById(R.id.buttonCancel);

        textRating.setText(rating.getRating()+"");
        hint.setText("Swipe your screen left to right");

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rateNum = rating.getRating();

                SendPostRequest send = new SendPostRequest();
                send.sendRating(comic_id, rateNum, ca);

                dismiss();
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return v;
    }

}
