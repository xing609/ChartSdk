package com.star.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gjmetal.star.base.SimpleRecAdapter;
import com.gjmetal.star.kit.KnifeKit;

import com.star.app.api.Constant;
import com.star.app.model.FutureItem;
import com.star.app.ui.R;
import com.star.app.ui.R2;
import com.star.app.ui.chart.ExchangeChartActivity;
import com.star.app.ui.chart.MarketChartActivity;
import com.star.app.util.GjUtil;
import com.star.app.util.ValueUtil;
import com.star.app.widget.autoText.AutofitTextView;
import butterknife.BindView;

/**
 * Description：行情
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-3-29 15:56
 */
public class FutureAdapter extends SimpleRecAdapter<FutureItem, FutureAdapter.ViewHolder> {
    private Context mContext;
    private boolean showUpDowm = false;//默认显示涨跌
    private boolean showInterest = false;//默认显示成交量
    private CallBackLongClickLister callBackLongClickLister;
    private boolean mFav;//自选

    public FutureAdapter(Context context, boolean fav, CallBackLongClickLister clickLister) {
        super(context);
        this.mContext = context;
        this.mFav = fav;
        this.callBackLongClickLister = clickLister;
    }

    public void changeUpDowm(boolean mShowUpDown) {
        this.showUpDowm = mShowUpDown;
        notifyDataSetChanged();
    }

    public void changeInterest(boolean showInterest) {
        this.showInterest = showInterest;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_future_view;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final FutureItem bean = data.get(position);
        holder.tvFutureName.setText(ValueUtil.isStrNotEmpty(bean.getName()) ? bean.getName() : "");
        GjUtil.setNoDataShow(mContext,holder.tvFutureBestNew, bean.getLast());
        GjUtil.setNoDataShow(mContext,holder.tvFutureUpOrDown, bean.getUpdown());
        GjUtil.setNoDataShow(mContext,holder.tvVolume, bean.getVolume());//成交量
        GjUtil.setNoDataShow(mContext,holder.tvInterest, bean.getInterest());//持仓量

        if (position % 2 == 0) {
            holder.llItem.setBackgroundResource(R.drawable.shape_item_market_nor_selector);
        } else {
            holder.llItem.setBackgroundResource(R.drawable.shape_item_market_res_selector);
        }

        //自选管理
        if (mFav && position == data.size() - 1) {
            holder.tvMyExChange.setVisibility(View.VISIBLE);
            holder.tvMyExChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (User.getInstance().isLoginIng()) {
//                        String strJson = GsonUtil.toJson(data).toString();
//                        SharedUtil.put(Constant.CHANGE_DATA, Constant.HAS_CHNAGE_LIST, strJson);//保存选中数据
//                        AddMarketTagActivity.launch((Activity) mContext, true);
//                    } else {
//                        GjUtil.closeMarketTimer();
//                        LoginActivity.launch((Activity) mContext);
//                    }
                }
            });
        } else {
            holder.tvMyExChange.setVisibility(View.GONE);
        }

        holder.tvFutureUpOrDown.setVisibility(View.GONE);
        if (showUpDowm) {//涨幅
            holder.tvFutureUpOrDown.setVisibility(View.VISIBLE);
            holder.tvFutureVolume.setVisibility(View.GONE);
            GjUtil.showTextStyle(mContext,holder.tvFutureUpOrDown, bean.getPercent(), bean.getUpdown(), holder.tvFutureBestNew, holder.tvFutureUpOrDown, holder.tvFutureVolume);
        } else {//涨跌
            holder.tvFutureUpOrDown.setVisibility(View.GONE);
            holder.tvFutureVolume.setVisibility(View.VISIBLE);
            GjUtil.showTextStyle(mContext,holder.tvFutureVolume, bean.getUpdown(), bean.getUpdown(), holder.tvFutureBestNew, holder.tvFutureUpOrDown, holder.tvFutureVolume);
        }
        if (showInterest) {//成交量
            holder.tvInterest.setVisibility(View.VISIBLE);
            holder.tvVolume.setVisibility(View.GONE);
        } else {
            holder.tvInterest.setVisibility(View.GONE);
            holder.tvVolume.setVisibility(View.VISIBLE);
        }
        if (bean.getState() != null && bean.getState() == 1) {//数据发生变化
            holder.tvFutureBestNew.setTextColor(getColor(R2.color.cF8E71C));
        }
        holder.llItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mFav) {
                    callBackLongClickLister.OnLongClick(v, position, bean);
                }
                return false;
            }
        });
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValueUtil.isStrEmpty(bean.getContract())) {
                    return;
                }
                if (ValueUtil.isStrEmpty(bean.getBizType())) {
                    return;
                }
                String bizType = bean.getBizType();
                if (bizType.equals(Constant.BizType.IRATE.getValue())) {//利率
                    ExchangeChartActivity.launch((Activity) context, bean);//原生K线
                } else if (bizType.equals(Constant.BizType.FUTURES_CONTRACT.getValue())) {
                    MarketChartActivity.launch((Activity) context, bean);//原生K线
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R2.id.tvFutureName)
        AutofitTextView tvFutureName;
        @BindView(R2.id.tvFutureBestNew)
        AutofitTextView tvFutureBestNew;
        @BindView(R2.id.tvFutureUpOrDown)
        AutofitTextView tvFutureUpOrDown;
        @BindView(R2.id.tvFutureVolume)
        AutofitTextView tvFutureVolume;
        @BindView(R2.id.llItem)
        LinearLayout llItem;
        @BindView(R2.id.tvVolume)
        AutofitTextView tvVolume;
        @BindView(R2.id.tvInterest)
        AutofitTextView tvInterest;
        @BindView(R2.id.tvMyExChange)
        TextView tvMyExChange;//自选管理


        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }

    public interface CallBackLongClickLister {
        void OnLongClick(View view, int position, FutureItem futureItem);

    }
}

