package com.star.app.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjmetal.star.net.ApiSubscriber;
import com.gjmetal.star.net.NetError;
import com.gjmetal.star.net.XApi;

import java.util.List;

import com.star.app.api.Api;
import com.star.app.model.FutureItem;
import com.star.app.model.pop.KMenuTime;
import com.star.app.ui.R;
import com.star.app.util.GjUtil;
import com.star.app.util.ValueUtil;
import qiu.niorgai.StatusBarCompat;

/**
 * Description：Activity基类
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2019-3-5 11:16
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置状态栏的颜色
            StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.c2A2D4F));
        }
    }

    /**
     * 添加自选
     *
     * @param typeId
     * @param codeId
     */
    public void addFileFavoritesCode(String typeId, String codeId, final TextView tvAddPlus, final ImageView ivAddPlus, final BaseCallBack baseCallBack) {
//        if (!User.getInstance().isLoginIng()) {
//            LoginActivity.launch(context);
//            return;
//        }
//        DialogUtil.waitDialog(context);
        Api.getMarketService().addFileFavoritesCode(typeId, codeId)
                .compose(XApi.<BaseModel<FutureItem>>getApiTransformer())
                .compose(XApi.<BaseModel<FutureItem>>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel<FutureItem>>() {
                    @Override
                    public void onNext(BaseModel<FutureItem> listBaseModel) {
//                        DialogUtil.dismissDialog();
                        GjUtil.onRefreshMarket();
//                        ToastUtil.showToast(listBaseModel.getMessage());
                        baseCallBack.back(listBaseModel.getData());
                        tvAddPlus.setText(getString(R.string.txt_cancel_my_change));//取消自选
                        ivAddPlus.setBackgroundResource(R.mipmap.iv_chart_cancel_plus);
                    }

                    @Override
                    protected void onFail(NetError error) {
//                        DialogUtil.dismissDialog();
                    }
                });
    }

    /**
     * 是否显示盘口
     * contract
     *
     * @param vTape
     */
    public void getPositionQuotation(String contract, String bizType, final View vTape) {
        // DialogUtil.waitDialog(context);
        Api.getMarketService().getContainsPosition(contract, bizType)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    public void onNext(BaseModel listBaseModel) {
                        // DialogUtil.dismissDialog();
                        if (ValueUtil.isNotEmpty(listBaseModel)) {
                            boolean show = (boolean) listBaseModel.getData();
                            vTape.setVisibility(show ? View.VISIBLE : View.GONE);
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        // DialogUtil.dismissDialog();
                        if (vTape != null) {
                            vTape.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    /**
     * 删除自选
     *
     * @param list
     */
    public void delFavoritesCode(List<Long> list, final TextView tvAddPlus, final ImageView ivAddPlus) {
        // DialogUtil.waitDialog(context);
        Api.getMarketService().delFavoritesCode(list)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    public void onNext(BaseModel listBaseModel) {
                        tvAddPlus.setText(getString(R.string.txt_chart_plus));
                        ivAddPlus.setBackgroundResource(R.mipmap.iv_chart_add_plus);
                       // ToastUtil.showToast(listBaseModel.getMessage());
                      //  DialogUtil.dismissDialog();
                        GjUtil.onRefreshMarket();
                    }

                    @Override
                    protected void onFail(NetError error) {
                      //  DialogUtil.dismissDialog();
                    }
                });
    }

    /**
     * 获取K 线时间菜单
     */
    public void getMinuteKlineInterval(final BaseCallBack baseCallBack) {
        // DialogUtil.waitDialog(context);
        Api.getMarketService().getMinuteKlineInterval()
                .compose(XApi.<BaseModel<List<KMenuTime>>>getApiTransformer())
                .compose(XApi.<BaseModel<List<KMenuTime>>>getScheduler())
                .subscribe(new ApiSubscriber<BaseModel<List<KMenuTime>>>() {
                    @Override
                    public void onNext(BaseModel<List<KMenuTime>> listBaseModel) {
                        //  DialogUtil.dismissDialog();
                        baseCallBack.back(listBaseModel.getData());
                    }

                    @Override
                    protected void onFail(NetError error) {
                        //   DialogUtil.dismissDialog();
                    }
                });
    }


    /**
     * 检查是否添加到自选
     */
    public void getFileFavoritesCodecheck(String typeId, String codeId, final TextView tvAddPlus, final ImageView ivAddPlus, final BaseCallBack baseCallBack) {
//        if (!User.getInstance().isLoginIng()) {
//            tvAddPlus.setText(getString(R.string.txt_chart_plus));
//            ivAddPlus.setBackgroundResource(R.mipmap.iv_chart_add_plus);
//            return;
//        }
//        Api.getMarketService().getFileFavoritesCodecheck(typeId, codeId)
//                .compose(XApi.<BaseModel<FutureItem>>getApiTransformer())
//                .compose(XApi.<BaseModel<FutureItem>>getScheduler())
//                .subscribe(new ApiSubscriber<BaseModel<FutureItem>>() {
//                    @Override
//                    public void onNext(BaseModel<FutureItem> listBaseModel) {
//                        if (ValueUtil.isNotEmpty(listBaseModel)) {
//                            baseCallBack.back(listBaseModel.getData());
//                            if (ValueUtil.isNotEmpty(listBaseModel.getData()) && listBaseModel.getData().getId() != 0) {
//                                tvAddPlus.setText(getString(R.string.txt_cancel_my_change));//取消自选
//                                ivAddPlus.setBackgroundResource(R.mipmap.iv_chart_cancel_plus);
//                            } else {
//                                tvAddPlus.setText(getString(R.string.txt_chart_plus));
//                                ivAddPlus.setBackgroundResource(R.mipmap.iv_chart_add_plus);
//                            }
//                        }
//                    }
//
//                    @Override
//                    protected void onFail(NetError error) {
//                        tvAddPlus.setText(getString(R.string.txt_chart_plus));
//                        ivAddPlus.setBackgroundResource(R.mipmap.iv_chart_add_plus);
//                    }
//                });
    }
}
