package com.ran.cgalab1

import com.ran.engine.algebra.vector.TwoIntVector
import com.ran.engine.rendering.core.RenderingDelegate
import java.awt.Color

class PixelRenderingDelegate {

    private val PIXEL_WIDTH = 8

    var delegate: RenderingDelegate? = null

    fun setPixel(x: Int, y: Int) {
        val leftDownPoint = TwoIntVector(x * PIXEL_WIDTH, y * PIXEL_WIDTH)
        val rightUpPoint = TwoIntVector((x + 1) * PIXEL_WIDTH, (y + 1) * PIXEL_WIDTH)
        delegate?.drawRectangle(leftDownPoint, rightUpPoint, Color.BLACK)
    }

}
