package cn.edu.pku.yaoyangf.myweather;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.edu.pku.yaoyangf.bean.City;

/**
 * Created by dell on 2018/10/24.
 */

public class Myadapter extends BaseAdapter {

    private List<City> mCityList;

    public Myadapter(List<City> mCityList) {
        this.mCityList = mCityList;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

}
