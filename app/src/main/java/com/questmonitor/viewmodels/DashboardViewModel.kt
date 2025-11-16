package com.questmonitor.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.questmonitor.models.*
import com.questmonitor.repositories.SystemRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel for Dashboard screen
 * Manages system monitoring data and periodic updates
 */
class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SystemRepository(application)

    // LiveData for UI observations
    private val _cpuInfo = MutableLiveData<CpuInfo>()
    val cpuInfo: LiveData<CpuInfo> = _cpuInfo

    private val _memoryInfo = MutableLiveData<MemoryInfo>()
    val memoryInfo: LiveData<MemoryInfo> = _memoryInfo

    private val _storageInfo = MutableLiveData<StorageInfo>()
    val storageInfo: LiveData<StorageInfo> = _storageInfo

    private val _batteryInfo = MutableLiveData<BatteryInfo>()
    val batteryInfo: LiveData<BatteryInfo> = _batteryInfo

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private val _cleanMemoryResult = MutableLiveData<Boolean>()
    val cleanMemoryResult: LiveData<Boolean> = _cleanMemoryResult

    private var isAutoRefreshEnabled = false
    private var refreshInterval = 2000L // 2 seconds default

    init {
        refreshAll()
    }

    /**
     * Refresh all system information
     */
    fun refreshAll() {
        viewModelScope.launch {
            _isRefreshing.value = true

            // Run all refreshes in parallel
            launch { _cpuInfo.value = repository.getCpuInfo() }
            launch { _memoryInfo.value = repository.getMemoryInfo() }
            launch { _storageInfo.value = repository.getStorageInfo() }
            launch { _batteryInfo.value = repository.getBatteryInfo() }

            _isRefreshing.value = false
        }
    }

    /**
     * Clean memory by stopping background apps
     */
    fun cleanMemory() {
        viewModelScope.launch {
            val result = repository.cleanMemory()
            _cleanMemoryResult.value = result

            // Refresh memory info after cleaning
            delay(500)
            _memoryInfo.value = repository.getMemoryInfo()
        }
    }

    /**
     * Start auto-refresh with specified interval
     */
    fun startAutoRefresh(intervalMs: Long = refreshInterval) {
        refreshInterval = intervalMs
        isAutoRefreshEnabled = true

        viewModelScope.launch {
            while (isAutoRefreshEnabled) {
                refreshAll()
                delay(refreshInterval)
            }
        }
    }

    /**
     * Stop auto-refresh
     */
    fun stopAutoRefresh() {
        isAutoRefreshEnabled = false
    }

    override fun onCleared() {
        super.onCleared()
        stopAutoRefresh()
    }
}
