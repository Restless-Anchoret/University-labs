package com.ran.cgalab1

import com.ran.engine.opengl.handlers.EventHandler
import org.lwjgl.input.Keyboard

class PixelKeyboardHandler(
        val model: PixelRenderingModel
) : EventHandler {

//    private var previousEventKey: Int = 0

    override fun handleEvent() {
        if (!Keyboard.getEventKeyState()) {
            return
        }
//        val currentEventKey = Keyboard.getEventKey()
//        println("Prev: $previousEventKey")
//        println("Curr: $currentEventKey")
//        println("Empty: ${model.pixelObjects.isEmpty()}")
//        if (previousEventKey != Keyboard.KEY_SPACE && currentEventKey == Keyboard.KEY_SPACE && !model.pixelObjects.isEmpty()) {
        if (Keyboard.getEventKey() == Keyboard.KEY_SPACE && !model.pixelObjects.isEmpty()) {
            model.pixelObjects.removeAt(model.pixelObjects.size - 1)
        }
//        previousEventKey = currentEventKey
    }

}
