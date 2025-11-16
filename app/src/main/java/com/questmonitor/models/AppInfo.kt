package com.questmonitor.models

import android.graphics.drawable.Drawable

/**
 * Data class representing application information
 */
data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable?,
    val versionName: String = "",
    val versionCode: Long = 0,
    val isSystemApp: Boolean = false,
    val isRunning: Boolean = false,
    val memoryUsage: Long = 0,
    val installTime: Long = 0,
    val lastUpdateTime: Long = 0
) {
    /**
     * Format memory usage to human readable format
     */
    fun getFormattedMemoryUsage(): String {
        val mb = memoryUsage / (1024.0 * 1024.0)
        return if (mb >= 1.0) {
            String.format("%.1f MB", mb)
        } else {
            String.format("%.1f KB", memoryUsage / 1024.0)
        }
    }

    /**
     * Get app type label
     */
    fun getAppTypeLabel(): String {
        return if (isSystemApp) "System" else "User"
    }
}

/**
 * Data class for system information
 */
data class SystemInfo(
    val deviceName: String = "",
    val manufacturer: String = "",
    val model: String = "",
    val androidVersion: String = "",
    val apiLevel: Int = 0,
    val buildNumber: String = "",
    val securityPatch: String = "",
    val kernelVersion: String = "",
    val processor: String = "",
    val cpuCores: Int = 0,
    val totalRam: Long = 0,
    val screenResolution: String = "",
    val screenDensity: Int = 0,
    val uptime: Long = 0
) {
    /**
     * Format RAM to human readable format
     */
    fun getFormattedRam(): String {
        val gb = totalRam / (1024.0 * 1024.0 * 1024.0)
        return String.format("%.2f GB", gb)
    }

    /**
     * Format uptime to human readable format
     */
    fun getFormattedUptime(): String {
        val hours = uptime / (1000 * 60 * 60)
        val minutes = (uptime % (1000 * 60 * 60)) / (1000 * 60)
        return String.format("%dh %dm", hours, minutes)
    }
}
