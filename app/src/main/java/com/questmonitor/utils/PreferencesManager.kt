package com.questmonitor.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Extension property to create DataStore instance
 */
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "quest_monitor_prefs")

/**
 * Manager class for handling app preferences using DataStore
 */
class PreferencesManager(private val context: Context) {

    companion object {
        val THEME_KEY = stringPreferencesKey("theme")
        val REFRESH_INTERVAL_KEY = intPreferencesKey("refresh_interval")

        const val THEME_SYSTEM = "system"
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"

        const val REFRESH_1_SECOND = 1000
        const val REFRESH_2_SECONDS = 2000
        const val REFRESH_5_SECONDS = 5000
    }

    /**
     * Get theme preference as Flow
     */
    val themeFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_KEY] ?: THEME_DARK // Default to dark theme for VR comfort
    }

    /**
     * Get refresh interval preference as Flow
     */
    val refreshIntervalFlow: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[REFRESH_INTERVAL_KEY] ?: REFRESH_2_SECONDS // Default to 2 seconds
    }

    /**
     * Set theme preference
     */
    suspend fun setTheme(theme: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme
        }
    }

    /**
     * Set refresh interval preference
     */
    suspend fun setRefreshInterval(interval: Int) {
        context.dataStore.edit { preferences ->
            preferences[REFRESH_INTERVAL_KEY] = interval
        }
    }

    /**
     * Get current theme synchronously (for initial load)
     */
    suspend fun getTheme(): String {
        var theme = THEME_DARK
        context.dataStore.edit { preferences ->
            theme = preferences[THEME_KEY] ?: THEME_DARK
        }
        return theme
    }

    /**
     * Get current refresh interval synchronously (for initial load)
     */
    suspend fun getRefreshInterval(): Int {
        var interval = REFRESH_2_SECONDS
        context.dataStore.edit { preferences ->
            interval = preferences[REFRESH_INTERVAL_KEY] ?: REFRESH_2_SECONDS
        }
        return interval
    }
}
