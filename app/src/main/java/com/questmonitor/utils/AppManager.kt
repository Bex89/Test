package com.questmonitor.utils

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import com.questmonitor.models.AppInfo

/**
 * Utility class for managing applications
 */
class AppManager(private val context: Context) {

    private val packageManager: PackageManager = context.packageManager
    private val activityManager: ActivityManager by lazy {
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    }

    /**
     * Get list of all installed applications
     */
    fun getAllApps(): List<AppInfo> {
        val packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        val runningApps = getRunningAppPackages()

        return packages.map { packageInfo ->
            val appInfo = packageInfo.applicationInfo
            val isRunning = runningApps.contains(packageInfo.packageName)

            AppInfo(
                packageName = packageInfo.packageName,
                appName = appInfo.loadLabel(packageManager).toString(),
                icon = appInfo.loadIcon(packageManager),
                versionName = packageInfo.versionName ?: "Unknown",
                versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    packageInfo.longVersionCode
                } else {
                    @Suppress("DEPRECATION")
                    packageInfo.versionCode.toLong()
                },
                isSystemApp = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0,
                isRunning = isRunning,
                memoryUsage = 0, // Will be populated separately for running apps
                installTime = packageInfo.firstInstallTime,
                lastUpdateTime = packageInfo.lastUpdateTime
            )
        }.sortedBy { it.appName.lowercase() }
    }

    /**
     * Get list of running applications
     */
    fun getRunningApps(): List<AppInfo> {
        val allApps = getAllApps()
        val runningPackages = getRunningAppPackages()

        return allApps.filter { runningPackages.contains(it.packageName) }
    }

    /**
     * Get package names of running applications
     */
    private fun getRunningAppPackages(): Set<String> {
        val runningApps = mutableSetOf<String>()

        try {
            val processes = activityManager.runningAppProcesses ?: emptyList()
            for (processInfo in processes) {
                runningApps.addAll(processInfo.pkgList)
            }
        } catch (e: Exception) {
            // Some devices may restrict access to running apps
        }

        return runningApps
    }

    /**
     * Get user-installed applications only
     */
    fun getUserApps(): List<AppInfo> {
        return getAllApps().filter { !it.isSystemApp }
    }

    /**
     * Get system applications only
     */
    fun getSystemApps(): List<AppInfo> {
        return getAllApps().filter { it.isSystemApp }
    }

    /**
     * Kill/stop an application by package name
     */
    fun stopApp(packageName: String): Boolean {
        return try {
            activityManager.killBackgroundProcesses(packageName)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get memory usage for a specific app
     */
    fun getAppMemoryUsage(packageName: String): Long {
        return try {
            val processes = activityManager.runningAppProcesses ?: return 0
            val process = processes.find { it.pkgList.contains(packageName) } ?: return 0

            val pids = intArrayOf(process.pid)
            val memoryInfo = activityManager.getProcessMemoryInfo(pids)

            if (memoryInfo.isNotEmpty()) {
                memoryInfo[0].totalPss.toLong() * 1024 // Convert KB to bytes
            } else {
                0
            }
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Open app settings for a specific package
     */
    fun openAppSettings(packageName: String) {
        try {
            val intent = android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = android.net.Uri.fromParts("package", packageName, null)
            val settingsIntent = android.content.Intent(intent).apply {
                data = uri
                flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(settingsIntent)
        } catch (e: Exception) {
            // Handle error
        }
    }
}
