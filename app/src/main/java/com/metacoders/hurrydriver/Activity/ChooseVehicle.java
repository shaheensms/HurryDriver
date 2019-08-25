package com.metacoders.hurrydriver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

import com.metacoders.hurrydriver.R;

public class ChooseVehicle extends AppCompatActivity {

    CardView truck , car  , ambulance ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_vehicle);

        getSupportActionBar().hide();

        //init views



    }
}
