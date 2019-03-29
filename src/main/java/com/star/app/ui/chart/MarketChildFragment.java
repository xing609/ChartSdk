package com.star.app.ui.chart;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gjmetal.star.event.BusProvider;
import com.gjmetal.star.net.ApiSubscriber;
import com.gjmetal.star.net.NetError;
import com.gjmetal.star.net.XApi;

import java.util.ArrayList;
import java.util.List;

import com.star.app.adapter.FutureAdapter;
import com.star.app.api.Api;
import com.star.app.base.BaseFragment;
import com.star.app.base.BaseModel;
import com.star.app.event.BaseEvent;
import com.star.app.model.Future;
import com.star.app.model.FutureItem;
import com.star.app.ui.R;
import com.star.app.ui.R2;
import com.star.app.util.GjUtil;
import com.star.app.util.ValueUtil;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Description：子视图
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-3-28  17:14
 */

public class MarketChildFragment extends BaseFragment {
    @BindView(R2.id.rvFutureChild)
    RecyclerView rvFutureChild;
    @BindView(R2.id.viewTab)
    View viewTab;//列表栏
    @BindView(R2.id.vTopLine)
    View vTopLine;//列表栏
    @BindView(R2.id.tvFutureUpOrDown)
    TextView tvFutureUpOrDown;//成交量
    @BindView(R2.id.tvFutureVolume)
    TextView tvFutureVolume;
    @BindView(R2.id.tvFutureName)
    TextView tvFutureName;
    @BindView(R2.id.tvFutureBestNew)
    TextView tvFutureBestNew;
//    @BindView(R2.id.tabLayout)
//    TabLayout tabLayout;

    private List<Future.NodeListBean> mMetalSubjectList;
    private FutureAdapter futureAdapter;
    private List<FutureItem> futures = new ArrayList<>();
    //    private CountTimer countTimer;
    private int menuId;
    private int index = 0;
    private int parentIndex;//父的容器位置
    private LinearLayoutManager mLayoutManager;
    //    private MarketPopWindow marketPopWindow;
    private Future future;

    @Override
    protected int setRootView() {
        return R.layout.fragment_market_child;
    }

    @SuppressLint("ValidFragment")
    public MarketChildFragment(int parentIndex, int index, Future future, List<Future.NodeListBean> metalSubjectList) {
        this.future = future;
        this.parentIndex = parentIndex;
        this.index = index;
        this.mMetalSubjectList = metalSubjectList;
        if (ValueUtil.isListNotEmpty(metalSubjectList)) {
            menuId = metalSubjectList.get(index).getMenuId();
        }
    }

    public MarketChildFragment() {
    }

    private Runnable LOAD_DATA = new Runnable() {
        @Override
        public void run() {
            startTimer();
        }
    };
    private Handler mHandler = new Handler();

