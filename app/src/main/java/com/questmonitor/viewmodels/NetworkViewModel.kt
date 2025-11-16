package com.questmonitor.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.questmonitor.models.NetworkInfo
import com.questmonitor.repositories.SystemRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for Network screen
 */
class NetworkViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SystemRepository(application)

    private val _networkInfo = MutableLiveData<NetworkInfo>()
    val networkInfo: LiveData<NetworkInfo> = _networkInfo

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _networkInfo.value = repository.getNetworkInfo()
            _isRefreshing.value = false
        }
    }
}
