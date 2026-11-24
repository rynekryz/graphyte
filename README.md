<div align="center">

<!-- decor:top -->
<picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sky-banner-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sky-banner-light.svg" width="100%" alt="night sky"></picture>

<picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-dots-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-dots-light.svg" width="100%" alt=""></picture>
<!-- /decor:top -->

<img src="icon.svg" width="160" height="160" style="display: block; margin: 0 auto"/>
<h1>Graphyte</h1>
<p>Open-source maps for Android, powered by OpenStreetMap.</p>
<p>Search places, get turn-by-turn directions, and find nearby spots, with no API keys, accounts, or tracking.</p>
</div>
<p align="center">
  <a href="https://github.com/rynekryz/graphyte/stargazers"><img src="assets/badges/stars.svg" alt="GitHub stars" height="30"/></a>
  <a href="https://github.com/rynekryz/graphyte/tags"><img src="assets/badges/release.svg" alt="Latest release" height="30"/></a>
  <a href="LICENSE"><img src="assets/badges/licencegplv3.svg" alt="Licence GPLv3"/></a>
  <img src="assets/badges/android.svg" alt="Android"/>
  <img src="assets/badges/kotlin.svg" alt="Kotlin"/>
</p>

<p align="center">
  <a href="https://github.com/rynekryz/graphyte/releases"><img src="assets/badges/getgithub.svg" alt="Get it on GitHub" height="56"/></a>
</p>

<div align="center">
<picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-sparkles-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-sparkles-light.svg" width="100%" alt=""></picture>
</div>

## Features

- Offline-friendly vector maps via MapLibre
- Place search from Photon, reverse geocoding from Nominatim
- Nearby food, fuel, ATMs, transit and more through Overpass
- Turn-by-turn directions with maneuver icons and a step list via OSRM
- Driving, walking, or cycling
- Saved places stay on your device only
- Opens `geo:` links from other apps
- Search results that know where you are
- On-device voice guidance, so it can still tell you where to go when you're offline
- A profile button in the search bar that opens settings, Google Maps style
- Voice, distance units (auto/km/miles), theme, and one-tap history clearing
- Profile photo picked from your gallery and saved locally only
- A proper `User-Agent`, so we stay polite to the OSM tools
- Material 3 with adaptive navigation, dark and light
- Minimum Android 12 (API 31), targeting 37
- No API keys, no accounts, no analytics, no tracking
- etc.

<div align="center">
<picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-wave-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-wave-light.svg" width="100%" alt=""></picture>
</div>

## Technical Stack

- **UI:** Jetpack Compose with Material 3
- **Maps:** MapLibre
- **Network:** OkHttp
- **Services:** Photon, Nominatim, Overpass and OSRM
- **Language:** Kotlin
- **Build:** Gradle (Kotlin DSL)

<div align="center">
<picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-dots-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-dots-light.svg" width="100%" alt=""></picture>
</div>

## Services

| Service | What it does |
| --- | --- |
| [Photon](https://photon.komoot.io/) | Place search |
| [Nominatim](https://nominatim.openstreetmap.org/) | Reverse geocoding |
| [Overpass API](https://overpass-api.de/) | Nearby POIs |
| [OSRM](https://routing.openstreetmap.de/) | Routing |
| [MapLibre](https://maplibre.org/) | Map rendering |

All free, public, and key-free, which is what keeps Graphyte privacy-friendly by design.

<div align="center">
<picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-sparkles-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-sparkles-light.svg" width="100%" alt=""></picture>
</div>

## Building

You'll need Android SDK 37+ (or Android Studio) and Java 17+.

### In Android Studio

1. Clone the repo and open it in Android Studio.
2. Connect a device or start an emulator, then hit **Run**.

### From the command line

```bash
git clone https://github.com/rynekryz/graphyte.git
cd graphyte
./gradlew build
```

APK: `app/build/outputs/apk/debug/app-debug.apk`

### sdkmanager only (no Android Studio)

```bash
sdkmanager "platform-tools" "platforms;android-37.0" "build-tools;37.0.0"
echo "sdk.dir=$HOME/Android/Sdk" > local.properties
./gradlew build
```

<div align="center">
<picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-wave-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-wave-light.svg" width="100%" alt=""></picture>
</div>

## Project Structure

```
Graphyte/
└── app/src/main/kotlin/com/rynekryz/graphyte/
    ├── MainActivity.kt
    ├── data/
    ├── ui/
    └── util/
```

<div align="center">
<picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-dots-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-dots-light.svg" width="100%" alt=""></picture>
</div>

## License

Licensed under GPL-3.0. See [LICENSE](LICENSE).

<!-- decor:bottom -->
<div align="center">

<picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-wave-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/divider-wave-light.svg" width="100%" alt=""></picture>

<picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-heart-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-heart-light.svg" width="60" alt="heart"></picture> <picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-sparkle-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-sparkle-light.svg" width="60" alt="sparkle"></picture> <picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-moon-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-moon-light.svg" width="60" alt="moon"></picture> <picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-flower-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-flower-light.svg" width="60" alt="flower"></picture> <picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-cat-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-cat-light.svg" width="60" alt="cat"></picture> <picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-ghost-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-ghost-light.svg" width="60" alt="ghost"></picture> <picture><source media="(prefers-color-scheme: dark)" srcset="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-lock-dark.svg"><img src="https://raw.githubusercontent.com/rynekryz/rynekryz/main/pixel-art/sticker-lock-light.svg" width="60" alt="lock"></picture>

</div>
<!-- /decor:bottom -->

<div align="center">
<strong>Made with ♥︎ by Ryne</strong>
</div>
<div align="center">
  <a href="https://discord.gg/YKhWXXhKmC"><img src="assets/badges/discord.svg" alt="Discord"/></a>
  <a href="https://tiktok.com/@rynekryz"><img src="assets/badges/tiktok.svg" alt="TikTok"/></a>
</div>
