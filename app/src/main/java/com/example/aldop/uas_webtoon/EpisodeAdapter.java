package com.example.aldop.uas_webtoon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aldop on 12/7/2017.
 */

public class EpisodeAdapter extends BaseAdapter{
    private final Context context;
    private final ArrayList<Episode> eps;
    private final String link;

    public EpisodeAdapter(Context context, ArrayList<Episode> eps, String link){
        //super(context, R.layout.chapter_layout, eps);
        this.context = context;
        this.eps = new ArrayList<>();
        for(int i =eps.size()-1 ; i >= 0; i--){
            this.eps.add(eps.get(i));
        }

        this.link = link;

    }

    @Override
    public int getCount() {
        return eps.size();
    }

    @Override
    public Object getItem(int i) {
        return eps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return eps.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.chapter_layout, null);

        TextView tv = (TextView) rowView.findViewById(R.id.textGenre);
        ImageView iv = (ImageView) rowView.findViewById(R.id.imgEpPre);
        TextView tvn = (TextView) rowView.findViewById(R.id.textChapterNumber);
        TextView tvl = (TextView) rowView.findViewById(R.id.textChapterLike);

        //URL url = null;
        String urlEpNo = eps.get(position).getEpisode_no()+"";
        tvn.setText("#"+urlEpNo);
        //tvl.setText(eps.get(position).get);
        tv.setText(eps.get(position).getEpisode_name());
        ImageDownloader downloader = new ImageDownloader(iv);
        downloader.execute(Connection.Ip + link + eps.get(position).getImage() + "preview.jpg");
        //Toast.makeText(context,  link + eps.get(position).getImage(), Toast.LENGTH_SHORT).show();
        return rowView;

    }



}
