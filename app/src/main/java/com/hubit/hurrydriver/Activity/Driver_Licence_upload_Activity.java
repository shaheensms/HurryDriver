package com.hubit.hurrydriver.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.hubit.hurrydriver.Constants.constants;
import com.hubit.hurrydriver.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class Driver_Licence_upload_Activity extends AppCompatActivity {

    String uid , imageIdentifier = null  ;
    String  STATE = "";
    ProgressDialog mprogressDialog ;
    TextView  title ;
    ImageView image ;
    Button  takePhotoBtn ;
    StorageReference mStorageReference ;

    private Bitmap compressedImageFile;
    Uri mFilePathUri ;
    DatabaseReference mref ;


    CircleImageView circleImageView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__licence_upload);

        getSupportActionBar().hide();
        uid = FirebaseAuth.getInstance().getUid() ;

        mStorageReference = FirebaseStorage.getInstance().getReference("drivers_profile_Pic").child(uid);
        mref = FirebaseDatabase.getInstance().getReference(constants.driverProfileLink).child(uid);
        //initViews

        title = findViewById(R.id.titleText);
        image = findViewById(R.id.imageOnUploadPage);
        takePhotoBtn = findViewById(R.id.takePhotoBtn) ;
        circleImageView = findViewById(R.id.circlerImageOnUpload);
        //pregress dialog
        mprogressDialog = new ProgressDialog(Driver_Licence_upload_Activity.this  );




        Intent  y = getIntent() ;

        STATE = y.getStringExtra("STATE");


       deteermineWhatToView(STATE) ;



    takePhotoBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(Driver_Licence_upload_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Driver_Licence_upload_Activity.this,
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



        if(state.contains("driverLicence"))

        {
            imageIdentifier = "driver_license_image" ;
            circleImageView.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            title.setText("Pick a photo of your Driver's License");

        }

        else if ( state.contains("nidCard"))

        {     imageIdentifier = "nid_card_image" ;
            circleImageView.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            title.setText("Pick a photo of your NID Card");
        }
        else if ( state.contains("fitness"))
        {
            imageIdentifier = "fitness_license_image" ;
            circleImageView.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            title.setText("Pick a photo of your Vehicle Fitness Card Image");
        }

        else if ( state.contains("taxToken"))

        {
            imageIdentifier = "tax_token_image" ;
            circleImageView.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            title.setText("Pick a photo of your Vehicle Tax Token Image");
        }

        else if ( state.contains("vehicleRegImage"))

        {
            imageIdentifier = "vehicle_reg_image" ;
            circleImageView.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            title.setText("Pick a photo of your Vehicle Registration  Image");
        }

        else if ( state.contains("ppUpload"))

        {
            title.setText("Pick a photo of You  ");
            imageIdentifier = "profile_picture" ;


            circleImageView.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);


        }


    }
    private void BringImagePicker () {

        if(STATE.contains("ppUpload"))
        {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .setCropShape(CropImageView.CropShape.OVAL) //shaping the image
                    .start(Driver_Licence_upload_Activity.this);

        }
        else {

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.RECTANGLE) //shaping the image
                    .start(Driver_Licence_upload_Activity.this);
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
                if (STATE.contains("ppUpload"))
                {
                    circleImageView.setImageURI(mFilePathUri);
                }
                else {
                    image.setImageURI(mFilePathUri);
                }


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

                compressedImageFile = new Compressor(Driver_Licence_upload_Activity.this)
                        .setMaxHeight(920)
                        .setMaxWidth(920)
                        .setQuality(60)
                        .compressToBitmap(newImageFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 40, baos);
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

                    mprogressDialog.setTitle("Uploading.......");
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
