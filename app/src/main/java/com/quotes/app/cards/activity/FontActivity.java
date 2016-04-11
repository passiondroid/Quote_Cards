package com.quotes.app.cards.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.quotes.app.cards.R;
import com.quotes.app.cards.adapter.FontListAdapter;
import com.quotes.app.cards.utils.CustomFontsLoader;
import com.quotes.app.cards.utils.SharedPreferenceUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FontActivity extends AppCompatActivity implements FontListAdapter.OnItemClickListener, View.OnClickListener {

    @Bind(R.id.imageView)ImageView imageView;
    @Bind(R.id.leftIV)ImageButton leftIV;
    @Bind(R.id.centerIV)ImageButton centerIV;
    @Bind(R.id.rightIV)ImageButton rightIV;
    @Bind(R.id.sizePlus)ImageButton sizePlus;
    @Bind(R.id.sizeMinus)ImageButton sizeMinus;
    @Bind(R.id.quoteTV)TextView quoteTV;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        String text = getIntent().getStringExtra("text");
        quoteTV.setText(text);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.fontList);
        FontListAdapter adapter = new FontListAdapter(this);
        adapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        leftIV.setOnClickListener(this);
        centerIV.setOnClickListener(this);
        rightIV.setOnClickListener(this);
        sizePlus.setOnClickListener(this);
        sizeMinus.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int imageId = SharedPreferenceUtils.getSelectedImage(this);
        imageView.setImageResource(imageId);

        int fontId = SharedPreferenceUtils.getFont(this);
        quoteTV.setTypeface(CustomFontsLoader.getTypeface(this, fontId));

        int fontSize = SharedPreferenceUtils.getFontSize(this);
        quoteTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        int alignment = SharedPreferenceUtils.getTextAlignment(this);
        setAlignment(alignment);

        size = SharedPreferenceUtils.getFontSize(this);
    }

    private void setAlignment(int alignment) {
        switch (alignment){
            case 0:
                quoteTV.setGravity(Gravity.START);
                if(Build.VERSION.SDK_INT >=17) {
                    quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                }
                break;
            case 1:
                quoteTV.setGravity(Gravity.END);
                if(Build.VERSION.SDK_INT >=17) {
                    quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                }
                break;
            case 2:
                quoteTV.setGravity(Gravity.CENTER);
                if(Build.VERSION.SDK_INT >=17) {
                    quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                }
                break;
        }

    }

    @Override
    public void onItemClick(View itemView, int position) {
        quoteTV.setTypeface(CustomFontsLoader.getTypeface(this, position));
        SharedPreferenceUtils.setFont(this, position);
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
    public void onClick(View v) {
        if(v.getId()==R.id.leftIV){
            quoteTV.setGravity(Gravity.END);
            if(Build.VERSION.SDK_INT >=17) {
                quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            }

            SharedPreferenceUtils.setTextAlignment(this, SharedPreferenceUtils.TEXT_ALIGNMENT_END);
        }else if(v.getId()==R.id.rightIV){
            quoteTV.setGravity(Gravity.START);
            if(Build.VERSION.SDK_INT >=17) {
                quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }
            SharedPreferenceUtils.setTextAlignment(this,SharedPreferenceUtils.TEXT_ALIGNMENT_START);
        }else if(v.getId()==R.id.centerIV){
            quoteTV.setGravity(Gravity.CENTER);
            if(Build.VERSION.SDK_INT >=17) {
                quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            SharedPreferenceUtils.setTextAlignment(this,SharedPreferenceUtils.TEXT_ALIGNMENT_CENTER);
        }else if(v.getId()==R.id.sizePlus) {
            if (size < 45){
                quoteTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, ++size);
                SharedPreferenceUtils.setFontSize(this,size);
            }
        }else if(v.getId()==R.id.sizeMinus){
            if(size > 30) {
                quoteTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, --size);
                SharedPreferenceUtils.setFontSize(this, size);
            }
        }
    }


}
