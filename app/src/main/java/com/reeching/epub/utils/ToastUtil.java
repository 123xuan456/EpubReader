package com.reeching.epub.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 绍轩 on 2017/10/10.
 * 自定义Toast提示框
 */

public class ToastUtil {

    //自定义toast对象
    private static Toast toast;

    public static void showToast(final Context context, final String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
