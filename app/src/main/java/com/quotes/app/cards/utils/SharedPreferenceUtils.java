package com.quotes.app.cards.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.View;

public class SharedPreferenceUtils {

	public static final String QUOTE_CARDS = "QUOTE_CARDS";
	public static final String IS_IMAGE = "IS_IMAGE";
	public static final String IMAGE_ID = "IMAGE_ID";
	public static final String IS_PATH = "IS_PATH";
	public static final String IMAGE_PATH = "IMAGE_PATH";
	public static final String FONT_ID = "FONT_ID";
	public static final String FONT_SIZE = "FONT_SIZE";
	public static final String TEXT_ALIGNMENT = "TEXT_ALIGNMENT";
	public static final int TEXT_ALIGNMENT_START = 0;
	public static final int TEXT_ALIGNMENT_END = 1;
	public static final int TEXT_ALIGNMENT_CENTER = 2;
	public static final String TEXT_COLOR = "TEXT_COLOR";

	public static int getTextColor(Context context) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		return pref.getInt(TEXT_COLOR, Color.WHITE);
	}

	public static void setTextColor(Context context,int color) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putInt(TEXT_COLOR, color);
		editor.commit();
	}


	public static boolean isImage(Context context) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		return pref.getBoolean(IS_IMAGE, true);
	}

	public static boolean isPath(Context context) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		return pref.getBoolean(IS_PATH, true);
	}

	public static String getSelectedImagePath(Context context) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		return pref.getString(IMAGE_PATH,"");
	}

	public static void setImagePath(Context context, String imagePath) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean(IS_PATH,true);
		editor.putBoolean(IS_IMAGE,false);
		editor.putInt(IMAGE_ID, 0);
		editor.putString(IMAGE_PATH, imagePath);
		editor.commit();
	}

	public static int getSelectedImageId(Context context) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		return pref.getInt(IMAGE_ID, 0);
	}

	public static void setImageId(Context context, int imageId) {
		SharedPreferences pref = context.getSharedPreferences(QUOTE_CARDS, Context.MODE_PRIVATE);
		Editor editor = pref.edit();
		editor.putBoolean(IS_IMAGE,true);
		editor.putBoolean(IS_PATH,false);
		editor.putString(IMAGE_PATH, "");
		editor.putInt(IMAGE_ID, imageId);
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