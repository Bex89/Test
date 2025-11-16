package com.questmonitor.models

/**
 * Data class representing CPU information
 */
data class CpuInfo(
    val usagePercent: Float = 0f,
    val coreCount: Int = 0,
    val currentFrequency: Long = 0,
    val maxFrequency: Long = 0,
    val minFrequency: Long = 0,
    val architecture: String = "Unknown"
) {
    /**
     * Get formatted frequency in MHz
     */
    fun getFormattedFrequency(): String {
        val mhz = currentFrequency / 1000
        return "${mhz} MHz"
    }

    /**
     * Get usage status for UI display
     */
    fun getUsageStatus(): UsageStatus {
        return when {
            usagePercent < 30f -> UsageStatus.GOOD
            usagePercent < 70f -> UsageStatus.WARNING
            else -> UsageStatus.CRITICAL
        }
    }
}

enum class UsageStatus {
    GOOD, WARNING, CRITICAL
}
