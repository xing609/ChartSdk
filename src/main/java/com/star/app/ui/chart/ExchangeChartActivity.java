package com.star.app.ui.chart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gjmetal.star.event.BusProvider;
import com.gjmetal.star.kit.KnifeKit;
import com.gjmetal.star.net.ApiSubscriber;
import com.gjmetal.star.net.NetError;
import com.gjmetal.star.net.XApi;
import com.gjmetal.star.router.Router;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.star.app.api.Api;
import com.star.app.api.Constant;
import com.star.app.base.BaseActivity;
import com.star.app.base.BaseCallBack;
import com.star.app.base.BaseModel;
import com.star.app.event.BaseEvent;
import com.star.app.manager.PictureMergeManager;
import com.star.app.model.FutureItem;
import com.star.app.model.RateModel;
import com.star.app.model.TrendChartModel;
import com.star.app.model.kminute.NewLast;
import com.star.app.model.share.ShareContent;
import com.star.app.ui.R;
import com.star.app.ui.R2;
import com.star.app.util.GjUtil;
import com.star.app.util.ValueUtil;
import com.star.app.view.MarketLeftHorizontalView;
import com.star.app.widget.dialog.ExplainDialog;
import com.star.app.widget.dialog.ShareDialog;
import com.star.kchart.rate.RateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Description：利率
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-10-25 14:03
 */

public class ExchangeChartActivity extends BaseActivity {
    @BindView(R2.id.tvExChangeLast)
    TextView tvExChangeLast;
    @BindView(R2.id.tvExchangeUpdown)
    TextView tvExchangeUpdown;
    @BindView(R2.id.viewline)
    View viewline;
    @BindView(R2.id.rlLayout)
    RelativeLayout rlLayout;
    @BindView(R2.id.llPlus)
    LinearLayout llPlus;
    @BindView(R2.id.llSpecs)
    LinearLayout llSpecs;
    @BindView(R2.id.llWarn)
    LinearLayout llWarn;
    @BindView(R2.id.tvShare)
    TextView tvShare;
    @BindView(R2.id.llShare)
    LinearLayout llShare;
    @BindView(R2.id.llBottomTab)
    LinearLayout llBottomTab;
    @BindView(R2.id.rateView)
    RateView rateView;
    @BindView(R2.id.ivAddPlus)
    ImageView ivAddPlus;
    @BindView(R2.id.tvAddPlus)
    TextView tvAddPlus;
    @BindView(R2.id.viewMinuteLeftMessage)
    MarketLeftHorizontalView viewMinuteLeftMessage;
    @BindView(R2.id.lineMinuteView)
    View lineMinuteView;
    @BindView(R2.id.vExchangeTop)
    View vExchangeTop;
    @BindView(R2.id.exchangeEmpty)
    View exchangeEmpty;
    @BindView(R2.id.tvContactName)
    TextView tvContactName;
    @BindView(R2.id.lineName)
    View lineName;
    @BindView(R2.id.llTape)
    LinearLayout llTape;
    @BindView(R2.id.ivLeft)
    ImageView ivLeft;
    @BindView(R2.id.tvTitle)
    TextView tvTitle;
    @BindView(R2.id.titleBar)
    View titleBar;

    private ExplainDialog explainDialog = null;
    private ShareDialog shareDialog = null;//分享的dialog
    private boolean isShow = false;//是不是弹出图标
    //生成图片
    private final int IMAGESSUCCESS = 1000;
    private MyRunnable myRunnable = null;
    private Thread mythread = null;
    private FutureItem futureItem;
    private ShareContent shareContent = new ShareContent();//分享内容
    private List<TrendChartModel> mTrendChartModels;

    private Context context;
    private TrendChartModel mTrendChartModel;
    private List<RateModel> mRateModels;
    private FutureItem changeAdd;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IMAGESSUCCESS:
                    try {
                        Bitmap bitmap = PictureMergeManager.getPictureMergeManager().getScreenBitmap(ExchangeChartActivity.this, rlLayout);
                        setShareDialog(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_chart);
        ButterKnife.bind(this);
        KnifeKit.bind(this);
        context = this;
        initView();
        fillData();
    }

