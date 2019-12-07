package com.haitao.marqueeview;

import android.app.Application;

/**
 * @ClassName: MyApplication
 * @Description: java类作用描述
 * @Author: Pan
 * @CreateDate: 2019/12/7 17:16
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
