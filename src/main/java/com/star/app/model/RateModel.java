package com.star.app.model;


import com.star.kchart.comInterface.IRate;
import com.star.kchart.utils.StrUtil;

import java.util.Date;
public class RateModel implements IRate {
    public Date date;
    public String value;
    public String change;
    public String percent;

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public float getValue() {
        if (value == null || value.equals("-") || value.equals("- -")) {
            return -1;
        } else {
            return Float.valueOf(value);
        }
    }

    @Override
    public int getValueIndex() {
        return StrUtil.getPriceBits(value);
    }

    @Override
    public String getChange() {
        return change;
    }

    @Override
    public String getPercent() {
        return percent;
    }
}
