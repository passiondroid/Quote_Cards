package com.quotes.app.cards.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.OnColorSelectedListener;
import com.pavelsikun.vintagechroma.colormode.ColorMode;
import com.quotes.app.cards.R;
import com.quotes.app.cards.adapter.FontListAdapter;
import com.quotes.app.cards.utils.CustomFontsLoader;
import com.quotes.app.cards.utils.SharedPreferenceUtils;

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
    @Bind(R.id.fontLayout) View fontLayoutView;
    private int size;
    public final int color = -1506326529;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        loadAd();

        String text = getIntent().getStringExtra("text");
        quoteTV.setText(text);

        //quoteTV.setOnTouchListener(this);

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
        quoteTV.setOnClickListener(this);
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

        if(SharedPreferenceUtils.getTextColor(FontActivity.this)==0){
            quoteTV.setTextColor(Color.WHITE);
        }else{
            int textColor=SharedPreferenceUtils.getTextColor(FontActivity.this);
            quoteTV.setTextColor(textColor);
        }

        int fontSize = SharedPreferenceUtils.getFontSize(this);
        quoteTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        int alignment = SharedPreferenceUtils.getTextAlignment(this);
        setAlignment(alignment);

        size = SharedPreferenceUtils.getFontSize(this);
    }

    private void loadAd() {
        mAdView = (AdView) findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice("67324194FAF655F4E72D8C3BC700E5DE").build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("67324194FAF655F4E72D8C3BC700E5DE").build();
        mAdView.loadAd(adRequest);
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
            quoteTV.setText(quoteTV.getText().toString().trim());
        }else if(v.getId()==R.id.rightIV){
            quoteTV.setGravity(Gravity.START);
            if(Build.VERSION.SDK_INT >=17) {
                quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }
            SharedPreferenceUtils.setTextAlignment(this,SharedPreferenceUtils.TEXT_ALIGNMENT_START);
            quoteTV.setText(quoteTV.getText().toString().trim());
        }else if(v.getId()==R.id.centerIV){
            quoteTV.setGravity(Gravity.CENTER);
            if(Build.VERSION.SDK_INT >=17) {
                quoteTV.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            }
            SharedPreferenceUtils.setTextAlignment(this,SharedPreferenceUtils.TEXT_ALIGNMENT_CENTER);
            quoteTV.setText(quoteTV.getText().toString().trim());
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
        else if(v.getId()==R.id.quoteTV){
            int initialColor = SharedPreferenceUtils.getTextColor(FontActivity.this);
            if(initialColor==0){
                initialColor = color;
            }
            new ChromaDialog.Builder()
                    .initialColor(initialColor)
                    .colorMode(ColorMode.ARGB)
                    .indicatorMode(IndicatorMode.HEX) //HEX or DECIMAL;
                    .onColorSelected(new OnColorSelectedListener() {
                        @Override public void onColorSelected(int newColor) {
                            quoteTV.setTextColor(newColor);
                            SharedPreferenceUtils.setTextColor(FontActivity.this,newColor);
                        }
                    })
                    .create()
                    .show(getSupportFragmentManager(),"colorPicker");
        }
    }


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) v.getLayoutParams();
//
//        switch(event.getAction())
//        {
//            case MotionEvent.ACTION_MOVE:
//            {
//                params.topMargin = (int)event.getRawY() - (v.getHeight());
//                params.leftMargin = (int)event.getRawX() - (v.getWidth()/2);
//                v.setLayoutParams(params);
//                break;
//            }
//            case MotionEvent.ACTION_UP:
//            {
//                params.topMargin = (int)event.getRawY() - (v.getHeight());
//                params.leftMargin = (int)event.getRawX() - (v.getWidth()/2);
//                v.setLayoutParams(params);
//                break;
//            }
//            case MotionEvent.ACTION_DOWN:
//            {
//                v.setLayoutParams(params);
//                break;
//            }
//        }
//        return false;
//    }
}
