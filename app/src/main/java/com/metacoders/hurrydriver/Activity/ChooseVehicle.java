package com.metacoders.hurrydriver.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.metacoders.hurrydriver.R;
import com.metacoders.hurrydriver.TruckReg;

public class ChooseVehicle extends AppCompatActivity {

    CardView truck , car  , ambulance ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_vehicle);

        getSupportActionBar().hide();

        //init views

        truck = findViewById(R.id.truckBtn) ;
        car = findViewById(R.id.privateCarBtn) ;
        ambulance = findViewById(R.id.ambulanceBtn) ;



        truck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i  = new Intent(getApplicationContext() , TruckReg.class);
                startActivity(i);

            }
        });


        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });


    }
}
