package com.quotes.app.cards.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.lyft.android.scissors.CropView;
import com.quotes.app.cards.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CropActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton cropButton;
    private CropView cropView;
    private static final String TAG = "CropActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Crop");
        cropButton = (FloatingActionButton)findViewById(R.id.cropButton);
        cropButton.setOnClickListener(this);
        cropView = (CropView)findViewById(R.id.crop_view);
        Uri uri = getIntent().getParcelableExtra("URI");
        try {
            Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            cropView.setImageBitmap(image);
        } catch (IOException e1) {
            Log.e(TAG, "onCreate: ",e1 );
        }
    }

    @Override
    public void onClick(View view) {
        Bitmap croppedBitmap = cropView.crop();
        CropImageTask cropImageTask = new CropImageTask();
        cropImageTask.execute(croppedBitmap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CropImageTask extends AsyncTask<Bitmap,Void,String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CropActivity.this);
            progressDialog.setMessage("Cropping image....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Bitmap... params) {
            Bitmap bitmap = params[0];
            FileOutputStream out = null;
            String path = "";
            String timeStamp = new SimpleDateFormat("mm_ss").format(new Date());
            try {
                File file = new File(getFilesDir(), "image_"+timeStamp+".png");
                path = file.getAbsolutePath();
                out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return path;
        }

        @Override
        protected void onPostExecute(String path) {
            super.onPostExecute(path);
            progressDialog.dismiss();
            Intent intent  = new Intent();
            intent.putExtra("CROP_IMAGE_PATH",path);
            setResult(RESULT_OK,intent);
            CropActivity.this.finish();
        }
    }
}
