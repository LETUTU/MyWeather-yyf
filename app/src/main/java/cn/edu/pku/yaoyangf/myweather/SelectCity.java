package cn.edu.pku.yaoyangf.myweather;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.pku.yaoyangf.app.MyApplication;
import cn.edu.pku.yaoyangf.bean.City;

/**
 * Created by dell on 2018/10/20.
 */

public class SelectCity extends Activity implements View.OnClickListener{

//    private ImageView mBackBtn;
    private ImageView backBtn;
    private ListView cityListLv;
    private EditText searchEt;
    private ImageView searchBtn;

    private List<City> mCityList;
    private MyApplication mApplication;
    private ArrayList<String> mArrayList;
    private int updateCityCode = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

//        initViews();

//        mBackBtn = (ImageView) findViewById(R.id.title_back);
//        mBackBtn.setOnClickListener(this);
        searchEt = (EditText)findViewById(R.id.selectcity_search);
        searchBtn = (ImageView)findViewById(R.id.selectcity_search_button);
        searchBtn.setOnClickListener(this);

//        String[] listData = {"1","2","3"};
        mApplication = (MyApplication)getApplication();
        mCityList = mApplication.getCityList();
        mArrayList = new ArrayList<String>();
        for(int i=0;i<mCityList.size();i++)
        {
            String No_ = Integer.toString(i+1);
            String number = mCityList.get(i).getNumber();
            String provinceName = mCityList.get(i).getProvince();
            String cityName = mCityList.get(i).getCity();
            mArrayList.add("No."+No_+":"+number+"-"+provinceName+"-"+cityName);
        }
        cityListLv = (ListView)findViewById(R.id.selectcity_lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectCity.this,R.layout.support_simple_spinner_dropdown_item,mArrayList);
        cityListLv.setAdapter(adapter);

        //添加ListView项的点击事件的动作
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                updateCityCode = Integer.parseInt(mCityList.get(position).getNumber());
                Log.d("update city code",Integer.toString(updateCityCode));
            }
        };
        //为组件绑定监听
        cityListLv.setOnItemClickListener(itemClickListener);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
//            case R.id.title_back:
//                Intent i = new Intent();
//                i.putExtra("cityCode",Integer.toString(updateCityCode));//"101160101"
//                setResult(RESULT_OK, i);
//                finish();
////                Intent intent = new Intent(this,MainActivity.class);
////                intent.putExtra("citycode",Integer.toString(updateCityCode));
////                startActivity(intent);
//                break;
            case R.id.selectcity_search_button:
                String citycode = searchEt.getText().toString();
                Log.d("Search",citycode);

                ArrayList<String> mSearchList = new ArrayList<String>();
                for(int i=0;i<mSearchList.size();i++)
                {
                    String No_ = Integer.toString(i+1);
                    String number = mCityList.get(i).getNumber();
                    String provinceName = mCityList.get(i).getProvince();
                    String cityName = mCityList.get(i).getCity();
                    if(number.equals(citycode)){
                        mSearchList.add("No."+No_+":"+number+"-"+provinceName+"-"+cityName);
                        Log.d("changed adapter data","No."+No_+":"+number+"-"+provinceName+"-"+cityName);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectCity.this,R.layout.support_simple_spinner_dropdown_item,mSearchList);
                    cityListLv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
//                Timer timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//
//                        /**
//                         * 延时执行的代码
//                         */
//
//                    }
//                },3000); // 延时1秒
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("cityCode",citycode);
                setResult(RESULT_OK, intent);
                finish();
//                startActivity(intent);
            default:
                break;
        }
    }
//    private void initViews(){
//        mBackBtn = (ImageView)findViewById(R.id.title_back_right);
//        mBackBtn.setOnClickListener(this);
//
////        mClearEditText = (ClearEditText) findViewById(R.id.search_city);
////        mListView = (ListView) findViewById(R.id.title_list);
//
//        mList = (ListView)findViewById(R.id.title_list);
//        MyApplication myApplication = (MyApplication) getApplication();
//        cityList = myApplication.getCityList();
//        for (City city : cityList){
//            filterDataList.add(city);
//        }
//        myadapter = new Myadapter(cityList);//SelectCity.this,
//        mList.setAdapter(myadapter);
//        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                City city = filterDataList.get(position);
//                Intent i = new Intent();
//                i.putExtra("cityCode",city.getNumber());
//                setResult(RESULT_OK,i);
//                finish();
//            }
//        });
//    }
}
