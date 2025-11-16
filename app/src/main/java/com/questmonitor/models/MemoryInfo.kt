package com.questmonitor.models

/**
 * Data class representing memory information
 */
data class MemoryInfo(
    val totalMemory: Long = 0,
    val availableMemory: Long = 0,
    val usedMemory: Long = 0,
    val threshold: Long = 0,
    val lowMemory: Boolean = false
) {
    /**
     * Get memory usage percentage
     */
    fun getUsagePercent(): Float {
        if (totalMemory == 0L) return 0f
        return (usedMemory.toFloat() / totalMemory.toFloat()) * 100f
    }

    /**
     * Format bytes to human readable format
     */
    fun formatBytes(bytes: Long): String {
        val gb = bytes / (1024.0 * 1024.0 * 1024.0)
        val mb = bytes / (1024.0 * 1024.0)

        return when {
            gb >= 1.0 -> String.format("%.2f GB", gb)
            mb >= 1.0 -> String.format("%.0f MB", mb)
            else -> String.format("%.2f KB", bytes / 1024.0)
        }
    }

    /**
     * Get formatted total memory
     */
    fun getFormattedTotal(): String = formatBytes(totalMemory)

    /**
     * Get formatted used memory
     */
    fun getFormattedUsed(): String = formatBytes(usedMemory)

    /**
     * Get formatted available memory
     */
    fun getFormattedAvailable(): String = formatBytes(availableMemory)

    /**
     * Get usage status for UI display
     */
    fun getUsageStatus(): UsageStatus {
        val percent = getUsagePercent()
        return when {
            percent < 60f -> UsageStatus.GOOD
            percent < 80f -> UsageStatus.WARNING
            else -> UsageStatus.CRITICAL
        }
    }
}
