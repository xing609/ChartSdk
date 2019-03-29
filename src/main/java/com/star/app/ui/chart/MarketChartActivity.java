package com.star.app.ui.chart;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gjmetal.star.event.BusProvider;
import com.gjmetal.star.kit.ContextUtil;
import com.gjmetal.star.kit.KnifeKit;
import com.gjmetal.star.net.ApiSubscriber;
import com.gjmetal.star.net.NetError;
import com.gjmetal.star.net.XApi;
import com.gjmetal.star.router.Router;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.star.app.adapter.KChartAdapter;
import com.star.app.adapter.RvChoosemhAdapter;
import com.star.app.api.Api;
import com.star.app.api.Constant;
import com.star.app.base.BaseActivity;
import com.star.app.base.BaseCallBack;
import com.star.app.base.BaseModel;
import com.star.app.event.BaseEvent;
import com.star.app.manager.GjOptions;
import com.star.app.manager.PictureMergeManager;
import com.star.app.model.FutureItem;
import com.star.app.model.kminute.KLine;
import com.star.app.model.kminute.Minute;
import com.star.app.model.kminute.MinuteModel;
import com.star.app.model.kminute.MinuteTime;
import com.star.app.model.kminute.NewLast;
import com.star.app.model.pop.KMenuTime;
import com.star.app.model.pop.RvChoosemh;
import com.star.app.model.share.ShareContent;
import com.star.app.ui.R;
import com.star.app.ui.R2;
import com.star.app.util.GjUtil;
import com.star.app.util.ValueUtil;
import com.star.app.util.data.DataRequest;
import com.star.app.util.data.MinuteDataHelper;
import com.star.app.view.MarketLeftHorizontalView;
import com.star.app.widget.InvOrVolumeaPopwindow;
import com.star.app.widget.RvChoosemhPopuWindow;
import com.star.app.widget.autoText.AutofitTextView;
import com.star.app.widget.dialog.ExplainDialog;
import com.star.app.widget.dialog.ShareDialog;
import com.star.kchart.formatter.ValueFormatter;
import com.star.kchart.minute.BaseMinuteView;
import com.star.kchart.minute.MinuteTimeView;
import com.star.kchart.utils.DensityUtil;
import com.star.kchart.utils.StrUtil;
import com.star.kchart.view.KMarketChartView;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Description：K线、分时图
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-10-25 14:03
 */

public class MarketChartActivity extends BaseActivity implements KMarketChartView.KChartRefreshListener {
    @BindView(R2.id.tvName)
    TextView tvName;
    @BindView(R2.id.rvChoosemh)
    RecyclerView rvChoosemh;
    @BindView(R2.id.rlLayout)
    RelativeLayout rlLayout;
    @BindView(R2.id.tvValue)
    AutofitTextView tvValue;
    @BindView(R2.id.tvValueRose)
    AutofitTextView tvValueRose;
    @BindView(R2.id.tvLme)
    TextView tvLme;
    @BindView(R2.id.tvImportsProfit)
    TextView tvImportsProfit;
    @BindView(R2.id.ivDownOrUp)
    ImageView ivDownOrUp;
    @BindView(R2.id.tvSell)
    TextView tvSell;
    @BindView(R2.id.tvSellNum)
    TextView tvSellNum;
    @BindView(R2.id.tvBuy)
    TextView tvBuy;
    @BindView(R2.id.tvBuyNum)
    TextView tvBuyNum;
    @BindView(R2.id.llRoseSellOrBuy)
    LinearLayout llRoseSellOrBuy;
    @BindView(R2.id.llPlus)
    LinearLayout llPlus;
    @BindView(R2.id.llSpecs)
    LinearLayout llSpecs;
    @BindView(R2.id.llWarn)
    LinearLayout llWarn;
    @BindView(R2.id.llShare)
    LinearLayout llShare;
    @BindView(R2.id.viewKLeftMessage)
    MarketLeftHorizontalView viewKLeftMessage;
    @BindView(R2.id.kchart_view)
    KMarketChartView kchartView;
    @BindView(R2.id.viewMinuteLeftMessage)
    MarketLeftHorizontalView viewMinuteLeftMessage;
    @BindView(R2.id.minuteChartView)
    MinuteTimeView minuteChartView;
    @BindView(R2.id.llMinuteView)
    View llMinuteView;//分时布局
    @BindView(R2.id.llkChartView)
    View llKView;//k 线布局
    @BindView(R2.id.lineKView)
    View lineKView;
    @BindView(R2.id.lineMinuteView)
    View lineMinuteView;
    @BindView(R2.id.tvBuyTitle)
    TextView tvBuyTitle;
    @BindView(R2.id.llSellBuy)
    LinearLayout llSellBuy;
    @BindView(R2.id.llDownOrUp)
    LinearLayout llDownOrUp;
    @BindView(R2.id.llBottomTab)
    LinearLayout llBottomTab;
    @BindView(R2.id.rlDateTime)
    RelativeLayout rlDateTime;
    @BindView(R2.id.view)
    View view;
    @BindView(R2.id.rlLmeOrIntoLP)
    RelativeLayout rlLmeOrIntoLP;
    @BindView(R2.id.tvSellTitle)
    TextView tvSellTitle;
    @BindView(R2.id.viewline)
    View viewline;
    @BindView(R2.id.ivAddPlus)
    ImageView ivAddPlus;
    @BindView(R2.id.tvAddPlus)
    TextView tvAddPlus;
    @BindView(R2.id.tvShare)
    TextView tvShare;
    @BindView(R2.id.kChartEmpty)
    View kChartEmpty;//k线空布局
    @BindView(R2.id.minuteEmpty)
    View minuteEmpty;
    @BindView(R2.id.llTape)
    LinearLayout llTape;
    @BindView(R2.id.tvTitle)
    TextView tvTitle;
    @BindView(R2.id.titleBar)
    View titleBar;
    @BindView(R2.id.ivLeft)
    ImageView ivLeft;
    @BindView(R2.id.rlParent)
    RelativeLayout rlParent;

    private RvChoosemhAdapter rvChoosemhAdapter;
    private List<RvChoosemh> rvList = new ArrayList<>();
    private List<KMenuTime> kMenuTimeList = new ArrayList<>();
    private RvChoosemhPopuWindow rvChoosemhPopuWindow = null;
    private InvOrVolumeaPopwindow invOrVolumeaPopwindow = null;
    private boolean isOrientation;//代表方向
    private int width;
    private ExplainDialog explainDialog = null;
    private ShareDialog shareDialog = null;//分享的dialog
    private long mPresentStartTimer = 0;

