package cn.com.library.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.com.library.R;

/**
 * 文件名：StatusBarUtils
 * 描    述：沉浸式标题栏
 * 作    者：stt
 * 时    间：2017.10.10
 * 版    本：V1.0.0
 */
public class StatusBarUtils {

    public static Context mContext;

    public static void ff(final Context context, final int color) {
        mContext = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(
                (Activity) context);
        tintManager.setStatusBarTintEnabled(true);
        // 通知栏所需颜色
        tintManager.setStatusBarTintResource(color);
    }

    public static void ff(final Context context) {
        mContext = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(
                (Activity) context);
        tintManager.setStatusBarTintEnabled(true);
        // 通知栏所需颜色
        tintManager.setStatusBarTintResource(R.color.blue);

    }

    public static void whiteTheme(final Context context, int color) {
        mContext = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(
                (Activity) context);
        tintManager.setStatusBarTintEnabled(true);
        // 通知栏所需颜色
        tintManager.setStatusBarTintResource(color);

    }

    @TargetApi(19)
    private static void setTranslucentStatus(boolean on) {
        Window win = ((Activity) mContext).getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
