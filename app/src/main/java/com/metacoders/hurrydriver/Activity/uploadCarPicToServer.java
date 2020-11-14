package com.metacoders.hurrydriver.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.metacoders.hurrydriver.Constants.constants;
import com.metacoders.hurrydriver.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class uploadCarPicToServer extends AppCompatActivity {

    String uid , imageIdentifier = null  ;
    String  STATE = "";
    ProgressDialog mprogressDialog ;
    TextView title ;
    ImageView image ;
    Button takePhotoBtn ;
    StorageReference mStorageReference ;

    private Bitmap compressedImageFile;
    Uri mFilePathUri ;
    DatabaseReference mref ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_nid_upload);


        getSupportActionBar().hide();
        uid = FirebaseAuth.getInstance().getUid() ;
        mStorageReference = FirebaseStorage.getInstance().getReference("drivers_profile_Pic").child(uid).child("carPics");
        mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid).child("carPics");

        title = findViewById(R.id.title);
        image = findViewById(R.id.selectedPic);
        takePhotoBtn = findViewById(R.id.buttonSumbit) ;



        //pregress dialog
        mprogressDialog = new ProgressDialog(uploadCarPicToServer.this  );
        Intent y = getIntent() ;

        STATE = y.getStringExtra("STATE");
       // Toast.makeText(getApplicationContext(), "Satte " + STATE , Toast.LENGTH_SHORT).show();

        deteermineWhatToView(STATE) ;


        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(uploadCarPicToServer.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(uploadCarPicToServer.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                        BringImagePicker();



                    } else {

                        BringImagePicker();

                    }

                } else {

                    BringImagePicker();

                }


            }
        });



    }





    private void deteermineWhatToView(String state) {



        if(state.contains("front"))

        {
            imageIdentifier = "car_front_image" ;

            image.setVisibility(View.VISIBLE);
            title.setText("Pick a Front photo of your Car");

        }

        else if ( state.contains("back"))

        {     imageIdentifier = "car_back_image" ;

            image.setVisibility(View.VISIBLE);
            title.setText("Pick a photo of your Car's Back");
        }
        else if ( state.equals("side"))
        {
            imageIdentifier = "car_side_image" ;

            image.setVisibility(View.VISIBLE);
            title.setText("Pick a photo of your Car's Side");
        }

        else if ( state.contains("inside"))

        {
            imageIdentifier = "car_inside_image" ;
            image.setVisibility(View.VISIBLE);
            title.setText("Pick a photo of your Vehicle Tax Token Image");
        }




    }

    private void BringImagePicker () {

        if(STATE.contains("ppUpload"))
        {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .setCropShape(CropImageView.CropShape.OVAL) //shaping the image
                    .start(uploadCarPicToServer.this);

        }
        else {

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.RECTANGLE) //shaping the image
                    .start(uploadCarPicToServer.this);
        }

    }
    @Override
    protected void onActivityResult(/*int requestCode, int resultCode, @Nullable Intent data*/
            int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mFilePathUri = result.getUri();

                    image.setImageURI(mFilePathUri);



                //sending data once  user select the image
                uploadPicToServer(mFilePathUri) ;


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Toast.makeText(this, error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

    }

    private void uploadPicToServer(Uri mFilePathUri) {

        if(mFilePathUri != null)
        {
            final String randomName = UUID.randomUUID().toString();

            // PHOTO UPLOAD
            File newImageFile = new File(mFilePathUri.getPath());

            try {

                compressedImageFile = new Compressor(uploadCarPicToServer.this)
                        .setMaxHeight(920)
                        .setMaxWidth(920)
                        .setQuality(60)
                        .compressToBitmap(newImageFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageData = baos.toByteArray();
            UploadTask filePath = mStorageReference.child(randomName+uid + ".jpg").putBytes(imageData);

            filePath.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloaduri = uriTask.getResult();



                    //   String ts =mref.push().getKey() ;


                    mref.child(imageIdentifier).setValue(downloaduri.toString());
                    sentToPrevActivity();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mprogressDialog.hide();
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                    mprogressDialog.setMessage("Uploading.......");
                    mprogressDialog.show();


                }
            });


        }

    }

    private void sentToPrevActivity() {

        setResult(RESULT_OK);
        finish();


    }

}