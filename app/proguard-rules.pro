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

-keep public class * extends android.support.v4.app.FragmentActivity
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.app.Fragment
-keep public class com.android.vending.licensing.ILicensingService
-keep public class android.net.http.SslError
-keep public class android.webkit.WebViewClient
-keep class android.support.v4.**{*;}
-keep class android.app.ActivityManagerNative
-keep class sun.misc.Unsafe
-keep class com.android.vending.licensing.ILicensingService
-keep class android.webkit.**{*;}

#项目混淆
-keep class com.kxg.livewallpaper.api.**{*;}
-keep class com.kxg.livewallpaper.base.**{*;}
-keep class com.kxg.livewallpaper.constant.**{*;}
-keep class com.kxg.livewallpaper.model.**{*;}
-keep class com.kxg.livewallpaper.mvp.**{*;}
-keep class com.kxg.livewallpaper.util.**{*;}
-keep class com.kxg.livewallpaper.config.**{*;}
-keep class com.kxg.livewallpaper.banner.**{*;}
-keep class com.kxg.livewallpaper.factory.**{*;}
-keep class com.kxg.livewallpaper.enumeration.**{*;}
-keep class com.kxg.livewallpaper.wallpaper.**{*;}
-keep class com.kxg.livewallpaper.ui.**{*;}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# RxJava and RxAndroid
-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# OkHttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature

# Gson
-keep class com.google.gson.stream.** { *; }
-keepattributes EnclosingMethod

# Event Bus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-dontwarn io.card.**



#AdMob
-keep public class com.google.android.gms.ads.** {
   public *;
}

-keep public class com.google.ads.** {
   public *;
}

#腾讯MTA统计
-keep class com.tencent.stat.** { *;}

-keep class com.tencent.mid.** { *;}

#Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}
