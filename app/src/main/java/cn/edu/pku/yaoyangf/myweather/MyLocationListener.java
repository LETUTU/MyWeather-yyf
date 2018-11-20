package cn.edu.pku.yaoyangf.myweather;

import android.util.Log;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * Created by dell on 2018/11/19.
 */

public class MyLocationListener implements BDLocationListener {
    Button locBtn;
    MyLocationListener(Button locBtn)
    {
        this.locBtn = locBtn;
    }
    String cityName;
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        cityName = bdLocation.getCity();
        Log.d("Locate",cityName);
        locBtn.setText(cityName);
    }
}
