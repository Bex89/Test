# Changelog

All notable changes to Quest Monitor will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2024-11-16

### Added
- **Dashboard** with real-time CPU, Memory, Storage, and Battery monitoring
- **Circular progress indicators** with color-coded status (good/warning/critical)
- **App Manager** to view and stop running applications
- **Storage Analyzer** with visual breakdown of space usage
- **Battery Insights** with health, temperature, and voltage information
- **System Information** screen showing device details and specifications
- **Settings** with theme switching (Light/Dark/System)
- **Configurable refresh intervals** (1s, 2s, 5s)
- **Material Design 3** UI with adaptive color scheme
- **VR-optimized interface** with large text and touch targets
- **Dark theme as default** for comfortable VR viewing
- **Quick Actions** for memory cleaning and optimization
- **Swipe-to-refresh** on all data screens
- **Bottom navigation** for easy screen switching
- **Auto-refresh** capability for live monitoring
- **DataStore** for persistent user preferences
- **MVVM architecture** with ViewModels and Repositories
- **Kotlin Coroutines** for efficient async operations

### Technical Details
- Minimum SDK: API 29 (Android 10)
- Target SDK: API 34 (Android 14)
- Language: Kotlin 1.9.20
- Build System: Gradle 8.2
- Libraries: Material Design 3, Jetpack Lifecycle, Coroutines, DataStore

### Known Issues
- Stopping system apps may require root access on some devices
- Usage Stats permission requires manual grant through Settings
- Storage categorization accuracy varies by device

## [Unreleased]

### Planned
- Historical data visualization with graphs
- Widget support for quick system stats
- Export system reports to file
- CPU temperature monitoring
- Network speed testing
- App usage statistics
- Automated optimization scheduling
- Notification alerts for critical resource usage
