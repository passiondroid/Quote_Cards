package com.quotes.app.cards.utils;

import android.app.Dialog;
import android.view.Window;

import com.quotes.app.cards.R;

public final class DialogUtils {

    private DialogUtils() {
        //do nothing
    }

    /**
     * API to create a dialog box.
     */
    public static void createDialog(Dialog dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.enter_text_dialog);
        dialog.show();
    }
}