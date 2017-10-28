package com.ran.cgalab1

import com.ran.cgalab1.MouseHandlerState.*
import com.ran.engine.algebra.vector.TwoIntVector
import com.ran.engine.opengl.handlers.EventHandler
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.Display

class PixelMouseHandler(
        val model: PixelRenderingModel
) : EventHandler {

    private val PIXEL_WIDTH = 8

    private var previousState: MouseHandlerState = NO_BUTTON_PRESSED

    override fun handleEvent() {
        val currentState = when {
            Mouse.isButtonDown(0) -> LEFT_BUTTON_PRESSED
            Mouse.isButtonDown(1) -> RIGHT_BUTTON_PRESSED
            Mouse.isButtonDown(2) -> MIDDLE_BUTTON_PRESSED
            else -> NO_BUTTON_PRESSED
        }

        val currentPixel = findCurrentPixel()
        if (previousState == NO_BUTTON_PRESSED && currentState == LEFT_BUTTON_PRESSED) {
            model.pixelObjects.add(LinePixelObject(currentPixel, currentPixel))
        } else if (previousState == NO_BUTTON_PRESSED && currentState == RIGHT_BUTTON_PRESSED) {
            model.pixelObjects.add(CirclePixelObject(currentPixel, currentPixel))
        } else if (previousState == NO_BUTTON_PRESSED && currentState == MIDDLE_BUTTON_PRESSED) {
            model.pixelObjects.add(DdaLinePixelObject(currentPixel, currentPixel))
        } else if (previousState == currentState && currentState != NO_BUTTON_PRESSED) {
            model.pixelObjects[model.pixelObjects.size - 1].secondPixel = findCurrentPixel()
        }
        previousState = currentState
    }

    private fun findCurrentPixel(): TwoIntVector {
        val x = Mouse.getX()
        val y = Display.getHeight() - Mouse.getY()
        val pixelX = x / PIXEL_WIDTH
        val pixelY = y / PIXEL_WIDTH
        return TwoIntVector(pixelX, pixelY)
    }

}

enum class MouseHandlerState {
    LEFT_BUTTON_PRESSED,
    MIDDLE_BUTTON_PRESSED,
    RIGHT_BUTTON_PRESSED,
    NO_BUTTON_PRESSED
}
