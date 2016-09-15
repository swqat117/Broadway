package com.raywenderlich.alltherages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by AKSHAY on 8/22/2016.
 */
public class RageComicDetailsFragment extends Fragment {

    public static RageComicDetailsFragment newInstance(){
        return new RageComicDetailsFragment();
    }
    public RageComicDetailsFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rage_comic_details,container,false);
    }
}
