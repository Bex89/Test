package com.questmonitor.models

/**
 * Data class representing storage information
 */
data class StorageInfo(
    val totalSpace: Long = 0,
    val freeSpace: Long = 0,
    val usedSpace: Long = 0,
    val breakdown: StorageBreakdown = StorageBreakdown()
) {
    /**
     * Get storage usage percentage
     */
    fun getUsagePercent(): Float {
        if (totalSpace == 0L) return 0f
        return (usedSpace.toFloat() / totalSpace.toFloat()) * 100f
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
     * Get formatted total space
     */
    fun getFormattedTotal(): String = formatBytes(totalSpace)

    /**
     * Get formatted used space
     */
    fun getFormattedUsed(): String = formatBytes(usedSpace)

    /**
     * Get formatted free space
     */
    fun getFormattedFree(): String = formatBytes(freeSpace)

    /**
     * Get usage status for UI display
     */
    fun getUsageStatus(): UsageStatus {
        val percent = getUsagePercent()
        return when {
            percent < 70f -> UsageStatus.GOOD
            percent < 85f -> UsageStatus.WARNING
            else -> UsageStatus.CRITICAL
        }
    }
}

/**
 * Breakdown of storage by category
 */
data class StorageBreakdown(
    val apps: Long = 0,
    val images: Long = 0,
    val videos: Long = 0,
    val audio: Long = 0,
    val documents: Long = 0,
    val cache: Long = 0,
    val other: Long = 0
)
