 package com.example.kirsguo.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
 public class MainActivity extends AppCompatActivity {

     private IntentFilter intentFilter;
//     监听网络变化
     private NetworkChangeReceiver networkChangeReceiver;
     private LocalReceiver localReceiver;
     private LocalBroadcastManager localBroadcastManager;

     class NetworkChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

//              通过getSystemService()得到ConnectivityManager实例（管理网络连接的系统服务类）
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isAvailable()){
                Toast.makeText(context,"network is available",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context,"network is unavailable",Toast.LENGTH_SHORT).show();
            }
        }
     }
     class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context,"received local broadcast",Toast.LENGTH_SHORT).show();
        }
     }

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
//         获取实例
         localBroadcastManager = LocalBroadcastManager.getInstance(this);

         Button button = (Button)findViewById(R.id.button);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 Intent intent = new Intent("com.example.kirsguo.broadcasttest.MY_BROADCAST");
//                    发送标准广播
//                 sendBroadcast(intent);
//                    发送有序广播
//                 sendOrderedBroadcast(intent,null);

//                 发送本地广播
                 Intent intent = new Intent("com.example.kirsguo.broadcasttest.LOCAL_BROADCAST");
                 localBroadcastManager.sendBroadcast(intent);
             }
         });



//         intentFilter = new IntentFilter();
//          想监听什么广播就在这里添加相应action
//          网络变化
//         intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//          本地广播
         intentFilter.addAction("com.example.kirsguo.broadcasttest.LOCAL_BROADCAST");
//          注册
//         networkChangeReceiver = new NetworkChangeReceiver();
         localReceiver = new LocalReceiver();
//         registerReceiver(networkChangeReceiver,intentFilter);
         localBroadcastManager.registerReceiver(localReceiver,intentFilter);
     }

     @Override
     protected void onDestroy() {
         super.onDestroy();

//         unregisterReceiver(networkChangeReceiver);
//         解除本地广播注册
         localBroadcastManager.unregisterReceiver(localReceiver);

     }
 }
