package com.metacoders.hurrydriver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.AndroidException;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class CarReg extends AppCompatActivity {


    AutoCompleteTextView  manufactureTv ;
    AutoCompleteTextView  carModelTv ;
    AutoCompleteTextView  carYearTv ;


    private  static  final  String[] COMPANY = new String[]{

      "TOYOTA", "HONDA", "TATA" , "TOYOTA" , "KIA" , "LEXUS"

    };

    private  static  final  String[] CAR_MODEL = new String[]{

            "Hi-Ace", "NOAH", "COROLLA", "PREMIO", "ALION"

    };

    private  static  final  String[] CAR_YEAR = new String[]{

            "2019", "2018", "2017", "2016"

    };




    String manufratureCompany  ;
    String carModel  ;
    String carYear  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_reg);

        manufactureTv = findViewById(R.id.carManufacturAutoComplete);
        carModelTv = findViewById(R.id.ed_model);
        carYearTv = findViewById(R.id.ed_year);

        ArrayAdapter<String>companyAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, COMPANY) ;
        ArrayAdapter<String>carModelAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, CAR_MODEL) ;
        ArrayAdapter<String>carYearAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, CAR_YEAR) ;

        manufactureTv.setAdapter(companyAdapter);
        carModelTv.setAdapter(carModelAdapter);
        carYearTv.setAdapter(carYearAdapter);


        // getting the text form  the  auto compete text view ;

        manufratureCompany = manufactureTv.getText().toString() ;
        carModel = carModelTv.getText().toString() ;
        carYear = carYearTv.getText().toString() ;








    }
}
