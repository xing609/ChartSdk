package com.star.app.model.kminute;

import com.star.kchart.comInterface.IMinuteLine;

import java.util.Date;

/**
 * Description：分时图实体
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-10-12 14:48
 */

public class MinuteLine implements IMinuteLine {
    /**
     * time : 09:30
     * price : 3.53
     * avg : 3.5206
     * vol : 9251
     */

    public Date time;
    public float price;
    public float avg;
    public float volume;

    @Override
    public float getAverage() {
        return avg;
    }

    @Override
    public int getAverageBits() {
        return 0;
    }

    @Override
    public float getLast() {
        return price;
    }

    @Override
    public int getLastBits() {
        return 0;
    }

    @Override
    public Date getDate() {
        return time;
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public String getChgVolume() {
        return "";
    }

    @Override
    public float getOpen() {
        return 0;
    }

    @Override
    public float getClose() {
        return 0;
    }

    @Override
    public float getCount() {
        return 0;
    }

    @Override
    public float getInterest() {
        return 0;
    }

    @Override
    public String getChgInterest() {
        return "";
    }


    @Override
    public float getUpLimit() {
        return 0;
    }

    @Override
    public float getLoLimit() {
        return 0;
    }

    @Override
    public float getTurnover() {
        return 0;
    }

    @Override
    public float getDea() {
        return 0;
    }

    @Override
    public float getDiff() {
        return 0;
    }

    @Override
    public float getMacd() {
        return 0;
    }


    @Override
    public float getSettle() {
        return 0;
    }

    @Override
    public float getHighest() {
        return 0;
    }

    @Override
    public float getLowest() {
        return 0;
    }

    @Override
    public float getAsk1p() {
        return 0;
    }

    @Override
    public float getAsk1v() {
        return 0;
    }

    @Override
    public float getBid1p() {
        return 0;
    }

    @Override
    public float getBid1v() {
        return 0;
    }

    @Override
    public float getPreSettle() {
        return 0;
    }

    @Override
    public float getPreClose() {
        return 0;
    }

    @Override
    public float getPreInterest() {
        return 0;
    }

    @Override
    public String getUpdown() {
        return "";
    }

    @Override
    public String getPercent() {
        return null;
    }


}
