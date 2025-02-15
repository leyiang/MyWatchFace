package top.moop.mywatch

import android.view.SurfaceHolder
import androidx.wear.watchface.CanvasType
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.WatchFace
import androidx.wear.watchface.WatchFaceService
import androidx.wear.watchface.WatchFaceType
import androidx.wear.watchface.WatchState
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyleSchema
import org.checkerframework.checker.units.qual.Current

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
        // 创建并返回复杂功能插槽管理器，这里仅作为演示
        return ComplicationSlotsManager(emptyList(), currentUserStyleRepository)
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