    public static void launch(Activity context, FutureItem futureItem) {
        GjUtil.closeMarketTimer();
        GjUtil.closeHelperTimer();
        GjUtil.closeLEMTimer();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.MODEL, futureItem);
        Router.newIntent(context)
                .to(ExchangeChartActivity.class)
                .data(bundle)
                .launch();

    }

    protected void initView() {
        futureItem = (FutureItem) getIntent().getSerializableExtra(Constant.MODEL);
        //默认显示自选
        llPlus.setVisibility(View.VISIBLE);
        tvAddPlus.setText(getString(R.string.txt_chart_plus));
        ivAddPlus.setBackgroundResource(R.mipmap.iv_chart_add_plus);
        if (ValueUtil.isEmpty(futureItem)) {
            return;
        }
        tvTitle.setText(ValueUtil.isStrNotEmpty(futureItem.getName()) ? futureItem.getName() : "");
        getScreenState();
        myRunnable = new MyRunnable();
        tvContactName.setText(ValueUtil.isStrNotEmpty(futureItem.getName()) ? futureItem.getName() : "");
        /**
         * 检查是否显示盘口
         */
        getPositionQuotation(futureItem.getContract(), futureItem.getBizType(), llTape);
    }

    protected void fillData() {
        BusProvider.getBus().register(this);
        BusProvider.getBus().toFlowable(BaseEvent.class)
                .subscribe(new Consumer<BaseEvent>() {
                    @Override
                    public void accept(final BaseEvent baseEvent) throws Exception {
                        if (ValueUtil.isNotEmpty(baseEvent) && baseEvent.isLogin()) {
                            //是否添加到自选
                            getFileFavoritesCodecheck(futureItem.getBizType(), futureItem.getContract(), tvAddPlus, ivAddPlus, new BaseCallBack() {
                                @Override
                                public void back(Object obj) {
                                    changeAdd = (FutureItem) obj;
                                }
                            });
                        }
                    }

                });
        mRateModels = new ArrayList<>();
        rateView.setScrollEnable(true); //是否滑动
        rateView.setGridRows(8);//横线
        rateView.setGridColumns(6);//竖线
        initTrendChartData();
        //是否添加到自选
        getFileFavoritesCodecheck(futureItem.getBizType(), futureItem.getContract(), tvAddPlus, ivAddPlus, new BaseCallBack() {
            @Override
            public void back(Object obj) {
                changeAdd = (FutureItem) obj;
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (explainDialog != null && explainDialog.isShowing()) {
            explainDialog.dismiss();
        }
        if (shareDialog != null && shareDialog.isShowing()) {
            shareDialog.dismiss();
        }
        getScreenState();
        GjUtil.getScreenConfiguration(context, titleBar, new GjUtil.ScreenStateCallBack() {
            @Override
            public void onPortrait() {

            }

            @Override
            public void onLandscape() {

            }
        });
    }

    private void getScreenState() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
            llBottomTab.setVisibility(View.GONE);
            viewMinuteLeftMessage.setVisibility(View.VISIBLE);
            lineName.setVisibility(View.VISIBLE);
            tvContactName.setVisibility(View.VISIBLE);
            lineMinuteView.setVisibility(View.VISIBLE);
            vExchangeTop.setVisibility(View.GONE);
            titleBar.setVisibility(View.GONE);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
            titleBar.setVisibility(View.VISIBLE);
            llBottomTab.setVisibility(View.VISIBLE);
            viewMinuteLeftMessage.setVisibility(View.GONE);
            lineName.setVisibility(View.GONE);
            tvContactName.setVisibility(View.GONE);
            lineMinuteView.setVisibility(View.GONE);
            vExchangeTop.setVisibility(View.VISIBLE);
        }
    }


    private void initTrendChartData() {
//        DialogUtil.waitDialog(context);
        Api.getMarketService().getMarketIndexChart(futureItem.getContract())
                .compose(XApi.<BaseModel<List<TrendChartModel>>>getApiTransformer())
                .compose(XApi.<BaseModel<List<TrendChartModel>>>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel<List<TrendChartModel>>>() {
                    @Override
                    public void onNext(BaseModel<List<TrendChartModel>> listBaseModel) {
//                        DialogUtil.dismissDialog();
                        if (ValueUtil.isListEmpty(listBaseModel.getData())) {
                            rateView.setVisibility(View.GONE);
//                            exchangeEmpty.setVisibility(View.VISIBLE);
//                            exchangeEmpty.setNoData(Constant.BgColor.BLUE);
                            return;
                        }
                        mTrendChartModels = listBaseModel.getData();
                        tvExChangeLast.setText(mTrendChartModels.get(mTrendChartModels.size() - 1).getValue());
                        String change = mTrendChartModels.get(mTrendChartModels.size() - 1).getChange();
                        if (change != null) {
                            tvExchangeUpdown.setText(change + "(" + mTrendChartModels.get(mTrendChartModels.size() - 1).getPercent() + ")");
                            GjUtil.lastUpOrDown(context, change, tvExChangeLast, tvExchangeUpdown);
                        }
                        for (int i = 0; i < mTrendChartModels.size(); i++) {
                            RateModel rateModel = new RateModel();
                            rateModel.date = new Date(mTrendChartModels.get(i).getDate());
                            rateModel.value = mTrendChartModels.get(i).getValue();
                            rateModel.change = mTrendChartModels.get(i).getChange();
                            rateModel.percent = mTrendChartModels.get(i).getPercent();
                            mRateModels.add(rateModel);

                        }
                        rateView.initData(mRateModels);
                        if (ValueUtil.isListEmpty(mTrendChartModels)) {
                            return;
                        }
//                        RateModel rateModel = mRateModels.get(mRateModels.size() - 1);
                        NewLast newLast = new NewLast();
                        newLast.setLast(mTrendChartModels.get(mTrendChartModels.size() - 1).getValue());
                        newLast.setUpdown(mTrendChartModels.get(mTrendChartModels.size() - 1).getChange());
                        newLast.setPercent(mTrendChartModels.get(mTrendChartModels.size() - 1).getPercent());
                        viewMinuteLeftMessage.fillData(newLast, MarketLeftHorizontalView.MarketType.IRATE);
                    }

                    @Override
                    protected void onFail(NetError error) {
//                        DialogUtil.dismissDialog();
                        GjUtil.showEmptyHint(context, Constant.BgColor.BLUE, error, exchangeEmpty, new BaseCallBack() {
                            @Override
                            public void back(Object obj) {
                                initTrendChartData();
                            }
                        }, rateView);
                    }
                });

    }


    @OnClick({R2.id.llPlus, R2.id.llTape, R2.id.llSpecs, R2.id.llWarn, R2.id.llShare})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R2.id.llPlus: //加自选
                if (tvAddPlus.getText().equals(context.getString(R.string.txt_cancel_my_change))) {
                    List<Long> longList = new ArrayList<>();
                    longList.add(changeAdd.getId());
                    delFavoritesCode(longList, tvAddPlus, ivAddPlus);
                } else {
                    addFileFavoritesCode(futureItem.getBizType(), futureItem.getContract(), tvAddPlus, ivAddPlus, new BaseCallBack() {
                        @Override
                        public void back(Object obj) {
                            changeAdd = (FutureItem) obj;
                        }
                    });
                }
                break;
            case R2.id.llTape://盘口
                TapeActivity.launch((Activity) context, futureItem);
                break;
            case R2.id.llSpecs: //说明
                setShowDialog();
                break;
            case R2.id.llWarn: //添加预警
                if (ValueUtil.isStrEmpty(futureItem.getName()) || ValueUtil.isStrEmpty(futureItem.getContract())) {
                    return;
                }
//                if (User.getInstance().isLoginIng()) {
//                    WarningAddActivity.launch(this, futureItem.getName(), "ExchangeChartActivity",
//                            futureItem.getContract(), futureItem.getIndicatorType());
//                } else {
//                    LoginActivity.launch(this);
//                }
                break;
            case R2.id.llShare: //分享
                mythread = new Thread(myRunnable);
                mythread.start();
                break;
        }
    }


    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            handler.sendEmptyMessage(IMAGESSUCCESS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && mythread != null) {
            handler.removeMessages(IMAGESSUCCESS);
            handler.removeCallbacksAndMessages(null);
            if (mythread != null) {
                handler.removeCallbacks(mythread);
                mythread = null;
            }
            handler = null;
        }
//        if (rateView != null) {
//            rateView.releaseMemory();
//        }

//        if (User.getInstance().isLoginIng()) {
//            GjUtil.startMarketTimer();
//        }
        BusProvider.getBus().unregister(this);
    }


    //说明
    private void setShowDialog() {
        explainDialog = new ExplainDialog(this, R.style.Theme_dialog);
        explainDialog.setCancelable(true);
        explainDialog.setContract(futureItem.getContract(), "ExchangeChartActivity");
        explainDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        explainDialog.getWindow().setGravity(Gravity.CENTER);
        explainDialog.show();
    }


    //分享
    private void setShareDialog(Bitmap bitmap) {
        shareContent.setBitmap(bitmap);
        shareContent.setTitle(futureItem != null ? futureItem.getName() : "");
        shareContent.setUrl(Constant.APP_DIALOG_SHARE_UEL);
        shareDialog = new ShareDialog(0, this, R.style.Theme_dialog, shareContent);
        shareDialog.setCancelable(false);
        shareDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        shareDialog.getWindow().setGravity(Gravity.CENTER);
        shareDialog.show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {
//                if (User.getInstance().isLoginIng()) {
//                    GjUtil.startMarketTimer();
//                }
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}





















