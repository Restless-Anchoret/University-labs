package com.ran.cgalab1

import com.ran.engine.algebra.vector.TwoIntVector

class CirclePixelObject(
        firstPixel: TwoIntVector,
        secondPixel: TwoIntVector
) : PixelObject(firstPixel, secondPixel) {

    override fun draw(delegate: PixelRenderingDelegate) {
        val minX = Math.min(firstPixel.x, secondPixel.x)
        val maxX = Math.max(firstPixel.x, secondPixel.x)
        val minY = Math.min(firstPixel.y, secondPixel.y)
        val maxY = Math.max(firstPixel.y, secondPixel.y)
        val centerX = (minX + maxX) / 2
        val centerY = (minY + maxY) / 2
        val radius = Math.min(centerX - minX, centerY - minY)

        var x = radius
        var y = 0
        putPixel(delegate, centerX, centerY, x, y)

        while (x > y) {
            val overDiagonal = ((x - 1) * (x - 1) + (y + 1) * (y + 1) - radius * radius > -x)
            y++
            if (overDiagonal) {
                x--
            }
            putPixel(delegate, centerX, centerY, x, y)
        }
    }

    private fun putPixel(delegate: PixelRenderingDelegate, centerX: Int, centerY: Int, x: Int, y: Int) {
        delegate.setPixel(centerX + x, centerY + y)
        delegate.setPixel(centerX + x, centerY - y)
        delegate.setPixel(centerX - x, centerY + y)
        delegate.setPixel(centerX - x, centerY - y)
        delegate.setPixel(centerX + y, centerY + x)
        delegate.setPixel(centerX + y, centerY - x)
        delegate.setPixel(centerX - y, centerY + x)
        delegate.setPixel(centerX - y, centerY - x)
    }

}
