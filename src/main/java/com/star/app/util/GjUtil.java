package com.star.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;
import com.gjmetal.star.event.BusProvider;
import com.gjmetal.star.net.NetError;

import java.util.ArrayList;
import java.util.List;

import com.star.app.api.Constant;
import com.star.app.base.BaseCallBack;
import com.star.app.event.BaseEvent;
import com.star.app.model.ExChange;
import com.star.app.ui.R;
import com.star.kchart.base.IDateTimeFormatter;
import com.star.kchart.formatter.MonthTimeFormatter;
import com.star.kchart.formatter.ShortDateFormatter;
import com.star.kchart.formatter.TimeFormatter;
import com.star.kchart.formatter.YearMonthDayFormatter;
import com.star.kchart.formatter.YearMonthFormatter;


/**
 * Description：国金业务工具类
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-3-28  17:19
 */
public class GjUtil {

    /**
     * 判断Activity 是否销毁，然后中断网络请求，停止界面刷新
     *
     * @param activity
     */
    public static void checkActState(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (activity.isDestroyed()) {
                return;
            }
        }
    }
    public static void setNoDataShow(Context mContext, TextView tv, String data) {
        if (ValueUtil.isStrNotEmpty(data)) {
            if (data.equals("-")) {
                tv.setText(mContext.getResources().getString(R.string.no_helper_data));
            } else {
                tv.setText(data);
            }
        } else {
            tv.setText(mContext.getResources().getString(R.string.no_helper_data));
        }
    }


    /**
     * 最新、涨幅的字体颜色跟着涨跌变化
     *
     * @param tvShow
     * @param tvValue
     * @param updown
     * @param tvList
     */
    public static void showTextStyle(Context mContext, TextView tvShow, String tvValue, String updown, TextView... tvList) {
        try {
            setNoDataShow(mContext, tvShow, tvValue);
            if (ValueUtil.isStrNotEmpty(tvValue)) {
                if (updown.equals("- -") || updown.equals("-") || updown.equals("--")) {
                    setTextColor(mContext.getResources().getColor(R.color.cD8DDE3), tvList);
                } else {
                    if (updown.startsWith("-") && updown.length() > 1) {//负数
                        setTextColor(mContext.getResources().getColor(R.color.c35CB6B), tvList);
                    } else if (updown.startsWith("+")) {//正数
                        setTextColor(mContext.getResources().getColor(R.color.cFF5252), tvList);
                    } else {//0或其它正数
                        if (updown.contains("bp")) {
                            updown = updown.replace("bp", "");
                        }
                        if (Double.valueOf(updown) > 0) {
                            setTextColor(mContext.getResources().getColor(R.color.cFF5252), tvList);
                        } else {
                            setTextColor(mContext.getResources().getColor(R.color.cD8DDE3), tvList);
                        }
                    }
                }
            } else {
                tvShow.setText("- -");
                setTextColor(mContext.getResources().getColor(R.color.cD8DDE3), tvList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断根据最新，红涨绿跌
     *
     * @param context
     * @param value
     * @return
     */
    public static int lastUpOrDown(Context context, String value, TextView... textViews) {
        int color = context.getResources().getColor(R.color.cD8DDE3);
        try {
            if (ValueUtil.isStrNotEmpty(value)) {
                if (value.length() > 1) {
                    if (value.equals(context.getString(R.string.no_helper_data)) || value.equals("-/-")) {
                        color = context.getResources().getColor(R.color.cD8DDE3);
                    } else {
                        if (value.contains("bp") || value.contains(" bp")) {
                            if (value.contains("bp")) {
                                value = value.replace("bp", "");
                            }
                            if (value.contains(" bp")) {
                                value = value.replace("bp", "");
                            }
                            if (Float.parseFloat(value) > 0) {
                                color = context.getResources().getColor(R.color.cFF5252);
                            } else if (Float.parseFloat(value) < 0) {
                                color = context.getResources().getColor(R.color.c35CB6B);
                            } else {
                                color = context.getResources().getColor(R.color.cD8DDE3);
                            }
                        } else if (value.contains("+")) {
                            color = context.getResources().getColor(R.color.cFF5252);
                        } else if (value.contains("%")) {
                            value = value.replace("%", "");
                            if (Float.parseFloat(value) == 0) {
                                color = context.getResources().getColor(R.color.cffffff);
                            } else {
                                color = value.startsWith("-") ? context.getResources().getColor(R.color.c35CB6B) : context.getResources().getColor(R.color.cFF5252);
                            }
                        } else if (value.startsWith("-")) {
                            color = context.getResources().getColor(R.color.c35CB6B);
                        } else {
                            if (Float.parseFloat(value) == 0) {
                                color = context.getResources().getColor(R.color.cffffff);
                            } else {
                                color = value.startsWith("-") ? context.getResources().getColor(R.color.c35CB6B) : context.getResources().getColor(R.color.cFF5252);
                            }
                        }
                    }
                } else {
                    if (value.startsWith("-") && value.length() == 1) {
                        color = context.getResources().getColor(R.color.cD8DDE3);
                    } else if (value.equals("0")) {
                        color = context.getResources().getColor(R.color.cD8DDE3);
                    } else {
                        color = context.getResources().getColor(R.color.cFF5252);
                    }
                }
            } else {
                color = context.getResources().getColor(R.color.cD8DDE3);
            }
            if (textViews != null && textViews.length > 0) {
                for (TextView tv : textViews) {
                    tv.setTextColor(color);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;

    }



    public static void setTextColor(int color, TextView... tvList) {
        for (TextView tv : tvList) {
            if (tv != null) {
                tv.setTextColor(color);
            }
        }
    }
    public static void getScreenConfiguration(Context mContext, View v, ScreenStateCallBack stateCallBack) {
            if (v != null) {
                if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
                    v.setVisibility(View.GONE);
                    stateCallBack.onLandscape();
                } else if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
                    v.setVisibility(View.VISIBLE);
                    stateCallBack.onPortrait();
                }
            }
    }

    public interface ScreenStateCallBack {
        void onPortrait();

        void onLandscape();
    }
    /**
     * K 线日期格式化
     *
     * @param unitType
     * @return
     */
    public static IDateTimeFormatter chartDataFormat(String unitType) {
        if (ValueUtil.isStrEmpty(unitType)) {
            return null;
        }
        if (unitType.equals("d")) {
            return new ShortDateFormatter();
        } else if (unitType.equals("min") || unitType.equals("h")) {
            return new TimeFormatter();
        } else if (unitType.equals("w") || unitType.equals("mon") || unitType.equals("q") || unitType.equals("y")) {
            return new YearMonthFormatter();
        }
        ShortDateFormatter shortDateFormatter = null;
        return shortDateFormatter;
    }


    /**
     * 加载失败提示
     *
     * @param error
     * @param emptyView
     * @param baseCallBack
     */
    public static void showEmptyHint(Context mContext, Constant.BgColor bgColor, NetError error, View emptyView, final BaseCallBack baseCallBack, View... views) {
        if (emptyView == null) {
            return;
        }
        if (views != null && views.length > 0) {
            for (View v : views) {
                if (v != null) {
                    v.setVisibility(View.GONE);
                }
            }
        }
        emptyView.setVisibility(View.VISIBLE);
        if (error == null) {
          //  emptyView.setNoData(bgColor);
        } else {
            if (error.getType().equals(NetError.NoConnectError)) {
              //  emptyView.setOnNetError(bgColor);
            } else if (error.getType().equals(Constant.ResultCode.TOKEN_ERROR.getValue())) {//帐号被挤
                //LoginActivity.launch((Activity) mContext);
             //   emptyView.setOnError(bgColor);
            } else {
             //   emptyView.setOnError(bgColor);
            }
        }
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseCallBack.back(v);
            }
        });
    }


    /**
     * K 线日期格式化
     *
     * @param unitType
     * @return
     */
//    public static IDateTimeFormatter chartDataFormat(String unitType) {
//        if (ValueUtil.isStrEmpty(unitType)) {
//            return null;
//        }
//        if (unitType.equals("d")) {
//            return new ShortDateFormatter();
//        } else if (unitType.equals("min") || unitType.equals("h")) {
//            return new TimeFormatter();
//        } else if (unitType.equals("w") || unitType.equals("mon") || unitType.equals("q") || unitType.equals("y")) {
//            return new YearMonthFormatter();
//        }
//        ShortDateFormatter shortDateFormatter = null;
//        return shortDateFormatter;
//    }


    /**
     * k线日期是否必须月日规则
     *
     * @param unitType
     * @return
     */
    public static boolean getMustDateMonthDay(String unitType) {
        if (ValueUtil.isStrEmpty(unitType)) {
            return true;
        }
        if (unitType.equals("d")||unitType.equals("w") || unitType.equals("mon") || unitType.equals("q") || unitType.equals("y")) {
            return true;
        } else if (unitType.equals("min")||unitType.equals("h")) {
            return false;
        }
        return true;
    }

    /**
     * K线卡片选中时日期显示格式
     *
     * @param unitType
     * @return
     */
    public static IDateTimeFormatter charCardDateFormat(String unitType) {
        if (ValueUtil.isStrEmpty(unitType)) {
            return null;
        }
        if (unitType.equals("d")) {
            return new YearMonthDayFormatter();
        } else if (unitType.equals("min") || unitType.equals("h")) {
            return new MonthTimeFormatter();
        } else if (unitType.equals("w") || unitType.equals("mon") || unitType.equals("q") || unitType.equals("y")) {
            return new YearMonthFormatter();
        }
        ShortDateFormatter shortDateFormatter = null;
        return shortDateFormatter;
    }


    /**
     * 根据字符个数计算偏移量
     */
    private static int getOffsetWidth(int index, List<String> stringArrayList) {
        String str = "";
        for (int i = 0; i < index; i++) {
            str += stringArrayList.get(i);
        }
        return str.length() * 14 + index * 12;
    }


    /**
     * 设置控件右侧图片
     *
     * @param mContext
     * @param tv
     * @param res
     */
    public static void setRightDrawable(Context mContext, TextView tv, Integer res) {
        if (res != null) {
            Drawable nav_up = mContext.getResources().getDrawable(res);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            tv.setCompoundDrawables(null, null, nav_up, null);
        } else {
            tv.setCompoundDrawables(null, null, null, null);
        }

    }



    /**
     * 设置涨跌字体颜色
     *
     * @param mContext
     * @param tv
     * @param value
     */
    public static void setUporDownColor(Context mContext, TextView tv, String value) {
//        tv.setText(ValueUtil.isStrNotEmpty(value) ? value : "-");
        if (ValueUtil.isStrNotEmpty(value) && value.equals("0")) {
            tv.setTextColor(mContext.getResources().getColor(R.color.cE7EDF5));
            tv.setText(value);
        } else if (ValueUtil.isStrNotEmpty(value) && Float.parseFloat(value) < 0) {
            tv.setTextColor(mContext.getResources().getColor(R.color.c35CB6B));
            tv.setText(value);
        } else if (ValueUtil.isStrNotEmpty(value) && Float.parseFloat(value) > 0) {
            tv.setTextColor(mContext.getResources().getColor(R.color.cFF5252));
            if (value.contains("+")) {
                tv.setText(value);
            } else {
                tv.setText("+" + value);
            }

        }

    }


    /**
     * 带小数点的String 转int
     *
     * @param numStr
     * @return
     */
    public static int getNumForString(String numStr) {
        int i = 0;
        if (ValueUtil.isStrNotEmpty(numStr)) {
            Number num = Float.parseFloat(numStr) * 10000;
            i = num.intValue();
        }
        return i;
    }


    /**
     * 启动当时选中的页面定时器，父的位置+子fragment的位置当key
     *
     * @param parentPosition
     * @param childPosition
     */
    public static void startMarketSelectedTimer(int parentPosition, int childPosition) {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setStartMarketTimer(true);
        baseEvent.setMarketType(parentPosition + "/" + childPosition);
        BusProvider.getBus().post(baseEvent);
    }

    /**
     * 行情定时器
     */
    public static void closeMarketTimer() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setCloseMarketTimer(true);
        BusProvider.getBus().post(baseEvent);
    }

    public static void startMarketTimer() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setStartMarketTimer(true);
        BusProvider.getBus().post(baseEvent);
    }

    public static void clearHasChange() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setClearHasChange(true);
        BusProvider.getBus().post(baseEvent);
    }

    public static void closeMonthTimer() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setOpenMonthTimer(false);
        baseEvent.setCloseMonthTimer(true);
        baseEvent.setRefershMeMonth(false);
        BusProvider.getBus().post(baseEvent);
    }

    public static void openMonthTimer() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setOpenMonthTimer(true);
        baseEvent.setCloseMonthTimer(false);
        baseEvent.setRefershMeMonth(false);
        BusProvider.getBus().post(baseEvent);
    }

    //添加预警返回
    public static void setBackMonth() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setBackAddMonth(true);
        BusProvider.getBus().post(baseEvent);
    }

    /**
     * 刷新主界面
     */
    public static void onRefreshMarket() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setRefreshMarketMain(true);
        BusProvider.getBus().post(baseEvent);
    }

    /**
     * 刷新全部
     */
    public static void onRefreshAllFuture() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setRefreshAllChoose(true);
        BusProvider.getBus().post(baseEvent);
    }


    /**
     * 交易助手定时器
     */
    public static void startHelperTimer() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setCloseHelperTimer(false);
        baseEvent.setStartHelperTimer(true);
        BusProvider.getBus().post(baseEvent);
    }

    public static void closeHelperTimer() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setCloseHelperTimer(true);
        baseEvent.setStartHelperTimer(false);
        BusProvider.getBus().post(baseEvent);
    }


    //LEM
    public static void openLEMTimer() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setOpenLEmTimer(true);
        baseEvent.setColseLemTimer(false);
        BusProvider.getBus().post(baseEvent);
    }

    public static void closeLEMTimer() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setOpenLEmTimer(false);
        baseEvent.setColseLemTimer(true);
        BusProvider.getBus().post(baseEvent);
    }

    //KeyBoard
    public static void openKeyBoard() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setExitKeyBoard(false);
        baseEvent.setOpenKeyBoard(true);
        BusProvider.getBus().post(baseEvent);
    }

    public static void closeKeyBoard() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setOpenKeyBoard(false);
        baseEvent.setExitKeyBoard(true);
        BusProvider.getBus().post(baseEvent);
    }

    public static void refershMeMonth() {
        BaseEvent baseEvent = new BaseEvent();
        baseEvent.setRefershMeMonth(true);
        BusProvider.getBus().post(baseEvent);
    }


    /**
     * 行情搜索
     *
     * @param key
     * @param allChangeList
     * @return
     */
    public static List<ExChange> searchForAllChange(String key, List<ExChange> allChangeList) {
        if (ValueUtil.isListEmpty(allChangeList)) {
            return null;
        }
        String smallKey = key.toLowerCase();//小写
        String bigKey = key.toUpperCase();//大写
        List<ExChange> mSearchList = new ArrayList<>();
        for (ExChange bean : allChangeList) {
            String name = bean.getContract();
            if (ValueUtil.isStrNotEmpty(name)) {
                if (name.contains(smallKey) || name.contains(bigKey)) {
                    mSearchList.add(bean);
                }
            }
        }
        return mSearchList;
    }


}
