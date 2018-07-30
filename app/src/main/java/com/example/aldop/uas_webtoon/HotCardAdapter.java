package com.example.aldop.uas_webtoon;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by asus on 12/11/2017.
 */

public class HotCardAdapter extends BaseAdapter{
    ArrayList<Comic> coms;
    Context context;

    public HotCardAdapter (Context context, ArrayList<Comic> comics){
        this.context = context;
        this.coms = comics;
    }
    @Override
    public int getCount() {
        return coms.size();
    }

    @Override
    public Object getItem(int i) {
        return coms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return coms.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.hotcard_layout, null);

        ImageView img = (ImageView) v.findViewById(R.id.imagePrev);
        TextView textGenre = (TextView) v.findViewById(R.id.textGenre);
        TextView textTitle = (TextView) v.findViewById(R.id.textTitle);
        TextView textDesc = (TextView) v.findViewById(R.id.textDesc) ;
        TextView textRating = (TextView) v.findViewById(R.id.textRating) ;

        Log.e("HOTCARD", coms.size()+"");
        textGenre.setText(coms.get(i).getGenre());
        textTitle.setText(coms.get(i).getName());
        textDesc.setText(coms.get(i).getDescription());
        textRating.setText(coms.get(i).getRating()+"");

        //isi rating dengan coms.get(position).getRating();

        URL url = null;
        ImageDownloader downloader = new ImageDownloader(img);
        downloader.execute(Connection.Ip + coms.get(i).getImagePreview());
        return v;
    }
}