    //分时图
    private List<MinuteTime> mMinuteTimeModels;
    private List<MinuteTime> mMinuteTimeModels1;
    private List<MinuteTime> mMinuteTimeModels2;
    private List<MinuteTime> mMinuteTimeModels3;
    private List<MinuteTime> mMinuteTimeModels4;
    private List<MinuteTime> mMinuteTimeModels5;
    private List<Minute> mMinuteDataCounts;
    private List<Minute> mMinuteDataMadels;
    private List<Minute> mMinuteDataMadels1;
    private List<Minute> mMinuteDataMadels2;
    private List<Minute> mMinuteDataMadels3;
    private List<Minute> mMinuteDataMadels4;
    private List<Minute> mMinuteDataMadels5;
    private int mScaleValue = 1;
    private Date[] minTime = new Date[5];
    private Date[] maxTime = new Date[5];
    private String[] preSettle = new String[5];
    //K线图
    private KChartAdapter mKAdapter;
    private String dataType;
    private String unitType;
    private boolean isShow = false;//是不是弹出图标
    private CountDownTimer countDownTimer = null;

    private int mMinuteTotalTime = 1 * 60 * 1000;
    private CountDownTimer mCountMinuteDownTimer = null;
    private boolean mIsShowMinute = true; //判断是否显示分时图
    private boolean isFirstCacheMinuteDatas = false; //判断是否缓存一天
    private boolean mIsMinuteRefreshFailing = true; //用于判断定时器刷新失败
    private boolean mIsClickMinute = false; //用于判断点击刷新还是定时器刷新
    private boolean mIsTimerRefreshMinuteDatas = false; //用于判断点击刷新还是定时器刷新

    //生成图片
    private final int IMAGESSUCCESS = 1000;
    private MyRunnable myRunnable = null;
    private Thread mythread = null;
    private FutureItem changeAdd;
    private FutureItem futureItem;
    private ShareContent shareContent = new ShareContent();//分享内容
    private Bitmap bitmap = null;//截取的图片
    private Context context;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IMAGESSUCCESS:
                    try {
                        bitmap = PictureMergeManager.getPictureMergeManager().getScreenBitmap(MarketChartActivity.this, rlLayout);
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
        context = this;
        initView();
        fillData();
    }

