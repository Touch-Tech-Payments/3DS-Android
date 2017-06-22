![Touchtech Payments](samples/TTP_logo.png)

# 3DS-Android

Touchtech 3DS Android SDK supports Android API 15 and above.

## Installation

Add the following repository:
```
repositories {
    maven {
        url "https://raw.githubusercontent.com/Touch-Tech-Payments/3DS-Android/repo"
    }
}
```

Add the following dependency:
```
compile ('tech.touch:3ds-android:1.0')
```

## Dependency graph

```
+--- io.realm:realm-android-library:3.1.4
|    +--- io.realm:realm-annotations:3.1.4
|    \--- com.getkeepsafe.relinker:relinker:1.2.2
+--- io.realm:realm-annotations:3.1.4
+--- io.reactivex:rxandroid:1.2.1
|    \--- io.reactivex:rxjava:1.1.6 -> 1.2.5
+--- io.reactivex:rxjava:1.2.5
+--- com.squareup.okhttp3:okhttp:3.5.0
|    \--- com.squareup.okio:okio:1.11.0
+--- com.google.code.gson:gson:2.8.0
\--- com.android.support:appcompat-v7:25.3.1
```

## Permissions

Touchtech 3DS Android SDK requires the following permissions:
```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```
