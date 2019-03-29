package com.star.app.ui.chart;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjmetal.star.net.ApiSubscriber;
import com.gjmetal.star.net.NetError;
import com.gjmetal.star.net.XApi;

import java.util.ArrayList;
import java.util.List;

import com.star.app.api.Api;
import com.star.app.base.BaseFragment;
import com.star.app.base.BaseModel;
import com.star.app.model.Future;
import com.star.app.ui.R;
import com.star.app.ui.R2;
import com.star.app.util.GjUtil;
import com.star.app.util.ValueUtil;
import com.star.app.widget.looper.LoopViewPager;
import butterknife.BindView;

/**
 * Description：行情
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2019-3-19 15:04
 */

public class MarketFragment extends BaseFragment {
    @BindView(R2.id.vpFuture)
    LoopViewPager vpFuture;
    @BindView(R2.id.tvTabTitle)
    TextView tvTabTitle;

    private List<String> mDataList;
    private List<Future> titleList;
    private List<Fragment> mFragments = new ArrayList<>();
    private int pageIndex = 0;


//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_market);
//        mContext=this;
//        ButterKnife.bind(this);
//
//        GjChart.netInit(this);
//        getExChanges();
//    }

    @Override
    protected int setRootView() {
        return R.layout.fragment_market;
    }

    @Override
    protected void initView() {
        getExChanges();
    }

    private void getExChanges() {
        Api.getMarketService().getMenus()
                .compose(XApi.<BaseModel<List<Future>>>getApiTransformer())
                .compose(XApi.<BaseModel<List<Future>>>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel<List<Future>>>() {
                    @Override
                    public void onNext(BaseModel<List<Future>> listBaseModel) {
                        try {
                            vpFuture.setVisibility(View.VISIBLE);

                            mDataList = new ArrayList<>();
                            titleList = new ArrayList<>();

                            if (ValueUtil.isListNotEmpty(listBaseModel.getData())) {
                                titleList.addAll(listBaseModel.getData());
                                for (Future bean : titleList) {
                                    if (ValueUtil.isStrNotEmpty(bean.getName())) {
                                        mDataList.add(bean.getName());
                                    }
                                }
                                upDateTitleTable(mDataList);
                            } else {
                                failAgainLoad(null);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {

                        failAgainLoad(error);
                    }
                });

    }

    private void failAgainLoad(NetError error) {
//        if (vEmpty == null) {
//            return;
//        }
//        if (ValueUtil.isListEmpty(mDataList)) {
//            vpFuture.setVisibility(View.GONE);
//            GjUtil.showEmptyHint(getActivity(), Constant.BgColor.BLUE, error, vEmpty, new BaseCallBack() {
//                @Override
//                public void back(Object obj) {
//                    vEmpty.setVisibility(View.GONE);
//                    getExChanges();
//                }
//            });
//        } else {
//            vpFuture.setVisibility(View.VISIBLE);
//            vEmpty.setVisibility(View.GONE);
//        }
    }

    /**
     * 更新标题栏
     *
     * @param mDataList
     */
    private void upDateTitleTable(final List<String> mDataList) {
        for (int i = 0; i < mDataList.size(); i++) {
            MarketChildFragment marketChildFragment = new MarketChildFragment(i, 0, titleList.get(i), titleList.get(i).getNodeList());
            mFragments.add(marketChildFragment);
        }
        vpFuture.setOffscreenPageLimit(mFragments.size() - 1);
        vpFuture.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int var1) {
                return mFragments.get(var1);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object); //防止fragment 被内存回收
            }
        });
        vpFuture.setCurrentItem(0, false);
        if (ValueUtil.isListEmpty(mDataList)) {
            return;
        }
        tvTabTitle.setText(mDataList.get(pageIndex));

        vpFuture.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pageIndex = position;
                tvTabTitle.setText(mDataList.get(position));
                GjUtil.startMarketTimer();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
