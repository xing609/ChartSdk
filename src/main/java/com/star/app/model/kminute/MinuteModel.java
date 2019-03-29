package com.star.app.model.kminute;

import java.util.List;

import com.star.app.base.BaseModel;

public class MinuteModel extends BaseModel {


    /**
     * min : 1543496400000
     * max : 1543561200000
     * preClose : null
     * preSettle : 13950
     * minuteDatas : [{....}]
     * tradeRanges : [{"start":1543496400000,"end":1543510800000},{"start":1543539600000,"end":1543544100000},{"start":1543545000000,"end":1543548600000},{"start":1543555800000,"end":1543561200000}]
     * allTradeTotal : 464
     * businessStop : false
     */

    private long min;
    private long max;
    private String preClose; //中值(进口测算, 跨月基差)
    private String preSettle;  //中值
    private int allTradeTotal;
    private boolean businessStop;
    private List<MinuteDatasBean> minuteDatas;
    private List<TradeRangesBean> tradeRanges; //交易时间

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public String getPreClose() {
        return preClose;
    }

    public void setPreClose(String preClose) {
        this.preClose = preClose;
    }

    public String getPreSettle() {
        return preSettle;
    }

    public void setPreSettle(String preSettle) {
        this.preSettle = preSettle;
    }

    public int getAllTradeTotal() {
        return allTradeTotal;
    }

    public void setAllTradeTotal(int allTradeTotal) {
        this.allTradeTotal = allTradeTotal;
    }

    public boolean isBusinessStop() {
        return businessStop;
    }

    public void setBusinessStop(boolean businessStop) {
        this.businessStop = businessStop;
    }

    public List<MinuteDatasBean> getMinuteDatas() {
        return minuteDatas;
    }

    public void setMinuteDatas(List<MinuteDatasBean> minuteDatas) {
        this.minuteDatas = minuteDatas;
    }

    public List<TradeRangesBean> getTradeRanges() {
        return tradeRanges;
    }

    public void setTradeRanges(List<TradeRangesBean> tradeRanges) {
        this.tradeRanges = tradeRanges;
    }

    public static class MinuteDatasBean {
        /**
         * ruleAt : 1543496520000
         * last : 13980
         * open : null
         * ask1p : 13955
         * ask1v : 1
         * bid1p : 13925
         * bid1v : 1
         * highest : null
         * lowest : null
         * upLimit : 14645
         * loLimit : 13250
         * interest : 640
         * volume : 0
         * chgVolume : null
         * turnover : 0
         * average : 0
         * settle : null
         * close : null
         * preSettle : 13950
         * preClose : 13980
         * preInterest : 640
         * chgInterest : 0
         * updown : 30
         * percent : 0.22%
         */
        private long ruleAt;   //日期  X轴值  (进口测算, 跨月基差)
        private String last; //成交价 最新报价 Y轴值 (进口测算, 跨月基差)
        private String interest; //持仓量
        private String chgInterest; //持仓变化量
        private String volume; //成交量
        private String chgVolume; //成交量变化量
        private String average; //均价
        private String updown; //涨跌  (进口测算, 跨月基差)
        private String percent; //涨跌幅度  (进口测算, 跨月基差)

        private String open; //开盘价
        private String ask1p; //卖价
        private String ask1v; //卖量
        private String bid1p; //买价
        private String bid1v; //买量
        private String highest; //最高价
        private String lowest; //最低价
        private String upLimit;
        private String loLimit;
        private String turnover;
        private String settle; //结算价
        private String close; //收盘价
        private String preSettle; //前一日结算价
        private String preClose; //前一日持仓量
        private String preInterest; //持仓变化量


        public long getRuleAt() {
            return ruleAt;
        }

        public void setRuleAt(long ruleAt) {
            this.ruleAt = ruleAt;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
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

        public String getHighest() {
            return highest;
        }

        public void setHighest(String highest) {
            this.highest = highest;
        }

        public String getLowest() {
            return lowest;
        }

        public void setLowest(String lowest) {
            this.lowest = lowest;
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

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getChgVolume() {
            return chgVolume;
        }

        public void setChgVolume(String chgVolume) {
            this.chgVolume = chgVolume;
        }

        public String getTurnover() {
            return turnover;
        }

        public void setTurnover(String turnover) {
            this.turnover = turnover;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public String getSettle() {
            return settle;
        }

        public void setSettle(String settle) {
            this.settle = settle;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
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

        public String getPreInterest() {
            return preInterest;
        }

        public void setPreInterest(String preInterest) {
            this.preInterest = preInterest;
        }

        public String getChgInterest() {
            return chgInterest;
        }

        public void setChgInterest(String chgInterest) {
            this.chgInterest = chgInterest;
        }

        public String getUpdown() {
            return updown;
        }

        public void setUpdown(String updown) {
            this.updown = updown;
        }

        public String getPercent() {
            return percent;
        }

        public void setPercent(String percent) {
            this.percent = percent;
        }
    }

    public static class TradeRangesBean {
        /**
         * start : 1543496400000
         * end : 1543510800000
         */

        private long start;
        private long end;
        private long trade;

        public long getStart() {
            return start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return end;
        }

        public long getTrade() {
            return trade;
        }

        public void setTrade(long trade) {
            this.trade = trade;
        }

        public void setEnd(long end) {
            this.end = end;
        }
    }
}




