package com.questmonitor.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.os.SystemClock
import android.text.format.Formatter
import com.questmonitor.models.*
import java.io.File
import java.io.RandomAccessFile

/**
 * Utility class for monitoring system resources
 */
class SystemMonitor(private val context: Context) {

    private val activityManager: ActivityManager by lazy {
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    }

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val wifiManager: WifiManager by lazy {
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    /**
     * Get current CPU information
     */
    fun getCpuInfo(): CpuInfo {
        val usage = readCpuUsage()
        val coreCount = Runtime.getRuntime().availableProcessors()

        return CpuInfo(
            usagePercent = usage,
            coreCount = coreCount,
            currentFrequency = readCpuFrequency(0),
            maxFrequency = readCpuMaxFrequency(0),
            minFrequency = readCpuMinFrequency(0),
            architecture = Build.SUPPORTED_ABIS.firstOrNull() ?: "Unknown"
        )
    }

    /**
     * Read CPU usage percentage
     */
    private fun readCpuUsage(): Float {
        return try {
            val reader = RandomAccessFile("/proc/stat", "r")
            val load = reader.readLine()
            reader.close()

            val toks = load.split(" +".toRegex())
            if (toks.size < 5) return 0f

            val idle = toks[4].toLong()
            val total = toks.drop(1).take(7).sumOf { it.toLongOrNull() ?: 0 }

            if (total == 0L) return 0f

            val usage = (total - idle).toFloat() / total.toFloat() * 100f
            usage.coerceIn(0f, 100f)
        } catch (e: Exception) {
            0f
        }
    }

    /**
     * Read CPU frequency for a specific core
     */
    private fun readCpuFrequency(core: Int): Long {
        return try {
            val file = File("/sys/devices/system/cpu/cpu$core/cpufreq/scaling_cur_freq")
            if (file.exists()) {
                file.readText().trim().toLong()
            } else {
                0L
            }
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * Read CPU max frequency
     */
    private fun readCpuMaxFrequency(core: Int): Long {
        return try {
            val file = File("/sys/devices/system/cpu/cpu$core/cpufreq/cpuinfo_max_freq")
            if (file.exists()) {
                file.readText().trim().toLong()
            } else {
                0L
            }
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * Read CPU min frequency
     */
    private fun readCpuMinFrequency(core: Int): Long {
        return try {
            val file = File("/sys/devices/system/cpu/cpu$core/cpufreq/cpuinfo_min_freq")
            if (file.exists()) {
                file.readText().trim().toLong()
            } else {
                0L
            }
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * Get current memory information
     */
    fun getMemoryInfo(): MemoryInfo {
        val memInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memInfo)

        val totalMem = memInfo.totalMem
        val availMem = memInfo.availMem
        val usedMem = totalMem - availMem

        return MemoryInfo(
            totalMemory = totalMem,
            availableMemory = availMem,
            usedMemory = usedMem,
            threshold = memInfo.threshold,
            lowMemory = memInfo.lowMemory
        )
    }

    /**
     * Get current storage information
     */
    fun getStorageInfo(): StorageInfo {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)

        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong

        val totalSpace = totalBlocks * blockSize
        val freeSpace = availableBlocks * blockSize
        val usedSpace = totalSpace - freeSpace

        return StorageInfo(
            totalSpace = totalSpace,
            freeSpace = freeSpace,
            usedSpace = usedSpace,
            breakdown = StorageBreakdown()
        )
    }

    /**
     * Get current battery information
     */
    fun getBatteryInfo(): BatteryInfo {
        val batteryIntent = context.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )

        if (batteryIntent == null) {
            return BatteryInfo()
        }

        val level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        val scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
        val temperature = batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
        val voltage = batteryIntent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)
        val health = batteryIntent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN)
        val status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN)
        val plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
        val technology = batteryIntent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY) ?: "Unknown"

        return BatteryInfo(
            level = level,
            scale = scale,
            temperature = temperature,
            voltage = voltage,
            health = mapBatteryHealth(health),
            status = mapBatteryStatus(status),
            plugged = plugged > 0,
            technology = technology
        )
    }

    /**
     * Map battery health integer to enum
     */
    private fun mapBatteryHealth(health: Int): BatteryHealth {
        return when (health) {
            BatteryManager.BATTERY_HEALTH_GOOD -> BatteryHealth.GOOD
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> BatteryHealth.OVERHEAT
            BatteryManager.BATTERY_HEALTH_DEAD -> BatteryHealth.DEAD
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> BatteryHealth.OVER_VOLTAGE
            BatteryManager.BATTERY_HEALTH_COLD -> BatteryHealth.COLD
            else -> BatteryHealth.UNKNOWN
        }
    }

    /**
     * Map battery status integer to enum
     */
    private fun mapBatteryStatus(status: Int): BatteryStatus {
        return when (status) {
            BatteryManager.BATTERY_STATUS_CHARGING -> BatteryStatus.CHARGING
            BatteryManager.BATTERY_STATUS_DISCHARGING -> BatteryStatus.DISCHARGING
            BatteryManager.BATTERY_STATUS_FULL -> BatteryStatus.FULL
            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> BatteryStatus.NOT_CHARGING
            else -> BatteryStatus.UNKNOWN
        }
    }

    /**
     * Get current network information
     */
    fun getNetworkInfo(): NetworkInfo {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        if (capabilities == null) {
            return NetworkInfo(isConnected = false)
        }

        val connectionType = when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectionType.WIFI
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectionType.MOBILE
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectionType.ETHERNET
            else -> ConnectionType.NONE
        }

        val wifiInfo = if (connectionType == ConnectionType.WIFI) {
            getWifiInfo()
        } else null

        return NetworkInfo(
            isConnected = true,
            connectionType = connectionType,
            wifiInfo = wifiInfo
        )
    }

    /**
     * Get WiFi specific information
     */
    private fun getWifiInfo(): WifiInfo? {
        return try {
            val info = wifiManager.connectionInfo
            val ssid = info.ssid.removeSurrounding("\"")
            val ipAddress = Formatter.formatIpAddress(info.ipAddress)

            WifiInfo(
                ssid = ssid,
                bssid = info.bssid ?: "",
                ipAddress = ipAddress,
                linkSpeed = info.linkSpeed,
                frequency = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    info.frequency
                } else {
                    0
                },
                rssi = info.rssi,
                signalLevel = WifiManager.calculateSignalLevel(info.rssi, 5)
            )
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Get system information
     */
    fun getSystemInfo(): SystemInfo {
        val memInfo = getMemoryInfo()
        val uptime = SystemClock.elapsedRealtime()

        return SystemInfo(
            deviceName = "${Build.MANUFACTURER} ${Build.MODEL}",
            manufacturer = Build.MANUFACTURER,
            model = Build.MODEL,
            androidVersion = Build.VERSION.RELEASE,
            apiLevel = Build.VERSION.SDK_INT,
            buildNumber = Build.DISPLAY,
            securityPatch = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Build.VERSION.SECURITY_PATCH
            } else {
                "N/A"
            },
            kernelVersion = System.getProperty("os.version") ?: "Unknown",
            processor = Build.HARDWARE,
            cpuCores = Runtime.getRuntime().availableProcessors(),
            totalRam = memInfo.totalMemory,
            screenResolution = "${context.resources.displayMetrics.widthPixels}x${context.resources.displayMetrics.heightPixels}",
            screenDensity = context.resources.displayMetrics.densityDpi,
            uptime = uptime
        )
    }
}
