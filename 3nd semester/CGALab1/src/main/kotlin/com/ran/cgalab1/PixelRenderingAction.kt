package com.ran.cgalab1

import com.ran.engine.rendering.core.RenderingAction
import com.ran.engine.rendering.core.RenderingDelegate
import com.ran.engine.rendering.core.RenderingInfo

class PixelRenderingAction(
        val model: PixelRenderingModel
) : RenderingAction {

    private val pixelRenderingDelegate = PixelRenderingDelegate()

    override fun performRendering(delegate: RenderingDelegate, info: RenderingInfo) {
        pixelRenderingDelegate.delegate = delegate
        delegate.clear(info.backgroundColor)
        model.pixelObjects.forEach { pixelObject ->
            pixelObject.draw(pixelRenderingDelegate)
        }
    }

}
