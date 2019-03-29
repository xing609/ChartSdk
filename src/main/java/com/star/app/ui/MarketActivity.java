package com.star.app.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.star.app.base.BaseActivity;
import com.star.app.ui.chart.MarketFragment;

/**
 * Description：行情
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2019-3-21 9:56
 */

public class MarketActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new MarketFragment())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();
    }
}
