# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#k线抽象类
#-keep class com.github.tifezh.kchartlib.chart.comInterface.*{*;}
#-keep class com.github.tifezh.kchartlib.utils.*{*;}
#-keep class com.github.tifezh.kchartlib.chart.formatter.*{*;}
#-keep class com.github.tifezh.kchartlib.chart.base.IDateTimeFormatter.*{*;}
#-keep class com.github.tifezh.kchartlib.chart.base.*{*;}
#-keep class com.github.tifezh.kchartlib.chart.view.*{*;}
#-keep class com.github.tifezh.kchartlib.chart.minute.*{*;}


-ignorewarnings
-dontoptimize
-dontpreverify  #忽略与dex编译器和Dalvik VM不相干的预校验
-verbose         #开启混淆记录日志，指定映射文件的名称
-printmapping proguardMapping.txt
-keepattributes *Annotation*   #保留注释，RemoteViews通常需要annotations
-keepattributes Signature      #保持泛型
-dontusemixedcaseclassnames  # 混淆时不使用大小写混合，混淆后的类名为小写
-dontskipnonpubliclibraryclasses       # 指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclassmembers  # 指定不去忽略非公共的库的类的成员
#如果有引用v4包可以添加下面这行
#-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.** { *; }
#如果引用了v4或者v7包
-dontwarn android.support.*
# 混淆算法
-optimizations !code/simplification/cast,!field/*,!class/merging/*
#忽略警告
-ignorewarning
#基本规则
#四大组件(AndroidManifest.xml清单文件中内容)等不混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
#自定义控件不混淆
-keep public class * extends android.view.View {
   public <init>(android.content.Context);
   public <init>(android.content.Context, android.util.AttributeSet);
   public <init>(android.content.Context, android.util.AttributeSet, int);
   public void set*(...);
}
#包含特殊参数的构造方法及其类不混淆
-keepclasseswithmembers class * {
   public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
#所有的Onclick操作不混淆
-keepclassmembers class * extends android.content.Context {
  public void *(android.view.View);
  public void *(android.view.MenuItem);
}
#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# 对于R（资源）类中的静态方法不能被混淆
-keepclassmembers class **.R$* {
 public static <fields>;
}
#Javascript接口中的方法不能混淆
-keepclassmembers class * {
  @android.webkit.JavascriptInterface <methods>;
}
# 枚举类
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#webView处理
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}
#序列化不能被混淆
-keep public class * implements java.io.Serializable {*;}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#处理反射类 不能混淆
#处理js交互 不能混淆
#处理第三方依赖库 不能混淆
#自定义
-keep class com.cqyuntong.asource.components.** { * ; }
-keep class com.cqyuntong.asource.** { * ;}