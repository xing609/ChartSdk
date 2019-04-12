# 股票行情K线、分时图打包封装sdk

## 一.项目简介
    
*    整体是以Module 方式被其它应用接入，该Module 里网络请求(kchartlib-release.aar)和k线绘制(mvp-release.aar)打成aar 包
*    分时图主视图显示指标：分时线、均价;          子视图：MACD、CJL;
*    k 线（1日、1m、3m、5m、15m、30m、1h、2h、3h、4h）时间可通过接口动态配置。主视图显示指标：k线、MA、BOLL;     子视图指标：CJL、MACD、KDJ。日线时间轴5等份显示月/日，跨年显示（年/月）
，分时如果是同一个交易日显示（时/分）,不是同一个交易日显示（月/日）;
*    上Gif效果图：

   ![image](https://github.com/xing609/chartSdk/blob/master/assets/chart.gif)

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
                
##  四.技术交流  
*   感觉对你有帮助，记得右上角给个Star，或打赏作者喝咖啡：  
 <img border="0" src="https://github.com/xing609/AndroidDoc/blob/master/img/zfb_pay.jpg" width="300" hegiht="600" align=center>  <img border="0" src="https://github.com/xing609/AndroidDoc/blob/master/img/wx_pay.png" width="300" hegiht="600" align=center>
*    欢迎加入Android 学习交流群：**413893967**
   <a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5EUEsBC"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png"></a>
*    个人联系方式：512002160@qq.com   

