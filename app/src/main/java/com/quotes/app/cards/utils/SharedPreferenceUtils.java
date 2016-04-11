package com.quotes.app.cards.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;

public class SharedPreferenceUtils {

	public static final String QUOTE_CARDS = "QUOTE_CARDS";
	public static final String IMAGE_ID = "IMAGE_ID";
	public static final String FONT_ID = "FONT_ID";
	public static final String FONT_SIZE = "FONT_SIZE";
	public static final String TEXT_ALIGNMENT = "TEXT_ALIGNMENT";
	public static final int TEXT_ALIGNMENT_START = 0;
	public static final int TEXT_ALIGNMENT_END = 1;
	public static final int TEXT_ALIGNMENT_CENTER = 2;

	public static int getSelectedImage(Context context) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		return pref.getInt(IMAGE_ID, 0);
	}

	public static void setImage(Context context,int image) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt(IMAGE_ID, image);
		editor.commit();
	}

	public static void setFont(Context context,int id) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt(FONT_ID, id);
		editor.commit();
	}

	public static int getFont(Context context) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		return pref.getInt(FONT_ID, 0);
	}

	public static void setFontSize(Context context,int size) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt(FONT_SIZE, size);
		editor.commit();
	}

	public static int getFontSize(Context context) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		return pref.getInt(FONT_SIZE, 30);
	}

	public static void setTextAlignment(Context context,int alignment) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt(TEXT_ALIGNMENT, alignment);
		editor.commit();
	}

	public static int getTextAlignment(Context context) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		return pref.getInt(TEXT_ALIGNMENT, View.TEXT_ALIGNMENT_TEXT_START);
	}


}