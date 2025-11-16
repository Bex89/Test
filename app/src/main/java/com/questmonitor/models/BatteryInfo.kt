package com.questmonitor.models

/**
 * Data class representing battery information
 */
data class BatteryInfo(
    val level: Int = 0,
    val scale: Int = 100,
    val temperature: Int = 0,
    val voltage: Int = 0,
    val health: BatteryHealth = BatteryHealth.UNKNOWN,
    val status: BatteryStatus = BatteryStatus.UNKNOWN,
    val plugged: Boolean = false,
    val technology: String = "Unknown"
) {
    /**
     * Get battery percentage
     */
    fun getPercent(): Int {
        if (scale == 0) return 0
        return (level * 100) / scale
    }

    /**
     * Get temperature in Celsius
     */
    fun getTemperatureCelsius(): Int {
        return temperature / 10
    }

    /**
     * Get formatted temperature
     */
    fun getFormattedTemperature(): String {
        return "${getTemperatureCelsius()}°C"
    }

    /**
     * Get formatted voltage
     */
    fun getFormattedVoltage(): String {
        return "${voltage} mV"
    }

    /**
     * Get battery level status
     */
    fun getLevelStatus(): UsageStatus {
        val percent = getPercent()
        return when {
            percent >= 50 -> UsageStatus.GOOD
            percent >= 20 -> UsageStatus.WARNING
            else -> UsageStatus.CRITICAL
        }
    }
}

/**
 * Battery health status
 */
enum class BatteryHealth(val displayName: String) {
    GOOD("Good"),
    OVERHEAT("Overheat"),
    DEAD("Dead"),
    OVER_VOLTAGE("Over Voltage"),
    COLD("Cold"),
    UNKNOWN("Unknown")
}

/**
 * Battery charging status
 */
enum class BatteryStatus(val displayName: String) {
    CHARGING("Charging"),
    DISCHARGING("Discharging"),
    FULL("Full"),
    NOT_CHARGING("Not Charging"),
    UNKNOWN("Unknown")
}
