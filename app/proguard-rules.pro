################################################################################################
################################################################################################
## System Section
################################################################################################
################################################################################################

-dontskipnonpubliclibraryclasses
-dontshrink
-dontoptimize

############### ACRA specifics
# we need line numbers in our stack traces otherwise they are pretty useless
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

#-applymapping <atak.proguard.mapping>

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

-keep class * extends transapps.maps.plugin.tool.Tool {
}
-keep class * implements transapps.maps.plugin.lifecycle.Lifecycle {
}

# overcome an existing bug in the gradle subsystem (3.5.x)
-keep class module-info


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

-repackageclasses atakplugin.Geocoder
