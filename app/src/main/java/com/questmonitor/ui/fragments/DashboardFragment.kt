package com.questmonitor.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.questmonitor.R
import com.questmonitor.ui.views.CircularProgressView
import com.questmonitor.viewmodels.DashboardViewModel

/**
 * Dashboard fragment showing system overview
 */
class DashboardFragment : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var cpuProgress: CircularProgressView
    private lateinit var memoryProgress: CircularProgressView
    private lateinit var storageProgress: CircularProgressView
    private lateinit var batteryProgress: CircularProgressView

    private lateinit var cpuCores: TextView
    private lateinit var memoryInfo: TextView
    private lateinit var storageInfo: TextView
    private lateinit var batteryTemp: TextView

    private lateinit var btnCleanMemory: MaterialButton
    private lateinit var btnOptimize: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DashboardViewModel::class.java]

        initViews(view)
        setupObservers()
        setupListeners()

        // Start auto-refresh
        viewModel.startAutoRefresh(2000L)
    }

    private fun initViews(view: View) {
        swipeRefresh = view.findViewById(R.id.swipe_refresh)

        cpuProgress = view.findViewById(R.id.cpu_progress)
        memoryProgress = view.findViewById(R.id.memory_progress)
        storageProgress = view.findViewById(R.id.storage_progress)
        batteryProgress = view.findViewById(R.id.battery_progress)

        cpuCores = view.findViewById(R.id.cpu_cores)
        memoryInfo = view.findViewById(R.id.memory_info)
        storageInfo = view.findViewById(R.id.storage_info)
        batteryTemp = view.findViewById(R.id.battery_temp)

        btnCleanMemory = view.findViewById(R.id.btn_clean_memory)
        btnOptimize = view.findViewById(R.id.btn_optimize)
    }

    private fun setupObservers() {
        viewModel.cpuInfo.observe(viewLifecycleOwner) { cpu ->
            cpuProgress.setProgress(cpu.usagePercent, cpu.getUsageStatus())
            cpuCores.text = "${cpu.coreCount} Cores"
        }

        viewModel.memoryInfo.observe(viewLifecycleOwner) { memory ->
            memoryProgress.setProgress(memory.getUsagePercent(), memory.getUsageStatus())
            memoryInfo.text = "${memory.getFormattedUsed()} / ${memory.getFormattedTotal()}"
        }

        viewModel.storageInfo.observe(viewLifecycleOwner) { storage ->
            storageProgress.setProgress(storage.getUsagePercent(), storage.getUsageStatus())
            storageInfo.text = "${storage.getFormattedUsed()} / ${storage.getFormattedTotal()}"
        }

        viewModel.batteryInfo.observe(viewLifecycleOwner) { battery ->
            batteryProgress.setProgress(battery.getPercent().toFloat(), battery.getLevelStatus())
            batteryTemp.text = battery.getFormattedTemperature()
        }

        viewModel.isRefreshing.observe(viewLifecycleOwner) { isRefreshing ->
            swipeRefresh.isRefreshing = isRefreshing
        }

        viewModel.cleanMemoryResult.observe(viewLifecycleOwner) { success ->
            val message = if (success) {
                getString(R.string.msg_memory_cleaned)
            } else {
                "Failed to clean memory"
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupListeners() {
        swipeRefresh.setOnRefreshListener {
            viewModel.refreshAll()
        }

        btnCleanMemory.setOnClickListener {
            viewModel.cleanMemory()
            Toast.makeText(requireContext(), "Cleaning memory...", Toast.LENGTH_SHORT).show()
        }

        btnOptimize.setOnClickListener {
            viewModel.cleanMemory()
            Toast.makeText(requireContext(), getString(R.string.msg_optimization_complete), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.stopAutoRefresh()
    }
}
