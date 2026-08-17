# DIVO777 — ALTOMEDIA Release Package

**DIVO777** is a free-to-play classic slot-machine game for Android, built native
with Kotlin and Jetpack Compose. This folder contains everything needed to publish
the app on the Google Play Console.

- **Package:** `com.altomedia.divo777`
- **Version:** 1.0.0 (versionCode 1)
- **Min Android:** 6.0 (API 23)
- **Target Android:** 16 (API 37)
- **Compile SDK:** 36
- **Developer:** ALTOMEDIA

## Folder contents

```
ALTOMEDIA/
├── release/
│   ├── app-release.aab         # Signed App Bundle — upload this to Play Console
│   ├── app-release.apk         # Signed universal APK (for testing/sideloading)
│   ├── app-debug.apk           # Debug build for quick local testing
│   ├── ALTOMEDIA.jks           # Release keystore (KEEP SAFE, never commit publicly)
│   └── keystore.properties     # Keystore credentials (never commit publicly)
├── store-graphics/
│   ├── app_icon_512.png         # Play Store app icon (512x512)
│   ├── feature_graphic_1024x500.png
│   └── screenshot_1..5.png       # Phone screenshots (1080x1920)
├── Privacy_Policy.md            # Privacy policy (host publicly & link in Play Console)
├── Terms_of_Service.md          # Terms of service
├── Upload_and_Release_Guide.txt # Step-by-step Play Console upload instructions
├── Store_Listing_Guide.txt     # Copy-paste store listing copy + asset list
├── Release_Notes_v1.0.txt       # Release notes (EN + ID)
└── Blog_Article.md              # Promotional blog article
```

## Quick start

1. Read **Upload_and_Release_Guide.txt** for the full upload workflow.
2. Upload **release/app-release.aab** to the Play Console.
3. Use **Store_Listing_Guide.txt** + **store-graphics/** for the store listing.
4. Host **Privacy_Policy.md** on a public URL and link it in the Data safety form.
5. Use **Release_Notes_v1.0.txt** as the release notes.

## Important: keep the keystore safe

The keystore `ALTOMEDIA.jks` (alias `kdsmedia`) is the only key that can sign
updates to this app on Google Play. **If you lose it, you cannot publish
updates.** Back it up to multiple secure, offline locations. The keystore and
`keystore.properties` are excluded from git via `.gitignore` — do not commit
them to any public repository.

## Free-to-play, not gambling

DIVO777 is an entertainment app. It involves no real money, no wagers, and no
prizes of monetary value. Credits are a virtual in-game currency with no monetary
value. The app does not collect personal data, does not use ads, and does not
require an account.

---

_Developer: ALTOMEDIA · altomediaindonesia@gmail.com_
