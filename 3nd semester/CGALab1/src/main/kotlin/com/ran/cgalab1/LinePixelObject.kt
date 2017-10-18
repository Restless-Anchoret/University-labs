package com.ran.cgalab1

import com.ran.engine.algebra.vector.TwoIntVector

class LinePixelObject(
        firstPixel: TwoIntVector,
        secondPixel: TwoIntVector
) : PixelObject(firstPixel, secondPixel) {

    override fun draw(delegate: PixelRenderingDelegate) {
        var x = firstPixel.x
        var y = firstPixel.y

        val deltaX = Math.abs(secondPixel.x - firstPixel.x)
        val deltaY = Math.abs(secondPixel.y - firstPixel.y)

        val signX = Math.signum((secondPixel.x - firstPixel.x).toDouble()).toInt()
        val signY = Math.signum((secondPixel.y - firstPixel.y).toDouble()).toInt()

        var h = 0
        var eLessThenHalf = true
        delegate.setPixel(x, y)

        if (deltaY < deltaX) {
            for (k in 1..deltaX) {
                if (!eLessThenHalf) {
                    h++
                }
                eLessThenHalf = (2 * k * deltaY - (2 * h + 1) * deltaX <= 0)
                x += signX
                if (!eLessThenHalf) {
                    y += signY
                }
                delegate.setPixel(x, y)
            }
        } else {
            for (k in 1..deltaY) {
                if (!eLessThenHalf) {
                    h++
                }
                eLessThenHalf = (2 * k * deltaX - (2 * h + 1) * deltaY <= 0)
                y += signY
                if (!eLessThenHalf) {
                    x += signX
                }
                delegate.setPixel(x, y)
            }
        }
    }

}
