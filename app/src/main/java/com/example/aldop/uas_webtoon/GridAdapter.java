package com.example.aldop.uas_webtoon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by itdd on 11/27/2017.
 */

public class GridAdapter extends BaseAdapter {
    public Context context;
    public ArrayList<Comic> coms;

    public ImageView img;
    public TextView textTitle;
    public TextView textRating;

    public GridAdapter(Context context, ArrayList<Comic> coms) {
        this.context = context;
        this.coms = coms;
    }

    @Override
    public int getCount() {
        return coms.size();
    }

    @Override
    public Object getItem(int position) {
        return coms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return coms.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.grid_layout, null);

        img = (ImageView) v.findViewById(R.id.img);
        textTitle = (TextView) v.findViewById(R.id.textTitle);
        textRating = (TextView) v.findViewById(R.id.textRatingNumber) ;

        textTitle.setText(coms.get(position).getName());
        textRating.setText(coms.get(position).getRating()+"");

        //isi rating dengan coms.get(position).getRating();

        URL url = null;
        ImageDownloader downloader = new ImageDownloader(img);
        downloader.execute(Connection.Ip + coms.get(position).getImagePreview());
        return v;
    }
}
