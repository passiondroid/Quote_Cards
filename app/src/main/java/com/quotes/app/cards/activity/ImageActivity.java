package com.quotes.app.cards.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.quotes.app.cards.R;
import com.quotes.app.cards.adapter.ImageListAdapter;
import com.quotes.app.cards.model.BackgroundImage;
import com.quotes.app.cards.utils.CustomFontsLoader;
import com.quotes.app.cards.utils.SharedPreferenceUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageActivity extends AppCompatActivity implements ImageListAdapter.OnItemClickListener{
    private static final int GALLERY_PICTURE = 1;
    private static final int CAMERA_REQUEST = 2;
    List<BackgroundImage> backgroundImages;
    @Bind(R.id.imageView)
    ImageView imageView;

    @Bind(R.id.quoteTV)
    TextView quoteTV;
    private Bitmap bitmap;
    private String selectedImagePath;
    private String fileDir;
    Uri outputFileUri=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        generateBackgroundImageList();

        String text = getIntent().getStringExtra("text");
        quoteTV.setText(text);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imageList);
        ImageListAdapter adapter = new ImageListAdapter(backgroundImages,this);
        adapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
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

        if(SharedPreferenceUtils.getTextColor(ImageActivity.this)==0){
            quoteTV.setTextColor(Color.WHITE);
        }else{
            int textColor=SharedPreferenceUtils.getTextColor(ImageActivity.this);
            quoteTV.setTextColor(textColor);
        }

        int fontSize = SharedPreferenceUtils.getFontSize(this);
        quoteTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

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
                break;
            case SharedPreferenceUtils.TEXT_ALIGNMENT_END:
                quoteTV.setGravity(Gravity.END);
                if(Build.VERSION.SDK_INT >=17) {
                    quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                }
                break;
            case SharedPreferenceUtils.TEXT_ALIGNMENT_CENTER:
                quoteTV.setGravity(Gravity.CENTER);
                if(Build.VERSION.SDK_INT >=17) {
                    quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                break;
        }
    }

    private void generateBackgroundImageList() {
        backgroundImages = new ArrayList<>();
        BackgroundImage image1 = new BackgroundImage(R.drawable.alone2);
        BackgroundImage image2 = new BackgroundImage(R.drawable.alone1);
        BackgroundImage image3 = new BackgroundImage(R.drawable.alone3);
        BackgroundImage image4 = new BackgroundImage(R.drawable.alone4);
        BackgroundImage image5 = new BackgroundImage(R.drawable.alone5);
        BackgroundImage image6 = new BackgroundImage(R.drawable.alone6);
        BackgroundImage image7 = new BackgroundImage(R.drawable.beach1);
        BackgroundImage image8 = new BackgroundImage(R.drawable.beach2);
        BackgroundImage image9 = new BackgroundImage(R.drawable.beach3);
        BackgroundImage image10 = new BackgroundImage(R.drawable.beach4);
        BackgroundImage image11 = new BackgroundImage(R.drawable.beach5);
        BackgroundImage image12 = new BackgroundImage(R.drawable.beach6);
        BackgroundImage image13 = new BackgroundImage(R.drawable.beach7);
        BackgroundImage image14 = new BackgroundImage(R.drawable.beach8);
        BackgroundImage image15 = new BackgroundImage(R.drawable.man1);
        BackgroundImage image16 = new BackgroundImage(R.drawable.man2);
        BackgroundImage image17 = new BackgroundImage(R.drawable.man3);
        BackgroundImage image18 = new BackgroundImage(R.drawable.man4);
        BackgroundImage image19 = new BackgroundImage(R.drawable.man5);
        BackgroundImage image20 = new BackgroundImage(R.drawable.man6);
        BackgroundImage image21 = new BackgroundImage(R.drawable.man7);
        BackgroundImage image22 = new BackgroundImage(R.drawable.man8);
        BackgroundImage image23 = new BackgroundImage(R.drawable.nature1);
        BackgroundImage image24 = new BackgroundImage(R.drawable.nature2);
        BackgroundImage image26 = new BackgroundImage(R.drawable.nature4);
        BackgroundImage image27 = new BackgroundImage(R.drawable.nature5);
        BackgroundImage image28 = new BackgroundImage(R.drawable.nature7);
        BackgroundImage image29 = new BackgroundImage(R.drawable.sky1);
        BackgroundImage image30 = new BackgroundImage(R.drawable.sky2);
        BackgroundImage image31 = new BackgroundImage(R.drawable.sky3);
        BackgroundImage image32 = new BackgroundImage(R.drawable.sky4);
        backgroundImages.add(image1);
        backgroundImages.add(image2);
        backgroundImages.add(image3);
        backgroundImages.add(image4);
        backgroundImages.add(image5);
        backgroundImages.add(image6);
        backgroundImages.add(image7);
        backgroundImages.add(image8);
        backgroundImages.add(image9);
        backgroundImages.add(image10);
        backgroundImages.add(image11);
        backgroundImages.add(image12);
        backgroundImages.add(image13);
        backgroundImages.add(image14);
        backgroundImages.add(image15);
        backgroundImages.add(image16);
        backgroundImages.add(image17);
        backgroundImages.add(image18);
        backgroundImages.add(image19);
        backgroundImages.add(image20);
        backgroundImages.add(image21);
        backgroundImages.add(image22);
        backgroundImages.add(image23);
        backgroundImages.add(image24);
        backgroundImages.add(image26);
        backgroundImages.add(image27);
        backgroundImages.add(image28);
        backgroundImages.add(image29);
        backgroundImages.add(image30);
        backgroundImages.add(image31);
        backgroundImages.add(image32);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        if(position == 0){
            startDialog();
        }else {
            imageView.setImageResource(backgroundImages.get(position).getImageId());
            SharedPreferenceUtils.setImageId(this, backgroundImages.get(position).getImageId());
        }
    }

    private void startDialog() {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle("Upload Pictures Option");
        myAlertDialog.setMessage("How do you want to set your picture?");

        myAlertDialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent pictureActionIntent = null;

                        pictureActionIntent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(
                                pictureActionIntent,
                                GALLERY_PICTURE);

                    }
                });

        myAlertDialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                            fileDir = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES + "/QuoteCards/IMG_TEMP_" + timeStamp + ".jpg";
                            File f = new File(fileDir);
                            outputFileUri= Uri.fromFile(f);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        }
                    }
                });
        myAlertDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        selectedImagePath = null;

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            Toast.makeText(ImageActivity.this,"Saved",Toast.LENGTH_SHORT).show();
            if(outputFileUri!=null)
            {
                SharedPreferenceUtils.setImagePath(this,fileDir);
            }

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                selectedImagePath = c.getString(columnIndex);
                SharedPreferenceUtils.setImagePath(this,selectedImagePath);
                c.close();
                /*try {
                Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());

                imageView.setImageURI(data.getData());

                } catch (FileNotFoundException e) {
                    // handle errors
                } catch (IOException e) {
                    // handle errors
                }
                */
            } else {
                Toast.makeText(getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

}
