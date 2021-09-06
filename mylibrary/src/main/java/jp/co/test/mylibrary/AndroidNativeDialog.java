package jp.co.test.mylibrary;

import android.app.AlertDialog;
import android.content.Context;

public class AndroidNativeDialog {
    static public void showNativeDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("はい", null)
                .setNegativeButton("いいえ", null)
                .show();
    }
}
