# Quest Monitor

<div align="center">

**Advanced System Monitoring & Optimization Tool for Meta Quest 3**

A powerful, production-ready Android system utility designed specifically for Meta Quest 3 headsets. Quest Monitor provides real-time system resource tracking, app management, and optimization features with a beautiful Material Design 3 interface optimized for VR viewing.

</div>

## ✨ Features

### 📊 Real-Time Dashboard
- **CPU Monitoring**: Live CPU usage with core count and frequency information
- **Memory Tracking**: Real-time RAM usage with available/used metrics
- **Storage Analysis**: Internal storage usage with visual breakdown
- **Battery Status**: Battery level, temperature, health, and charging status
- **Quick Actions**: One-tap memory cleaning and system optimization

### 📱 App Manager
- View all installed applications (user and system)
- Filter by running apps, user apps, or system apps
- Stop background apps to free up resources
- Memory usage per application
- Beautiful Material Design cards with app icons

### 💾 Storage Analyzer
- Visual storage usage with circular progress indicator
- Total, used, and free space metrics
- Categorized breakdown (apps, media, documents, cache)
- Swipe-to-refresh for instant updates

### 🔋 Battery Insights
- Large, VR-friendly battery percentage display
- Real-time temperature monitoring
- Battery health status
- Voltage and technology information
- Charging status indicator

### 🌐 Network Monitor
- WiFi connection details
- Network speed and signal strength
- IP address information
- Connection type identification

### 🖥️ System Information
- Device model and manufacturer
- Android version and API level
- Kernel version
- CPU architecture and core count
- Screen resolution and density
- System uptime

### ⚙️ Settings
- **Theme Switching**: Light, Dark, or System default themes
- **Refresh Intervals**: Configurable update rates (1s, 2s, 5s)
- **About Dialog**: Comprehensive app information with version and features
- **Instant Theme Application**: Theme changes apply immediately

## 🎨 Design Highlights

- **Material Design 3**: Modern, polished UI following Material You guidelines
- **VR-Optimized**: Large text, high contrast, and touch-friendly controls perfect for headset use
- **Dark Theme Default**: Easier on eyes in VR environments
- **Smooth Animations**: 60 FPS performance with fluid fragment transitions
- **Splash Screen**: Professional app launch experience with animated icon
- **Landscape Orientation**: Optimized for Meta Quest 3 viewing
- **7 Dedicated Screens**: Dashboard, Apps, Storage, Battery, Network, System, Settings
- **Bottom Navigation**: Quick access to all monitoring features

## 🛠️ Technical Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **Minimum SDK**: API 29 (Android 10)
- **Target SDK**: API 34 (Android 14)
- **UI Framework**: Material Design 3, ViewBinding
- **Concurrency**: Kotlin Coroutines + Flow
- **Data Persistence**: DataStore Preferences
- **Lifecycle**: Android Jetpack Lifecycle components

## 📦 Installation

### Prerequisites
- Meta Quest 3 headset with Developer Mode enabled
- ADB (Android Debug Bridge) installed on your computer

### Steps

1. **Download the APK**
   ```bash
   # Download the latest release APK from GitHub Releases
   wget https://github.com/[username]/quest-monitor-android/releases/download/v1.0.0/QuestMonitor-v1.0.0.apk
   ```

2. **Connect Your Quest 3**
   ```bash
   # Connect Quest 3 via USB and verify connection
   adb devices
   ```

3. **Install the APK**
   ```bash
   # Install Quest Monitor
   adb install QuestMonitor-v1.0.0.apk
   ```

4. **Launch the App**
   - Open "Quest Monitor" from your Apps library on the headset
   - Grant necessary permissions when prompted
   - Enjoy real-time system monitoring!

## 🔐 Permissions

Quest Monitor requires the following permissions for full functionality:

| Permission | Purpose |
|------------|---------|
| `ACCESS_NETWORK_STATE` | Monitor network connectivity status |
| `ACCESS_WIFI_STATE` | Read WiFi connection details |
| `INTERNET` | Check network connectivity |
| `BATTERY_STATS` | Read battery information |
| `PACKAGE_USAGE_STATS` | Track app usage and running apps |
| `QUERY_ALL_PACKAGES` | List installed applications |

**Note**: Usage Stats permission must be granted manually through Android Settings.

## 🚀 Building from Source

### Requirements
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17
- Android SDK with API 34
- Gradle 8.2+

### Build Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/[username]/quest-monitor-android.git
   cd quest-monitor-android
   ```

2. **Open in Android Studio**
   - File → Open → Select the project directory
   - Sync Gradle files when prompted

3. **Build Debug APK**
   ```bash
   ./gradlew assembleDebug
   ```

4. **Build Release APK**
   ```bash
   ./gradlew assembleRelease
   ```

   Output: `app/build/outputs/apk/release/app-release.apk`

## 📱 Screenshots

### Dashboard
Real-time monitoring of CPU, Memory, Storage, and Battery with quick action buttons.

### App Manager
Comprehensive list of running apps with the ability to stop resource-hungry applications.

### Storage & Battery
Detailed breakdowns with large, VR-friendly circular progress indicators.

### Settings
Theme customization and refresh rate configuration.

## 🎯 Key Features for VR

- **Large Touch Targets**: All buttons and controls are 56dp minimum for easy VR interaction
- **High Contrast UI**: Clear visual hierarchy with Material Design 3 colors
- **Readable Typography**: Text sizes optimized for viewing distance in VR (18sp+ body text)
- **Landscape Layout**: Default orientation matches Quest 3 usage
- **Dark Theme**: Reduces eye strain during extended VR sessions
- **Smooth Performance**: Optimized for 60+ FPS with efficient resource usage

## 🔄 Auto-Refresh

The dashboard automatically refreshes every 2 seconds (configurable in settings) to provide real-time system metrics. This can be changed to:
- 1 second for ultra-responsive monitoring
- 2 seconds (default) for balanced performance
- 5 seconds for battery conservation

## 🛡️ Privacy & Security

- **No Internet Required**: All monitoring is done locally on device
- **No Data Collection**: Quest Monitor does not collect or transmit any user data
- **Open Source**: Full source code available for review
- **Minimal Permissions**: Only requests permissions necessary for functionality

## 🐛 Known Limitations

1. **App Stopping**: Stopping system apps may require root access
2. **Usage Stats**: Requires manual permission grant through Settings
3. **Storage Breakdown**: Detailed categorization may not be available on all devices
4. **Network Monitoring**: Some network features require Android 10+

## 🗺️ Roadmap

- [ ] Widget support for quick stats on home screen
- [ ] Historical data graphs and trends
- [ ] Export system reports
- [ ] CPU temperature monitoring (device-dependent)
- [ ] Network speed testing
- [ ] App usage statistics and screen time
- [ ] Notification for high resource usage
- [ ] Automated optimization scheduling

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Development Guidelines

1. Follow Kotlin coding conventions
2. Use meaningful variable and function names
3. Add comments for complex logic
4. Test on Meta Quest 3 when possible
5. Maintain Material Design 3 guidelines

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Material Design 3 guidelines by Google
- Meta Quest development community
- Android Jetpack libraries
- Kotlin Coroutines team

## 📞 Contact

For issues, questions, or suggestions:
- **GitHub Issues**: [Report a bug](https://github.com/[username]/quest-monitor-android/issues)
- **Discussions**: [Join the conversation](https://github.com/[username]/quest-monitor-android/discussions)

## ⭐ Star History

If you find Quest Monitor useful, please consider giving it a star! ⭐

---

**Built with ❤️ for the Meta Quest 3 community**

*Quest Monitor - Keep your VR experience running smoothly*
