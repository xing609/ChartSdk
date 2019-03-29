package com.star.app.model;

import java.io.Serializable;

import com.star.app.base.BaseModel;

/**
 * Description：
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-3-30 14:11
 */
public class ExChange extends BaseModel implements Serializable {
    /**
     * id : 8
     * metal : CU
     * exchange : LME
     * contract : LMECU,伦铜3月
     */

    private int id;
    private String metal;
    private String exchange;
    private String contract;
    private String contractCode;
    private int contractId;
    private int type;//测算：1是现货，2是比值
    private Integer sort;
    private Integer fromPage;//1是行情自选、2搜索
    private boolean hasChoose;//是否选中

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getFromPage() {
        return fromPage;
    }

    public void setFromPage(Integer fromPage) {
        this.fromPage = fromPage;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public boolean isHasChoose() {
        return hasChoose;
    }

    public void setHasChoose(boolean hasChoose) {
        this.hasChoose = hasChoose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

}
