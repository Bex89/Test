package com.questmonitor.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.questmonitor.models.BatteryInfo
import com.questmonitor.repositories.SystemRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for Battery screen
 */
class BatteryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SystemRepository(application)

    private val _batteryInfo = MutableLiveData<BatteryInfo>()
    val batteryInfo: LiveData<BatteryInfo> = _batteryInfo

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _batteryInfo.value = repository.getBatteryInfo()
            _isRefreshing.value = false
        }
    }
}
