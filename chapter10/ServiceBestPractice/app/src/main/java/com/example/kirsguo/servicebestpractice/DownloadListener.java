package com.example.kirsguo.servicebestpractice;

/**
 * Created by Kirsguo on 2017/4/18.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
