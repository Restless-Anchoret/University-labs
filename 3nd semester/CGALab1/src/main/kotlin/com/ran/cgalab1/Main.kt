package com.ran.cgalab1

import com.ran.engine.opengl.runner.OpenGLRunner
import com.ran.engine.rendering.world.WorldFactory

fun main(args: Array<String>) {
    val runner = OpenGLRunner(listOf(WorldFactory.empty()))
    runner.init()

    val model = PixelRenderingModel()

    runner.renderingEngine.renderingActions.clear()
    runner.renderingEngine.renderingActions.add(PixelRenderingAction(model))

    runner.keyboardEventHandlers.clear()
    runner.mouseEventHandlers.clear()
    runner.mouseEventHandlers.add(PixelMouseHandler(model))

    runner.run()
}
