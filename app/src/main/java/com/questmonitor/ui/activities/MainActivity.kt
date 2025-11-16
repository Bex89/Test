package com.questmonitor.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.questmonitor.R
import com.questmonitor.ui.fragments.*
import com.questmonitor.utils.PreferencesManager
import com.questmonitor.viewmodels.SettingsViewModel
import kotlinx.coroutines.launch

/**
 * Main activity that hosts all fragments
 */
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen before super.onCreate()
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // Apply theme before setContentView
        applyTheme()

        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        setupBottomNavigation()

        // Load initial fragment
        if (savedInstanceState == null) {
            loadFragment(DashboardFragment())
        }
    }

    /**
     * Apply theme from preferences
     */
    private fun applyTheme() {
        preferencesManager = PreferencesManager(this)

        lifecycleScope.launch {
            preferencesManager.themeFlow.collect { theme ->
                when (theme) {
                    PreferencesManager.THEME_LIGHT -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    PreferencesManager.THEME_DARK -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    else -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    }
                }
            }
        }
    }

    /**
     * Setup bottom navigation
     */
    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    loadFragment(DashboardFragment())
                    true
                }
                R.id.nav_apps -> {
                    loadFragment(AppsFragment())
                    true
                }
                R.id.nav_storage -> {
                    loadFragment(StorageFragment())
                    true
                }
                R.id.nav_battery -> {
                    loadFragment(BatteryFragment())
                    true
                }
                R.id.nav_network -> {
                    loadFragment(NetworkFragment())
                    true
                }
                R.id.nav_system -> {
                    loadFragment(SystemFragment())
                    true
                }
                R.id.nav_settings -> {
                    loadFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }
    }

    /**
     * Load fragment into container with slide animations
     */
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
