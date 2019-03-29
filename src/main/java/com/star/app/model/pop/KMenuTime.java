package com.star.app.model.pop;

/**
 * Description：K线时间
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-11-29 14:24
 */

public class KMenuTime {
    /**
     * mkCode : string
     * name : string
     * selected : true
     * type : string
     */
    private String mkCode;
    private String name;
    private boolean selected;
    private String type;
    private String unit;//单位

    private boolean chooseSelect;

    public boolean isChooseSelect() {
        return chooseSelect;
    }

    public void setChooseSelect(boolean chooseSelect) {
        this.chooseSelect = chooseSelect;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
