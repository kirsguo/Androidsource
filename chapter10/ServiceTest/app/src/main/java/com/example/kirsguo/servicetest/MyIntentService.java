package com.example.kirsguo.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Kirsguo on 2017/4/18.
 */

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
//        打印当前线程id
        Log.d(TAG, "Thread id is :" +Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: executed");
    }
}
