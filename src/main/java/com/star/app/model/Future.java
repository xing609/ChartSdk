package com.star.app.model;

import java.io.Serializable;
import java.util.List;

import com.star.app.base.BaseModel;

/**
 * Description：期货
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-3-29 10:50
 */
public class Future extends BaseModel implements Serializable {
    private int menuId;
    private String name;
    private String typeCode;
    private String describe;
    private boolean hasListDetail;
    private boolean fav;
    private boolean selected;
    private List<NodeListBean> nodeList;
    private Integer state;//刷新状态

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDescribe() {
        return describe;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }


    public boolean isHasListDetail() {
        return hasListDetail;
    }

    public void setHasListDetail(boolean hasListDetail) {
        this.hasListDetail = hasListDetail;
    }

    public List<NodeListBean> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<NodeListBean> nodeList) {
        this.nodeList = nodeList;
    }

    public static class NodeListBean implements Serializable {
        private String type;
        private String name;
        private String describe;
        private int menuId;
        private boolean hasListDetail;

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMenuId() {
            return menuId;
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
    }


//    private String contract;
//    private String lastPrice;
//    private String updown;
//    private String contractCode;
//    private String updownPercent;
//    private Integer contractId;
//    private Integer sort;
//    private Integer state;
//    private String exchange;
//    private String volume;//成交量
//    private String interest;//持仓量
//    private Object nameZh;
//    private String metal;
//    private boolean selected;//是否选中
//    private int menusType;
//    private int type;
//    private List<MetalSubject> metalSubjectList;//二级品种
//
//    public List<MetalSubject> getMetalSubjectList() {
//        return metalSubjectList;
//    }
//
//    public void setMetalSubjectList(List<MetalSubject> metalSubjectList) {
//        this.metalSubjectList = metalSubjectList;
//    }
//
//    public boolean isSelected() {
//        return selected;
//    }
//
//    public void setSelected(boolean selected) {
//        this.selected = selected;
//    }
//
//    public int getType() {
//
//        return type;
//    }
//
//    public String getInterest() {
//        return interest;
//    }
//
//    public void setInterest(String interest) {
//        this.interest = interest;
//    }
//
//    public int getMenusType() {
//        return menusType;
//    }
//
//    public void setMenusType(int menusType) {
//        this.menusType = menusType;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public String getMetal() {
//        return metal;
//    }
//
//    public void setMetal(String metal) {
//        this.metal = metal;
//    }
//
//    public String getVolume() {
//        return volume;
//    }
//
//    public void setVolume(String volume) {
//        this.volume = volume;
//    }
//
//    public void setContractId(Integer contractId) {
//        this.contractId = contractId;
//    }
//
//    public Integer getState() {
//        return state;
//    }
//
//    public void setState(Integer state) {
//        this.state = state;
//    }
//
//    public String getContractCode() {
//        return contractCode;
//    }
//
//    public void setContractCode(String contractCode) {
//        this.contractCode = contractCode;
//    }
//
//    public String getContract() {
//        return contract;
//    }
//
//    public void setContract(String contract) {
//        this.contract = contract;
//    }
//
//    public String getLastPrice() {
//        return lastPrice;
//    }
//
//    public void setLastPrice(String lastPrice) {
//        this.lastPrice = lastPrice;
//    }
//
//    public String getUpdown() {
//        return updown;
//    }
//
//    public void setUpdown(String updown) {
//        this.updown = updown;
//    }
//
//    public String getUpdownPercent() {
//        return updownPercent;
//    }
//
//    public void setUpdownPercent(String updownPercent) {
//        this.updownPercent = updownPercent;
//    }
//
//
//    public Integer getContractId() {
//        return contractId;
//    }
//
//    public void setContractId(int contractId) {
//        this.contractId = contractId;
//    }
//
//    public Integer getSort() {
//        return sort;
//    }
//
//    public void setSort(Integer sort) {
//        this.sort = sort;
//    }
//
//    public String getExchange() {
//        return exchange;
//    }
//
//    public void setExchange(String exchange) {
//        this.exchange = exchange;
//    }
//
//    public Object getNameZh() {
//        return nameZh;
//    }
//
//    public void setNameZh(Object nameZh) {
//        this.nameZh = nameZh;
//    }
//
//    public class MetalSubject extends BaseModel implements Serializable {
//        private String metalCode;
//        private String metalName;
//        private boolean choosed;
//        private int sort;
//
//        public int getSort() {
//            return sort;
//        }
//
//        public void setSort(int sort) {
//            this.sort = sort;
//        }
//
//        public boolean isChoosed() {
//            return choosed;
//        }
//
//        public void setChoosed(boolean choosed) {
//            this.choosed = choosed;
//        }
//
//        public String getMetalCode() {
//            return metalCode;
//        }
//
//        public void setMetalCode(String metalCode) {
//            this.metalCode = metalCode;
//        }
//
//        public String getMetalName() {
//            return metalName;
//        }
//
//        public void setMetalName(String metalName) {
//            this.metalName = metalName;
//        }
//    }
}
