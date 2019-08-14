package com.example.cropcheck;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cropcheck.models.Image;
import com.example.cropcheck.services.ImageService;
import com.example.cropcheck.utils.CoreUtils;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.io.File;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadImageActivity extends AppCompatActivity {
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    ProgressDialog progressDialog;
    File file;
    int farm_id = 1;
    int season_id = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        Permissions.check(this/*context*/, Manifest.permission.READ_EXTERNAL_STORAGE, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
            }
        });

        Button uploadImage  = (Button) findViewById(R.id.buttonUploadImage);
        Button pickImage = (Button) findViewById(R.id.buttonPickImage);


        pickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                loadImagefromGallery(v);
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });


    }

    public void loadImagefromGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        // Create intent to Open Image applications like Gallery, Google Photos
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

                    // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                Toast.makeText(this, "You have a correct image picked Image",
                        Toast.LENGTH_LONG).show();

                ImageView imgView = (ImageView) findViewById(R.id.imageUpload);
                Uri selectedImage = data.getData();
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                imgView.setImageBitmap(bitmap);

                String filePath = getRealPathFromURIPath(selectedImage, UploadImageActivity.this);
                file = new File(filePath);
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }


    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void uploadImage(){
        MultipartBody.Part fileToUpload;
        if(file==null){
            fileToUpload=null;

        }else{
            RequestBody mFile=RequestBody.create(MediaType.parse("image/*"), file);
            season_id =1;
            farm_id = 2;
            RequestBody season_id_req = RequestBody.create(MediaType.parse("text/plain"), season_id +"");
            RequestBody farm_id_req = RequestBody.create(MediaType.parse("text/plain"), farm_id +"");
            fileToUpload= MultipartBody.Part.createFormData("filename", file.getName(), mFile);

            Call<Image> call = CoreUtils.getAuthRetrofitClient(getToken()).create(ImageService.class).uploadImage(fileToUpload, season_id_req, farm_id_req);
            call.enqueue(new Callback<Image>() {
                @Override
                public void onResponse(Call<Image> call, final Response<Image> response) {
                    if(response.isSuccessful()){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"successfully Uploaded",Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),"Failed Uploading"+response.code(),Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Image> call, final Throwable t) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        }
    }

    public String getToken(){
        return PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("ACCESS_TOKEN", null);
    }

}
