package com.star.app.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.star.app.model.kminute.NewLast;
import com.star.app.ui.R;
import com.star.app.ui.R2;
import com.star.app.util.GjUtil;
import com.star.app.util.ValueUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description：k线横屏信息显示
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-10-12 15:32
 */
public class MarketLeftHorizontalView extends LinearLayout {
    @BindView(R2.id.txtSell)
    TextView txtSell;
    @BindView(R2.id.tvSellValue)
    TextView tvSellValue;//卖
    @BindView(R2.id.tvSellCount)
    TextView tvSellCount;
    @BindView(R2.id.rlSell)
    RelativeLayout rlSell;
    @BindView(R2.id.txtBuy)
    TextView txtBuy;
    @BindView(R2.id.tvBuyValue)
    TextView tvBuyValue;//买
    @BindView(R2.id.tvBuyCount)
    TextView tvBuyCount;
    @BindView(R2.id.rlBuy)
    RelativeLayout rlBuy;
    @BindView(R2.id.txtInventory)
    TextView txtInventory;
    @BindView(R2.id.tvInventoryValue)
    TextView tvInventoryValue;//持仓量
    @BindView(R2.id.rlInventory)
    RelativeLayout rlInventory;
    @BindView(R2.id.txtVolume)
    TextView txtVolume;
    @BindView(R2.id.tvVolumeValue)
    TextView tvVolumeValue;//成交量
    @BindView(R2.id.rlVolume)
    RelativeLayout rlVolume;
    @BindView(R2.id.tvChangeInventoryValue)
    TextView tvChangeInventoryValue;
    @BindView(R2.id.tvChangeVolumeValue)
    TextView tvChangeVolumeValue;
    @BindView(R2.id.llMarketBottom)
    LinearLayout llMarketBottom;
    @BindView(R2.id.tvLastValue)
    TextView tvLastValue;
    @BindView(R2.id.tvPrice)
    TextView tvPrice;
    @BindView(R2.id.tvPercentValue)
    TextView tvPercentValue;
    @BindView(R2.id.tvLMEValue)
    TextView tvLMEValue;
    @BindView(R2.id.tvProfitValue)
    TextView tvProfitValue;
    private MarketType marketType =MarketType.CONTACT;
    private Context mContext;

    public MarketLeftHorizontalView(Context context) {
        super(context);
    }

    public MarketLeftHorizontalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.view_kchart_topleft_horizontal, this);
        ButterKnife.bind(this);
        mContext = context;
        tvLMEValue.setVisibility(View.VISIBLE);
        tvProfitValue.setVisibility(View.VISIBLE);
    }

    public void fillData(NewLast datalist, MarketType marketType) {
        this.marketType = marketType;
        switch (marketType) {
            case IRATE:
                tvLMEValue.setVisibility(View.GONE);
                tvProfitValue.setVisibility(View.GONE);
                llMarketBottom.setVisibility(View.GONE);
                break;
            case CONTACT:
                tvLMEValue.setVisibility(View.VISIBLE);
                tvProfitValue.setVisibility(View.VISIBLE);
                llMarketBottom.setVisibility(View.VISIBLE);
                break;
        }
        if (ValueUtil.isEmpty(datalist)) {
            return;
        }
        tvLastValue.setText(ValueUtil.isStrEmpty(datalist.getLast()) ? "- -" : datalist.getLast());
        tvPrice.setText(ValueUtil.isStrEmpty(datalist.getUpdown()) ? "- -" : datalist.getUpdown());
        tvPercentValue.setText(ValueUtil.isStrEmpty(datalist.getPercent()) ? "- -" : datalist.getPercent());
        GjUtil.lastUpOrDown(mContext, datalist.getUpdown(), tvLastValue, tvPrice, tvPercentValue);//根据涨幅变色

        if (ValueUtil.isListEmpty(datalist.getMeasures())) {
            tvLMEValue.setVisibility(GONE);
            tvProfitValue.setVisibility(GONE);
        } else if (datalist.getMeasures().size() > 1) {
            tvLMEValue.setText(datalist.getMeasures().get(0).getKey() + ":" + datalist.getMeasures().get(0).getValue());
            tvProfitValue.setText(datalist.getMeasures().get(1).getKey() + ":" + datalist.getMeasures().get(1).getValue());
        }
        tvSellValue.setText(ValueUtil.isStrEmpty(datalist.getAsk1p()) ? "- -" : datalist.getAsk1p());
        tvSellCount.setText(ValueUtil.isStrEmpty(datalist.getAsk1v()) ? "- -" : datalist.getAsk1v());

        tvBuyValue.setText(ValueUtil.isStrEmpty(datalist.getBid1p()) ? "- -" : datalist.getBid1p());
        tvBuyCount.setText(ValueUtil.isStrEmpty(datalist.getBid1v()) ? "- -" : datalist.getBid1v());

        tvInventoryValue.setText(ValueUtil.isStrEmpty(datalist.getInterest()) ? "" : datalist.getInterest());
        tvChangeInventoryValue.setText(ValueUtil.isStrEmpty(datalist.getChgInterest()) ? "" : datalist.getChgInterest());
        tvVolumeValue.setText(ValueUtil.isStrEmpty(datalist.getVolume()) ? "" : datalist.getVolume());
        tvChangeVolumeValue.setText(ValueUtil.isStrEmpty(datalist.getChgVolume()) ? "" : datalist.getChgVolume());
    }

    public enum MarketType {
        CONTACT,//普通合约
        IRATE//利率
    }
}