    protected void initView() {
        setContentView(R.layout.activity_market_chart);
        KnifeKit.bind(this);
        width = getWindowManager().getDefaultDisplay().getWidth();
        futureItem = (FutureItem) getIntent().getSerializableExtra(Constant.MODEL);
        //默认显示自选
        llPlus.setVisibility(View.VISIBLE);
        tvAddPlus.setText(getString(R.string.txt_chart_plus));
        ivAddPlus.setBackgroundResource(R.mipmap.iv_chart_add_plus);
        if (ValueUtil.isEmpty(futureItem)) {
            return;
        }
        if (ValueUtil.isStrNotEmpty(futureItem.getName())) {
            tvTitle.setText(futureItem.getName());
        } else {
            tvTitle.setText("");
        }
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isOrientation = true;
            titleBar.setVisibility(View.GONE);
            setRvChoosemhWidth(true);
        } else {
            titleBar.setVisibility(View.VISIBLE);
            isOrientation = false;
            setRvChoosemhWidth(false);
        }
        /**
         * 检查是否显示盘口
         */
        getPositionQuotation(futureItem.getContract(), futureItem.getBizType(), llTape);
        //持仓量与成交量的弹框
        invOrVolumeaPopwindow = new InvOrVolumeaPopwindow(this, false);
        invOrVolumeaPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivDownOrUp.setImageResource(R.mipmap.iv_chart_details_nor);
            }
        });
        mMinuteTimeModels = new ArrayList<>();
        mMinuteTimeModels1 = new ArrayList<>();
        mMinuteTimeModels2 = new ArrayList<>();
        mMinuteTimeModels3 = new ArrayList<>();
        mMinuteTimeModels4 = new ArrayList<>();
        mMinuteTimeModels5 = new ArrayList<>();

        mMinuteDataCounts = new ArrayList<>();
        mMinuteDataMadels = new ArrayList<>();
        mMinuteDataMadels1 = new ArrayList<>();
        mMinuteDataMadels2 = new ArrayList<>();
        mMinuteDataMadels3 = new ArrayList<>();
        mMinuteDataMadels4 = new ArrayList<>();
        mMinuteDataMadels5 = new ArrayList<>();

        showMinuteView(); //默认显示分时图
        setToCanvas(false);

        setRvChoosemh();
        setShowDialog();

        setTimerConstant();//设置定时器
        myRunnable = new MyRunnable();
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
        setTimeContentData();
    }

    private void setTimeContentData() {
        //是否添加到自选
        getFileFavoritesCodecheck(futureItem.getBizType(), futureItem.getContract(), tvAddPlus, ivAddPlus, new BaseCallBack() {
            @Override
            public void back(Object obj) {
                changeAdd = (FutureItem) obj;
            }
        });
        //获取k线时间菜单
        getMinuteKlineInterval(new BaseCallBack() {
            @Override
            public void back(Object obj) {
                kMenuTimeList.clear();
                kMenuTimeList = (List<KMenuTime>) obj;
                if (ValueUtil.isListEmpty(kMenuTimeList)) {
                    return;
                }
                rvList.clear();
                if (kMenuTimeList.size() > 6) {
                    for (int i = 0; i < 7; i++) {
                        RvChoosemh rvChoosemh = new RvChoosemh();
                        if (0 == i) {
                            rvChoosemh.setChoose(true);
                            rvChoosemh.setValue(kMenuTimeList.get(i).getName());
                            rvChoosemh.setMkCode(kMenuTimeList.get(i).getMkCode());
                            rvChoosemh.setUnit(kMenuTimeList.get(i).getUnit());
                        } else if (6 == i) {
                            rvChoosemh.setChoose(false);
                            rvChoosemh.setValue(getResources().getString(R.string.txt_chart_more));
                            rvChoosemh.setMkCode(null);
                        } else {
                            rvChoosemh.setChoose(false);
                            rvChoosemh.setValue(kMenuTimeList.get(i).getName());
                            rvChoosemh.setMkCode(kMenuTimeList.get(i).getMkCode());
                            rvChoosemh.setUnit(kMenuTimeList.get(i).getUnit());
                        }
                        rvList.add(rvChoosemh);
                    }
                }
                rvChoosemhAdapter.setData(rvList);
            }
        });
    }

    @OnClick({R2.id.ivLeft, R2.id.llDownOrUp, R2.id.llTape, R2.id.tvShare, R2.id.llPlus, R2.id.llSpecs, R2.id.llWarn, R2.id.llShare})
    public void onViewClicked(View view) {
        int i = view.getId();//加自选
        if (i == R.id.ivLeft) {
            finish();
        } else if (i == R.id.llDownOrUp) {
            ivDownOrUp.setImageResource(R.mipmap.iv_chart_details_res);
            invOrVolumeaPopwindow.setShow(llRoseSellOrBuy, width);
        } else if (i == R.id.llPlus) {
            if (ValueUtil.isStrNotEmpty(GjOptions.getInstance().getmToken())) {
                if (tvAddPlus.getText().equals(context.getString(R.string.txt_cancel_my_change))) {
                    List<Long> longList = new ArrayList<>();//取消自选时要取检查是否在自选接口的id,因为只有自选列表返回了，其它列表没有这个id
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
            } else {
                //去登录
                if (ValueUtil.isStrNotEmpty(GjOptions.getInstance().getmLoginOutActivity())) {
                    Intent intent = new Intent();
                    intent.setClassName(ContextUtil.getContext(), GjOptions.getInstance().getmLoginOutActivity());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ContextUtil.getContext().startActivity(intent);
                }
            }


        } else if (i == R.id.llTape) { //盘口
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            if (mCountMinuteDownTimer != null) {
                mCountMinuteDownTimer.cancel();
            }
            TapeActivity.launch(this, futureItem);
        } else if (i == R.id.llSpecs) {//说明
            explainDialog.setContract(futureItem.getContract(), "MarketChartActivity");
            explainDialog.show();

        } else if (i == R.id.llWarn) { //添加预警
            if (ValueUtil.isEmpty(futureItem) || ValueUtil.isStrEmpty(futureItem.getContract())) {
                return;
            }
//                if (User.getInstance().isLoginIng()) {
//                    WarningAddActivity.launch(this, futureItem.getName(), "MarketChartActivity",
//                            futureItem.getContract(), futureItem.getIndicatorType());
//                } else {
//                    LoginActivity.launch(this);
//                }

        } else if (i == R.id.llShare) { //分享
            mythread = new Thread(myRunnable);
            mythread.start();

        }
    }


    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            handler.sendEmptyMessage(IMAGESSUCCESS);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        getNewLast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (mCountMinuteDownTimer != null) {
            mCountMinuteDownTimer.cancel();
        }

        if (minuteChartView != null) {
            minuteChartView.releaseMemory();
        }

        if (kchartView != null) {
            kchartView.releaseMemory();
        }
        if (bitmap != null && bitmap.isRecycled()) {
            bitmap.recycle();
        }

        if (handler != null && mythread != null) {
            handler.removeMessages(IMAGESSUCCESS);
            handler.removeCallbacksAndMessages(null);
            if (mythread != null) {
                handler.removeCallbacks(mythread);
                mythread = null;
            }
            handler = null;
        }
        BusProvider.getBus().unregister(this);
    }


    /**
     * 获取展示的数据
     */
    public void getNewLast() {
        Api.getMarketService().getNewLast(futureItem.getContract())
                .compose(XApi.<BaseModel<NewLast>>getApiTransformer())
                .compose(XApi.<BaseModel<NewLast>>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel<NewLast>>() {
                    @Override
                    public void onNext(BaseModel<NewLast> listBaseModel) {
//                        GjUtil.checkActState(context);
                        try {
                            NewLast datalist = listBaseModel.getData();
                            if (listBaseModel.getData() == null && datalist == null) {
                                if (countDownTimer != null) {
                                    countDownTimer.cancel();
                                }
                                return;
                            }
                            tvValue.setText(ValueUtil.isStrNotEmpty(datalist.getLast()) ? datalist.getLast() : "- -");
                            if (datalist.getUpdown() != null) {
                                tvValueRose.setText(datalist.getUpdown() + "(" + datalist.getPercent() + ")");
                                GjUtil.lastUpOrDown(context, datalist.getUpdown(), tvValue, tvValueRose);//根据涨幅变色
                            }

                            if (datalist.getMeasures() == null || datalist.getMeasures().size() == 0) {
                                rlLmeOrIntoLP.setVisibility(View.GONE);
                            } else {
                                if (datalist.getMeasures().size() > 1) {
                                    tvLme.setText(datalist.getMeasures().get(0).getKey() + ":" + datalist.getMeasures().get(0).getValue());
                                    tvImportsProfit.setText(datalist.getMeasures().get(1).getKey() + ":" + datalist.getMeasures().get(1).getValue());
                                }
                            }
                            tvSell.setText(datalist.getAsk1p());
                            tvSellNum.setText(datalist.getAsk1v());
                            tvBuy.setText(datalist.getBid1p());
                            tvBuyNum.setText(datalist.getBid1v());

                            //持仓量 持仓量变化值 成交量 成交量变化值
                            invOrVolumeaPopwindow.setTextValue(datalist.getInterest(), datalist.getChgInterest(), datalist.getVolume(), datalist.getChgVolume());
                            viewKLeftMessage.fillData(datalist, MarketLeftHorizontalView.MarketType.CONTACT);//日k横向数据设置
                            viewMinuteLeftMessage.fillData(datalist, MarketLeftHorizontalView.MarketType.CONTACT);//分时横向数据设置
                            countDownTimer.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    protected void onFail(NetError error) {
                        if (countDownTimer != null) {
                            countDownTimer.cancel();
                        }
                    }
                });
    }

    /**
     * 显示分时图
     */
    private void showMinuteView() {
        initMinuteData(0);
        initMinuteView();
        llMinuteView.setVisibility(View.VISIBLE);
        llKView.setVisibility(View.GONE);
    }

    /**
     * 显示K线
     */
    private void showKview() {
        initKView();
        llMinuteView.setVisibility(View.GONE);
        llKView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        width = getWindowManager().getDefaultDisplay().getWidth();
        if (explainDialog != null && explainDialog.isShowing()) {
            explainDialog.dismiss();
        }
        if (shareDialog != null && shareDialog.isShowing()) {
            shareDialog.dismiss();
        }
        if (rvChoosemhPopuWindow != null && rvChoosemhPopuWindow.isShowing()) {
            rvChoosemhPopuWindow.dismiss();
        }
        if (invOrVolumeaPopwindow != null && invOrVolumeaPopwindow.isShowing()) {
            invOrVolumeaPopwindow.dismiss();
        }
        GjUtil.getScreenConfiguration(context, titleBar, new GjUtil.ScreenStateCallBack() {
            @Override
            public void onPortrait() {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
                isOrientation = false;
                setRvChoosemhWidth(false);
            }

            @Override
            public void onLandscape() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
                isOrientation = true;
                setRvChoosemhWidth(true);
            }
        });
    }

    //计算设置横竖屏recycleview的宽度
    private void setRvChoosemhWidth(boolean isOrientation) {
        ViewGroup.LayoutParams layoutParams = rvChoosemh.getLayoutParams();
        if (isOrientation) {
            layoutParams.width = (width / 2);
            tvName.setVisibility(View.VISIBLE);
            llSellBuy.setVisibility(View.GONE);
            llBottomTab.setVisibility(View.GONE);
            // tvName.setText(titleBar.getTitle().getText().toString());
            //分时图
            viewMinuteLeftMessage.setVisibility(View.VISIBLE);
            lineMinuteView.setVisibility(View.VISIBLE);
            //k 线图
            viewKLeftMessage.setVisibility(View.VISIBLE);
            lineKView.setVisibility(View.VISIBLE);
            setToCanvas(true);
        } else {
            layoutParams.width = width;
            llSellBuy.setVisibility(View.VISIBLE);
            tvName.setVisibility(View.GONE);
            llBottomTab.setVisibility(View.VISIBLE);

            //分时图
            viewMinuteLeftMessage.setVisibility(View.GONE);
            lineMinuteView.setVisibility(View.GONE);
            //K 线图
            viewKLeftMessage.setVisibility(View.GONE);
            lineKView.setVisibility(View.GONE);
            setToCanvas(false);

        }
        rvChoosemh.setLayoutParams(layoutParams);
    }


    //初始化配置
    private void setRvChoosemh() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvChoosemh.setLayoutManager(gridLayoutManager);
        rvChoosemhAdapter = new RvChoosemhAdapter(this, false);
        rvChoosemhAdapter.setMyItemLister(new RvChoosemhAdapter.MyItemLister() {
            @Override
            public void setItem(View v, List<RvChoosemh> data, final int position) {
                if (!rvList.get(position).getValue().equals(getResources().getString(R.string.txt_chart_more))) {
                    for (int i = 0; i < rvList.size(); i++) {
                        rvList.get(i).setChoose(false);
                    }
                }
                rvList.get(position).setChoose(true);
                if (position == 6) {
                    if (rvList.get(position).isMoreOpon()) {
                        for (int i = 0; i < kMenuTimeList.size(); i++) {
                            if (kMenuTimeList.get(i).isChooseSelect()) {
                                rvList.get(position).setValue(kMenuTimeList.get(i).getName());
                                rvList.get(position).setChoose(true);
                                rvList.get(position).setMkCode(kMenuTimeList.get(i).getMkCode());
                            }
                        }
                        rvList.get(position).setMoreOpon(false);
                    } else {
                        for (int i = 0; i < kMenuTimeList.size(); i++) {
                            if (rvList.get(position).getValue().equals(kMenuTimeList.get(i).getName())) {
                                kMenuTimeList.get(i).setChooseSelect(true);
                                rvList.get(position).setValue(getResources().getString(R.string.txt_chart_more));
                            } else {
                                kMenuTimeList.get(i).setChooseSelect(false);
                            }
                        }
                        rvList.get(position).setMoreOpon(true);
                        rvChoosemhPopuWindow = new RvChoosemhPopuWindow(MarketChartActivity.this);
                        if (isOrientation) {
                            rvChoosemhPopuWindow.setShow(rvChoosemh, width / 2, kMenuTimeList);
                        } else {
                            rvChoosemhPopuWindow.setShow(rvChoosemh, width, kMenuTimeList);
                        }
                        //针对最后一个设置展开关闭回复初始
                        rvChoosemhPopuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                for (int i = 0; i < kMenuTimeList.size(); i++) {
                                    if (kMenuTimeList.get(i).isChooseSelect()) {
                                        rvList.get(position).setValue(kMenuTimeList.get(i).getName());
                                        rvList.get(position).setChoose(true);
                                        rvList.get(position).setMkCode(kMenuTimeList.get(i).getMkCode());
                                    }
                                }
                                rvList.get(position).setMoreOpon(false);
                                rvChoosemhAdapter.setData(rvList);
                            }
                        });
                        //选中刷新最后一个数据的值
                        rvChoosemhPopuWindow.setMyClick(new RvChoosemhPopuWindow.OnClickListener() {
                            @Override
                            public void onClick(View view, RvChoosemh bean) {
                                for (int i = 0; i < rvList.size(); i++) {
                                    rvList.get(i).setChoose(false);
                                }
                                rvList.get(position).setMoreOpon(false);
                                rvList.get(position).setChoose(true);
                                rvList.get(position).setValue(bean.getValue());
                                rvList.get(position).setMkCode(bean.getMkCode());
                                rvList.get(position).setUnit(bean.getUnit());
                                rvChoosemhAdapter.setData(rvList);
                                if (ValueUtil.isStrEmpty(rvList.get(position).getMkCode())) {//更多
                                    return;
                                }
                                dataType = rvList.get(position).getMkCode();
                                unitType = rvList.get(position).getUnit();
                                showKview();
                            }
                        });
                    }
                } else {
                    rvList.get(6).setMoreOpon(false);
                    rvList.get(6).setValue(getResources().getString(R.string.txt_chart_more));
                    rvList.get(6).setMkCode(null);
                }
                rvChoosemhAdapter.setData(rvList);
                if (position == 6 || ValueUtil.isStrEmpty(rvList.get(position).getMkCode())) {//更多
                    return;
                }
                dataType = rvList.get(position).getMkCode();
                unitType = rvList.get(position).getUnit();
                if (position == 0) {//分时图
                    showMinuteView();
                    mIsShowMinute = true;
                    setMinuteTimerConstant();

                } else {//K线
                    if (mCountMinuteDownTimer != null) {
                        mCountMinuteDownTimer.cancel();
                    }
                    showKview();
                    mIsShowMinute = false;
                }

            }
        });
        rvChoosemh.setAdapter(rvChoosemhAdapter);
    }

    //说明
    private void setShowDialog() {
        explainDialog = new ExplainDialog(this, R.style.Theme_dialog);
        explainDialog.setCancelable(true);
        explainDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        explainDialog.getWindow().setGravity(Gravity.CENTER);
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


    /**
     * 分时图模块
     */
    private void initMinuteView() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
            viewMinuteLeftMessage.setVisibility(View.VISIBLE);
            lineMinuteView.setVisibility(View.VISIBLE);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
            viewMinuteLeftMessage.setVisibility(View.GONE);
            lineMinuteView.setVisibility(View.GONE);
        }

        minuteChartView.setScaleEnable(true); //是否可以缩放
        minuteChartView.setGridRows(6);
        minuteChartView.setGridColumns(5);
        minuteChartView.setGridChildRows(4);
        minuteChartView.setViewScaleGestureListener(new BaseMinuteView.OnScaleGestureListener() {
            @Override
            public void setAddNumber() {
                mScaleValue++;
                if (mScaleValue <= 5 && mScaleValue >= 1) {
                    if (mScaleValue == 1) {
                        isFirstCacheMinuteDatas = false;
                        minuteChartView.setOpenMinute(1);
                        minuteChartView.initData(mMinuteDataMadels1, minTime[0], maxTime[0], mMinuteTimeModels1, preSettle[0], 1);

                    } else if (mScaleValue == 2) {
                        if (ValueUtil.isListEmpty(mMinuteDataMadels2)) {
                            isFirstCacheMinuteDatas = true;
                            mIsClickMinute = true;
                            mMinuteTimeModels2.clear();
                            initMinuteData(1);
                            mScaleValue--;
                            return;
                        }
                        setMinuteShrinkMun(2);
                        minuteChartView.initData(mMinuteDataMadels, minTime[1], maxTime[1], mMinuteTimeModels2, preSettle[1], 2);

                    } else if (mScaleValue == 3) {
                        if (ValueUtil.isListEmpty(mMinuteDataMadels3)) {
                            isFirstCacheMinuteDatas = true;
                            mIsClickMinute = true;
                            mMinuteTimeModels3.clear();
                            initMinuteData(2);
                            mScaleValue--;
                            return;
                        }
                        setMinuteShrinkMun(3);
                        minuteChartView.initData(mMinuteDataMadels, minTime[2], maxTime[2], mMinuteTimeModels3, preSettle[2], 3);

                    } else if (mScaleValue == 4) {
                        if (ValueUtil.isListEmpty(mMinuteDataMadels4)) {
                            isFirstCacheMinuteDatas = true;
                            mIsClickMinute = true;
                            mMinuteTimeModels4.clear();
                            initMinuteData(3);
                            mScaleValue--;
                            return;
                        }
                        setMinuteShrinkMun(4);
                        minuteChartView.initData(mMinuteDataMadels, minTime[3], maxTime[3], mMinuteTimeModels4, preSettle[3], 4);

                    } else if (mScaleValue == 5) {
                        if (ValueUtil.isListEmpty(mMinuteDataMadels5)) {
                            isFirstCacheMinuteDatas = true;
                            mIsClickMinute = true;
                            mMinuteTimeModels5.clear();
                            initMinuteData(4);
                            mScaleValue--;
                            return;
                        }
                        setMinuteShrinkMun(5);
                        minuteChartView.initData(mMinuteDataMadels, minTime[4], maxTime[4], mMinuteTimeModels5, preSettle[4], 5);
                    }
                } else {
                    mScaleValue = 5;
                }
            }

            @Override
            public void setLoseNumber() {
                mScaleValue--;
                if (mScaleValue <= 5 && mScaleValue >= 1) {
                    if (mScaleValue == 1) {
                        isFirstCacheMinuteDatas = false;
                        minuteChartView.setOpenMinute(1);
                        minuteChartView.initData(mMinuteDataMadels1, minTime[0], maxTime[0], mMinuteTimeModels1, preSettle[0], 1);

                    } else if (mScaleValue == 2) {
                        setMinutePowerMun(2);
                        minuteChartView.initData(mMinuteDataMadels, minTime[1], maxTime[1], mMinuteTimeModels2, preSettle[1], 2);

                    } else if (mScaleValue == 3) {
                        setMinutePowerMun(3);
                        minuteChartView.initData(mMinuteDataMadels, minTime[2], maxTime[2], mMinuteTimeModels3, preSettle[2], 3);

                    } else if (mScaleValue == 4) {
                        setMinutePowerMun(4);
                        minuteChartView.initData(mMinuteDataMadels, minTime[3], maxTime[3], mMinuteTimeModels4, preSettle[3], 4);

                    } else if (mScaleValue == 5) {
                        setMinutePowerMun(5);
                        minuteChartView.initData(mMinuteDataMadels, minTime[4], maxTime[4], mMinuteTimeModels5, preSettle[4], 5);
                    }
                } else {
                    mScaleValue = 1;
                }
            }
        });
    }

    private void setMinuteShrinkMun(int tag) {
        minuteChartView.setOpenMinute(1);
        mMinuteDataMadels.clear();
        addMinuteDatas(tag);
        if (ValueUtil.isListEmpty(mMinuteDataCounts)) {
            mScaleValue--;
            return;
        }
        for (int j = 1; j < mMinuteDataCounts.size(); j++) {
            if (j % 1 == 0) {
                mMinuteDataMadels.add(mMinuteDataCounts.get(j));
            }
        }
    }

    /**
     * @param tag 缩放倍率
     */
    private void setMinutePowerMun(int tag) {
        minuteChartView.setOpenMinute(1);
        mMinuteDataMadels.clear();
        addMinuteDatas(tag);
        if (ValueUtil.isListEmpty(mMinuteDataCounts)) {
            mScaleValue++;
            return;
        }
        for (int j = 1; j < mMinuteDataCounts.size(); j++) {
            if (j % 1 == 0) {
                mMinuteDataMadels.add(mMinuteDataCounts.get(j));
            }
        }
    }

    private void initMinuteData(final int preIndex) {
        if (preIndex >= 5) return;
        if (preIndex != 0) {
            // DialogUtil.waitDialog(this);
        }
        Api.getMarketService().getMinutes(futureItem.getContract(), preIndex)
                .compose(XApi.<BaseModel<MinuteModel>>getApiTransformer())
                .compose(XApi.<BaseModel<MinuteModel>>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel<MinuteModel>>() {
                    @Override
                    public void onNext(final BaseModel<MinuteModel> listBaseModel) {
                        if (preIndex != 0) {
                            //   DialogUtil.dismissDialog();
                        }
                        if (mIsMinuteRefreshFailing) {
                            mIsMinuteRefreshFailing = false;
                            setMinuteTimerConstant();
                        }
                        if (ValueUtil.isEmpty(listBaseModel.getData())) {
                            showNodata(false);
                            return;
                        }
                        minuteChartView.setVisibility(View.VISIBLE);
                        minuteEmpty.setVisibility(View.GONE);

                        if (preIndex == 0) {
                            mMinuteDataMadels1.clear();
                            mMinuteTimeModels1.clear();
                        }

                        List<MinuteModel.MinuteDatasBean> dataBeans = listBaseModel.getData().getMinuteDatas();
                        List<MinuteModel.TradeRangesBean> tradeRangesBeans = listBaseModel.getData().getTradeRanges();

                        //初始化当天起始时间，用于判断是否交易日发生变化
                        if (mPresentStartTimer == 0) {
                            mPresentStartTimer = listBaseModel.getData().getMin();
                        }

                        //判断交易日是否变化，当变化时更新所有数组中的数据
                        if (mPresentStartTimer != listBaseModel.getData().getMin() && preIndex == 0 && mScaleValue > 1) {
                            mPresentStartTimer = listBaseModel.getData().getMin();
                            for (int i = 1; i <= mScaleValue; i++) {
                                exchangeAllArrays(i);
                            }
                            return;
                        }

                        minTime[preIndex] = new Date(listBaseModel.getData().getMin());
                        maxTime[preIndex] = new Date(listBaseModel.getData().getMax());
                        if (ValueUtil.isStrNotEmpty(listBaseModel.getData().getPreSettle())) {
                            preSettle[preIndex] = listBaseModel.getData().getPreSettle();
                        } else {
                            preSettle[preIndex] = "0";
                        }

                        if (ValueUtil.isListNotEmpty(dataBeans)) {
                            for (int i = 0; i < dataBeans.size(); i++) {
                                Minute minuteDataMadel = new Minute();

                                minuteDataMadel.ruleAt = new Date(dataBeans.get(i).getRuleAt());
                                minuteDataMadel.last = dataBeans.get(i).getLast(); //成交价 最新报价 Y轴值
                                minuteDataMadel.average = dataBeans.get(i).getAverage(); //均价
                                minuteDataMadel.interest = dataBeans.get(i).getInterest();  //持仓量
                                minuteDataMadel.chgInterest = dataBeans.get(i).getChgInterest(); //持仓变化量
                                minuteDataMadel.volume = dataBeans.get(i).getVolume(); //成交量
                                minuteDataMadel.chgVolume = dataBeans.get(i).getChgVolume(); //成交量变化量
                                minuteDataMadel.settle = dataBeans.get(i).getSettle(); //结算价
                                minuteDataMadel.highest = dataBeans.get(i).getHighest(); //最高价
                                minuteDataMadel.lowest = dataBeans.get(i).getLowest(); //最低价
                                minuteDataMadel.open = dataBeans.get(i).getOpen();//开盘价
                                minuteDataMadel.close = dataBeans.get(i).getLast();//收盘价
                                minuteDataMadel.ask1p = dataBeans.get(i).getAsk1p();  //卖价
                                minuteDataMadel.ask1v = dataBeans.get(i).getAsk1v(); //卖量
                                minuteDataMadel.bid1p = dataBeans.get(i).getBid1p(); //买价
                                minuteDataMadel.bid1v = dataBeans.get(i).getBid1v(); //买量
                                minuteDataMadel.updown = dataBeans.get(i).getUpdown(); //涨跌
                                minuteDataMadel.percent = dataBeans.get(i).getPercent();//涨跌幅度
                                minuteDataMadel.preSettle = dataBeans.get(i).getPreSettle();  //买量
                                minuteDataMadel.preClose = dataBeans.get(i).getPreClose(); //涨跌
                                minuteDataMadel.preInterest = dataBeans.get(i).getPreInterest(); //涨跌幅度
                                minuteDataMadel.upLimit = dataBeans.get(i).getUpLimit(); //
                                minuteDataMadel.loLimit = dataBeans.get(i).getLoLimit();//
                                minuteDataMadel.turnover = dataBeans.get(i).getTurnover();//

                                if (preIndex == 0) {
                                    mMinuteDataMadels1.add(minuteDataMadel);  //变化的
                                    MinuteDataHelper.calculateMACD(mMinuteDataMadels1);

                                } else if (preIndex == 1) {
                                    mMinuteDataMadels2.add(minuteDataMadel);
                                    MinuteDataHelper.calculateMACD(mMinuteDataMadels2);

                                } else if (preIndex == 2) {
                                    mMinuteDataMadels3.add(minuteDataMadel);
                                    MinuteDataHelper.calculateMACD(mMinuteDataMadels3);

                                } else if (preIndex == 3) {
                                    mMinuteDataMadels4.add(minuteDataMadel);
                                    MinuteDataHelper.calculateMACD(mMinuteDataMadels4);

                                } else if (preIndex == 4) {
                                    mMinuteDataMadels5.add(minuteDataMadel);
                                    MinuteDataHelper.calculateMACD(mMinuteDataMadels5);
                                }
                            }

                        }

                        if (ValueUtil.isListNotEmpty(tradeRangesBeans)) {
                            for (int i = 0; i < tradeRangesBeans.size(); i++) {
                                MinuteTime minuteTimeModel = new MinuteTime();
                                minuteTimeModel.start = new Date(tradeRangesBeans.get(i).getStart());
                                minuteTimeModel.end = new Date(tradeRangesBeans.get(i).getEnd());
                                minuteTimeModel.trade = new Date(tradeRangesBeans.get(i).getTrade());

                                if (preIndex == 0) { //变化的
                                    mMinuteTimeModels1.add(minuteTimeModel);

                                } else if (preIndex == 1) {
                                    mMinuteTimeModels2.add(minuteTimeModel);

                                } else if (preIndex == 2) {
                                    mMinuteTimeModels3.add(minuteTimeModel);

                                } else if (preIndex == 3) {
                                    mMinuteTimeModels4.add(minuteTimeModel);

                                } else if (preIndex == 4) {
                                    mMinuteTimeModels5.add(minuteTimeModel);
                                }
                            }

                        }


                        if (preIndex == 1) {
                            mMinuteTimeModels2.addAll(mMinuteTimeModels1);

                        } else if (preIndex == 2) {
                            mMinuteTimeModels3.addAll(mMinuteTimeModels2);

                        } else if (preIndex == 3) {
                            mMinuteTimeModels4.addAll(mMinuteTimeModels3);

                        } else if (preIndex == 4) {
                            mMinuteTimeModels5.addAll(mMinuteTimeModels4);
                        }

                        if (preIndex == 0 && !isFirstCacheMinuteDatas && mPresentStartTimer == minTime[0].getTime()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    minuteChartView.setOpenMinute(1);
                                    minuteChartView.initData(mMinuteDataMadels1, minTime[0], maxTime[0], mMinuteTimeModels1,
                                            preSettle[0], 1);
                                }
                            });
                        }


                        if (isFirstCacheMinuteDatas && mIsClickMinute && mPresentStartTimer == minTime[0].getTime()) {
                            initMinuteView(preIndex);
                            mIsClickMinute = false;
                        }

                        if (mIsTimerRefreshMinuteDatas && mPresentStartTimer == minTime[0].getTime()) {
                            mIsTimerRefreshMinuteDatas = false;
                            initMinuteView(mScaleValue - 1);
                        }


                    }

                    @Override
                    protected void onFail(NetError error) {
                        if (preIndex != 0) {
                            // DialogUtil.dismissDialog();
                        }
                        if (mCountMinuteDownTimer != null) {
                            mIsMinuteRefreshFailing = true;
                            mCountMinuteDownTimer.cancel();
                        }
//                        GjUtil.showEmptyHint(context, Constant.BgColor.BLUE, error, minuteEmpty, new BaseCallBack() {
//                            @Override
//                            public void back(Object obj) {
//                                initMinuteData(preIndex);
//                            }
//                        }, minuteChartView);

                    }
                });

    }

    private void exchangeAllArrays(int postion) {
        switch (postion) {
            case 1:
                break;
            case 2:
                mMinuteDataMadels2.clear();
                mMinuteDataMadels2.addAll(mMinuteDataMadels1);
                mMinuteDataMadels1.clear();

                mMinuteTimeModels2.clear();
                mMinuteTimeModels2.addAll(mMinuteTimeModels1);
                mMinuteTimeModels1.clear();
                break;
            case 3:
                mMinuteDataMadels3.clear();
                mMinuteDataMadels3.addAll(mMinuteDataMadels2);
                mMinuteDataMadels2.clear();
                mMinuteDataMadels2.addAll(mMinuteDataMadels1);
                mMinuteDataMadels1.clear();

                mMinuteTimeModels3.clear();
                mMinuteTimeModels3.addAll(mMinuteTimeModels2);
                mMinuteTimeModels2.clear();
                mMinuteTimeModels2.addAll(mMinuteTimeModels1);
                mMinuteTimeModels1.clear();
                break;
            case 4:
                mMinuteDataMadels4.clear();
                mMinuteDataMadels4.addAll(mMinuteDataMadels3);
                mMinuteDataMadels3.clear();
                mMinuteDataMadels3.addAll(mMinuteDataMadels2);
                mMinuteDataMadels2.clear();
                mMinuteDataMadels2.addAll(mMinuteDataMadels1);
                mMinuteDataMadels1.clear();

                mMinuteTimeModels4.clear();
                mMinuteTimeModels4.addAll(mMinuteTimeModels3);
                mMinuteTimeModels3.clear();
                mMinuteTimeModels3.addAll(mMinuteTimeModels2);
                mMinuteTimeModels2.clear();
                mMinuteTimeModels2.addAll(mMinuteTimeModels1);
                mMinuteTimeModels1.clear();
                break;
            case 5:
                mMinuteDataMadels5.clear();
                mMinuteDataMadels5.addAll(mMinuteDataMadels4);
                mMinuteDataMadels4.clear();
                mMinuteDataMadels4.addAll(mMinuteDataMadels3);
                mMinuteDataMadels3.clear();
                mMinuteDataMadels3.addAll(mMinuteDataMadels2);
                mMinuteDataMadels2.clear();
                mMinuteDataMadels2.addAll(mMinuteDataMadels1);
                mMinuteDataMadels1.clear();

                mMinuteTimeModels5.clear();
                mMinuteTimeModels5.addAll(mMinuteTimeModels4);
                mMinuteTimeModels4.clear();
                mMinuteTimeModels4.addAll(mMinuteTimeModels3);
                mMinuteTimeModels3.clear();
                mMinuteTimeModels3.addAll(mMinuteTimeModels2);
                mMinuteTimeModels2.clear();
                mMinuteTimeModels2.addAll(mMinuteTimeModels1);
                mMinuteTimeModels1.clear();
                break;
            default:
                break;


        }
    }


    //初始化分时图
    private void initMinuteView(int preIndex) {
        switch (preIndex) {
            case 0:
                break;
            case 1:
                if (!mIsTimerRefreshMinuteDatas) {
                    mScaleValue = preIndex + 1;
                }
                minuteChartView.setOpenMinute(1);
                mMinuteDataMadels.clear();
                addMinuteDatas(2);
                for (int j = 1; j < mMinuteDataCounts.size(); j++) {
                    if (j % 1 == 0) {
                        mMinuteDataMadels.add(mMinuteDataCounts.get(j));
                    }
                }
                minuteChartView.initData(mMinuteDataMadels, minTime[1], maxTime[1], mMinuteTimeModels2, preSettle[1], 2);
                break;

            case 2:
                if (!mIsTimerRefreshMinuteDatas) {
                    mScaleValue = preIndex + 1;
                }
                minuteChartView.setOpenMinute(1);
                mMinuteDataMadels.clear();
                addMinuteDatas(3);
                for (int j = 1; j < mMinuteDataCounts.size(); j++) {
                    if (j % 1 == 0) {
                        mMinuteDataMadels.add(mMinuteDataCounts.get(j));
                    }
                }
                minuteChartView.initData(mMinuteDataMadels, minTime[2], maxTime[2], mMinuteTimeModels3, preSettle[2], 3);
                break;

            case 3:
                if (!mIsTimerRefreshMinuteDatas) {
                    mScaleValue = preIndex + 1;
                }
                minuteChartView.setOpenMinute(1);
                mMinuteDataMadels.clear();
                addMinuteDatas(4);
                for (int j = 1; j < mMinuteDataCounts.size(); j++) {
                    if (j % 1 == 0) {
                        mMinuteDataMadels.add(mMinuteDataCounts.get(j));
                    }
                }
                minuteChartView.initData(mMinuteDataMadels, minTime[3], maxTime[3], mMinuteTimeModels4, preSettle[3], 4);
                break;

            case 4:
                if (!mIsTimerRefreshMinuteDatas) {
                    mScaleValue = preIndex + 1;
                }
                minuteChartView.setOpenMinute(1);
                mMinuteDataMadels.clear();
                addMinuteDatas(5);
                for (int j = 1; j < mMinuteDataCounts.size(); j++) {
                    if (j % 1 == 0) {
                        mMinuteDataMadels.add(mMinuteDataCounts.get(j));
                    }
                }
                minuteChartView.initData(mMinuteDataMadels, minTime[4], maxTime[4], mMinuteTimeModels5, preSettle[4], 5);
                break;

            default:
                break;


        }

    }


    private void addMinuteDatas(int tag) {
        mMinuteDataCounts.clear();
        switch (tag) {
            case 1:
                break;
            case 2:
                mMinuteDataCounts.addAll(mMinuteDataMadels2);
                mMinuteDataCounts.addAll(mMinuteDataMadels1);
                break;
            case 3:
                mMinuteDataCounts.addAll(mMinuteDataMadels3);
                mMinuteDataCounts.addAll(mMinuteDataMadels2);
                mMinuteDataCounts.addAll(mMinuteDataMadels1);
                break;

            case 4:
                mMinuteDataCounts.addAll(mMinuteDataMadels4);
                mMinuteDataCounts.addAll(mMinuteDataMadels3);
                mMinuteDataCounts.addAll(mMinuteDataMadels2);
                mMinuteDataCounts.addAll(mMinuteDataMadels1);
                break;

            case 5:
                mMinuteDataCounts.addAll(mMinuteDataMadels5);
                mMinuteDataCounts.addAll(mMinuteDataMadels4);
                mMinuteDataCounts.addAll(mMinuteDataMadels3);
                mMinuteDataCounts.addAll(mMinuteDataMadels2);
                mMinuteDataCounts.addAll(mMinuteDataMadels1);
                break;
            default:
                break;
        }

    }

    /**
     * K 线图
     */
    private void initKView() {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
            viewKLeftMessage.setVisibility(View.VISIBLE);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
            viewKLeftMessage.setVisibility(View.GONE);
        }
        mKAdapter = new KChartAdapter();
        kchartView.setAdapter(mKAdapter);
        mKAdapter.notifyDataSetChanged();
        kchartView.setDateTimeFormatter(unitType, GjUtil.chartDataFormat(unitType), GjUtil.getMustDateMonthDay(unitType));//09/11 日K格式  09:11 分格式
        kchartView.setCardDateTimeFormatter(GjUtil.charCardDateFormat(unitType));//卡片日期显示格式
        kchartView.setGridRows(6);//横线
        kchartView.setGridColumns(5);//竖线
        kchartView.setGridLineWidth(3);
        kchartView.setLongPress(false);
        kchartView.setClosePress(true);
        kchartView.setSelectedLineWidth(1);//长按选中线宽度
        kchartView.setSelectorBackgroundColor(ContextCompat.getColor(this, R.color.c4F5490));//选择器背景色
        kchartView.setSelectorTextColor(ContextCompat.getColor(this, R.color.cE7EDF5));//选择器文字颜色
        kchartView.setBackgroundColor(ContextCompat.getColor(this, R.color.c2A2D4F));//背景
        kchartView.setGridLineColor(ContextCompat.getColor(this, R.color.cFF333556));//表格线颜色
        kchartView.setCandleSolid(true);//蜡柱是否实心
        kchartView.setRefreshListener(this);
        kchartView.resetLoadMoreEnd();
        kchartView.showLoading();
