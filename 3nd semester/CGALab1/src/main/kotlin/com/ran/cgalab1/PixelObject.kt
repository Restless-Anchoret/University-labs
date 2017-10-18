package com.ran.cgalab1

import com.ran.engine.algebra.vector.TwoIntVector

abstract class PixelObject(
        var firstPixel: TwoIntVector,
        var secondPixel: TwoIntVector
) {

    abstract fun draw(delegate: PixelRenderingDelegate)

}
