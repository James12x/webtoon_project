package com.example.aldop.uas_webtoon;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.aldop.uas_webtoon.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActionFragment extends Fragment {
    ArrayList<Comic> comics;
    Context context;
    public ActionFragment() {
        // Required empty public constructor
    }

    public void Test(Context cont, ArrayList<Comic> coms){
        comics = coms;
        context = cont;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_action, container, false);
        GridView grid = (GridView) v.findViewById(R.id.grid);

        GridAdapter adapter = new GridAdapter(context, comics);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Comic c = comics.get(i);
                Intent intent = new Intent(context, ComicActivity.class);
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


       // Toast.makeText(context, comics.size()+"", Toast.LENGTH_SHORT).show();

        // Inflate the layout for this fragment


        return v;
    }

}
