package com.star.app.manager;

import android.content.Context;

import com.gjmetal.star.net.NetError;
import com.gjmetal.star.net.NetProvider;
import com.gjmetal.star.net.RequestHandler;
import com.gjmetal.star.net.XApi;

import com.star.app.util.AppUtil;
import com.star.app.util.ValueUtil;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description：
 * Author: star
 * Email: guimingxing@163.com
 * Date: 2019-3-22 14:53
 */

public class GjChart {
    public static GjOptions mGjoptions;

    public static synchronized void init(Context mContext, GjOptions gjOptions) {
        if (mContext == null) new NullPointerException("Context is null...");
        mGjoptions = gjOptions;

        if (gjOptions == null) new NullPointerException("GjOptions is null...");
        initNet(mContext);
    }

    public static GjOptions getmGjoptions() {
        return mGjoptions;
    }

    /**
     * 初始化网络请求
     *
     * @param mContext
     */
    public static void initNet(Context mContext) {
        final String versionCode = String.valueOf(AppUtil.getAppVersionCode(mContext));
        final String appVersion = String.valueOf(AppUtil.getAppVersionName(mContext));
        final String mobileType = AppUtil.getClientModel();
        final String mobileSystem = AppUtil.getOSVersionCode();
        final String token = GjOptions.getInstance().getmToken();
        XApi.registerProvider(new NetProvider() {
            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {
            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return new RequestHandler() {
                    @Override
                    public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
                        return request.newBuilder()
                                .addHeader("Content-Type", "application/json; charset=UTF-8")
                                .addHeader("Connection", "keep-alive")
                                .addHeader("Accept", "*/*")
                                .addHeader("Cookie", "add cookies here")
                                .addHeader("Mobile-type", mobileType)
                                .addHeader("VersionCode", versionCode)
                                .addHeader("Mobile-System", mobileSystem)
                                .addHeader("x-requested-with", ValueUtil.isStrNotEmpty(token) ? token : "")
                                .addHeader("AppVersion", appVersion)
                                .addHeader("User-Agent", "Android," + mobileType + mobileSystem + ",")
                                .build();
                    }

                    @Override
                    public Response onAfterRequest(Response response, Interceptor.Chain chain) {
                        return null;
                    }
                };
            }

            @Override
            public long configConnectTimeoutMills() {
                return 30 * 1000;
            }

            @Override
            public long configReadTimeoutMills() {
                return 30 * 1000;
            }

            @Override
            public boolean configLogEnable() {
                return true;
            }

            @Override
            public boolean configGzip() {//是否对请求数据压缩
                return false;
            }

            @Override
            public boolean handleError(NetError error) {
//                DialogUtil.dismissDialog();
                return false;
            }

            @Override
            public boolean dispatchProgressEnable() {
                return false;
            }
        });
    }

}
