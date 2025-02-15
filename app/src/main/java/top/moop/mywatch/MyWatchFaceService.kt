package top.moop.mywatch

import android.graphics.RectF
import android.util.Log
import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlot
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.ComplicationTapFilter
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.complications.ComplicationSlotBounds
import androidx.wear.watchface.complications.DefaultComplicationDataSourcePolicy
import androidx.wear.watchface.complications.SystemDataSources
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyleSchema

class MyWatchFaceService : WatchFaceService() {
    /**
     * Creates and returns the user style schema for this watch face.
     */
    override fun createUserStyleSchema(): UserStyleSchema {
        // 返回一个用户风格模式，这里仅作示范，可能需要更复杂的配置
        return UserStyleSchema(listOf())
    }

	fun getRatioByPixel(left: Float, top: Float, right: Float, bottom: Float): RectF {
        val size = 450f
        return RectF(
            left / size,    // convert left pixel to ratio
            top / size,     // convert top pixel to ratio
            right / size,   // convert right pixel to ratio
            bottom / size   // convert bottom pixel to ratio
        )
    }

    /**
     * 创建并返回此手表表面的复杂功能插槽管理器。
     */
    override fun createComplicationSlotsManager(
        currentUserStyleRepository: CurrentUserStyleRepository
    ): ComplicationSlotsManager {
        val canvasComplicationFactory = CanvasComplicationFactory { watchState, listener ->
            val complicationDrawable = ComplicationDrawable(this).apply {
                setContext(this@MyWatchFaceService)
            }
            CanvasComplicationDrawable(complicationDrawable, watchState, listener)
        }

        return ComplicationSlotsManager(
            listOf(
                ComplicationSlot.createRoundRectComplicationSlotBuilder(
                    /*id */ 1,
                    canvasComplicationFactory,
                    listOf(
                        ComplicationType.RANGED_VALUE,
                        ComplicationType.LONG_TEXT,
                        ComplicationType.SHORT_TEXT,
                        ComplicationType.MONOCHROMATIC_IMAGE,
                        ComplicationType.SMALL_IMAGE
                    ),
                    DefaultComplicationDataSourcePolicy(
                        SystemDataSources.DATA_SOURCE_STEP_COUNT,  // Steps
                        ComplicationType.SHORT_TEXT
                    ),
                    ComplicationSlotBounds(
                        getRatioByPixel(51f, 300f, 151f, 400f)
                    )
                ).build(),

                ComplicationSlot.createRoundRectComplicationSlotBuilder(
                    /*id */ 2,
                    canvasComplicationFactory,
                    listOf(
                        ComplicationType.RANGED_VALUE,
                        ComplicationType.LONG_TEXT,
                        ComplicationType.SHORT_TEXT,
                        ComplicationType.MONOCHROMATIC_IMAGE,
                        ComplicationType.SMALL_IMAGE
                    ),
                    DefaultComplicationDataSourcePolicy(
                        SystemDataSources.DATA_SOURCE_DATE,  // Battery level
                        ComplicationType.SHORT_TEXT
                    ),
                    ComplicationSlotBounds(
                        getRatioByPixel(175f, 300f, 275f, 400f)
                    )
                ).build(),

                ComplicationSlot.createRoundRectComplicationSlotBuilder(
                    /*id */ 3,
                    canvasComplicationFactory,
                    listOf(
                        ComplicationType.RANGED_VALUE,
                        ComplicationType.LONG_TEXT,
                        ComplicationType.SHORT_TEXT,
                        ComplicationType.MONOCHROMATIC_IMAGE,
                        ComplicationType.SMALL_IMAGE
                    ),
                    DefaultComplicationDataSourcePolicy(
                        SystemDataSources.DATA_SOURCE_SUNRISE_SUNSET,  // Sunrise/Sunset
                        ComplicationType.SHORT_TEXT
                    ),
                    ComplicationSlotBounds(
                        getRatioByPixel(299f, 300f, 399f, 400f)
                    )
                ).build(),

                // Text-only complication at bottom center
                ComplicationSlot.createEdgeComplicationSlotBuilder(
                    /*id */ 4,
                    canvasComplicationFactory,
                    listOf(
                        ComplicationType.SHORT_TEXT
                    ),
                    DefaultComplicationDataSourcePolicy(
                        SystemDataSources.DATA_SOURCE_SUNRISE_SUNSET,
                        ComplicationType.SHORT_TEXT
                    ),
                    ComplicationSlotBounds(
                        RectF(0.4f, 0.9f, 0.6f, 1.0f)  // Positioned at the bottom center
                    ),
                    object : ComplicationTapFilter {
                        fun onComplicationTap(complicationId: Int) {
                            // Handle tap event
                        }
                    }
                ).build()
            ),
            currentUserStyleRepository
        )
    }

    /**
     * 创建并返回此手表表面本身，包括用于绘制的渲染器。
     */
    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        // 创建并返回手表表面，包括其渲染器
        val renderer = MyWatchRender(
            context = this,
            surfaceHolder = surfaceHolder,
            watchState = watchState,
            complicationSlotsManager = complicationSlotsManager,
            currentUserStyleRepository = currentUserStyleRepository,
            canvasType = CanvasType.HARDWARE
        )

        Log.d("MyWatchFaceService", "Creating complication slot 4")

        return WatchFace(WatchFaceType.DIGITAL, renderer)
    }

}