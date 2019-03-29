package com.star.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import com.star.app.model.Tape;
import com.star.app.ui.R;
import com.star.app.util.ValueUtil;
import com.star.app.widget.autoText.AutofitTextView;


/**
 * Description：盘口
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-12-14 14:50
 */

public class TapeGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<Tape> dataList;
    private int upColor;

    public TapeGridViewAdapter(Context context, List<Tape> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        upColor = mContext.getResources().getColor(R.color.cFFFFFF);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_market_tape,null);
            holder = new ViewHolder();
            holder.tvName = (AutofitTextView) convertView.findViewById(R.id.tvName);
            holder.tvValue = (AutofitTextView) convertView.findViewById(R.id.tvValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Tape bean = dataList.get(position);
        String name = bean.getKey();
        holder.tvName.setText(ValueUtil.isStrNotEmpty(name) ? name : "- -");
        holder.tvValue.setText(ValueUtil.isStrNotEmpty(bean.getValue()) ? bean.getValue() : "- -");
        // (卖价、买价、最新、涨跌、开盘、最高、最低、均价、结算)字段的数据根据最新价 红涨绿跌 白平, (涨停)为红色 (涨跌)为绿色
        // (卖量、买量、成交量、持仓量、日增仓、昨结、昨收)字段为白色

        if (ValueUtil.isStrNotEmpty(bean.getValue()) && bean.getValue().equals("- -")||bean.getValue().equals("-")) {
            holder.tvValue.setTextColor(mContext.getResources().getColor(R.color.cFFFFFF));
        } else {
            if (ValueUtil.isStrNotEmpty(name) && name.equals("卖量") || name.equals("买量") || name.equals("成交量")
                    || name.equals("持仓量") || name.equals("日增仓") || name.equals("昨结") || name.equals("昨收")) {//卖量、买量、成交量、持仓量、日增仓、昨结、昨收
                holder.tvValue.setTextColor(mContext.getResources().getColor(R.color.cFFFFFF));
            } else if (name.equals("跌停")) {
                holder.tvValue.setTextColor(mContext.getResources().getColor(R.color.c35CB6B));
            } else if (name.equals("涨停")) {
                holder.tvValue.setTextColor(mContext.getResources().getColor(R.color.cFF5252));
            } else {
                holder.tvValue.setTextColor(upColor);
            }
        }
        return convertView;
    }

    /**
     * 是涨、跌颜色
     *
     * @param
     */
    public void upOrDown(int color) {
        upColor = color;
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        AutofitTextView tvName;
        AutofitTextView tvValue;
    }
}

