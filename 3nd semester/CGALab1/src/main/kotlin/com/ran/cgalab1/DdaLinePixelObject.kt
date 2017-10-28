package com.ran.cgalab1

import com.ran.engine.algebra.vector.TwoIntVector

class DdaLinePixelObject(
        firstPixel: TwoIntVector,
        secondPixel: TwoIntVector
) : PixelObject(firstPixel, secondPixel) {

    override fun draw(delegate: PixelRenderingDelegate) {
        delegate.setPixel(firstPixel.x, firstPixel.y)

        val deltaX = Math.abs(secondPixel.x - firstPixel.x)
        val deltaY = Math.abs(secondPixel.y - firstPixel.y)

        if (deltaX == 0 && deltaY == 0) {
            return
        }

        if (deltaX > deltaY) {
            val incX: Int = (secondPixel.x - firstPixel.x) / deltaX
            val incY: Float = (secondPixel.y - firstPixel.y).toFloat() / deltaX

            var currX: Int = firstPixel.x
            var currY: Float = firstPixel.y.toFloat()

            for (k in 1..deltaX) {
                currX += incX
                currY += incY
                delegate.setPixel(currX, currY.toInt())
            }
        } else {
            val incX: Float = (secondPixel.x - firstPixel.x).toFloat() / deltaY
            val incY: Int = (secondPixel.y - firstPixel.y) / deltaY

            var currX: Float = firstPixel.x.toFloat()
            var currY: Int = firstPixel.y

            for (k in 1..deltaY) {
                currX += incX
                currY += incY
                delegate.setPixel(currX.toInt(), currY)
            }
        }
    }

}
