package com.example.kirsguo.lbstest;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public LocationClient mLocationClient;

    private TextView positionText;

    private MapView mapView;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.bmapView);

        baiduMap = mapView.getMap();//获取地图
        baiduMap.setMyLocationEnabled(true);//我的位置信息

        positionText = (TextView)findViewById(R.id.position_text_view);

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
        }else {
            requestLocation();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(2000);
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//定位模式为传感器
        option.setIsNeedAddress(true);//获取当前位置详细地址信息
        mLocationClient.setLocOption(option);
    }

    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }
    private void navigateTo(BDLocation bdlocation){
        if (isFirstLocate){
            LatLng ll = new LatLng(bdlocation.getLatitude(),bdlocation.getLongitude());

            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);

            update = MapStatusUpdateFactory.zoomTo(25f);
            baiduMap.animateMapStatus(update);

            isFirstLocate =false;
        }

        //显示自身位置
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(bdlocation.getLatitude());
        locationBuilder.longitude(bdlocation.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0){
                    for (int result :grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else{
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
//            final StringBuilder currentPosition = new StringBuilder();
//            currentPosition.append("纬度: ").append(bdLocation.getLatitude()).append("\n");
//            currentPosition.append("经度: ").append(bdLocation.getLongitude()).append("\n");
//            currentPosition.append("国家: ").append(bdLocation.getCountry()).append("\n");
//            currentPosition.append("省: ").append(bdLocation.getProvince()).append("\n");
//            currentPosition.append("市: ").append(bdLocation.getCity()).append("\n");
//            currentPosition.append("区: ").append(bdLocation.getDistrict()).append("\n");
//            currentPosition.append("街道: ").append(bdLocation.getStreet()).append("\n");
//            currentPosition.append("定位方式: ");
//            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation){
//                currentPosition.append("GPS");
//            }else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
//                currentPosition.append("网络");
//            }
//            Log.d("MainActivity", "onReceiveLocation: "+ Thread.currentThread().getId());
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    positionText.setText(currentPosition);
//                }
//            });


            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                    || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(bdLocation);
            }


        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}
