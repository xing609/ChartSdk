package com.star.app.model.pop;

/**
 * Description：选择时间字段
 * Author: chenshanshan
 * Email: 1175558532@qq.com
 * Date: 2018-10-12  14:22
 */
public class RvChoosemh {
    private String value;
    private boolean isChoose;
    private String id;
    private boolean isMoreOpon;

    private String mkCode;
    private boolean selected;
    private String type;
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMkCode() {
        return mkCode;
    }

    public void setMkCode(String mkCode) {
        this.mkCode = mkCode;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMoreOpon() {
        return isMoreOpon;
    }

    public void setMoreOpon(boolean moreOpon) {
        isMoreOpon = moreOpon;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
