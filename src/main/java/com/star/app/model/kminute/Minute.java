package com.star.app.model.kminute;


import java.util.Date;

import com.star.app.util.ValueUtil;
import com.star.kchart.comInterface.IMinuteLine;
import com.star.kchart.utils.StrUtil;

/**
 * Description： 分时数据Model
 * Author: puyantao
 * Email: 1067899750@qq.com
 * Date: 2018-10-15 9:39
 */

public class Minute implements IMinuteLine {
    /**
     * "ruleAt": 1539867600000, 日期  X轴值
     * "last": null, 最新报价 Y轴值
     * "open": null, 开盘价
     * "ask1p": null, 卖价
     * "ask1v": null, 卖量
     * "bid1p": null, 买价
     * "bid1v": null, 买量
     * "highest": null, 最高价
     * "lowest": null, 最低价
     * "upLimit": null,
     * "loLimit": null,
     * "interest": null, 持仓量
     * "volume": null, 成交量
     * "turnover": null,
     * "average": null, 均价
     * "settle": null,结算价
     * "close": null,收盘价
     * "preSettle": null,前一日结算价
     * "preClose": null,前一日收盘价
     * "preInterest": null,前一日持仓量
     * "chgInterest": null,持仓变化量
     * "updown": null,涨跌
     * "percent": null 涨跌幅度
     */

    public Date ruleAt;
    public String last; //成交价 最新报价 Y轴值
    public int lastBits;
    public String average; //均价
    public int averageBits;
    public String interest; //持仓量
    public String volume; //成交量
    public String chgVolume; //成交量

    public String settle; //结算价
    public String highest; //最高价
    public String lowest; //最低价

    public String open; //开盘价
    public String close; //收盘价


    public String ask1p; //卖价
    public String ask1v; //卖量
    public String bid1p; //买价
    public String bid1v; //买量
    public String updown; //涨跌
    public String percent; //涨跌幅度

    public String upLimit;
    public String loLimit;
    public String turnover;
    public String preSettle; //前一日结算价
    public String preClose; //前一日收盘价
    public String preInterest; //前一日持仓量
    public String chgInterest; //持仓变化量

    public float count; //总成交量


    /**
     * 用于MACD
     */
    public float dea;
    public float diff;
    public float macd;

    @Override
    public float getAverage() {
        if (average == null || average.equals("-") || average.equals("- -")) {
            return -1;
        }
        try {
            return Float.parseFloat(average);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("数据异常");
        }

    }

    @Override
    public int getAverageBits() {
        return StrUtil.getPriceBits(average);
    }

    @Override
    public float getLast() {
        if (last == null || last.equals("-") || last.equals("- -")) {
            return -1;
        }
        try {
            return Float.parseFloat(last);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("数据异常");
        }

    }

    @Override
    public int getLastBits() {
        return StrUtil.getPriceBits(last);
    }

    @Override
    public Date getDate() {
        return ruleAt;
    }

    @Override
    public float getVolume() {
        return ValueUtil.strToFloat(volume);
    }

    @Override
    public String getChgVolume() {
        if (ValueUtil.isStrEmpty(chgVolume)) {
            return "- -";
        }
        return chgVolume;
    }

    @Override
    public float getOpen() {
        return ValueUtil.strToFloat(open);

    }

    @Override
    public float getClose() {
        return ValueUtil.strToFloat(close);
    }

    @Override
    public float getCount() {
        return count;
    }

    @Override
    public float getInterest() {
        return ValueUtil.strToFloat(interest);
    }

    @Override
    public String getChgInterest() {
        if (ValueUtil.isStrEmpty(chgInterest)) {
            return "- -";
        }
        return chgInterest;
    }

    @Override
    public float getSettle() {
        return ValueUtil.strToFloat(settle);
    }

    @Override
    public float getHighest() {
        if (highest == null || highest.equals("-") || highest.equals("- -")) {
            return Float.valueOf(last);
        }
        try {
            return Float.valueOf(highest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("数据异常");
        }

    }

    @Override
    public float getLowest() {
        if (lowest == null || lowest.equals("-") || lowest.equals("- -")) {
            return Float.valueOf(last);
        }
        try {
            return Float.valueOf(lowest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("数据异常");
        }

    }

    @Override
    public float getAsk1p() {
        return ValueUtil.strToFloat(ask1p);
    }

    @Override
    public float getAsk1v() {
        return ValueUtil.strToFloat(ask1v);
    }

    @Override
    public float getBid1p() {
        return ValueUtil.strToFloat(bid1p);
    }

    @Override
    public float getBid1v() {
        return ValueUtil.strToFloat(bid1v);
    }

    @Override
    public float getPreSettle() {
        return ValueUtil.strToFloat(preSettle);
    }

    @Override
    public float getPreClose() {
        return ValueUtil.strToFloat(preClose);
    }

    @Override
    public float getPreInterest() {
        return ValueUtil.strToFloat(preInterest);
    }

    @Override
    public String getUpdown() {
        if (ValueUtil.isStrEmpty(updown)) {
            return "- -";
        }
        return updown;
    }

    @Override
    public String getPercent() {
        if (ValueUtil.isStrEmpty(percent)) {
            return "- -";
        }
        return percent;
    }

    @Override
    public float getUpLimit() {
        return ValueUtil.strToFloat(upLimit);
    }

    @Override
    public float getLoLimit() {
        return ValueUtil.strToFloat(loLimit);
    }

    @Override
    public float getTurnover() {
        return ValueUtil.strToFloat(turnover);
    }

    @Override
    public float getDea() {
        return dea;
    }

    @Override
    public float getDiff() {
        return diff;
    }

    @Override
    public float getMacd() {
        return macd;
    }


}







