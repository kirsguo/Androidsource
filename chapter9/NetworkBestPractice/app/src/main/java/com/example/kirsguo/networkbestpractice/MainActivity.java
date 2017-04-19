package com.example.kirsguo.networkbestpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String address = "http://www.baidu.com";
//        HttpURLConnection的调用
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
//                返回内容的逻辑
            }

            @Override
            public void onError(Exception e) {
//                异常处理
            }
        });
//        OkHttp的调用
        HttpUtil.sendOkHttpRequest(address,new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
//                返回内容
            }

            @Override
            public void onFailure(Call call, IOException e) {
//                异常处理
            }
        });


    }
}
