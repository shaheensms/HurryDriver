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
    private  static  final  String[] COMPANY = new String[]{

      "TOYOTA", "HONDA", "TATA" , "TOYOTA" , "KIA" , "LEXUS"

    };



String manufratureCompany  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_reg);

        manufactureTv = findViewById(R.id.carManufacturAutoComplete);

        ArrayAdapter<String>companyAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, COMPANY) ;

        manufactureTv.setAdapter(companyAdapter);



        // getting the text form  the  auto compete text view ;

        manufratureCompany = manufactureTv.getText().toString() ;








    }
}
