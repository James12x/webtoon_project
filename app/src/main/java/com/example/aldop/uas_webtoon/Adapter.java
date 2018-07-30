package com.example.aldop.uas_webtoon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by aldop on 12/4/2017.
 */

public class Adapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> fragmentList = new ArrayList<>();

    public Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }

    public void setItem(int position, Fragment fragment){
        fragmentList.set(position, fragment);
    }



    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment f){
        fragmentList.add(f);
    }
}
