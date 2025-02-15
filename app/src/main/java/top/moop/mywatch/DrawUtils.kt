package top.moop.mywatch

import android.graphics.Canvas
import android.graphics.Paint

object DrawUtils {
    fun drawRect(
        canvas: Canvas,
        left: Float, top: Float,
        width: Float, height: Float,
        radius: Float,
        paint: Paint
    ) {
        canvas.drawRoundRect(
            left, top,
            left + width,
            top + height,
            radius, radius,
            paint,
        )
    }

    fun drawCircle(
        canvas: Canvas,
        left: Float, top: Float,
        radius: Float,
        paint: Paint
    ) {
        canvas.drawCircle(
            left + radius,  // centerX (left + radius gives us the center)
            top + radius,   // centerY (top + radius for vertical position)
            radius,         // radius of circle
            paint
        )
    }
} 