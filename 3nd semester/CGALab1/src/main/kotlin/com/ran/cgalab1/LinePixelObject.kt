package com.ran.cgalab1

import com.ran.engine.algebra.vector.TwoIntVector

class LinePixelObject(
        firstPixel: TwoIntVector,
        secondPixel: TwoIntVector
) : PixelObject(firstPixel, secondPixel) {

    override fun draw(delegate: PixelRenderingDelegate) {
        delegate.setPixel(firstPixel.x, firstPixel.y)
        delegate.setPixel(secondPixel.x, secondPixel.y)
    }

}
