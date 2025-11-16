package com.questmonitor.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.questmonitor.BuildConfig
import com.questmonitor.R
import com.questmonitor.utils.PreferencesManager
import com.questmonitor.viewmodels.SettingsViewModel

class SettingsFragment : Fragment() {

    private lateinit var viewModel: SettingsViewModel
    private lateinit var radioTheme: RadioGroup
    private lateinit var radioRefresh: RadioGroup
    private lateinit var btnAbout: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        initViews(view)
        setupObservers()
        setupListeners()
    }

    private fun initViews(view: View) {
        radioTheme = view.findViewById(R.id.radio_theme)
        radioRefresh = view.findViewById(R.id.radio_refresh)
        btnAbout = view.findViewById(R.id.btn_about)
    }

    private fun setupObservers() {
        viewModel.theme.observe(viewLifecycleOwner) { theme ->
            when (theme) {
                PreferencesManager.THEME_LIGHT -> radioTheme.check(R.id.radio_light)
                PreferencesManager.THEME_DARK -> radioTheme.check(R.id.radio_dark)
                else -> radioTheme.check(R.id.radio_system)
            }
        }

        viewModel.refreshInterval.observe(viewLifecycleOwner) { interval ->
            when (interval) {
                PreferencesManager.REFRESH_1_SECOND -> radioRefresh.check(R.id.radio_1s)
                PreferencesManager.REFRESH_2_SECONDS -> radioRefresh.check(R.id.radio_2s)
                PreferencesManager.REFRESH_5_SECONDS -> radioRefresh.check(R.id.radio_5s)
            }
        }
    }

    private fun setupListeners() {
        radioTheme.setOnCheckedChangeListener { _, checkedId ->
            val theme = when (checkedId) {
                R.id.radio_light -> PreferencesManager.THEME_LIGHT
                R.id.radio_dark -> PreferencesManager.THEME_DARK
                else -> PreferencesManager.THEME_SYSTEM
            }
            viewModel.setTheme(theme)

            // Apply theme immediately
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

        radioRefresh.setOnCheckedChangeListener { _, checkedId ->
            val interval = when (checkedId) {
                R.id.radio_1s -> PreferencesManager.REFRESH_1_SECOND
                R.id.radio_5s -> PreferencesManager.REFRESH_5_SECONDS
                else -> PreferencesManager.REFRESH_2_SECONDS
            }
            viewModel.setRefreshInterval(interval)
        }

        btnAbout.setOnClickListener {
            showAboutDialog()
        }
    }

    /**
     * Show about dialog with app information
     */
    private fun showAboutDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_about, null)

        // Set version from BuildConfig
        val versionText = dialogView.findViewById<TextView>(R.id.version_text)
        versionText.text = "Version ${BuildConfig.VERSION_NAME}"

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        dialogView.findViewById<MaterialButton>(R.id.btn_close).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
