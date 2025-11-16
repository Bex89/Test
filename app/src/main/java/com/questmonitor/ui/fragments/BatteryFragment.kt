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
import com.questmonitor.ui.views.CircularProgressView
import com.questmonitor.viewmodels.BatteryViewModel

class BatteryFragment : Fragment() {

    private lateinit var viewModel: BatteryViewModel
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var batteryProgress: CircularProgressView
    private lateinit var batteryStatus: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_battery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[BatteryViewModel::class.java]

        initViews(view)
        setupObservers()
        setupListeners()
    }

    private fun initViews(view: View) {
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        batteryProgress = view.findViewById(R.id.battery_progress)
        batteryStatus = view.findViewById(R.id.battery_status)
    }

    private fun setupObservers() {
        viewModel.batteryInfo.observe(viewLifecycleOwner) { battery ->
            batteryProgress.setProgress(battery.getPercent().toFloat(), battery.getLevelStatus())
            batteryStatus.text = "${battery.status.displayName} • ${battery.getFormattedTemperature()}"

            // Update detail rows
            view?.findViewById<View>(R.id.row_health)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.battery_health)
                row.findViewById<TextView>(R.id.value)?.text = battery.health.displayName
            }

            view?.findViewById<View>(R.id.row_temperature)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.battery_temperature)
                row.findViewById<TextView>(R.id.value)?.text = battery.getFormattedTemperature()
            }

            view?.findViewById<View>(R.id.row_voltage)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.battery_voltage)
                row.findViewById<TextView>(R.id.value)?.text = battery.getFormattedVoltage()
            }

            view?.findViewById<View>(R.id.row_technology)?.let { row ->
                row.findViewById<TextView>(R.id.label)?.text = getString(R.string.battery_technology)
                row.findViewById<TextView>(R.id.value)?.text = battery.technology
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
