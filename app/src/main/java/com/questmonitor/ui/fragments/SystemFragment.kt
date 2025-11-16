package com.questmonitor.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.questmonitor.R
import com.questmonitor.viewmodels.SystemViewModel

/**
 * Fragment for displaying system information
 */
class SystemFragment : Fragment() {

    private lateinit var viewModel: SystemViewModel
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_system, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SystemViewModel::class.java]

        initViews(view)
        setupObservers()
        setupListeners()
    }

    private fun initViews(view: View) {
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
    }

    private fun setupObservers() {
        viewModel.systemInfo.observe(viewLifecycleOwner) { system ->
            // Device Information
            view?.findViewById<View>(R.id.row_device_name)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = "Device"
                row.findViewById<TextView>(R.id.value)?.text = system.deviceName
            }

            view?.findViewById<View>(R.id.row_manufacturer)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_manufacturer)
                row.findViewById<TextView>(R.id.value)?.text = system.manufacturer
            }

            view?.findViewById<View>(R.id.row_model)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_model)
                row.findViewById<TextView>(R.id.value)?.text = system.model
            }

            // Android Information
            view?.findViewById<View>(R.id.row_android_version)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_android_version)
                row.findViewById<TextView>(R.id.value)?.text = system.androidVersion
            }

            view?.findViewById<View>(R.id.row_api_level)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_api_level)
                row.findViewById<TextView>(R.id.value)?.text = system.apiLevel.toString()
            }

            view?.findViewById<View>(R.id.row_build_number)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_build_number)
                row.findViewById<TextView>(R.id.value)?.text = system.buildNumber
            }

            view?.findViewById<View>(R.id.row_security_patch)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_security_patch)
                row.findViewById<TextView>(R.id.value)?.text = system.securityPatch
            }

            view?.findViewById<View>(R.id.row_kernel)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_kernel)
                row.findViewById<TextView>(R.id.value)?.text = system.kernelVersion
            }

            // Hardware Information
            view?.findViewById<View>(R.id.row_processor)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_processor)
                row.findViewById<TextView>(R.id.value)?.text = system.processor
            }

            view?.findViewById<View>(R.id.row_cores)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_cores)
                row.findViewById<TextView>(R.id.value)?.text = system.cpuCores.toString()
            }

            view?.findViewById<View>(R.id.row_ram)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_ram)
                row.findViewById<TextView>(R.id.value)?.text = system.getFormattedRam()
            }

            // Display Information
            view?.findViewById<View>(R.id.row_resolution)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_resolution)
                row.findViewById<TextView>(R.id.value)?.text = system.screenResolution
            }

            view?.findViewById<View>(R.id.row_density)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_density)
                row.findViewById<TextView>(R.id.value)?.text = "${system.screenDensity} dpi"
            }

            // System Status
            view?.findViewById<View>(R.id.row_uptime)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.system_uptime)
                row.findViewById<TextView>(R.id.value)?.text = system.getFormattedUptime()
            }
        }

        viewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
            swipeRefresh.isRefreshing = isRefreshing
        }
    }

    private fun setupListeners() {
        swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}
