package cn.com.letuizu.app;
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
*      | | :  `-\`.;`\ _ /`;.`/-`  : | |
*      \ \ `-.   \_ __\ /__ _ /  ._` / /
* ======`-.___`-.___\_____/___.-`___.-`======
*                   `=___=`
*^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
*           佛祖保佑      永无BUG
*/

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.facebook.stetho.inspector.database.DefaultDatabaseConnectionProvider;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.github.mzule.activityrouter.annotation.Modules;

import java.util.ArrayList;
import java.util.List;

import cn.com.letuizu.R;
import cn.com.library.app.LibApplication;
import cn.rongcloud.contactcard.ContactCardExtensionModule;
import cn.rongcloud.contactcard.IContactCardClickListener;
import cn.rongcloud.contactcard.IContactCardInfoProvider;
import cn.rongcloud.contactcard.message.ContactMessage;
import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.display.FadeInBitmapDisplayer;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.widget.provider.RealTimeLocationMessageProvider;
import io.rong.imlib.ipc.RongExceptionHandler;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;
import io.rong.recognizer.RecognizeExtensionModule;
import letuizu.com.moduleim.db.Friend;
import letuizu.com.moduleim.message.TestMessage;
import letuizu.com.moduleim.message.provider.ContactNotificationMessageProvider;
import letuizu.com.moduleim.message.provider.TestMessageProvider;
import letuizu.com.moduleim.other.SealAppContext;
import letuizu.com.moduleim.other.SealUserInfoManager;
import letuizu.com.moduleim.server.pinyin.CharacterParser;
import letuizu.com.moduleim.server.utils.NLog;
import letuizu.com.moduleim.server.utils.RongGenerate;
import letuizu.com.moduleim.stetho.RongDatabaseDriver;
import letuizu.com.moduleim.stetho.RongDatabaseFilesProvider;
import letuizu.com.moduleim.stetho.RongDbFilesDumperPlugin;
import letuizu.com.moduleim.ui.activity.UserDetailActivity;
import letuizu.com.moduleim.utils.SharedPreferencesContext;


/**
 * 文件名：MyApplication
 * 描    述：初始化数据
 * 作    者：stt
 * 时    间：2017.10.10
 * 版    本：V1.0.0
 */
@Modules({"app", "moduleA", "moduleB", "moduleIM"})
public class MyApplication extends LibApplication {
    private static DisplayImageOptions options;

    @Override
    public void onCreate() {
        super.onCreate();
        initRongYun();
    }

    private void initRongYun() {
        Stetho.initialize(new Stetho.Initializer(this) {
            @Override
            protected Iterable<DumperPlugin> getDumperPlugins() {
                return new Stetho.DefaultDumperPluginsBuilder(MyApplication.this)
                        .provide(new RongDbFilesDumperPlugin(MyApplication.this, new RongDatabaseFilesProvider(MyApplication.this)))
                        .finish();
            }

            @Override
            protected Iterable<ChromeDevtoolsDomain> getInspectorModules() {
                Stetho.DefaultInspectorModulesBuilder defaultInspectorModulesBuilder = new Stetho.DefaultInspectorModulesBuilder(MyApplication.this);
                defaultInspectorModulesBuilder.provideDatabaseDriver(new RongDatabaseDriver(MyApplication.this, new RongDatabaseFilesProvider(MyApplication.this), new DefaultDatabaseConnectionProvider()));
                return defaultInspectorModulesBuilder.finish();
            }
        });

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

//            LeakCanary.install(this);//内存泄露检测
            RongPushClient.registerHWPush(this);
            RongPushClient.registerMiPush(this, "2882303761517473625", "5451747338625");
            /**
             * 注意：
             *
             * IMKit SDK调用第一步 初始化
             *
             * context上下文
             *
             * 只有两个进程需要初始化，主进程和 push 进程
             */
            RongIM.setServerInfo("nav.cn.ronghub.com", "up.qbox.me");
            RongIM.init(this);
            NLog.setDebug(true);//Seal Module Log 开关
            SealAppContext.init(this);
            SharedPreferencesContext.init(this);
            Thread.setDefaultUncaughtExceptionHandler(new RongExceptionHandler(this));

            try {
                RongIM.registerMessageTemplate(new ContactNotificationMessageProvider());
                RongIM.registerMessageTemplate(new RealTimeLocationMessageProvider());
                RongIM.registerMessageType(TestMessage.class);
                RongIM.registerMessageTemplate(new TestMessageProvider());


            } catch (Exception e) {
                e.printStackTrace();
            }

            openSealDBIfHasCachedToken();

            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(cn.com.library.R.drawable.de_default_portrait)
                    .showImageOnFail(cn.com.library.R.drawable.de_default_portrait)
                    .showImageOnLoading(cn.com.library.R.drawable.de_default_portrait)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();

//            RongExtensionManager.getInstance().registerExtensionModule(new PTTExtensionModule(this, true, 1000 * 60));
            RongExtensionManager.getInstance().registerExtensionModule(new ContactCardExtensionModule(new IContactCardInfoProvider() {
                @Override
                public void getContactAllInfoProvider(final IContactCardInfoCallback contactInfoCallback) {
                    SealUserInfoManager.getInstance().getFriends(new SealUserInfoManager.ResultCallback<List<Friend>>() {
                        @Override
                        public void onSuccess(List<Friend> friendList) {
                            contactInfoCallback.getContactCardInfoCallback(friendList);
                        }

                        @Override
                        public void onError(String errString) {
                            contactInfoCallback.getContactCardInfoCallback(null);
                        }
                    });
                }

                @Override
                public void getContactAppointedInfoProvider(String userId, String name, String portrait, final IContactCardInfoCallback contactInfoCallback) {
                    SealUserInfoManager.getInstance().getFriendByID(userId, new SealUserInfoManager.ResultCallback<Friend>() {
                        @Override
                        public void onSuccess(Friend friend) {
                            List<UserInfo> list = new ArrayList<>();
                            list.add(friend);
                            contactInfoCallback.getContactCardInfoCallback(list);
                        }

                        @Override
                        public void onError(String errString) {
                            contactInfoCallback.getContactCardInfoCallback(null);
                        }
                    });
                }

            }, new IContactCardClickListener() {
                @Override
                public void onContactCardClick(View view, ContactMessage content) {
                    Intent intent = new Intent(view.getContext(), UserDetailActivity.class);
                    Friend friend = SealUserInfoManager.getInstance().getFriendByID(content.getId());
                    if (friend == null) {
                        UserInfo userInfo = new UserInfo(content.getId(), content.getName(),
                                Uri.parse(TextUtils.isEmpty(content.getImgUrl()) ? RongGenerate.generateDefaultAvatar(content.getName(), content.getId()) : content.getImgUrl()));
                        friend = CharacterParser.getInstance().generateFriendFromUserInfo(userInfo);
                    }
                    intent.putExtra("friend", friend);
                    view.getContext().startActivity(intent);
                }
            }));
            RongExtensionManager.getInstance().registerExtensionModule(new RecognizeExtensionModule());
        }
    }

    public static DisplayImageOptions getOptions() {
        return options;
    }

    private void openSealDBIfHasCachedToken() {
        SharedPreferences sp = getSharedPreferences("config", MODE_PRIVATE);
        String cachedToken = sp.getString("loginToken", "");
        if (!TextUtils.isEmpty(cachedToken)) {
            String current = getCurProcessName(this);
            String mainProcessName = getPackageName();
            if (mainProcessName.equals(current)) {
                SealUserInfoManager.getInstance().openDB();
            }
        }
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
