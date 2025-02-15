package top.moop.mywatch

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.SurfaceHolder
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.Renderer
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import java.time.ZonedDateTime
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.data.PlainComplicationText

class MyWatchRender(
    private val context: Context,
    surfaceHolder: SurfaceHolder,
    watchState: WatchState,
    private val complicationSlotsManager: ComplicationSlotsManager,
    currentUserStyleRepository: CurrentUserStyleRepository,
    canvasType: Int
) : Renderer.CanvasRenderer(
    surfaceHolder = surfaceHolder,
    currentUserStyleRepository = currentUserStyleRepository,
    watchState = watchState,
    canvasType = canvasType,
    interactiveDrawModeUpdateDelayMillis = 16L
) {

    @SuppressLint("DefaultLocale")
    fun drawTime(canvas: Canvas, zonedDateTime: ZonedDateTime) {
        val customTypeface = androidx.core.content.res.ResourcesCompat.getFont(
            context,
            R.font.my_hack
        )
        
        val style = Paint().apply {
            color = Color.WHITE
            textSize = 82f
            typeface = customTypeface

        }

        val timeText = String.format("%02d:%02d", 
            zonedDateTime.hour,
            zonedDateTime.minute
        )
        canvas.drawText(timeText, 34f, 174f, style)
    }

    @SuppressLint("DefaultLocale")
    fun drawDate(canvas: Canvas, zonedDateTime: ZonedDateTime) {
        val style = Paint().apply {
            color = Color.WHITE
            textSize = 32f
            textAlign = Paint.Align.CENTER
        }

        // Format Gregorian date and weekday
        val weekDays = arrayOf("日", "一", "二", "三", "四", "五", "六")
        val dateText = String.format("%02d月%02d   周%s",
            zonedDateTime.monthValue,
            zonedDateTime.dayOfMonth,
            weekDays[zonedDateTime.dayOfWeek.value % 7]
        )

        val wholeDateText = "$dateText   正月十五"

        canvas.drawText(wholeDateText, 225f, 92f, style)
    }

	fun drawSome(canvas: Canvas) {
        val myRectStyle = Paint().apply {
            color = Color.DKGRAY
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        val style = Paint().apply {
            color = Color.WHITE
            textSize = 36f
        }
        
        DrawUtils.drawRect(canvas, 296f, 113f, 110f, 65f, 8f, myRectStyle)
		canvas.drawText("惊蛰", 312f, 161f, style)
	}

    override fun render(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
        // First draw the background
        canvas.drawColor(Color.BLACK)
        
        // Draw your existing watch face elements
        val rectPaint = Paint().apply {
            color = Color.DKGRAY
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        // Draw circle
        val circlePaint = Paint().apply {
            color = Color.DKGRAY
            style = Paint.Style.FILL
            isAntiAlias = true
        }

		val textPaint = Paint().apply {
            color = Color.LTGRAY
            textSize = 24f
            textAlign = Paint.Align.CENTER
        }

        DrawUtils.drawCircle(canvas, 51f, 300f, 50f, circlePaint)
        DrawUtils.drawCircle(canvas, 175f, 300f, 50f, circlePaint)
        DrawUtils.drawCircle(canvas, 299f, 300f, 50f, circlePaint)

        DrawUtils.drawRect(canvas, 0f, 193f, 450f, 92f, 20f, rectPaint)

        // Comment out the manual text since we're replacing it with a complication
        // canvas.drawText("\uD83C\uDF1E 06:13", 225f, 442f, textPaint)
        
        drawTime(canvas, zonedDateTime)
        drawDate(canvas, zonedDateTime)
        drawSome(canvas)

        // Draw complications with conditional rendering
        for ((id, complication) in complicationSlotsManager.complicationSlots) {
            if (id == 4) {
                // For the bottom text complication (ID 4), draw only the text without background
                val paint = Paint().apply {
                    color = Color.LTGRAY
                    textSize = 24f
                    textAlign = Paint.Align.CENTER
                }
                // Get the text from the complication and draw it directly
                val data = complication.complicationData.value
                if (data is ShortTextComplicationData) {
                    // Convert ZonedDateTime to Instant and ensure we get a String
                    val text = data.text.getTextAt(context.resources, zonedDateTime.toInstant()).toString()
                    canvas.drawText("🌞 $text", bounds.exactCenterX(), 444f, paint)
                }
            } else {
                // For other complications, render normally
                complication.render(canvas, zonedDateTime, renderParameters)
            }
        }
    }

    override fun renderHighlightLayer(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
//                // 设置背景颜色
//        canvas.drawColor(Color.BLACK)
//
//        // 创建并设置画笔
//        val textPaint = Paint().apply {
//            color = Color.WHITE
//            textSize = 50f
//            textAlign = Paint.Align.CENTER
//        }
//
//        // 绘制文本
//        canvas.drawText("Hello Wear OS", bounds.exactCenterX(), bounds.exactCenterY(), textPaint)
//
//        // 绘制圆形
//        val circlePaint = Paint().apply {
//            color = Color.BLUE
//            style = Paint.Style.FILL
//        }
//        val radius = 100f
//        canvas.drawCircle(bounds.exactCenterX(), bounds.exactCenterY() + 100, radius, circlePaint)
//
//        // 渲染复杂功能插槽
///*
//        complicationSlotsManager.renderComplications(canvas, zonedDateTime)
//*/
//        // 创建并设置画笔用于高亮层
//        val highlightPaint = Paint().apply {
//            color = Color.argb(100, 255, 255, 255) // 半透明白色
//        }
//
//        // 绘制覆盖整个表面的半透明矩形
//        canvas.drawRect(Rect(0, 0, bounds.width(), bounds.height()), highlightPaint)
    }
}