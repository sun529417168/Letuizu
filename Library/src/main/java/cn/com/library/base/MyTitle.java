package cn.com.library.base;
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

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.library.R;
import cn.com.library.utils.LTActivityUtil;
import cn.com.library.utils.NetWorkUtils;


/**
 * 文件名：MyTitle
 * 描    述：title的公共使用
 * 作    者：stt
 * 时    间：2017.4.26
 * 版    本：V1.0.0
 */

public class MyTitle implements View.OnClickListener {
    private static MyTitle title;
    private LinearLayout back;
    private TextView titleName;
    private LTActivityUtil PGApp;
    private TextView netText;
    private Context context;

    /**
     * 方法名：MyTitle
     * 功    能：单利模式获取唯一title
     * 参    数：
     * 返回值：
     */
    public static MyTitle getInstance() {
        if (null == title) {
            title = new MyTitle();
        }
        return title;
    }

    /**
     * 方法名：setTitle
     * 功    能：设置标题名称
     * 参    数：BaseActivity view, String info, LTActivityUtil PGApp, boolean flag
     * 返回值：
     */
    public void setTitle(BaseActivity view, String info, LTActivityUtil PGApp, boolean flag) {
        this.PGApp = PGApp;
        this.context = view;
        back = (LinearLayout) view.findViewById(R.id.title_back);
        back.setOnClickListener(this);
        if (flag) {
            back.setVisibility(View.VISIBLE);
        } else {
            back.setVisibility(View.GONE);
        }
        titleName = (TextView) view.findViewById(R.id.title_name);
        titleName.setText(info);
        netText = (TextView) view.findViewById(R.id.title_net);
        netText.setOnClickListener(this);
        boolean netConnect = view.isNetConnect();
        if (netConnect) {
            netText.setVisibility(View.GONE);
        } else {
            netText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 方法名：setNetText
     * 功    能：判断网络变化提示
     * 参    数：BaseActivity view, int netMobile
     * 返回值：
     */
    public void setNetText(BaseActivity view, int netMobile) {
        this.context = view;
        netText = (TextView) view.findViewById(R.id.title_net);
        netText.setOnClickListener(this);
        //网络状态变化时的操作
        if (netMobile == NetWorkUtils.NETWORK_NONE) {
            netText.setVisibility(View.VISIBLE);
        } else {
            netText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.title_back) {
            PGApp.finishTop();
        }
        if (i == R.id.title_net) {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        }
    }
}
