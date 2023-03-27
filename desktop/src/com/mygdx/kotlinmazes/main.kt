package com.mygdx.kotlinmazes

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.aldousBroder
import com.mygdx.kotlinmazes.scenes.SquareGridDistance
import ktx.math.vec2

object EngineConfig {
    const val VIEWPORT_WIDTH = 1920f
    const val VIEWPORT_HEIGHT = 1080f
    val VIEWPORT_CENTER = vec2(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2)
}


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
fun main(arg: Array<String>) {
    val grid = SquareGrid(50, 80).also { aldousBroder(it) }
    val dists = Distance(grid.get(grid.height / 2, grid.width / 2)!!)
    playScene(SquareGridDistance(grid, dists))
}

fun playScene(scene: Scene) {
    val config = Lwjgl3ApplicationConfiguration()
    config.setForegroundFPS(60)
    config.setTitle("Kotlin Mazes")
    config.setWindowedMode(EngineConfig.VIEWPORT_WIDTH.toInt(), EngineConfig.VIEWPORT_HEIGHT.toInt())
    Lwjgl3Application(scene, config)
}