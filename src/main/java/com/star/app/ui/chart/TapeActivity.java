package com.star.app.ui.chart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjmetal.star.kit.KnifeKit;
import com.gjmetal.star.net.ApiSubscriber;
import com.gjmetal.star.net.NetError;
import com.gjmetal.star.net.XApi;
import com.gjmetal.star.router.Router;

import java.util.ArrayList;
import java.util.List;

import com.star.app.adapter.TapeGridViewAdapter;
import com.star.app.api.Api;
import com.star.app.api.Constant;
import com.star.app.base.BaseActivity;
import com.star.app.base.BaseModel;
import com.star.app.model.FutureItem;
import com.star.app.model.Tape;
import com.star.app.ui.R;
import com.star.app.ui.R2;
import com.star.app.util.GjUtil;
import com.star.app.util.ValueUtil;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description：盘口
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-12-14 14:14
 */

public class TapeActivity extends BaseActivity {
    @BindView(R2.id.gvTape)
    GridView gvTape;
    //    @BindView(R.id.vEmpty)
//    EmptyView vEmpty;
    @BindView(R2.id.vTop)
    View vTop;
    @BindView(R2.id.vBottom)
    View vBottom;
    @BindView(R2.id.ivLeft)
    ImageView ivLeft;
    @BindView(R2.id.tvTitle)
    TextView tvTitle;
    @BindView(R2.id.titleBar)
    View titleBar;


    private FutureItem futureItem;
    private CountDownTimer countDownTimer;
    private TapeGridViewAdapter tapeGridViewAdapter;
    private List<Tape> tapeList;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mContext = this;
        fillData();
    }

    protected void initView() {
        setContentView(R.layout.activity_tape);
        KnifeKit.bind(this);
    }

    protected void fillData() {
        futureItem = (FutureItem) getIntent().getSerializableExtra(Constant.MODEL);
        if (ValueUtil.isEmpty(futureItem)) {
            return;
        }
        tvTitle.setText(ValueUtil.isStrNotEmpty(futureItem.getName()) ? futureItem.getName() + "盘口" : "");
        tapeList = new ArrayList<>();
        vTop.setVisibility(View.VISIBLE);
        vBottom.setVisibility(View.VISIBLE);
        tapeGridViewAdapter = new TapeGridViewAdapter(mContext, tapeList);
        gvTape.setAdapter(tapeGridViewAdapter);
        tapeGridViewAdapter.notifyDataSetInvalidated();
    }
    @OnClick({R2.id.ivLeft})
    public void onViewClicked(View view) {
        int i = view.getId();//加自选
        if (i == R.id.ivLeft) {
            finish();
        }
    }
    private void getPositionQuotation(final boolean firstLoad, String contract) {
//        if (firstLoad) {
//            DialogUtil.waitDialog(context);
//        }
        Api.getMarketService().getPositionQuotation(contract)
                .compose(XApi.<BaseModel<List<Tape>>>getApiTransformer())
                .compose(XApi.<BaseModel<List<Tape>>>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel<List<Tape>>>() {
                    @Override
                    public void onNext(BaseModel<List<Tape>> listBaseModel) {
                        startTimer();
//                        if (vEmpty != null) {
//                            vEmpty.setVisibility(View.GONE);
//                        }
                        if (gvTape != null) {
                            gvTape.setVisibility(View.VISIBLE);
                        }
                        if (vBottom != null) {
                            vBottom.setVisibility(View.VISIBLE);
                        }
                        if (vTop != null) {
                            vTop.setVisibility(View.VISIBLE);
                        }
//                        if (firstLoad) {
//                            DialogUtil.dismissDialog();
//                        }
                        if (ValueUtil.isEmpty(listBaseModel.getData())) {
                            vBottom.setVisibility(View.GONE);
                            vTop.setVisibility(View.GONE);
                            gvTape.setVisibility(View.GONE);
//                            vEmpty.setVisibility(View.VISIBLE);
//                            vEmpty.setNoData(Constant.BgColor.BLUE);
                            return;
                        }
                        updateUI(listBaseModel.getData());
                    }

                    @Override
                    protected void onFail(NetError error) {
//                        TimerManager.getInstance().closeTimer();
//                        if (gvTape == null) {
//                            return;
//                        }
//                        GjUtil.showEmptyHint(context, Constant.BgColor.BLUE, error, vEmpty, new BaseCallBack() {
//                            @Override
//                            public void back(Object obj) {
//                                getPositionQuotation(true, futureItem.getContract());
//                            }
//                        }, gvTape, vBottom, vTop);
//                        if (firstLoad) {
//                            DialogUtil.dismissDialog();
//                        }
                    }
                });

    }

    private void updateUI(List<Tape> datalist) {
        if (ValueUtil.isListNotEmpty(tapeList)) {
            tapeList.clear();
        }
        if (ValueUtil.isListNotEmpty(datalist)) {
            tapeList.addAll(datalist);
        }
        for (Tape bean : datalist) {
            if (ValueUtil.isStrNotEmpty(bean.getKey()) && bean.getKey().equals("涨跌")) {
                String value = null;
                if (ValueUtil.isNotEmpty(bean.getValue())) {
                    value = bean.getValue().substring(0, bean.getValue().indexOf("/"));
                }
                tapeGridViewAdapter.upOrDown(GjUtil.lastUpOrDown(mContext, value));
                break;
            }
        }
        tapeGridViewAdapter.notifyDataSetChanged();
    }


    //设置定时器
    private void startTimer() {
        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                getPositionQuotation(false, futureItem.getContract());
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        getPositionQuotation(true, futureItem.getContract());
    }

    public static void launch(Activity context, FutureItem futureItem) {
//        GjUtil.closeMarketTimer();
//        GjUtil.closeHelperTimer();
//        GjUtil.closeLEMTimer();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.MODEL, futureItem);
        Router.newIntent(context)
                .to(TapeActivity.class)
                .data(bundle)
                .launch();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
