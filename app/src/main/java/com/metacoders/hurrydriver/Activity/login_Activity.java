package com.metacoders.hurrydriver.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.metacoders.hurrydriver.R;
import com.shashank.sony.fancytoastlib.FancyToast;

public class login_Activity extends AppCompatActivity {

    EditText phoneNumEdit ;
    String  phone ;
    ImageButton nextBtn ;
    String State ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        //reciving data form the prev


        Intent  o = getIntent();

        State = o.getStringExtra("STATE");


        //init view
        nextBtn = findViewById(R.id.nextBtnmobileverification) ;

        phoneNumEdit = findViewById(R.id.phonenumEditText);

        // getting number from the edit text  ;

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((phoneNumEdit.getText().toString().length()) == 11 ){


                    phone ="+88"+ phoneNumEdit.getText().toString() ;

                    Intent i = new Intent(getApplicationContext() , mobileVerifiacitonActivity.class);

                    i.putExtra("PHONE" , phone);
                    i.putExtra("STATe", State) ;
                    startActivity(i);

                }
                else {

                    FancyToast.makeText(login_Activity.this,"Number Format Invalid!!",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }

            }
        });












    }
}
