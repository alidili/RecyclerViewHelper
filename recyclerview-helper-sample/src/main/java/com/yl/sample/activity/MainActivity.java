package com.yl.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yl.sample.R;

/**
 * Index
 * Created by yangle on 2017/10/26.
 * <p>
 * Website：http://www.yangle.tech
 * <p>
 * GitHub：https://github.com/alidili
 * <p>
 * CSDN：http://blog.csdn.net/kong_gu_you_lan
 * <p>
 * JianShu：http://www.jianshu.com/u/34ece31cd6eb
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, HeaderAndFooterViewActivity.class));
    }
}
