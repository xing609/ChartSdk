package com.star.app.model.kminute;
import com.star.kchart.comInterface.IMinuteTime;

import java.util.Date;

public class MinuteTime implements IMinuteTime {
    public Date start;
    public Date end;
    public Date trade;

    @Override
    public Date getStartDate() {
        return start;
    }

    @Override
    public Date getEndDate() {
        return end;
    }

    @Override
    public Date getTradeDate() {
        return trade;
    }


}










