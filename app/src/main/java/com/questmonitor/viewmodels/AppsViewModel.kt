package com.questmonitor.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.questmonitor.models.AppInfo
import com.questmonitor.repositories.SystemRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for Apps screen
 * Manages application list and operations
 */
class AppsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SystemRepository(application)

    private val _apps = MutableLiveData<List<AppInfo>>()
    val apps: LiveData<List<AppInfo>> = _apps

    private val _runningApps = MutableLiveData<List<AppInfo>>()
    val runningApps: LiveData<List<AppInfo>> = _runningApps

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _filterType = MutableLiveData(FilterType.ALL)
    val filterType: LiveData<FilterType> = _filterType

    init {
        loadApps()
    }

    /**
     * Load all applications
     */
    fun loadApps() {
        viewModelScope.launch {
            _isLoading.value = true

            when (_filterType.value) {
                FilterType.ALL -> _apps.value = repository.getAllApps()
                FilterType.RUNNING -> _apps.value = repository.getRunningApps()
                FilterType.USER -> _apps.value = repository.getUserApps()
                FilterType.SYSTEM -> _apps.value = repository.getSystemApps()
                null -> _apps.value = repository.getAllApps()
            }

            _runningApps.value = repository.getRunningApps()
            _isLoading.value = false
        }
    }

    /**
     * Set filter type and reload apps
     */
    fun setFilter(filter: FilterType) {
        _filterType.value = filter
        loadApps()
    }

    /**
     * Stop a specific app
     */
    fun stopApp(packageName: String) {
        viewModelScope.launch {
            repository.stopApp(packageName)
            // Reload apps after stopping
            loadApps()
        }
    }

    /**
     * Refresh app list
     */
    fun refresh() {
        loadApps()
    }
}

enum class FilterType {
    ALL, RUNNING, USER, SYSTEM
}
