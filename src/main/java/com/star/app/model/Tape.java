package com.star.app.model;

import com.star.app.base.BaseModel;

/**
 * Description：盘口
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-12-14 15:07
 */

public class Tape extends BaseModel {
    private String key;
    private String value;

    public Tape(String key, String value){
        this.key=key;
        this.value=value;
    }

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
