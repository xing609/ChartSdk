# 天下金属-行情K线打包封装sdk

## 一.项目简介
    
*    整体是以Module 方式被其它应用接入，该Module 里网络请求(kchartlib-release.aar)和k线绘制(mvp-release.aar)打成aar 包

## 二.接入流程
*    1.先clone 拉取sdk 包，然后Import Module引入主项目里，clean 一下；

*    2.在主项目app 的build文件里配置：  
    apply plugin: 'com.jakewharton.butterknife' //为避免和chartSdk里使用冲突

    dependencies {  
    implementation fileTree( include: ['*.jar'], dir: 'libs')  
    implementation 'com.jakewharton:butterknife:8.8.1'  
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'  
    implementation project(':chartSdk')  
    }  
    //引用library里的aar文件  
    repositories {
    flatDir {
        dirs 'libs', '../chartSdk/libs'
    }
}
*    3.找到根项目的build.gradle 配置：  
    dependencies {  
    classpath 'com.jakewharton:butterknife-gradle-plugin:9.0.0'  
    }
*    4.在主项目app 配置AndroidManifest.xml  
<!--lib行情列表-->  
        <activity
            android:name="com.star.app.ui.MarketActivity"
            android:configChanges="orientation|screenSize|keyboardHidden"
            android:launchMode="singleTask"
            android:screenOrientation="portrait"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar"
            android:windowSoftInputMode="stateAlwaysHidden|adjustPan" />  
            <!--libK线详情 -->
        <activity
            android:name="com.star.app.ui.chart.MarketChartActivity"
            android:configChanges="orientation|screenSize|keyboardHidden"
            android:launchMode="standard"
            android:windowSoftInputMode="stateHidden" />  
            <!--lib盘口-->
        <activity
            android:name="com.star.app.ui.chart.TapeActivity"
            android:configChanges="orientation|screenSize|keyboardHidden"
            android:launchMode="singleTask"
            android:screenOrientation="portrait"
            android:theme="@style/Theme.AppCompat.Light.NoActionBar"
            android:windowSoftInputMode="stateAlwaysHidden|adjustPan" />  
            <!--lib盘口-->
        <activity
            android:name="com.star.app.ui.chart.ExchangeChartActivity"
            android:configChanges="orientation|screenSize|keyboardHidden"
            android:launchMode="standard"
            android:windowSoftInputMode="stateHidden" />

    
## 三.使用方法  

*   找到应用Application类初始化配置:  
    GjChart.init(this, new GjOptions.Builder()  
                  .setRequestUrl(com.star.app.api.Constant.ReqUrl.BASE_URL_PRO)  //任意切换请求地址，正式、测试、live  
                  .setToken(token)  //用户令牌token  
                  .setLoginOutActivity("com.engjmetal.app.ui.login.LoginActivity")  //帐号被挤时跳转的Activity  
                  .build());  
*   除网络请求框架、k线图已经生成aa包不可修改，其它都可以任意修改样式。
                