//        DialogUtil.waitDialog(context);
    }

    /**
     * 填充K线数据
     *
     * @param lineList
     */
    private void fillKData(final List<KLine> lineList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (ValueUtil.isListNotEmpty(lineList)) {
                    try {
                        for (Iterator iterator = lineList.iterator(); iterator.hasNext(); ) {
                            KLine bean = (KLine) iterator.next();
                            if (bean.getClosePrice() == 0 || bean.getClosePrice() < bean.getLowPrice()) {
                                iterator.remove();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                final List<KLine> kdata = DataRequest.getKData(lineList, mKAdapter.getCount(), lineList.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //第一次加载时开始动画
                        if (mKAdapter.getCount() == 0) {
                            mKAdapter.clearData();
                            kchartView.startAnimation();
                        }
                        if (ValueUtil.isListNotEmpty(lineList)) {
                            int num = 0;//默认保留两位
                            if (ValueUtil.isStrNotEmpty(lineList.get(0).getOpen())) {
                                num = StrUtil.getPriceBits(lineList.get(0).getOpen());
                            }
                            kchartView.setValueFormatterNum(num);
                            kchartView.setValueFormatter(new ValueFormatter(num));
                        }
                        mKAdapter.addFooterData(kdata, lineList.size(), lineList.size());
                        kchartView.refreshEnd();
                    }
                });
            }
        }).start();
    }


    /**
     * 获取K线数据
     *
     * @param contract
     * @param dataType
     */
    private void getKData(String contract, final String dataType) {
        Api.getMarketService().getKlines(contract, dataType)
                .compose(XApi.<BaseModel<List<KLine>>>getApiTransformer())
                .compose(XApi.<BaseModel<List<KLine>>>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel<List<KLine>>>() {
                    @Override
                    public void onNext(BaseModel<List<KLine>> listBaseModel) {
                        kchartView.hideLoading();
                        //DialogUtil.dismissDialog();
                        if (ValueUtil.isListNotEmpty(listBaseModel.getData())) {
                            kchartView.setVisibility(View.VISIBLE);
                            kChartEmpty.setVisibility(View.GONE);
                            fillKData(listBaseModel.getData());
                        } else {
                            kchartView.refreshEnd();
                            showNodata(true);
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        kchartView.hideLoading();
                        //  DialogUtil.dismissDialog();
                        kchartView.refreshEnd();
                        GjUtil.showEmptyHint(context, Constant.BgColor.BLUE, error, kChartEmpty, new BaseCallBack() {
                            @Override
                            public void back(Object obj) {
                                getKData(futureItem.getContract(), dataType);
                            }
                        }, kchartView, minuteChartView);
                    }
                });
    }


    /***
     * 休市中
     */
    public void showNodata(boolean isKChart) {
        if (mCountMinuteDownTimer != null) {
            mIsMinuteRefreshFailing = true;
            mCountMinuteDownTimer.cancel();
        }

        if (isKChart) {
            minuteChartView.setVisibility(View.GONE);
            kchartView.setVisibility(View.GONE);
            kChartEmpty.setVisibility(View.VISIBLE);
            //   kChartEmpty.setNoData(Constant.BgColor.BLUE);
        } else {
            minuteChartView.setVisibility(View.GONE);
            kchartView.setVisibility(View.GONE);
            minuteEmpty.setVisibility(View.VISIBLE);
            //    minuteEmpty.setNoData(Constant.BgColor.BLUE);
        }
    }

    //对弹框字体大小根据横竖屏来设置 横屏位true 否则false
    public void setToCanvas(boolean ishv) {
        if (ishv) {
            if (width <= 800) {
                kchartView.setSelectorTextSize(DensityUtil.dp2px(8));//选择器文字大小
            } else {
                kchartView.setSelectorTextSize(DensityUtil.dp2px(10));//选择器文字大小
            }
        } else {
            if (width <= 480) {
                kchartView.setSelectorTextSize(DensityUtil.dp2px(10));//选择器文字大小
            } else {
                kchartView.setSelectorTextSize(DensityUtil.dp2px(12));//选择器文字大小
            }
        }

    }


    @Override
    public void onLoadMoreBegin(KMarketChartView chart) {
        getKData(futureItem.getContract(), dataType);
    }

    public static void launch(Activity context, FutureItem futureItem) {
//        if (TimeUtils.isCanClick()) {
//            GjUtil.closeMarketTimer();
//            GjUtil.closeHelperTimer();
//            GjUtil.closeLEMTimer();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.MODEL, futureItem);
        Router.newIntent(context)
                .to(MarketChartActivity.class)
                .data(bundle)
                .launch();
//        }
    }

    //设置定时器
    private void setTimerConstant() {
        countDownTimer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                getNewLast();
            }
        };

    }

    private void setMinuteTimerConstant() {
        if (mCountMinuteDownTimer != null) {
            mCountMinuteDownTimer.cancel();
        }
        mCountMinuteDownTimer = new CountDownTimer(6 * mMinuteTotalTime, mMinuteTotalTime) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.e("---> MinuteTime ", Thread.currentThread() + "");
                mIsTimerRefreshMinuteDatas = true;
                initMinuteData(0);
            }

            @Override
            public void onFinish() {
//                if (User.getInstance().isLoginIng()) {
//                    if (mCountMinuteDownTimer != null) {
//                        mCountMinuteDownTimer.start();
//                    }
//                }
            }
        };
        mCountMinuteDownTimer.start();

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mCountMinuteDownTimer != null) {
            mCountMinuteDownTimer.cancel();
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mIsShowMinute) {
            setMinuteTimerConstant();
        }
    }


}






























































