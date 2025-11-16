package com.questmonitor.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.questmonitor.models.SystemInfo
import com.questmonitor.repositories.SystemRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for System Info screen
 */
class SystemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SystemRepository(application)

    private val _systemInfo = MutableLiveData<SystemInfo>()
    val systemInfo: LiveData<SystemInfo> = _systemInfo

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _systemInfo.value = repository.getSystemInfo()
            _isRefreshing.value = false
        }
    }
}
