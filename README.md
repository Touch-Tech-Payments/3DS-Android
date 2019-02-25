![Touchtech Payments](samples/TTP_logo.png)

# 3DS-Android

Touchtech 3DS Android SDK supports Android API 16 and above.

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
compile ('tech.touch:3ds-android:1.0.10')
```

## Dependency graph

```
+--- io.reactivex:rxandroid:1.2.1
+--- io.reactivex:rxjava:1.3.8
+--- com.squareup.okhttp3:okhttp:3.12.1
+--- com.google.code.gson:gson:2.8.5
\--- com.android.support:appcompat-v7:25.3.1
```

## Permissions

Touchtech 3DS Android SDK requires the following permissions:
```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```
