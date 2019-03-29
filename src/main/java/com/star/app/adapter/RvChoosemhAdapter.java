package com.star.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjmetal.star.base.SimpleRecAdapter;
import com.gjmetal.star.kit.KnifeKit;

import java.util.List;

import com.star.app.model.pop.RvChoosemh;
import com.star.app.ui.R;
import com.star.app.ui.R2;
import butterknife.BindView;

/**
 * Description：日期选择
 * Author: chenshanshan
 * Email: 1175558532@qq.com
 * Date: 2018-10-12  14:22
 */
public class RvChoosemhAdapter extends SimpleRecAdapter<RvChoosemh, RvChoosemhAdapter.MyViewHolder> {
    private MyItemLister myItemLister = null;
    private boolean isOpen = false;//弹出的时候位true

    public RvChoosemhAdapter(Context context, boolean isOpen) {
        super(context);
        this.isOpen = isOpen;
    }

    @Override
    public MyViewHolder newViewHolder(View itemView) {
        return new MyViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_rvchoosemh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvValue.setText(data.get(position).getValue());
        if (isOpen) {
            if (data.get(position).isChoose()) {
                holder.tvValue.setTextColor(context.getResources().getColor(R.color.cFFFFFF));
                holder.tvTab.setVisibility(View.VISIBLE);
            } else {
                holder.tvValue.setTextColor(context.getResources().getColor(R.color.c9EB2CD));
                holder.tvTab.setVisibility(View.GONE);
            }

        } else {
            if (data.get(position).isChoose()) {
                if (data.get(position).getValue().equals(context.getResources().getString(R.string.txt_chart_more))) {
                    holder.tvTab.setVisibility(View.GONE);
                } else {
                    holder.tvValue.setTextColor(context.getResources().getColor(R.color.cFFFFFF));
                    holder.tvTab.setVisibility(View.VISIBLE);
                }
            } else {
                holder.tvValue.setTextColor(context.getResources().getColor(R.color.c9EB2CD));
                holder.tvTab.setVisibility(View.GONE);
            }
            if (position == 6) {
                holder.ivopen.setVisibility(View.VISIBLE);
                if (data.get(position).isMoreOpon()) {
                    holder.ivopen.setImageResource(R.mipmap.iv_chart_date_up);
                } else {
                    holder.ivopen.setImageResource(R.mipmap.iv_chart_date_down);
                }
            } else {
                holder.ivopen.setVisibility(View.GONE);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myItemLister != null) {
                    myItemLister.setItem(v, data, position);
                }

            }
        });
    }

    public void setMyItemLister(MyItemLister myItemLister) {
        this.myItemLister = myItemLister;
    }

    public interface MyItemLister {
        public void setItem(View v, List<RvChoosemh> data, int position);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tvValue)
        TextView tvValue;
        @BindView(R2.id.tvTab)
        TextView tvTab;
        @BindView(R2.id.ivopen)
        ImageView ivopen;


        public MyViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
