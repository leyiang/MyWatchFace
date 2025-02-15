package top.moop.mywatch

import android.graphics.RectF
import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlot
import androidx.wear.watchface.ComplicationSlotsManager
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

    /**
     * 创建并返回此手表表面的复杂功能插槽管理器。
     */
    override fun createComplicationSlotsManager(
        currentUserStyleRepository: CurrentUserStyleRepository
    ): ComplicationSlotsManager {
        val canvasComplicationFactory = CanvasComplicationFactory { watchState, listener ->
            CanvasComplicationDrawable(ComplicationDrawable(this), watchState, listener)
        }
        return ComplicationSlotsManager(
            listOf(
                ComplicationSlot.createRoundRectComplicationSlotBuilder(
                    /*id */ 0,
                    canvasComplicationFactory,
                    listOf(
                        ComplicationType.RANGED_VALUE,
                        ComplicationType.LONG_TEXT,
                        ComplicationType.SHORT_TEXT,
                        ComplicationType.MONOCHROMATIC_IMAGE,
                        ComplicationType.SMALL_IMAGE
                    ),
                    DefaultComplicationDataSourcePolicy(
                        SystemDataSources.DATA_SOURCE_DAY_OF_WEEK,
                        ComplicationType.SHORT_TEXT
                    ),
                    ComplicationSlotBounds(RectF(0.15625f, 0.1875f, 0.84375f, 0.3125f))
                )
                    .build(),
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
                        SystemDataSources.DATA_SOURCE_STEP_COUNT,
                        ComplicationType.SHORT_TEXT
                    ),
                    ComplicationSlotBounds(RectF(0.1f, 0.5625f, 0.35f, 0.8125f))
                )
                    .build()
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

        return WatchFace(WatchFaceType.DIGITAL, renderer)
    }

}