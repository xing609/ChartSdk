package com.star.app.model.kminute;

import java.util.List;

public class NewLast {


    /**
     * ask1p : string
     * ask1v : string
     * bid1p : string
     * bid1v : string
     * chgInterest : string
     * chgVolume : string
     * highest : string
     * interest : string
     * last : string
     * lowest : string
     * measures : [{"key":"string","value":"string"}]
     * name : string
     * percent : string
     * updown : string
     * volume : string
     */



    /**
     * 卖价
     */
    private String ask1p;
    /**
     * 卖量
     */
    private String ask1v;
    /**
     * 买价
     */
    private String bid1p;
    /**
     * 买量
     */
    private String bid1v;
    /**
     * 持仓量变化
     */
    private String chgInterest;
    /**
     * 成交量变化
     */
    private String chgVolume;
    /**
     * 最高
     */
    private String highest;
    /**
     * 持仓量
     */
    private String interest;
    /**
     * 最新
     */
    private String last;
    /**
     * 最低
     */
    private String lowest;
    private String name;
    private String percent;
    private String updown;
    /**
     * 成交量
     */
    private String volume;
    /**
     * 开盘价
     */
    private String open;
    /**
     * 均价
     */
    private String average;
    /**
     * 昨结
     */
    private String preSettle;
    /**
     * 昨收
     */
    private String preClose;
    /**
     * 涨停价
     */
    private String upLimit;
    /**
     * 跌停价
     */
    private String loLimit;
    private List<MeasuresBean> measures;

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getPreSettle() {
        return preSettle;
    }

    public void setPreSettle(String preSettle) {
        this.preSettle = preSettle;
    }

    public String getPreClose() {
        return preClose;
    }

    public void setPreClose(String preClose) {
        this.preClose = preClose;
    }

    public String getUpLimit() {
        return upLimit;
    }

    public void setUpLimit(String upLimit) {
        this.upLimit = upLimit;
    }

    public String getLoLimit() {
        return loLimit;
    }

    public void setLoLimit(String loLimit) {
        this.loLimit = loLimit;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getAsk1p() {
        return ask1p;
    }

    public void setAsk1p(String ask1p) {
        this.ask1p = ask1p;
    }

    public String getAsk1v() {
        return ask1v;
    }

    public void setAsk1v(String ask1v) {
        this.ask1v = ask1v;
    }

    public String getBid1p() {
        return bid1p;
    }

    public void setBid1p(String bid1p) {
        this.bid1p = bid1p;
    }

    public String getBid1v() {
        return bid1v;
    }

    public void setBid1v(String bid1v) {
        this.bid1v = bid1v;
    }

    public String getChgInterest() {
        return chgInterest;
    }

    public void setChgInterest(String chgInterest) {
        this.chgInterest = chgInterest;
    }

    public String getChgVolume() {
        return chgVolume;
    }

    public void setChgVolume(String chgVolume) {
        this.chgVolume = chgVolume;
    }

    public String getHighest() {
        return highest;
    }

    public void setHighest(String highest) {
        this.highest = highest;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getLowest() {
        return lowest;
    }

    public void setLowest(String lowest) {
        this.lowest = lowest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getUpdown() {
        return updown;
    }

    public void setUpdown(String updown) {
        this.updown = updown;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public List<MeasuresBean> getMeasures() {
        return measures;
    }

    public void setMeasures(List<MeasuresBean> measures) {
        this.measures = measures;
    }

    public static class MeasuresBean {
        /**
         * key : string
         * value : string
         */

        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
