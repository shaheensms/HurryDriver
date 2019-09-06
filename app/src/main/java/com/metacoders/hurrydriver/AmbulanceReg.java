package com.metacoders.hurrydriver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class AmbulanceReg extends AppCompatActivity {


    AutoCompleteTextView manufactureTv, modelTv, yearTv;

    private static final String[] AMBULANCE_MANUFACTURER = new String[]{

            "TOYOTA", "HONDA", "TATA" , "TOYOTA" , "KIA" , "LEXUS"

    };

    private static final String[] AMBULANCE_MODEL = new String[]{

            "Hi-Ace", "NOAH"

    };

    private static final String[] AMBULANCE_YEAR = new String[]{

            "2019", "2018", "2017", "2016"

    };

    String manufactureCompany;
    String carModel;
    String carYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_reg);

        manufactureTv = findViewById(R.id.ed_manufacturer);
        modelTv = findViewById(R.id.ed_model);
        yearTv = findViewById(R.id.ed_year);

        ArrayAdapter<String>companyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AMBULANCE_MANUFACTURER);
        ArrayAdapter<String>carModelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AMBULANCE_MODEL);
        ArrayAdapter<String>carYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AMBULANCE_YEAR);

        manufactureTv.setAdapter(companyAdapter);
        modelTv.setAdapter(carModelAdapter);
        yearTv.setAdapter(carYearAdapter);

        manufactureCompany = manufactureTv.getText().toString().trim();
        carModel = modelTv.getText().toString().trim();
        carYear = yearTv.getText().toString().trim();

    }
}
