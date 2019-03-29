package com.star.app.model.kminute;

import com.star.app.util.ValueUtil;
import com.star.kchart.comInterface.IKLine;
import com.star.kchart.utils.DateUtil;

/**
 * Description：K线图实体
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-10-12 14:47
 */

public class KLine implements IKLine {
    public String contract;//合约名
    public String preClose;//结算价
    public String settle;//结算价
    public String source;
    public String interest;
    public String chgInterest;//持仓量变化
    public String updown;
    public String percent;
    public long ruleAt;//时间
    public long tradeDate;//交易日时间
    public String open;//开盘价
    public String highest;//最高价
    public String lowest;//最低价
    public String close;//收盘价
    public String volume;//成交量
    public String chgVolume;//成交量变化量

    public boolean showDifDate;//显示日期的第一个点
    public int dateNum;//集合里该日期点的个数
    public float MA5Price;


    public void setShowDifDate(boolean showDifDate) {
        this.showDifDate = showDifDate;
    }

    public float MA10Price;

    public float MA20Price;

    public float MA26Price;

    public float MA40Price;

    public float MA60Price;

    public float dea;

    public float dif;

    public float macd;

    public float k;

    public float d;

    public float j;

    public float rsi1;

    public float rsi2;

    public float rsi3;

    public float up;

    public float mb;

    public float dn;

    public float MA5Volume;

    public float MA10Volume;

    public long getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(long tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getSettle() {
        return formatValue(settle);
    }

    @Override
    public String getStrSettle() {
        return formatNullValue(settle);
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getPreClose() {
        return formatValue(preClose);
    }

    @Override
    public String getChgInterest() {
        return formatValue(chgInterest);
    }

    @Override
    public String getStrChgInterest() {
        return formatNullValue(chgInterest);
    }

    public String getDatetime() {
        return DateUtil.getStringDateByLong(ruleAt, 7);
    }

    @Override
    public float getOpenPrice() {
        return Float.valueOf(open);
    }

    @Override
    public String getStrOpenPrice() {
        return formatNullValue(open);
    }

    public String getOpen() {
        return formatValue(open);
    }

    @Override
    public float getHighPrice() {
        return formatValueFloat(highest);
    }

    @Override
    public String getStrHighPrice() {
        return formatNullValue(highest);
    }

    @Override
    public float getLowPrice() {
        return formatValueFloat(lowest);
    }

    @Override
    public String getStrLowPrice() {
        return formatNullValue(lowest);
    }

    @Override
    public float getClosePrice() {
        return formatValueFloat(close);
    }

    @Override
    public String getStrClosePrice() {
        return formatNullValue(close);
    }

    @Override
    public float getMA5Price() {
        return MA5Price;
    }

    @Override
    public float getMA10Price() {
        return MA10Price;
    }

    @Override
    public float getMA20Price() {
        return MA20Price;
    }

    @Override
    public float getMA26Price() {
        return MA26Price;
    }

    @Override
    public float getMA40Price() {
        return MA40Price;
    }

    @Override
    public float getMA60Price() {
        return MA60Price;
    }

    @Override
    public boolean getDifDate() {
        return showDifDate;
    }

    @Override
    public int getXDateNum() {
        return dateNum;
    }

    @Override
    public void setXDateNum(int num) {
        this.dateNum=num;
    }

    @Override
    public void setDifDate(boolean showDif) {
        this.showDifDate=showDif;
    }


    @Override
    public float getVolume() {
        return Float.parseFloat(formatValue(volume));
    }

    @Override
    public String getStrVolume() {
        return formatNullValue(volume);
    }

    @Override
    public float getDea() {
        return dea;
    }

    @Override
    public float getDif() {
        return dif;
    }

    @Override
    public float getMacd() {
        return macd;
    }

    @Override
    public float getK() {
        return k;
    }

    @Override
    public float getD() {
        return d;
    }

    @Override
    public float getJ() {
        return j;
    }

    @Override
    public float getRsi1() {
        return rsi1;
    }

    @Override
    public float getRsi2() {
        return rsi2;
    }

    @Override
    public float getRsi3() {
        return rsi3;
    }

    @Override
    public float getUp() {
        return up;
    }

    @Override
    public float getMb() {
        return mb;
    }

    @Override
    public float getDn() {
        return dn;
    }

    @Override
    public String getChgVolume() {//成交量变化

        return formatNullValue(chgVolume);
    }

    @Override
    public String getInterest() {//持仓量
        return formatValue(interest);
    }

    @Override
    public String getStrInterest() {//字符串持仓量
        return formatNullValue(interest);
    }

    @Override
    public String getUpDown() {
        return formatNullValue(updown);
    }

    @Override
    public String getPercent() {
        return formatNullValue(percent);
    }


    @Override
    public float getMA5Volume() {
        return MA5Volume;
    }

    @Override
    public float getMA10Volume() {
        return MA10Volume;
    }


    /**
     * 处理空为0，格式化float
     *
     * @param value
     * @return
     */
    private String formatValue(String value) {
        return value == null || value.equals("- -") || value.equals("-") ? "0" : value;
    }

    private Float formatValueFloat(String value) {
        return value == null || value.equals("- -") || value.equals("-") ? 0 : Float.valueOf(value);
    }

    /**
     * 保留原始数据，处理空为- -
     *
     * @param value
     * @return
     */
    private String formatNullValue(String value) {
        return ValueUtil.isStrEmpty(value) ? "- -" : value;
    }

}
