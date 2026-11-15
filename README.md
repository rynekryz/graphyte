<div align="center">
<img src="icon.svg" width="160" height="160" style="display: block; margin: 0 auto"/>
<h1>Graphyte</h1>
<p>Open-source maps app for Android, powered by OpenStreetMap</p>
<p>No API keys. No accounts. No tracking. Just maps.</p>
</div>
<p align="center">
  <a href="https://github.com/rynekryz/graphyte/stargazers"><img src="assets/badges/stars.svg" alt="GitHub stars" height="30"/></a>
  <a href="https://github.com/rynekryz/graphyte/tags"><img src="assets/badges/release.svg" alt="Latest release" height="30"/></a>
  <a href="LICENSE"><img src="assets/badges/licencegplv3.svg" alt="Licence GPLv3"/></a>
  <img src="assets/badges/android.svg" alt="Android"/>
  <img src="assets/badges/kotlin.svg" alt="Kotlin"/>
</p>

## Building the APK

### Requirements
- Android SDK 37+ (or Android Studio)
- Java 17+
- Git

### Build with Android Studio
1. Clone the repo and open it in Android Studio
2. Wait for Gradle sync
3. Connect an Android device or use an emulator
4. Click **Run** to build and install

### Build from command line
```bash
git clone https://github.com/rynekryz/graphyte.git
cd graphyte
./gradlew build
```
APK location: `app/build/outputs/apk/debug/app-debug.apk`

### Build with sdkmanager only (no Android Studio)
```bash
sdkmanager "platform-tools" "platforms;android-37.0" "build-tools;37.0.0"
echo "sdk.dir=$HOME/Android/Sdk" > local.properties
./gradlew build
```

## Screenshots

## Features

- Offline-friendly vector map rendering via MapLibre
- Place search powered by Photon (OpenStreetMap geocoder)
- Reverse geocoding of dropped pins via Nominatim
- Discover nearby categories (food, fuel, ATMs, transit stops, etc.) via Overpass API
- Turn-by-turn directions with maneuver icons and step list via OSRM
- Multi-mode routing (driving / walking / cycling)
- Saved places store, kept on device only
- Handles `geo:` URIs from other apps
- Location-aware search results (queries nearby coordinates)
- On-device voice guidance for turn-by-turn navigation (offline text-to-speech)
- Google Maps-style account button: tap your profile photo in the search bar to open settings
- Settings for voice guidance, distance units (auto/km/miles) and theme, plus one-tap search history clearing
- Profile photo is picked from your gallery and saved locally only
- Proper `User-Agent` header for OpenStreetMap API set of tools etiquette
- Material design 3 with adaptive navigation, dark & light mode support
- Minimum Android 12 (API 31), targeting API 37
- No API keys, no accounts, no analytics, no tracking
- etc.

## Under-the-hood services

| Service | Purpose |
| --- | --- |
| [Photon](https://photon.komoot.io/) | Place search |
| [Nominatim](https://nominatim.openstreetmap.org/) | Reverse geocoding |
| [Overpass API](https://overpass-api.de/) | Nearby POIs |
| [OSRM](https://routing.openstreetmap.de/) | Routing |
| [MapLibre](https://maplibre.org/) | Map rendering |

All free, public, and key-free, which is what keeps Graphyte privacy-respecting by design.

## License

Licensed under GPL-3.0. See [LICENSE](LICENSE) for details.

<div align="center">
<strong>Made by Ryne</strong>
</div>
<div align="center">
  <a href="https://discord.gg/YKhWXXhKmC"><img src="assets/badges/discord.svg" alt="Discord"/></a>
  <a href="https://tiktok.com/@rynekryz"><img src="assets/badges/tiktok.svg" alt="TikTok"/></a>
</div>
