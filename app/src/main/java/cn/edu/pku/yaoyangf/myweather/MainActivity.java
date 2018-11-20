package cn.edu.pku.yaoyangf.myweather;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.edu.pku.yaoyangf.bean.TodayWeather;
import cn.edu.pku.yaoyangf.util.NetUtil;

/**
 * Created by dell on 2018/10/9.
 */

public class MainActivity extends Activity implements View.OnClickListener {

//    private static final int UPDATE_TODAY_WEATHER=1;
    private static final int DB=2;
    private String updateCityCode;
    TodayWeather todayWeather = null;
    private ImageView mTitleLocation;
    private ImageView mUpdateBtn;
    private ImageView mCitySelect;
    private ImageView mTitleShare;
    private TextView cityTv, timeTv, humidityTv, weekTv, pmDataTv,
            pmQualityTv,
            temperatureTv, climateTv, windTv, city_name_Tv;
    private ImageView weatherImg, pmImg;

    private TextView week1T,week2T,week3T,temperature1T,temperature2T,temperature3T,wind1T,wind2T,
            wind3T,climate1T,climate2T,climate3T;
    private ProgressBar mUpdateProgress;
    private static final int UPDATE_TODAY_WEATHER = 1;


    private LocationClient mLocationClient=null;
//    private MyLocationListener myListener = new MyLocationListener();
//    private String code = null;
//    private String cityName;

    private Handler mHandler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TODAY_WEATHER:
                    updateTodayWeather((TodayWeather) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initLocation();
        setContentView(R.layout.weather_info);

        mUpdateBtn = (ImageView) findViewById(R.id.title_update_btn);
        mUpdateBtn.setOnClickListener(this);

//        mTitleLocation=(ImageView)findViewById(R.id.title_location);
//        mTitleLocation.setOnClickListener(this);

        if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
            Log.d("myWeather", "网络OK");
            Toast.makeText(MainActivity.this, "网络OK！", Toast.LENGTH_LONG).show();
        } else {
            Log.d("myWeather", "网络挂了");
            Toast.makeText(MainActivity.this, "网络挂了！", Toast.LENGTH_LONG).show();
        }

        mCitySelect = (ImageView) findViewById(R.id.title_city_manager);
        mCitySelect.setOnClickListener(this);

        initView();

//        mLocationClient = new LocationClient(getApplicationContext());
//        myLocationListener = new MyLocationListener();
//        mLocationClient.registerLocationListener(myListener);
//        initLocation();
//        mLocationClient.start();
    }
    public void setUpdateProgress(){
        mUpdateBtn=(ImageView) findViewById(R.id.title_update_btn);
        mUpdateProgress=(ProgressBar)findViewById(R.id.title_update_progress);
        mTitleShare=(ImageView)findViewById(R.id.title_share);

        mUpdateBtn.setVisibility(View.GONE);

        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)mTitleShare.getLayoutParams();
        params.addRule(RelativeLayout.LEFT_OF,R.id.title_update_progress);
        mTitleShare.setLayoutParams(params);

        mUpdateProgress.setVisibility(View.VISIBLE);
    }
    public void setUpdateBtn(){
        mUpdateBtn=(ImageView) findViewById(R.id.title_update_btn);
        mUpdateProgress=(ProgressBar)findViewById(R.id.title_update_progress);
        mUpdateBtn.setVisibility(View.VISIBLE);

        mTitleShare=(ImageView)findViewById(R.id.title_share);

        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)mTitleShare.getLayoutParams();
        // params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.LEFT_OF,R.id.title_update_btn);
        mTitleShare.setLayoutParams(params);

        mUpdateProgress.setVisibility(View.GONE);
    }
