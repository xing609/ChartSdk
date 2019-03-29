package com.star.app.manager;

import com.star.app.api.Constant;
/**
 * Description：配置类
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2019-3-22 14:50
 */

public class GjOptions {
    private static Builder instance;
    public static Builder getInstance() {
        if (instance == null) {
            synchronized (Builder.class) {
                if (instance == null) {
                    instance = new Builder();
                }
            }
        }
        return instance;
    }

    public GjOptions(Builder buidler) {
        this.instance = buidler;
    }

    public static class Builder {
        private String mReqUrl = Constant.ReqUrl.BASE_URL_TEST;//默认是测试地址
        private String token;//用户token
        private String loginOutActivity;//帐号被挤跳转的activity
        /**
         * 配置BASE 请求域名
         *
         * @param reqUrl
         * @return
         */
        public Builder setRequestUrl(String reqUrl) {
            this.mReqUrl = reqUrl;
            if (reqUrl == null || reqUrl.length() == 0)
                throw new IllegalArgumentException("The requestUrl can not be empty,Please configure!");
            return this;
        }
        public String getmReqUrl() {
            return mReqUrl;
        }
        public Builder setToken(String token) {
            this.token = token;
            return this;
        }
        public String getToken() {
            return token;
        }
        public String getLoginOutActivity() {
            return loginOutActivity;
        }

        public Builder setLoginOutActivity(String mLoginOutActivity) {
            if (mLoginOutActivity == null || mLoginOutActivity.length() == 0)
                throw new IllegalArgumentException("The loginOutActivity can not be empty,Please configure!");
            this.loginOutActivity = mLoginOutActivity;
            return this;
        }
        public GjOptions build() {
            return new GjOptions(this);
        }
    }


}
