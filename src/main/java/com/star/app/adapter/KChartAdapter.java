package com.star.app.adapter;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.star.app.model.kminute.KLine;
import com.star.kchart.base.BaseKChartAdapter;
import com.star.kchart.utils.DateUtil;


public class KChartAdapter extends BaseKChartAdapter {

    private List<KLine> datas = new ArrayList<>();

    public KChartAdapter() {

    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    /**
     * 真实日期
     * @param position
     * @return
     */
    @Override
    public Date getDate(int position) {
        try {
            String s = datas.get(position).getDatetime();
            return DateUtil.getDateByByStringDate(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 交易日
     * @param position
     * @return
     */
    @Override
    public Date getTradeDate(int position) {
        try {
            String s = DateUtil.getStringDateByLong(datas.get(position).getTradeDate(), 7);
            return DateUtil.getDateByByStringDate(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向头部添加数据
     */
    public void addHeaderData(List<KLine> data) {
        if (data != null && !data.isEmpty()) {
            datas.addAll(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 向尾部添加数据
     */
    public void addFooterData(List<KLine> data, int countSize, int size) {
        if (data != null && !data.isEmpty()) {
            if (countSize < size) {
                datas.clear();
            }
            datas.addAll(0, data);
            notifyDataSetChanged();
        }
    }

    /**
     * 清空数据
     */
    public void clearData() {
        if (datas != null && datas.size() > 0) {
            datas.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 改变某个点的值
     *
     * @param position 索引值
     */
    public void changeItem(int position, KLine data) {
        datas.set(position, data);
        notifyDataSetChanged();
    }

}
