package com.hubit.hurrydriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeErrorDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeWarningDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hubit.hurrydriver.Activity.remainingStepsActivity;
import com.hubit.hurrydriver.Constants.constants;
import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.Map;

public class CarReg extends AppCompatActivity {


    AutoCompleteTextView  manufactureTv ;
    AutoCompleteTextView  carModelTv ;
    AutoCompleteTextView  carYearTv ;
    RadioGroup  acRadioGroup , carSeatNumber ;
    String acType = "NULL" , carType = "Private-Car" , carSeatNum ;
    String uid  ;



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
    Button submitBtn ;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_reg);


        uid = FirebaseAuth.getInstance().getUid();


        manufactureTv = findViewById(R.id.carManufacturAutoComplete);
        carModelTv = findViewById(R.id.ed_carModel);
        carYearTv = findViewById(R.id.ed_carYear);
        submitBtn = findViewById(R.id.submitBtn);
        acRadioGroup = findViewById(R.id.acTypeGroup) ;
        carSeatNumber = findViewById(R.id.numberOFPPl);


        ArrayAdapter<String>companyAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, COMPANY) ;
        ArrayAdapter<String>carModelAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, CAR_MODEL) ;
        ArrayAdapter<String>carYearAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1, CAR_YEAR) ;

        manufactureTv.setAdapter(companyAdapter);
        manufactureTv.setThreshold(1);
        carModelTv.setAdapter(carModelAdapter);
        carModelTv.setThreshold(1);
        carYearTv.setAdapter(carYearAdapter);


        // getting the text form  the  auto compete text view ;

        manufactureTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                manufactureTv.showDropDown();

                return true;
            }
        });

        carModelTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                carModelTv.showDropDown();

                return true;
            }
        });


        // set ON click
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int carRadioButtonID = carSeatNumber.getCheckedRadioButtonId();
                int acRadioButtonId = acRadioGroup.getCheckedRadioButtonId() ;


                RadioButton  acRadioButton = (RadioButton) findViewById(acRadioButtonId);
                RadioButton carSeatButton = findViewById(carRadioButtonID);

               // Log.d("TAG", "onClick: "+ carRadioButtonID + R.id.);
               carSeatNum = carSeatButton.getText().toString();
                manufratureCompany = manufactureTv.getText().toString() ;
                carModel = carModelTv.getText().toString() ;
                carYear = carYearTv.getText().toString() ;
                acType = acRadioButton.getText().toString().toUpperCase() ;

                Map<String, Object> map = new HashMap<String,Object>();

                map.put("carType" ,carType) ;
                map.put("regStep", "2");
                map.put("acType", acType);
                map.put("buildCompany" , manufratureCompany) ;
                map.put("carModel",carModel);
                map.put("carYear", carYear) ;
                map.put("truckSize","null" ) ;
                map.put("sitCount",carSeatNum)  ;

                 String playerId = "NULL";
                try {
                    OneSignal.sendTag("ID","CAR");
                    playerId = OneSignal.getDeviceState().getUserId();

                } catch (Exception e) {

                }
                map.put("driverNotificationId", playerId);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid);


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

                                new AwesomeWarningDialog(CarReg.this)
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
}