    @SuppressLint("RestrictedApi")
    public void initView() {
        if (index == 0 && parentIndex == 0 && rvFutureChild != null) {
            if (rvFutureChild != null && rvFutureChild.getVisibility() == View.GONE) {
                mHandler.postDelayed(LOAD_DATA, 200);
            }
        }
//        pullRefreshlayout.setFootHeight(1);
//        pullRefreshlayout.setHeadHeight(1);
//        pullRefreshlayout.setMaxFootHeight(2);
//        pullRefreshlayout.setMaxHeadHeight(2);
//
//        pullRefreshlayout.setRefreshListener(new BaseRefreshListener() {
//            @Override
//            public void refresh() {
//                if (ValueUtil.isListEmpty(mMetalSubjectList)) {
//                    pullRefreshlayout.finishRefresh();
//                    return;
//                }
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 结束刷新
//                        pullRefreshlayout.finishRefresh();
//                        if (index > 0) {
//                            index = index - 1;
//                            tabLayout.getTabAt(index).select();
//                            selectedTop();
//                            startTimer();
//                        }
//                    }
//                }, 5);
//            }
//
//            @Override
//            public void loadMore() {
//                if (ValueUtil.isListEmpty(mMetalSubjectList)) {
//                    pullRefreshlayout.finishLoadMore();
//                    return;
//                }
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        // 结束加载更多
//                        pullRefreshlayout.finishLoadMore();
//                        if (index < mMetalSubjectList.size() - 1) {
//                            index = index + 1;
//                            tabLayout.getTabAt(index).select();
//                            selectedTop();
//                            startTimer();
//                        }
//                    }
//                }, 5);
//            }
//        });
        GjUtil.setRightDrawable(getActivity(), tvFutureUpOrDown, R.mipmap.icon_market_change);
        GjUtil.setRightDrawable(getActivity(), tvFutureVolume, R.mipmap.icon_market_change);
        tvFutureUpOrDown.setText(getString(R.string.volume));
        //成交量切换
        tvFutureUpOrDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvFutureUpOrDown.getText().equals(getString(R.string.volume))) {
                    tvFutureUpOrDown.setText(getString(R.string.interest));
                    futureAdapter.changeInterest(true);
                } else {
                    futureAdapter.changeInterest(false);
                    tvFutureUpOrDown.setText(getString(R.string.volume));
                }
            }
        });

        //涨幅
        tvFutureVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvFutureVolume.getText().equals(getString(R.string.updownPercent))) {
                    tvFutureVolume.setText(getString(R.string.upDown));
                    futureAdapter.changeUpDowm(false);
                } else {
                    futureAdapter.changeUpDowm(true);
                    tvFutureVolume.setText(getString(R.string.updownPercent));
                }
            }
        });
        addTabLayout();
        BusProvider.getBus().register(this);
        //接收对象处理
        BusProvider.getBus().toFlowable(BaseEvent.class)
                .subscribe(new Consumer<BaseEvent>() {
                    @Override
                    public void accept(BaseEvent baseEvent) throws Exception {
                        if (!isAdded()) {
                            return;
                        }
                        if (baseEvent.isRefreshMarketMain()) {
                            if (future.isFav()) {//自选
                                getFutures();
                            }
                        } else if (baseEvent.isCloseMarketTimer()) {
//                            if (countTimer != null) {
//                                countTimer.cancel();
//                            }
                        } else if (baseEvent.isStartMarketTimer()) {
//                            String marketIndex = SharedUtil.get(Constant.MARKET_PAGE_INDEX_1);
//                            String mainPageSelected = SharedUtil.get(Constant.MAIN_PAGE_SELECTED);
//                            if (ValueUtil.isStrNotEmpty(mainPageSelected) && mainPageSelected.equals("0") && ValueUtil.isStrNotEmpty(marketIndex) && rvFutureChild != null) {
//                                if (Integer.parseInt(marketIndex) == parentIndex) {
//                                    startTimer();
//                                }
//                            }
                            startTimer();
                        }
                    }
                });
        mLayoutManager = new LinearLayoutManager(getContext());
        futureAdapter = new FutureAdapter(getActivity(), future.isFav(), new FutureAdapter.CallBackLongClickLister() {
            @Override
            public void OnLongClick(View view, final int position, final FutureItem bean) {
//                marketPopWindow = new MarketPopWindow(getActivity(), view, false, new MarketPopWindow.OnClickListener() {
//                    @Override
//                    public void onTop() {//置顶
//                        resetSortFavoritesCode(bean.getId(), futureAdapter.getDataSource().get(0).getSort());
//                    }
//
//                    @Override
//                    public void onDelete() {
//                        List<Long> longList = new ArrayList<>();
//                        longList.add(bean.getId());
//                        futureAdapter.removeElement(position);
//                        delFavoritesCode(longList);//删除
//                    }
//
//                    @Override
//                    public void onEdit() {
//
//                    }
//                });

            }
        });
        futureAdapter.setData(futures);
        if (rvFutureChild != null) {
            rvFutureChild.setLayoutManager(mLayoutManager);
            rvFutureChild.setAdapter(futureAdapter);
        }

    }

    /**
     * 默认选择第0位置
     */
    public void selectedTop() {
        rvFutureChild.scrollToPosition(0);
        LinearLayoutManager mLayoutManager = (LinearLayoutManager) rvFutureChild.getLayoutManager();
        mLayoutManager.scrollToPositionWithOffset(0, 0);
    }

    /**
     * 启动定时器
     *
     * @param
     */
    public void startTimer() {
        onRefresh();
//        if (countTimer != null) {
//            countTimer.cancel();
//        }
//        countTimer = new CountTimer(2000) {
//            @Override
//            public void onStart(long millisFly) {
//            }
//
//            @Override
//            public void onCancel(long millisFly) {
//            }
//
//            @Override
//            public void onPause(long millisFly) {
//            }
//
//            @Override
//            public void onResume(long millisFly) {
//
//            }
//
//            @Override
//            public void onTick(long millisFly) {
//                onRefresh();
//            }
//        };
//        countTimer.start();

    }


    public void addTabLayout() {
//        if (ValueUtil.isListEmpty(mMetalSubjectList)||ValueUtil.isListNotEmpty(mMetalSubjectList)&&mMetalSubjectList.size()==1) {
//            tabLayout.setVisibility(View.GONE);
//            vTopLine.setVisibility(View.GONE);
//            return;
//        }
//        vTopLine.setVisibility(View.VISIBLE);
//        tabLayout.setVisibility(View.VISIBLE);
//        ArrayList<String> tabList = new ArrayList<>();
//        for (Future.NodeListBean bean : mMetalSubjectList) {
//            tabList.add(bean.getName());
//        }
//        GjUtil.addApleMetalTabLayout(getActivity(), tabLayout, tabList, new BaseCallBack() {
//            @Override
//            public void back(Object obj) {
//                index = (int) obj;
//                menuId = mMetalSubjectList.get(index).getMenuId();
//                selectedTop();
//                startTimer();
//            }
//        });
    }


    /**
     * 排序
     *
     * @param id
     * @param sort
     */
    private void resetSortFavoritesCode(long id, int sort) {
        Api.getMarketService().resetSortFavoritesCode(id, sort)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    public void onNext(BaseModel listBaseModel) {
                        getFutures();
                    }

                    @Override
                    protected void onFail(NetError error) {
                    }
                });
    }

    /**
     * 删除
     *
     * @param list
     */
    private void delFavoritesCode(List<Long> list) {
//        DialogUtil.waitDialog(getActivity());
        Api.getMarketService().delFavoritesCode(list)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    public void onNext(BaseModel listBaseModel) {
                        getFutures();
//                        DialogUtil.dismissDialog();
                    }

                    @Override
                    protected void onFail(NetError error) {
//                        DialogUtil.dismissDialog();
                    }
                });
    }

    /**
     * 显示刷新列表
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            String mainPageSelected = SharedUtil.get(Constant.MAIN_PAGE_SELECTED);
//            if (ValueUtil.isStrNotEmpty(mainPageSelected) && mainPageSelected.equals("0") && rvFutureChild != null) {
//                startTimer();
//            }
//        } else {
//            if(!isVisibleToUser)    {
//                mHandler.removeCallbacks(LOAD_DATA);
//            }
//            if (marketPopWindow != null && marketPopWindow.isShowing()) {
//                marketPopWindow.dismiss();
//            }
//            GjUtil.closeMarketTimer();
//        }
    }

    private void onRefresh() {
        if (!isAdded()) {
            return;
        }
        if (future.isFav()) {//自选
            getFutures();
//            if (User.getInstance().isLoginIng()) {
//                getFutures();
//            } else {
//                myChooseDataView();
//            }
        } else {
            getExchangesList();
        }
    }

    /**
     * 自选
     */
    private void getFutures() {
        Api.getMarketService().getFutures()
                .compose(XApi.<BaseModel<List<FutureItem>>>getApiTransformer())
                .compose(XApi.<BaseModel<List<FutureItem>>>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel<List<FutureItem>>>() {
                    @Override
                    public void onNext(BaseModel<List<FutureItem>> listBaseModel) {
                        GjUtil.checkActState(getActivity());
                        viewTab.setVisibility(ValueUtil.isListNotEmpty(listBaseModel.getData()) ? View.VISIBLE : View.GONE);
                        if (ValueUtil.isListEmpty(listBaseModel.getData())) {
                            myChooseDataView();
                        } else {
                            rvFutureChild.setVisibility(View.VISIBLE);
                            if (ValueUtil.isListNotEmpty(futures)) {
                                futures.clear();
                            }
                            futures.addAll(listBaseModel.getData());
//                            vEmpty.setVisibility(View.GONE);
                        }
                        upDateUI();
                    }

                    @Override
                    protected void onFail(NetError error) {
                        GjUtil.checkActState(getActivity());
//                        if(error!=null&&error.getType().equals(Constant.ResultCode.TOKEN_ERROR.getValue())){
//                            LoginActivity.launch(getActivity());
//                            myChooseDataView();
//                        }else {
//                            showAgainLoad(error);
//                        }

                    }
                });
    }


    private void myChooseDataView() {
        viewTab.setVisibility(View.GONE);
        rvFutureChild.setVisibility(View.GONE);
//        vEmpty.showAddHint(Constant.BgColor.BLUE, R2.mipmap.ic_future_add_nor, R2.string.txt_add_my_change, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (User.getInstance().isLoginIng()) {
//                    AddMarketTagActivity.launch(getActivity(), false);
//                } else {
//                    LoginActivity.launch((Activity) getContext());
//                }
//            }
//        });
//        vEmpty.setVisibility(View.VISIBLE);
    }

    /**
     * 获取列表数据
     */
    private void getExchangesList() {
        Api.getMarketService().getQuotations(menuId)
                .compose(XApi.<BaseModel<List<FutureItem>>>getApiTransformer())
                .compose(XApi.<BaseModel<List<FutureItem>>>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel<List<FutureItem>>>() {
                    @Override
                    public void onNext(BaseModel<List<FutureItem>> listBaseModel) {
                        if (ValueUtil.isListEmpty(listBaseModel.getData())) {
                            if (viewTab != null) {
                                viewTab.setVisibility(View.GONE);
                            }
                            if (rvFutureChild != null) {
                                rvFutureChild.setVisibility(View.GONE);
                            }
//                            if (vEmpty != null) {
//                                vEmpty.setVisibility(View.VISIBLE);
//                                vEmpty.setNoData(Constant.BgColor.BLUE);
//                            }
                        } else {
                            try {
//                                if (vEmpty != null) {
//                                    vEmpty.setVisibility(View.GONE);
//                                }
                                if (viewTab != null) {
                                    viewTab.setVisibility(View.VISIBLE);
                                }
                                if (rvFutureChild != null) {
                                    rvFutureChild.setVisibility(View.VISIBLE);
                                }
                                futures = listBaseModel.getData();
                                upDateUI();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    protected void onFail(NetError error) {
                        showAgainLoad(error);
                    }
                });
    }

    private void showAgainLoad(NetError error) {
//        if (countTimer != null) {
//            countTimer.cancel();
//        }
//        if (vEmpty == null) {
//            return;
//        }
//        GjUtil.showEmptyHint(getActivity(),Constant.BgColor.BLUE,error, vEmpty, new BaseCallBack() {
//            @Override
//            public void back(Object obj) {
//                onRefresh();
//            }
//        },rvFutureChild,viewTab);
    }

    private void upDateUI() {
        if (ValueUtil.isListNotEmpty(futures)) {
            for (FutureItem firstBean : futureAdapter.getDataSource()) {
                for (FutureItem bean : futures) {
                    if (bean.getContract() != null) {
                        if (firstBean.getContract().equals(bean.getContract())) {
                            if (ValueUtil.isStrNotEmpty(firstBean.getLast()) && firstBean.getLast().equals(bean.getLast())) {
                                bean.setState(2);//平仓
                            } else {
                                bean.setState(1);
                            }
                        }
                    } else {
                        bean.setState(2);//平仓
                    }
                }
            }
            futureAdapter.setData(futures);
            //最新数据波动背景变色
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (ValueUtil.isListNotEmpty(futures)) {
                        futureAdapter.notifyDataSetChanged();
                        for (FutureItem bean : futures) {
                            bean.setState(null);
                        }
                    }
                }
            }, 500);
        }
    }

    @Override
    public void onDestroyView() {
//        if (countTimer != null) {
//            countTimer.cancel();
//        }
        BusProvider.getBus().unregister(this);
        super.onDestroyView();
    }

}
