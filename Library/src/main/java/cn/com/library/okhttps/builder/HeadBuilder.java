package cn.com.library.okhttps.builder;


import cn.com.library.okhttps.OkHttpUtils;
import cn.com.library.okhttps.request.OtherRequest;
import cn.com.library.okhttps.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
