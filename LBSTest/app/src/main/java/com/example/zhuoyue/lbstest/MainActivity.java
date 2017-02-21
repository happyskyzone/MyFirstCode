package com.example.zhuoyue.lbstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
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
    private TextView tvLoaction;
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient mLocationClient;

    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        //地图初始化，注意要在setContentView之前进行
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        tvLoaction = (TextView) findViewById(R.id.tv_location);
        mapView = (MapView) findViewById(R.id.map_baidu);//注意在生命周期中释放资源
        //获取BaiduMap对其操作(对显示的地图操作即对baiduMap操作)
        baiduMap = mapView.getMap();
        //允许在地图上显示位置标记
        baiduMap.setMyLocationEnabled(true);//注意需要到onDestroy中取消

        //危险权限申请运行时权限
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        } else {
            requestLocation();
        }


    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        //设置实时更新定位
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(3000);//更新间隔3000ms
        option.setIsNeedAddress(true);//查询地理位置
        option.setIsNeedAltitude(true);//查询海拔
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(MainActivity.this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(MainActivity.this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;


        }
    }

    public class MyLocationListener implements BDLocationListener {
        StringBuilder builder = null;

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
//            builder = new StringBuilder();
//            builder.append("维度:").append(bdLocation.getLatitude()).append("\n");
//            builder.append("经度:").append(bdLocation.getLongitude()).append("\n");
//            builder.append("海拔:").append(bdLocation.getAltitude()).append("\n");
//            builder.append("国家:").append(bdLocation.getCountry()).append("\n");
//            builder.append("省份:").append(bdLocation.getProvince()).append("\n");
//            builder.append("城市:").append(bdLocation.getCity()).append("\n");
//            builder.append("区:").append(bdLocation.getDistrict()).append("\n");
//            builder.append("街道:").append(bdLocation.getStreet()).append("\n");
//            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
//                builder.append("定位方式:GPS").append("\n");
//            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//                builder.append("定位方式:网络").append("\n");
//            }
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    tvLoaction.setText(builder.toString());
//                }
//            });

            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(bdLocation);
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * 定位到某个位置
     * @param location
     */
    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        //定位显示当前位置的标记
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
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
        //结束程序时关闭定位节省电量
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
}
