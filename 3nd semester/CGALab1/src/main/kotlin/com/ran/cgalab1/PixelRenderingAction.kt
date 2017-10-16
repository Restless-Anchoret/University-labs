package com.ran.cgalab1

import com.ran.engine.rendering.core.RenderingAction
import com.ran.engine.rendering.core.RenderingDelegate
import com.ran.engine.rendering.core.RenderingInfo

class PixelRenderingAction(
        val model: PixelRenderingModel
) : RenderingAction {

    override fun performRendering(delegate: RenderingDelegate, info: RenderingInfo) {
        delegate.clear(info.backgroundColor)
    }

}
