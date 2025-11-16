package com.questmonitor.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.questmonitor.R
import com.questmonitor.models.ConnectionType
import com.questmonitor.ui.views.CircularProgressView
import com.questmonitor.viewmodels.NetworkViewModel

/**
 * Fragment for displaying network information
 */
class NetworkFragment : Fragment() {

    private lateinit var viewModel: NetworkViewModel
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var connectionStatus: TextView
    private lateinit var connectionType: TextView
    private lateinit var cardWifi: View
    private lateinit var cardSignal: View
    private lateinit var signalProgress: CircularProgressView
    private lateinit var signalQuality: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_network, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NetworkViewModel::class.java]

        initViews(view)
        setupObservers()
        setupListeners()
    }

    private fun initViews(view: View) {
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        connectionStatus = view.findViewById(R.id.connection_status)
        connectionType = view.findViewById(R.id.connection_type)
        cardWifi = view.findViewById(R.id.card_wifi)
        cardSignal = view.findViewById(R.id.card_signal)
        signalProgress = view.findViewById(R.id.signal_progress)
        signalQuality = view.findViewById(R.id.signal_quality)
    }

    private fun setupObservers() {
        viewModel.networkInfo.observe(viewLifecycleOwner) { network ->
            if (network.isConnected) {
                connectionStatus.text = getString(R.string.network_connected)
                connectionStatus.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.status_good)
                )
                connectionType.text = network.getConnectionTypeName()

                // Show WiFi details if connected via WiFi
                if (network.connectionType == ConnectionType.WIFI && network.wifiInfo != null) {
                    cardWifi.visibility = View.VISIBLE
                    cardSignal.visibility = View.VISIBLE

                    val wifi = network.wifiInfo

                    // Update WiFi details
                    view?.findViewById<View>(R.id.row_ssid)?.let { row ->
                        row.findViewById<TextView>(R.id.label)?.text = getString(R.string.network_ssid)
                        row.findViewById<TextView>(R.id.value)?.text = wifi.ssid
                    }

                    view?.findViewById<View>(R.id.row_ip)?.let { row ->
                        row.findViewById<TextView>(R.id.label)?.text = getString(R.string.network_ip)
                        row.findViewById<TextView>(R.id.value)?.text = wifi.ipAddress
                    }

                    view?.findViewById<View>(R.id.row_speed)?.let { row ->
                        row.findViewById<TextView>(R.id.label)?.text = getString(R.string.network_speed)
                        row.findViewById<TextView>(R.id.value)?.text = wifi.getFormattedSpeed()
                    }

                    view?.findViewById<View>(R.id.row_frequency)?.let { row ->
                        row.findViewById<TextView>(R.id.label)?.text = getString(R.string.network_frequency)
                        row.findViewById<TextView>(R.id.value)?.text = wifi.getFormattedFrequency()
                    }

                    view?.findViewById<View>(R.id.row_signal)?.let { row ->
                        row.findViewById<TextView>(R.id.label)?.text = getString(R.string.network_signal)
                        row.findViewById<TextView>(R.id.value)?.text = "${wifi.rssi} dBm"
                    }

                    // Update signal strength indicator
                    val signalPercent = wifi.getSignalPercent().toFloat()
                    signalProgress.setProgress(signalPercent, wifi.getSignalStatus())

                    signalQuality.text = when {
                        signalPercent >= 80 -> "Excellent"
                        signalPercent >= 60 -> "Good"
                        signalPercent >= 40 -> "Fair"
                        signalPercent >= 20 -> "Weak"
                        else -> "Poor"
                    }
                } else {
                    cardWifi.visibility = View.GONE
                    cardSignal.visibility = View.GONE
                }
            } else {
                connectionStatus.text = getString(R.string.network_disconnected)
                connectionStatus.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.status_critical)
                )
                connectionType.text = getString(R.string.network_type)
                cardWifi.visibility = View.GONE
                cardSignal.visibility = View.GONE
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
