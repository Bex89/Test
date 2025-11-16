package com.questmonitor.repositories

import android.content.Context
import com.questmonitor.models.*
import com.questmonitor.utils.AppManager
import com.questmonitor.utils.SystemMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository class for system information
 * Handles data operations and provides a clean API for ViewModels
 */
class SystemRepository(context: Context) {

    private val systemMonitor = SystemMonitor(context)
    private val appManager = AppManager(context)

    /**
     * Get current CPU information
     */
    suspend fun getCpuInfo(): CpuInfo = withContext(Dispatchers.IO) {
        systemMonitor.getCpuInfo()
    }

    /**
     * Get current memory information
     */
    suspend fun getMemoryInfo(): MemoryInfo = withContext(Dispatchers.IO) {
        systemMonitor.getMemoryInfo()
    }

    /**
     * Get current storage information
     */
    suspend fun getStorageInfo(): StorageInfo = withContext(Dispatchers.IO) {
        systemMonitor.getStorageInfo()
    }

    /**
     * Get current battery information
     */
    suspend fun getBatteryInfo(): BatteryInfo = withContext(Dispatchers.IO) {
        systemMonitor.getBatteryInfo()
    }

    /**
     * Get current network information
     */
    suspend fun getNetworkInfo(): NetworkInfo = withContext(Dispatchers.IO) {
        systemMonitor.getNetworkInfo()
    }

    /**
     * Get system information
     */
    suspend fun getSystemInfo(): SystemInfo = withContext(Dispatchers.IO) {
        systemMonitor.getSystemInfo()
    }

    /**
     * Get all installed applications
     */
    suspend fun getAllApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        appManager.getAllApps()
    }

    /**
     * Get running applications
     */
    suspend fun getRunningApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        appManager.getRunningApps()
    }

    /**
     * Get user-installed applications
     */
    suspend fun getUserApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        appManager.getUserApps()
    }

    /**
     * Get system applications
     */
    suspend fun getSystemApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        appManager.getSystemApps()
    }

    /**
     * Stop an application
     */
    suspend fun stopApp(packageName: String): Boolean = withContext(Dispatchers.IO) {
        appManager.stopApp(packageName)
    }

    /**
     * Get memory usage for a specific app
     */
    suspend fun getAppMemoryUsage(packageName: String): Long = withContext(Dispatchers.IO) {
        appManager.getAppMemoryUsage(packageName)
    }

    /**
     * Perform memory cleanup (kill background processes)
     */
    suspend fun cleanMemory(): Boolean = withContext(Dispatchers.IO) {
        try {
            val runningApps = appManager.getRunningApps()
            val userApps = runningApps.filter { !it.isSystemApp }

            userApps.forEach { app ->
                appManager.stopApp(app.packageName)
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
