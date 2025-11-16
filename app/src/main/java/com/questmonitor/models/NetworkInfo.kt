package com.questmonitor.models

/**
 * Data class representing network information
 */
data class NetworkInfo(
    val isConnected: Boolean = false,
    val connectionType: ConnectionType = ConnectionType.NONE,
    val wifiInfo: WifiInfo? = null,
    val mobileInfo: MobileInfo? = null
) {
    /**
     * Get connection type name
     */
    fun getConnectionTypeName(): String {
        return connectionType.displayName
    }
}

/**
 * WiFi specific information
 */
data class WifiInfo(
    val ssid: String = "Unknown",
    val bssid: String = "",
    val ipAddress: String = "0.0.0.0",
    val linkSpeed: Int = 0,
    val frequency: Int = 0,
    val rssi: Int = 0,
    val signalLevel: Int = 0
) {
    /**
     * Get formatted link speed
     */
    fun getFormattedSpeed(): String {
        return "$linkSpeed Mbps"
    }

    /**
     * Get formatted frequency
     */
    fun getFormattedFrequency(): String {
        return "$frequency MHz"
    }

    /**
     * Get signal strength percentage
     */
    fun getSignalPercent(): Int {
        // WiFi signal level is typically -100 to -50 dBm
        return when {
            rssi >= -50 -> 100
            rssi <= -100 -> 0
            else -> ((rssi + 100) * 2)
        }
    }

    /**
     * Get signal status
     */
    fun getSignalStatus(): UsageStatus {
        val percent = getSignalPercent()
        return when {
            percent >= 60 -> UsageStatus.GOOD
            percent >= 30 -> UsageStatus.WARNING
            else -> UsageStatus.CRITICAL
        }
    }
}

/**
 * Mobile data specific information
 */
data class MobileInfo(
    val networkOperator: String = "Unknown",
    val networkType: String = "Unknown",
    val isRoaming: Boolean = false
)

/**
 * Connection type enum
 */
enum class ConnectionType(val displayName: String) {
    WIFI("Wi-Fi"),
    MOBILE("Mobile Data"),
    ETHERNET("Ethernet"),
    NONE("Not Connected")
}
