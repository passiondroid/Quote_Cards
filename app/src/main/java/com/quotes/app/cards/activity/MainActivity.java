package com.quotes.app.cards.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.quotes.app.cards.R;
import com.quotes.app.cards.utils.CustomFontsLoader;
import com.quotes.app.cards.utils.RuntimePermissionHelper;
import com.quotes.app.cards.utils.SharedPreferenceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.changeImageButton)LinearLayout changeImageButton;
    @Bind(R.id.fontButton)LinearLayout fontButton;
    @Bind(R.id.addQuoteButton)LinearLayout addQuoteButton;
    EditText quoteText;
    @Bind(R.id.frameLayout)FrameLayout frameLayout;
    @Bind(R.id.editLayout)LinearLayout editLayout;
    @Bind(R.id.imageView)ImageView imageView;
    @Bind(R.id.quoteTV)TextView quoteTV;
    private boolean changed;
    private SaveImageTask saveImageTask;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        changeImageButton.setOnClickListener(this);
        addQuoteButton.setOnClickListener(this);
        fontButton.setOnClickListener(this);
        quoteTV.setOnClickListener(this);
        SharedPreferenceUtils.setImageId(this, R.drawable.man1);
        SharedPreferenceUtils.setFont(this, 0);
        SharedPreferenceUtils.setFontSize(this, 30);
        SharedPreferenceUtils.setTextAlignment(this, SharedPreferenceUtils.TEXT_ALIGNMENT_CENTER);
        quoteTV.setText(getResources().getString(R.string.title_text));
        requestPermissions();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            RuntimePermissionHelper runtimePermissionHelper = RuntimePermissionHelper.getInstance(this);
            if (!runtimePermissionHelper.isAllPermissionAvailable()) {
                runtimePermissionHelper.setActivity(this);
                runtimePermissionHelper.requestPermissionsIfDenied();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPreferenceUtils.isImage(this)) {
            int imageId = SharedPreferenceUtils.getSelectedImageId(this);
            imageView.setImageResource(imageId);
        }
        else {
            String imagePath=SharedPreferenceUtils.getSelectedImagePath(this);
            Glide.with(this).load(imagePath).into(imageView);
        }
        int fontId = SharedPreferenceUtils.getFont(this);
        quoteTV.setTypeface(CustomFontsLoader.getTypeface(this, fontId));

        int fontSize = SharedPreferenceUtils.getFontSize(this);
        quoteTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        if(SharedPreferenceUtils.getTextColor(MainActivity.this)==0){
            quoteTV.setTextColor(Color.WHITE);
        }else{
            int textColor=SharedPreferenceUtils.getTextColor(MainActivity.this);
            quoteTV.setTextColor(textColor);
        }


        int alignment = SharedPreferenceUtils.getTextAlignment(this);
        setAlignment(alignment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int alignment = SharedPreferenceUtils.getTextAlignment(this);
        setAlignment(alignment);

    }

    private void setAlignment(int alignment) {
        switch (alignment){
            case SharedPreferenceUtils.TEXT_ALIGNMENT_START:
                quoteTV.setGravity(Gravity.START);
                if(Build.VERSION.SDK_INT >=17) {
                    quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                }
                quoteTV.setText(quoteTV.getText().toString().trim());
                break;
            case SharedPreferenceUtils.TEXT_ALIGNMENT_END:
                quoteTV.setGravity(Gravity.END);
                if(Build.VERSION.SDK_INT >=17) {
                    quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                }
                quoteTV.setText(quoteTV.getText().toString().trim());
                break;
            case SharedPreferenceUtils.TEXT_ALIGNMENT_CENTER:
                quoteTV.setGravity(Gravity.CENTER);
                if(Build.VERSION.SDK_INT >=17) {
                    quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                quoteTV.setText(quoteTV.getText().toString().trim());
                break;
        }

    }


    public void showChangeLangDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View view=inflater.inflate(R.layout.add_quote_dialog, null);
        View empty=new View(MainActivity.this);
        builder.setView(empty);
        empty.requestFocus();
        builder.setView(view);
        quoteText =(EditText) view.findViewById(R.id.addQouteEditText);
        if(changed) {
            quoteText.setText(quoteTV.getText().toString());
        }
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String quote= quoteText.getText().toString().trim();
                        if(quote.length()<1){
                            Toast.makeText(MainActivity.this,"No quote entered",Toast.LENGTH_SHORT).show();
                            }
                        else{
                            quoteTV.setText(quote);
                            changed=true;
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.changeImageButton){
            Intent intent = new Intent(this, ImageActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
            String text = quoteTV.getText().toString();
            intent.putExtra("text",text);
            ActivityCompat.startActivity(this,intent,options.toBundle());
        }else if(v.getId() == R.id.addQuoteButton){
            showChangeLangDialog();
        }else if(v.getId() == R.id.fontButton){
            Intent intent = new Intent(this, FontActivity.class);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
            text = quoteTV.getText().toString();
            intent.putExtra("text",text);
            ActivityCompat.startActivity(this, intent, options.toBundle());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                //quoteText.setVisibility(View.GONE);
                frameLayout.setDrawingCacheEnabled(true);
                frameLayout.buildDrawingCache();
                Bitmap bitmap = frameLayout.getDrawingCache();
//                if(null != saveImageTask && saveImageTask.getStatus().equals(AsyncTask.Status.FINISHED)) {
//                    saveImageTask.execute(bitmap);
//                }else {
                    saveImageTask = new SaveImageTask();
                    saveImageTask.execute(bitmap);
                //}

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void createDirectory() {
        if (Build.VERSION.SDK_INT >= 23) {
            RuntimePermissionHelper runtimePermissionHelper = RuntimePermissionHelper.getInstance(this);
            if (runtimePermissionHelper.isAllPermissionAvailable()) {
                File directory = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_PICTURES+"/QuoteCards/");
                if(!directory.exists()) {
                    directory.mkdirs();
                }
            } else {
                runtimePermissionHelper.setActivity(this);
                runtimePermissionHelper.requestPermissionsIfDenied();
            }
        }else{
            File directory = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_PICTURES+"/QuoteCards/");
            if(!directory.exists()) {
                directory.mkdirs();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Map<String, Integer> perms = new HashMap<>();

        // Fill with results*/
        for (int i = 0; i < permissions.length; i++)
            perms.put(permissions[i], grantResults[i]);

        boolean isAllPermissionsAllowed = false;
        for(int i : grantResults){
            if(i== PackageManager.PERMISSION_GRANTED){
                isAllPermissionsAllowed = true;
            }else{
                isAllPermissionsAllowed = false;
                break;
            }
        }
        if(isAllPermissionsAllowed){
            File directory = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_PICTURES+"/QuoteCards/");
            if(!directory.exists()) {
                directory.mkdirs();
            }
        } else {
            Toast.makeText(this,"Please allow all permissions",Toast.LENGTH_SHORT).show();
        }
    }

   private class SaveImageTask extends AsyncTask<Bitmap,Void,Void>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Saving image....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Bitmap... params) {
            Bitmap bitmap = params[0];
            FileOutputStream out = null;
            String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
            createDirectory();
            try {
                out = new FileOutputStream(new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_PICTURES+"/QuoteCards/IMG_"+timeStamp+".png"));
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            frameLayout.destroyDrawingCache();
            scan();
            postSaveDialogBox();
        }
    }

    public void postSaveDialogBox()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.on_save_dialog_box, null);
        final AlertDialog alertD = new AlertDialog.Builder(this).create();
        alertD.setTitle("Image Saved");
        ImageView share = (ImageView) view.findViewById(R.id.shareMain);
        ImageView viewGallery = (ImageView) view.findViewById(R.id.viewMain);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"You Selected Share",Toast.LENGTH_SHORT).show();
                alertD.dismiss();
            }
        });
        viewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"You Selected View in gallery.",Toast.LENGTH_SHORT).show();
                alertD.dismiss();
            }
        });
        alertD.setView(view);
        alertD.show();
    }
    private void scan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File f = new File("file://"+ Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_PICTURES+"/QuoteCards");
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
        }
        else
        {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_PICTURES+"/QuoteCards")));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferenceUtils.clear(this);
    }
}
