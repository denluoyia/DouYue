# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

#表示proguard对代码进行迭代优化的次数，Android一般为5
-optimizationpasses 5
#包名不混合大小写
-dontusemixedcaseclassnames

#如果引用了v4或者v7包 不混肴
-dontwarn android.support.**
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
  !static !transient <fields>;
  private void writeObject(java.io.ObjectOutputStream);
  private void readObject(java.io.ObjectInputStream);
  java.lang.Object writeReplace();
  java.lang.Object readResolve();
}