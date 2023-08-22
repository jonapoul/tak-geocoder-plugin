################################################################################################
################################################################################################
## System Section
################################################################################################
################################################################################################

# Do not strip any method/class that is annotated with @DontObfuscate
-keep,allowobfuscation @interface gov.tak.api.annotation.DontObfuscate
-keep @gov.tak.api.annotation.DontObfuscate class * {*;}
-keepclassmembers class * {
    @gov.tak.api.annotation.DontObfuscate *;
}

-dontskipnonpubliclibraryclasses
-dontshrink
-dontoptimize

############### ACRA specifics
# we need line numbers in our stack traces otherwise they are pretty useless
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keepattributes Signature, InnerClasses


-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclassmembers class * {
    @org.simpleframework.xml.* *;
}

-keep class * implements gov.tak.api.plugin.IPlugin {
}

-keep class org.simpleframework.** {*;}
-keep class com.atak.maps.daco.xml.** {*;}

# overcome an existing bug in the gradle subsystem (3.5.x)
-keep class module-info
-dontwarn module-info

######################### START KOTLINX SERIALISATION #########################
# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}
# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <2>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}
# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}
######################### END KOTLINX SERIALISATION #########################

####################### START OKHTTP #######################
# From https://square.github.io/okhttp/features/r8_proguard/

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-adaptresourcefilenames okhttp3/internal/publicsuffix/PublicSuffixDatabase.gz

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt and other security providers are available.
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**
####################### END OKHTTP #######################

####################### START OKIO #######################
# from https://github.com/square/okio/blob/parent-3.2.0/okio/src/jvmMain/resources/META-INF/proguard/okio.pro
-dontwarn org.codehaus.mojo.animal_sniffer.*
####################### END OKIO #######################

####################### START CUSTOM #######################
-repackageclasses atakplugin.Geocoder
-dontwarn androidx.** # included in SDK
-dontwarn kotlinx.** # included in SDK
#-ignorewarnings # https://github.com/Guardsquare/proguard/issues/265
####################### END CUSTOM #######################
