package cn.com.library.okhttps.utils;


import com.alibaba.fastjson.JSON;

import cn.com.library.okhttps.callback.IGenericsSerializator;

/**
 * Created by JimGong on 2016/6/23.
 */
public class JsonGenericsSerializator implements IGenericsSerializator {
    @Override
    public <T> T transform(String response, Class<T> classOfT) {
//        return mGson.fromJson(response, classOfT);
        return JSON.parseObject(response, classOfT);
    }
}
