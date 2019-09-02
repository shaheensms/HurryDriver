package com.metacoders.hurrydriver.Fragmnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metacoders.hurrydriver.R;

public class HomePageFragment extends Fragment {

    View view ;


        public  HomePageFragment(){


        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_home_page, container, false);


        return view;

    }
}
