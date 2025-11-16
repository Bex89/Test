package com.questmonitor.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.questmonitor.models.StorageInfo
import com.questmonitor.repositories.SystemRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for Storage screen
 */
class StorageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SystemRepository(application)

    private val _storageInfo = MutableLiveData<StorageInfo>()
    val storageInfo: LiveData<StorageInfo> = _storageInfo

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _storageInfo.value = repository.getStorageInfo()
            _isRefreshing.value = false
        }
    }
}
