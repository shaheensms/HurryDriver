package com.metacoders.hurrydriver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tiper.MaterialSpinner;

public class ChooseYourVehicle extends AppCompatActivity {

    MaterialSpinner carType , selectManufecture , selectCarModel , selectYear ;
    LinearLayout  carmodelLayout ;
    TextView carmodelText , manufetureText  ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_vehicle);

        // init views






    }
}