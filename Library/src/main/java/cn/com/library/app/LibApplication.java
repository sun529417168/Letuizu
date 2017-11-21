package cn.com.library.app;
/*
*
*                   _oo0oo_
*                  o8888888o
*                  88" . "88
*                  (| -_- |)
*                  0\  =  /0
*                ___/"---"\___
*              .' \\|     |// '.
*             / \\|||  :  |||// \
*            / -||||| -:- |||||- \
*            |  | \\\  -  /// |  |
*            | \_| ''\---/''|_/  |
*            \ .-\__  `-`  __/-. /
*          ___`. .' /--.--\ '. .`___
*       ."" '< `.___\_<|>_/___.` >' "".
*      | | :  `-\`.;` \ _ /`;.`/-`  : | |
*      \ \ `-.   \_ __\ /__ _ /  ._` / /
* ======`-.___`-.___\_____/___.-`___.-`======
*                   `=___=`
*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
*           佛祖保佑      永无BUG
*/

import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alivc.player.AliVcMediaPlayer;
import com.aliyun.vodplayer.downloader.AliyunDownloadConfig;
import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.aliyun.vodplayer.utils.VcPlayerLog;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import java.io.File;

import cn.com.library.R;

/**
 * 文件名：LibApplication
 * 描    述：初始化数据
 * 作    者：stt
 * 时    间：2017.10.10
 * 版    本：V1.0.0
 */
public class LibApplication extends MultiDexApplication {
    private static Context mAppContext;


    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
//        Config.DEBUG = false;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
        /**
         * 以下是阿里播放器的一些初始化工作
         */
        VcPlayerLog.enableLog();
//        LeakCanary.install(this);//初始化内存检测
        //设置保存密码。此密码如果更换，则之前保存的视频无法播放
        AliyunDownloadConfig config = new AliyunDownloadConfig();
        config.setSecretImagePath("/mnt/sdcard/aliyun/encryptedApp.dat");
//        config.setDownloadPassword("123456789");
        //设置保存路径。请确保有SD卡访问权限。
        config.setDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save/");
        //设置同时下载个数
        config.setMaxNums(2);
        AliyunDownloadManager.getInstance(this).setDownloadConfig(config);
        /**
         * 以下是直播的初始化
         * 检查/mnt/sdcard/TAOBAOPLAYER 是否存在,不存在创建
         */

        File rootPath = new File("/mnt/sdcard/aliyun");
        if (!rootPath.exists()) {
            rootPath.mkdir();
        }

        final String businessId = "";

        AliVcMediaPlayer.init(getApplicationContext(), businessId);

    }



    /**
     * 获取系统上下文：用于ToastUtil类
     */
    public static Context getAppContext() {
        return mAppContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    {
        PlatformConfig.setWeixin("wx5fdf641c48e75fb1", "d6c3b57e0ac3d7df2d08a7236a8a380e");
    }
}
