# TAK Geocoder Plugin

![Latest Release](https://img.shields.io/github/v/release/jonapoul/tak-geocoder-plugin?style=for-the-badge)

## Summary
A small plugin for the [ATAK](https://github.com/deptofdefense/AndroidTacticalAssaultKit-CIV) Android application, which allows you to see a human-readable address in the bottom-right of the screen - just above the standard coordinate widget.

Supports geocoding from the three built-in Geocoders:
- [Android Native `Geocoder`](https://developer.android.com/reference/android/location/Geocoder)
- Public TAK Server
- MapQuest

But also adds custom geocoders:
- [what3words](https://what3words.com/pretty.needed.chill?redirect=true)

Check the [latest release page](https://github.com/jonapoul/tak-geocoder-plugin/releases/latest) to find ready-to-install APKs. Let me know if you're looking for any other specific versions.

## Build Steps
1. Clone the repository
1. Create/open `local.properties` in the root of the project, then enter the following properties:
    ```properties
    takDebugKeyFile=path/to/your/keystore.jks
    takDebugKeyFilePassword=...
    takDebugKeyAlias=...
    takDebugKeyPassword=...
    takReleaseKeyFile=path/to/your/keystore.jks
    takReleaseKeyFilePassword=...
    takReleaseKeyAlias=...
    takReleaseKeyPassword=...
    ```
1. Install the `atak.apk` ATAK installer file from within [the SDK ZIP at this link](https://github.com/deptofdefense/AndroidTacticalAssaultKit-CIV/releases/download/4.5.1.13/atak-civ-sdk-4.5.1.13.zip). This is a "development build" of the app, so it will have a red overlay at the bottom of the map to tell you so.
1. Connect your phone to your PC with USB debugging enabled
1. Open this project in a terminal and run:
    ```bash
    ./gradlew app:installCivDebug
    ```

## Optional Geocoders

This plugin comes with optional geocoding functionality for:
- what3words
- positionstack

These services all have API usage limits, which is why I'm not distributing the APK fully-built with all features :)

If you want to enable any of these, you'll need an API key and an active internet connection on your device. Open/create the file `local.properties` in the root of the project, and add a property like in the snippet below:

```properties
# Register for a key here: https://what3words.com/select-plan?referrer=/public-api
W3W_API_KEY=your-api-key-here

# Register for a key here: https://positionstack.com/signup
POSITIONSTACK_API_KEY=your-api-key-here
```

If any of these keys are missing from the file, that geocoder will not be registered in ATAK. Make sure the property value exactly matches that given by the respective service on their account page, then build the project's APK with:

```shell
./gradlew app:assembleCivDebug
```

## Known Issues

The built-in MapQuest geocoder does not work at all in ATAK proper - but this plugin will support it if it ever gets fixed. It's not a problem with this plugin!

## TODO
- Custom geocoders:
  - positionstack
  - mapquest (working)
- configurable refresh period
- configurable API key for W3W (and others)
- add caching to avoid wasting API calls
- make HTTPS configurable for PositionStack