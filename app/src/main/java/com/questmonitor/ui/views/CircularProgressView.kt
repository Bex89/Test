package com.questmonitor.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.questmonitor.R
import com.questmonitor.models.UsageStatus

/**
 * Custom view for displaying circular progress indicator
 * Perfect for VR viewing with large, clear graphics
 */
class CircularProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progress: Float = 0f
    private var usageStatus: UsageStatus = UsageStatus.GOOD

    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 24f
        color = ContextCompat.getColor(context, R.color.md_theme_light_surfaceVariant)
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 24f
        strokeCap = Paint.Cap.ROUND
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 48f
        textAlign = Paint.Align.CENTER
    }

    private val rect = RectF()

    init {
        updateColors()
    }

    /**
     * Set progress value (0-100)
     */
    fun setProgress(value: Float, status: UsageStatus = UsageStatus.GOOD) {
        progress = value.coerceIn(0f, 100f)
        usageStatus = status
        updateColors()
        invalidate()
    }

    /**
     * Update colors based on usage status
     */
    private fun updateColors() {
        val progressColor = when (usageStatus) {
            UsageStatus.GOOD -> ContextCompat.getColor(context, R.color.status_good)
            UsageStatus.WARNING -> ContextCompat.getColor(context, R.color.status_warning)
            UsageStatus.CRITICAL -> ContextCompat.getColor(context, R.color.status_critical)
        }

        progressPaint.color = progressColor
        textPaint.color = ContextCompat.getColor(context, R.color.md_theme_light_onSurface)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width.coerceAtMost(height) / 2f) - backgroundPaint.strokeWidth

        // Draw background circle
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)

        // Draw progress arc
        rect.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )

        val sweepAngle = (progress / 100f) * 360f
        canvas.drawArc(rect, -90f, sweepAngle, false, progressPaint)

        // Draw percentage text
        val text = "${progress.toInt()}%"
        val textY = centerY - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText(text, centerX, textY, textPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = 200 // Default size in dp
        val width = resolveSize(size, widthMeasureSpec)
        val height = resolveSize(size, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }
}
