package com.star.app.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import com.star.app.model.FutureItem;
import com.star.app.ui.chart.MarketChartActivity;
import com.star.kchart.view.KMarketChartView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description：K线Sdk
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2019-3-5 15:44
 */

public class MainTestActivity extends AppCompatActivity {
    @BindView(R2.id.btnMarket)
    Button btnMarket;
    @BindView(R2.id.kchart_view)
    KMarketChartView kchartView;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext=this;
        btnMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MarketChartActivity.launch(MainTestActivity.this, new FutureItem("CUMAIN", "沪铜主力", "contract", "contract"));
            }
        });


    }
}
