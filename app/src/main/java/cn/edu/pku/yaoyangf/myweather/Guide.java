package cn.edu.pku.yaoyangf.myweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2018/11/17.
 */

public class Guide extends Activity{
    private Button startB;
    private List<View> views;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guide);
        initViews();
        startB = (Button) views.get(1).findViewById(R.id.startAppBtn);
//        startB = (Button)findViewById(R.id.startAppBtn);
        startB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guide.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void initViews(){
        LayoutInflater lf = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(lf.inflate(R.layout.guide_page01,null));
        views.add(lf.inflate(R.layout.guide_page02,null));

        viewPagerAdapter = new ViewPagerAdapter(views,this);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
