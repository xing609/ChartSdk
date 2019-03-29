package com.star.app.base;

import com.gjmetal.star.net.IModel;

import java.io.Serializable;

import com.star.app.api.Constant;
import com.star.app.util.ValueUtil;

/**
 * Description：Model 基类
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-3-28  18:20
 */
public class BaseModel<T> implements IModel, Serializable {
    protected boolean error;
    public String code;
    public String message;
    public T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        if (ValueUtil.isStrNotEmpty(code) && !code.equals(Constant.ResultCode.SUCCESS.getValue())) {
            return true;
        }
        return false;
    }

    @Override
    public String getErrorCode() {
        return this.code;
    }

    @Override
    public String getErrorMsg() {
        return this.message;
    }
}