//    private class MyLocationListener implements BDLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            Log.d("where","city:"+ location.getCountry());
//            code = location.getCity();
//            code = code.replace("市","");
//            code = code.replace("省","");
//            Log.d("where","定位显示"+code);
//        }
//    }
    void initLocation()
    {
//        mLocationClient = new LocationClient(getApplicationContext());
//        mLocationClient.registerLocationListener(myListener);
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
//        option.setCoorType("bd09ll");
//        option.setOpenGps(true);
//        option.setLocationNotify(true);
//        option.setIsNeedAddress(true);
//        option.setIsNeedLocationDescribe(true);
//        option.setIsNeedLocationPoiList(true);
//        option.setIgnoreKillProcess(false);
////        option.SetIgnoreCacheException(false);
////        option.setEnableSimulateGps(false);
//        mLocationClient.setLocOption(option);
//        mLocationClient.start();
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(0);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，
        // 可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，
        // 设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    void initView(){
        city_name_Tv = (TextView) findViewById(R.id.title_city_name);
        cityTv = (TextView) findViewById(R.id.city);
        timeTv = (TextView) findViewById(R.id.time);
        humidityTv = (TextView) findViewById(R.id.humidity);
        weekTv = (TextView) findViewById(R.id.week_today);
        pmDataTv = (TextView) findViewById(R.id.pm_data);
        pmQualityTv = (TextView) findViewById(R.id.pm2_5_quality);
        pmImg = (ImageView) findViewById(R.id.pm2_5_img);
        temperatureTv = (TextView) findViewById(R.id.temperature);
        climateTv = (TextView) findViewById(R.id.climate);
        windTv = (TextView) findViewById(R.id.wind);
        weatherImg = (ImageView) findViewById(R.id.weather_img);

        week1T = (TextView)findViewById(R.id.future3_no1_week);
        temperature1T = (TextView)findViewById(R.id.future3_no1_temperature);
        climate1T = (TextView)findViewById(R.id.future3_no1_weatherState);
        wind1T = (TextView)findViewById(R.id.future3_no1_wind);

        week2T = (TextView)findViewById(R.id.future3_no2_week);
        temperature2T = (TextView)findViewById(R.id.future3_no2_temperature);
        climate2T = (TextView)findViewById(R.id.future3_no2_weatherState);
        wind2T = (TextView)findViewById(R.id.future3_no2_wind);

        week3T = (TextView)findViewById(R.id.future3_no3_week);
        temperature3T = (TextView)findViewById(R.id.future3_no3_temperature);
        climate3T = (TextView)findViewById(R.id.future3_no3_weatherState);
        wind3T = (TextView)findViewById(R.id.future3_no3_wind);

        week1T.setText("N/A");
        temperature1T.setText("N/A");
        climate1T.setText("N/A");
        wind1T.setText("N/A");
        week2T.setText("N/A");
        temperature2T.setText("N/A");
        climate2T.setText("N/A");
        wind2T.setText("N/A");
        week3T.setText("N/A");
        temperature3T.setText("N/A");
        climate3T.setText("N/A");
        wind3T.setText("N/A");

        city_name_Tv.setText("N/A");
        cityTv.setText("N/A");
        timeTv.setText("N/A");
        humidityTv.setText("N/A");
        pmDataTv.setText("N/A");
        pmQualityTv.setText("N/A");
        weekTv.setText("N/A");
        temperatureTv.setText("N/A");
        climateTv.setText("N/A");
        windTv.setText("N/A");
    }

    /**
     *
     * @param cityCode
     */
    private void queryWeatherCode(String cityCode) {
        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cityCode;
        Log.d("myWeather", address);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection con=null;
                TodayWeather todayWeather = null;
                try{
                    URL url = new URL(address);
                    con = (HttpURLConnection)url.openConnection( );
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String str;
                    while((str=reader.readLine()) != null){
                        response.append(str);
                        Log.d("myWeather", str);
                    }
                    String responseStr=response.toString();
                    Log.d("myWeather", responseStr);

                    todayWeather = parseXML(responseStr);
                    if (todayWeather != null) {
                        Log.d("myWeather", todayWeather.toString());

                        Message msg =new Message();
                        msg.what = UPDATE_TODAY_WEATHER;
                        msg.obj=todayWeather;
                        mHandler.sendMessage(msg);

                    }

                    parseXML(responseStr);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(con != null){
                        con.disconnect();
                    }
                }
            }
        }).start();
    }
    void updateTodayWeather(TodayWeather todayWeather){
        city_name_Tv.setText(todayWeather.getCity()+"天气");
        cityTv.setText(todayWeather.getCity());
        timeTv.setText(todayWeather.getUpdatetime()+ "发布");
        humidityTv.setText("湿度："+todayWeather.getShidu());
        pmDataTv.setText(todayWeather.getPm25());
        pmQualityTv.setText(todayWeather.getQuality());
        weekTv.setText(todayWeather.getDate());
        temperatureTv.setText(todayWeather.getHigh()+"~"+todayWeather.getLow());
        climateTv.setText(todayWeather.getType());
        windTv.setText("风力:"+todayWeather.getFengli());

        week1T.setText(todayWeather.getDate1());
        temperature1T.setText(todayWeather.getHigh1()+"~"+todayWeather.getLow1());
        climate1T.setText(todayWeather.getType1());
        wind1T.setText("风力:"+todayWeather.getFengli1());

        week2T.setText(todayWeather.getDate2());
        temperature2T.setText(todayWeather.getHigh2()+"~"+todayWeather.getLow2());
        climate2T.setText(todayWeather.getType2());
        wind2T.setText("风力:"+todayWeather.getFengli2());

        week3T.setText(todayWeather.getDate3());
        temperature3T.setText(todayWeather.getHigh3()+"~"+todayWeather.getLow3());
        climate3T.setText(todayWeather.getType3());
        wind3T.setText("风力:"+todayWeather.getFengli3());

        Toast.makeText(MainActivity.this,"更新成功！",Toast.LENGTH_SHORT).show();
        try{
            Thread.sleep(2000);
        }catch(Exception e){

        }
        setUpdateBtn();
    }
    private TodayWeather parseXML(String xmldata){
        TodayWeather todayWeather = null;
        int fengxiangCount=0;
        int fengliCount =0;
        int dateCount=0;
        int highCount =0;
        int lowCount=0;
        int typeCount =0;

        int typeNum = 0;
        int fengliNum = 0;
        try {
            XmlPullParserFactory fac = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = fac.newPullParser();
            xmlPullParser.setInput(new StringReader(xmldata));
            int eventType = xmlPullParser.getEventType();
            Log.d("myWeather", "parseXML");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    // 判断当前事件是否为文档开始事件
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    // 判断当前事件是否为标签元素开始事件
                    case XmlPullParser.START_TAG:
                        String xmlString = xmlPullParser.getName().toString();
//                        Log.d("XML-XML-XML", xmlString);
                        if(xmlPullParser.getName().equals("resp")){
                            todayWeather= new TodayWeather();
                        }
                        if (todayWeather != null) {
                            if (xmlPullParser.getName().equals("type")) typeNum++;
                            if (xmlPullParser.getName().equals("fengli")) fengliNum++;
                            if (xmlPullParser.getName().equals("city")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setCity(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("updatetime")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setUpdatetime(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("shidu")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setShidu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("wendu")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setWendu(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("pm25")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setPm25(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("quality")) {
                                eventType = xmlPullParser.next();
                                todayWeather.setQuality(xmlPullParser.getText());
                            } else if (xmlPullParser.getName().equals("fengxiang") && fengxiangCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setFengxiang(xmlPullParser.getText());
                                fengxiangCount++;
                            } else if (xmlPullParser.getName().equals("fengli") && fengliCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setFengli(xmlPullParser.getText());
                                fengliCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setDate(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setHigh(xmlPullParser.getText().substring(2).trim());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setLow(xmlPullParser.getText().substring(2).trim());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 0) {
                                eventType = xmlPullParser.next();
                                todayWeather.setType(xmlPullParser.getText());
                                typeCount++;
                            }

                            else if (xmlPullParser.getName().equals("fengli") && fengliCount == 1 && fengliNum==3) {
                                eventType = xmlPullParser.next();
                                todayWeather.setFengli1(xmlPullParser.getText());
                                fengliCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 1) {
                                eventType = xmlPullParser.next();
                                todayWeather.setDate1(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 1) {
                                eventType = xmlPullParser.next();
                                todayWeather.setHigh1(xmlPullParser.getText().substring(2).trim());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 1) {
                                eventType = xmlPullParser.next();
                                todayWeather.setLow1(xmlPullParser.getText().substring(2).trim());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 1 && typeNum==3) {
                                eventType = xmlPullParser.next();
                                todayWeather.setType1(xmlPullParser.getText());
                                typeCount++;
                            }

                            else if (xmlPullParser.getName().equals("fengli") && fengliCount == 2 && fengliNum==5) {
                                eventType = xmlPullParser.next();
                                todayWeather.setFengli2(xmlPullParser.getText());
                                fengliCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 2) {
                                eventType = xmlPullParser.next();
                                todayWeather.setDate2(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 2) {
                                eventType = xmlPullParser.next();
                                todayWeather.setHigh2(xmlPullParser.getText().substring(2).trim());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 2) {
                                eventType = xmlPullParser.next();
                                todayWeather.setLow2(xmlPullParser.getText().substring(2).trim());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 2 && typeNum==5) {
                                eventType = xmlPullParser.next();
                                todayWeather.setType2(xmlPullParser.getText());
                                typeCount++;
                            }

                            else if (xmlPullParser.getName().equals("fengli") && fengliCount == 3 && fengliNum==7) {
                                eventType = xmlPullParser.next();
                                todayWeather.setFengli3(xmlPullParser.getText());
                                fengliCount++;
                            } else if (xmlPullParser.getName().equals("date") && dateCount == 3) {
                                eventType = xmlPullParser.next();
                                todayWeather.setDate3(xmlPullParser.getText());
                                dateCount++;
                            } else if (xmlPullParser.getName().equals("high") && highCount == 3) {
                                eventType = xmlPullParser.next();
                                todayWeather.setHigh3(xmlPullParser.getText().substring(2).trim());
                                highCount++;
                            } else if (xmlPullParser.getName().equals("low") && lowCount == 3) {
                                eventType = xmlPullParser.next();
                                todayWeather.setLow3(xmlPullParser.getText().substring(2).trim());
                                lowCount++;
                            } else if (xmlPullParser.getName().equals("type") && typeCount == 3 && typeNum==7) {
                                eventType = xmlPullParser.next();
                                todayWeather.setType3(xmlPullParser.getText());
                                typeCount++;
                            }
                        }
                        break;
                    // 判断当前事件是否为标签元素结束事件
                    case XmlPullParser.END_TAG:
                        break;
                }
                // 进入下一个元素并触发相应事件
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todayWeather;
    }
    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.title_city_manager){
            Intent i = new Intent(this,SelectCity.class);
//            startActivity(i);
            startActivityForResult(i,1);
        }

        if (view.getId() == R.id.title_update_btn) {
            setUpdateProgress();
            SharedPreferences sharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
            String cityCode = sharedPreferences.getString("main_city_code", "101010100");
            Log.d("myWeather",cityCode);
            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("myWeather", "网络OK");
                queryWeatherCode(cityCode);
            }else
            {
                Log.d("myWeather", "网络挂了");
                Toast.makeText(MainActivity.this,"网络挂了！",Toast.LENGTH_LONG).show();
            }
        }
//        if (view.getId()==R.id.title_location) {
////            setUpdateProgress();
//            if (mLocationClient.isStarted()) {
//                mLocationClient.stop();
//            }
//            mLocationClient.start();
//
//            final Handler BDHandler = new Handler() {
//                public void handleMessage(Message msg) {
//                    switch (msg.what) {
//                        case DB:
//                            if (msg.obj != null) {
//                                if (NetUtil.getNetworkState(MainActivity.this) != NetUtil.NETWORN_NONE) {
//                                    Log.d("myWeather", "网络ok");
//                                    queryWeatherCode(myListener.cityCode);
//                                    //  Toast.makeText(MainActivity.this,"网络ok！",Toast.LENGTH_LONG).show();
//                                } else {
//                                    Log.d("myWeather", "网络挂了");
//                                    Toast.makeText(MainActivity.this, "网络挂了！", Toast.LENGTH_LONG).show();
//                                }
//
//                            }
//                            myListener.cityCode = null;
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            };
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        while (myListener.cityCode==null){
//                            Thread.sleep(2000);
//                        }
//                        Message msg=new Message();
//                        msg.what=DB;
//                        msg.obj=myListener.cityCode;
//                        BDHandler.sendMessage(msg);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String newCityCode= data.getStringExtra("cityCode");
            Log.d("myWeather", "选择的城市代码为"+newCityCode);
            if (NetUtil.getNetworkState(this) != NetUtil.NETWORN_NONE) {
                Log.d("myWeather", "网络OK");
                queryWeatherCode(newCityCode);
            } else {
                Log.d("myWeather", "网络挂了");
                Toast.makeText(MainActivity.this, "网络挂了！", Toast.LENGTH_LONG).show();
            }
        }
    }
}
