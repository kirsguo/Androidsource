package com.example.kirsguo.networkbestpractice;

/**
 * Created by Kirsguo on 2017/4/17.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
