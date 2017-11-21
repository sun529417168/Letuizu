package cn.com.library.utils;

import android.view.Gravity;
import android.widget.Toast;

import cn.com.library.app.LibApplication;

/**
 * 文件名：ToastUtils
 * 描    述：Toast工具类
 * 作    者：stt
 * 时    间：2016.10.10
 * 版    本：V1.0.0
 */
public class ToastUtils {
    private static Toast toast;//实现不管我们触发多少次Toast调用，都只会持续一次Toast显示的时长

    /**
     * 短时间显示Toast【居下】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToast(String msg) {
        if (LibApplication.getAppContext() != null) {
            if (toast == null) {
                toast = Toast.makeText(LibApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    /**
     * 短时间显示Toast【居中】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToastCenter(String msg) {
        if (LibApplication.getAppContext() != null) {
            if (toast == null) {
                toast = Toast.makeText(LibApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    /**
     * 短时间显示Toast【居上】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showShortToastTop(String msg) {
        if (LibApplication.getAppContext() != null) {
            if (toast == null) {
                toast = Toast.makeText(LibApplication.getAppContext(), msg, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居下】
     *
     * @param msg 显示的内容-字符串
     */
    public static void show(String msg) {
        if (LibApplication.getAppContext() != null) {
            if (toast == null) {
                toast = Toast.makeText(LibApplication.getAppContext(), msg, Toast.LENGTH_LONG);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居中】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToastCenter(String msg) {
        if (LibApplication.getAppContext() != null) {
            if (toast == null) {
                toast = Toast.makeText(LibApplication.getAppContext(), msg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }

    /**
     * 长时间显示Toast【居上】
     *
     * @param msg 显示的内容-字符串
     */
    public static void showLongToastTop(String msg) {
        if (LibApplication.getAppContext() != null) {
            if (toast == null) {
                toast = Toast.makeText(LibApplication.getAppContext(), msg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 0);
            } else {
                toast.setText(msg);
            }
            toast.show();
        }
    }
}
