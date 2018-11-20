package cn.edu.pku.yaoyangf.myweather;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by xchen on 16/12/23.
 */
//public class Locate extends Activity{
//    private LocationClient mLocationClient;
//    private MyLocationListener myLocationListener;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.locate);
//        mLocationClient = new LocationClient(this);
//        myLocationListener = new MyLocationListener();
//        mLocationClient.registerLocationListener(myLocationListener);
//        initLocation();
//        mLocationClient.start();
//    }
//    void initLocation()
//    {
//        LocationClientOption option = new LocationClientOption();
//        option.setIsNeedAddress(true);
//        option.setOpenGps(true);
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        option.setCoorType("bd09ll");
//        option.setScanSpan(1000);
//        mLocationClient.setLocOption(option);
//    }
//
//}
//class MyLocationListener implements BDLocationListener{
//    @Override
//    public void onReceiveLocation(BDLocation bdLocation) {
//        String cityName = bdLocation.getCity();
//        Log.d("Locate",cityName);
//
//    }
//}
public class Locate extends Activity{
    private LocationClient mLocationClient;
    private MyLocationListener myLocationListener;

    Button locateBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locate);
        locateBtn = (Button)findViewById(R.id.locateBtn);

        mLocationClient = new LocationClient(this);
        myLocationListener = new MyLocationListener(locateBtn);
        mLocationClient.registerLocationListener(myLocationListener);
        initLocation();
        mLocationClient.start();
    }
    void initLocation()
    {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

}

