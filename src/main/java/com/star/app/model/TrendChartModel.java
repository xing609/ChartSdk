package com.star.app.model;

import com.star.app.base.BaseModel;

public class TrendChartModel extends BaseModel {


    /**
     * date : 1543766400000
     * value : 2.5150
     * change : -12.70 bp
     * "percent": "-"
     */

    private long date;
    private String value;
    private String change;
    private String percent;

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}










