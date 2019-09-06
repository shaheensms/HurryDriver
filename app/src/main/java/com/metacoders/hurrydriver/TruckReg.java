package com.metacoders.hurrydriver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class TruckReg extends AppCompatActivity {

    AutoCompleteTextView truckTypeTv ;
    AutoCompleteTextView  truckModelTv ;
    AutoCompleteTextView  truckYearTv ;
    AutoCompleteTextView  truckTonTv ;


    private  static  final  String[] TRUCK_TYPE = new String[]{

            "TATA" , "KIA" , "LEXUS"

    };

    private  static  final  String[] TRUCK_MODEL = new String[]{

            "Hi-Ace", "NOAH", "COROLLA", "PREMIO", "ALION"

    };

    private  static  final  String[] TRUCK_YEAR = new String[]{

            "2019", "2018", "2017", "2016"

    };

    private  static  final  String[] TRUCK_TOM = new String[]{

            "2019", "2018", "2017", "2016"

    };

    String truckType  ;
    String truckModel  ;
    String truckYear  ;
    String truckTon  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_reg);

        truckTypeTv = findViewById(R.id.ed_truckType);
        truckModelTv = findViewById(R.id.ed_truckModel);
        truckYearTv = findViewById(R.id.ed_truckYear);
        truckTonTv = findViewById(R.id.ed_truckTon);

        ArrayAdapter<String> truckTypeAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, TRUCK_TYPE) ;
        ArrayAdapter<String> truckModelAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, TRUCK_MODEL) ;
        ArrayAdapter<String>truckYearAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, TRUCK_YEAR) ;
        ArrayAdapter<String>truckTonAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, TRUCK_TOM) ;

        truckTypeTv.setAdapter(truckTypeAdapter);
        truckModelTv.setAdapter(truckModelAdapter);
        truckYearTv.setAdapter(truckYearAdapter);
        truckTonTv.setAdapter(truckTonAdapter);


        // getting the text form  the  auto compete text view ;

        truckType = truckTypeTv.getText().toString() ;
        truckModel = truckModelTv.getText().toString() ;
        truckYear = truckYearTv.getText().toString() ;
        truckTon = truckTonTv.getText().toString() ;
    }
}
