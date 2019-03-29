package com.star.app.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gjmetal.star.kit.KnifeKit;

import java.util.ArrayList;
import java.util.List;

import com.star.app.adapter.InvOrVolumeaAdapter;
import com.star.app.model.pop.InvOrVolumea;
import com.star.app.ui.R;

public class InvOrVolumeaPopwindow extends PopupWindow {
    TextView tvInventoryNum;
    TextView tvInventory;
    TextView tvVolumeNum;
    TextView tvVolume;
    LinearLayout llMarket;
    RecyclerView rvContent;
    private Context mContext;

    private InvOrVolumeaAdapter invOrVolumeaAdapter = null;
    private List<InvOrVolumea> listData = new ArrayList<>();//数据
    private boolean isMeasure = false;//false 行情  true 测算
//    private int screenWidth = LinearLayout.LayoutParams.MATCH_PARENT;//得到屏幕宽度

    public InvOrVolumeaPopwindow(Context context, boolean isMeasure) {
        super(context);
        this.mContext = context;
        this.isMeasure = isMeasure;
        initView();
    }

    public InvOrVolumeaPopwindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public InvOrVolumeaPopwindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }


    private void initView() {
        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popuwindow_invorvolumea, null, false);
        setContentView(contentView);
        KnifeKit.bind(contentView);
        rvContent = contentView.findViewById(R.id.rvContent);
        tvInventory = contentView.findViewById(R.id.tvInventory);
        tvInventoryNum = contentView.findViewById(R.id.tvInventoryNum);
        tvVolume = contentView.findViewById(R.id.tvVolume);
        tvVolumeNum = contentView.findViewById(R.id.tvVolumeNum);
        llMarket = contentView.findViewById(R.id.llMarket);
        if (isMeasure) {
            llMarket.setVisibility(View.GONE);
            rvContent.setVisibility(View.VISIBLE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvContent.setLayoutManager(gridLayoutManager);
            if (invOrVolumeaAdapter == null) {
                invOrVolumeaAdapter = new InvOrVolumeaAdapter(mContext);
                rvContent.setAdapter(invOrVolumeaAdapter);
            } else {
                invOrVolumeaAdapter.setData(listData);
            }

        } else {
            llMarket.setVisibility(View.VISIBLE);
            rvContent.setVisibility(View.GONE);
        }

//        setWidth(screenWidth);
//        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true); //能否获得焦点
        // 设置PopupWindow的背景
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        setTouchable(true);
//        setAnimationStyle(R.style.AnimBottom);
//        showAsDropDown(mView);
    }

    public void setShow(View mView, int screenWidth) {
        setWidth(screenWidth);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        showAsDropDown(mView);
    }

    public void setTextValue(String volume, String volumeNum, String inventory, String inventoryNum) {
        tvInventoryNum.setText(volume == null ? "" : volume);
        tvInventory.setText(volumeNum == null ? "" : volumeNum);
        tvVolumeNum.setText(inventory == null ? "" : inventory);
        tvVolume.setText(inventoryNum == null ? "" : inventoryNum);
    }

    public void setMeasureDatalist(List<InvOrVolumea> listData) {
        invOrVolumeaAdapter.setData(listData);
    }


}
