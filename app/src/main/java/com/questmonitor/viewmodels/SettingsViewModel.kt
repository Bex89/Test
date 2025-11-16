package com.questmonitor.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.questmonitor.utils.PreferencesManager
import kotlinx.coroutines.launch

/**
 * ViewModel for Settings screen
 */
class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val preferencesManager = PreferencesManager(application)

    // Theme preference
    val theme: LiveData<String> = preferencesManager.themeFlow.asLiveData()

    // Refresh interval preference
    val refreshInterval: LiveData<Int> = preferencesManager.refreshIntervalFlow.asLiveData()

    /**
     * Set theme preference
     */
    fun setTheme(theme: String) {
        viewModelScope.launch {
            preferencesManager.setTheme(theme)
        }
    }

    /**
     * Set refresh interval preference
     */
    fun setRefreshInterval(interval: Int) {
        viewModelScope.launch {
            preferencesManager.setRefreshInterval(interval)
        }
    }
}
