package com.metacoders.hurrydriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metacoders.hurrydriver.Activity.remainingStepsActivity;
import com.metacoders.hurrydriver.Activity.signUpAcitivity;
import com.metacoders.hurrydriver.Constants.constants;

import java.util.HashMap;
import java.util.Map;

public class AmbulanceReg extends AppCompatActivity {


    String uid =  "TEST"  ;




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
    RadioGroup  radioGroup , acRadioGroup  ;
    RadioButton  radioButton , acRadioButton ;
    String carType  = "null", acType = "null"  ;
    Button upadteBtn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_reg);

        // init views

        manufactureTv = findViewById(R.id.ed_manufacturer);
        modelTv = findViewById(R.id.ed_model);
        yearTv = findViewById(R.id.ed_year);
        radioGroup = findViewById(R.id.ambulanceTypeGroup) ;
        upadteBtn = findViewById(R.id.sumbitBtnAmbulance);
        acRadioGroup = findViewById(R.id.acTypeGroup) ;


        upadteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int  radioId = radioGroup.getCheckedRadioButtonId() ;
                int acradioID = acRadioGroup.getCheckedRadioButtonId() ;

                acRadioButton = (RadioButton) findViewById(acradioID);
                radioButton = (RadioButton) findViewById(radioId);

                carType = radioButton.getText().toString().toUpperCase() ;
                acType = acRadioButton.getText().toString().toUpperCase() ;


                Map<String, Object> map = new HashMap<String,Object>();
                map.put("carType" ,"Ambulance") ;
                map.put("acType", acType);
                map.put("buildCompany" , carType) ;
                map.put("carModel","null");
                map.put("carYear", "null") ;
                map.put("truckSize","null" ) ;
                map.put("sitCount","null" )  ;

       DatabaseReference    databaseReference = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid);


                databaseReference.updateChildren(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Intent o = new Intent(getApplicationContext(), remainingStepsActivity.class);
                                startActivity(o);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                new AwesomeWarningDialog(AmbulanceReg.this)
                                        .setTitle("Error")
                                        .setMessage("Error"+ e.getMessage()+ ". Please Try Again !!")
                                        .setColoredCircle(R.color.dialogNoticeBackgroundColor)
                                        .setDialogIconAndColor(R.drawable.ic_notice, R.color.white)
                                        .setCancelable(false)
                                        .setButtonText(getString(R.string.dialog_ok_button))
                                        .setButtonBackgroundColor(R.color.dialogNoticeBackgroundColor)
                                        .setButtonText(getString(R.string.dialog_ok_button))
                                        .setWarningButtonClick(new Closure() {
                                            @Override
                                            public void exec() {
                                                // click
                                                new AwesomeErrorDialog(getApplicationContext()).hide() ;

                                            }
                                        })
                                        .show();




                            }
                        }) ;



            }
        });









        }

      //  ArrayAdapter<String>companyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AMBULANCE_MANUFACTURER);
      //  ArrayAdapter<String>carModelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AMBULANCE_MODEL);
      //  ArrayAdapter<String>carYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AMBULANCE_YEAR);

     //   manufactureTv.setAdapter(companyAdapter);
    //    modelTv.setAdapter(carModelAdapter);
     //   yearTv.setAdapter(carYearAdapter);

      //  manufactureCompany = manufactureTv.getText().toString().trim();
    //    carModel = modelTv.getText().toString().trim();
     //   carYear = yearTv.getText().toString().trim();




    }

