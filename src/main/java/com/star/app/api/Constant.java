package com.star.app.api;

/**
 * Description：静态常量类
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2018-3-28  17:23
 */
public class Constant {
    public static String MODEL = "model";
    public static String APP_DIALOG_SHARE_UEL = "https://app.shmet.com/Welcome";   //分享的url

    public static class ReqUrl {
        //测试环境
        public static final String BASE_URL_TEST = "https://testapp.gjmetal.com";
        //正式环境
        public static final String BASE_URL_PRO = "https://app.shmet.com";
        //LIVE
        public static final String BASE_URL_LIVE = "https://liveapp.shmet.com";
        //LIVE2
        public static final String BASE_URL_LIVE2 = "https://live2app.gjmetal.com";
    }

    /**
     * 空数据提示背景
     */
    public enum BgColor {
        WHITE,
        BLUE
    }

    /**
     * 错误码
     */
    public enum ResultCode {
        SUCCESS("000000"),//成功
        FAILED("999999"),//失败
        TOKEN_ERROR("100000"),//会话超时，单点登录
        CODE_ERROR("200000"),//短信验证码校验错误
        IMAGE_CODE_ERROR("800000");//图片验证码校验错误


        private final String value;

        private ResultCode(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 通知栏类型
     */
    public enum NoticeType {
        JUMP_HREF("jumpHref", "跳转外部链接"),
        NEWS_FLASH("newsFlash", "快讯"),
        NEWS("news", "资讯"),
        MONITOR_CONTRACT("monitor_contract", "合约预警"),
        MONITOR_PROFIT_PARTIY("monitor_profitpartiy", "盈亏/比价预警"),
        SUBTRACTION("monitor_subtraction", "跨月基差"),
        MONITOR_IRATE("monitor_irate", "利率预警");

        private final String value;
        private final String name;

        private NoticeType(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public String getName() {
            return name;
        }

    }


    /**
     * 合约类型
     */
    public enum BizType {
        /**
         * 期货合约
         */
        FUTURES_CONTRACT("contract"),

        /**
         * 盈亏比价
         */
        PROFIT_PARITY("profitpartiy"),

        /**
         * 利率
         */
        IRATE("irate"),

        /**
         * 现货
         */
        SPOT("spot"),

        /**
         * 产业
         */
        INDUSTRY("industry"),

        /**
         * 库存
         */
        STOCK("stock"),

        /**
         * 新闻
         */
        NEWS("news"),

        /**
         * 预警
         */
        MONITOR("monitor");

        private final String value;

        private BizType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 分享的平台
     */
    public enum ShareType {
        QQ,
        SINA,
        WECHAT,
        WECHAT_FRIENDS
    }

    public enum ShareToType {
        IMAGE,//只分享图片
        ALL
    }


}
