package com.star.app.model;

import java.util.List;

import com.star.app.base.BaseModel;

/**
 * Description：行情列表
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-11-23 15:16
 */

public class FutureItem extends BaseModel {
    /**
     * contract : CU08
     * name : 沪铜1908
     * last : 49710
     * updown : -200
     * percent : -0.40%
     * volume : 366
     * interest : 3868
     * chgInterest : -22
     * bizType : contract
     * indicatorType : contract
     * hasFavored : false
     * alarmEnable : true
     */

    private long id;
    private String contract;
    private String name;
    private String last;
    private String updown;
    private String percent;
    private String volume;
    private String interest;
    private String chgInterest;
    private String bizType;
    private String indicatorType; //合约类型
    private boolean hasFavored;
    private boolean alarmEnable;
    private Integer state;
    private boolean selected;
    private String type;
    private int flag;
    private boolean isEnd;
    private boolean hasListDetail;
    private boolean fav;
    private List<Future.NodeListBean> nodeList;
    private int menuId;
    private boolean check;
    private int sort;
    private boolean screenPortrait;//竖屏

    public FutureItem() {
    }

    public FutureItem(String contract, String name, String indicatorType, String bizType) {
        this.contract = contract;
        this.name = name;
        this.indicatorType = indicatorType;
        this.bizType=bizType;
    }

    public boolean isScreenPortrait() {
        return screenPortrait;
    }

    public void setScreenPortrait(boolean screenPortrait) {
        this.screenPortrait = screenPortrait;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isCheck() {
        return check;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public boolean isHasListDetail() {
        return hasListDetail;
    }

    public void setHasListDetail(boolean hasListDetail) {
        this.hasListDetail = hasListDetail;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public List<Future.NodeListBean> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Future.NodeListBean> nodeList) {
        this.nodeList = nodeList;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
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

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getChgInterest() {
        return chgInterest;
    }

    public void setChgInterest(String chgInterest) {
        this.chgInterest = chgInterest;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(String indicatorType) {
        this.indicatorType = indicatorType;
    }

    public boolean isHasFavored() {
        return hasFavored;
    }

    public void setHasFavored(boolean hasFavored) {
        this.hasFavored = hasFavored;
    }

    public boolean isAlarmEnable() {
        return alarmEnable;
    }

    public void setAlarmEnable(boolean alarmEnable) {
        this.alarmEnable = alarmEnable;
    }

}
