package com.mygdx.kotlinmazes

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.AldousBroder
import com.mygdx.kotlinmazes.scenes.Scene
import com.mygdx.kotlinmazes.scenes.SquareGridDistance
import ktx.math.vec2

object EngineConfig {
    const val VIEWPORT_WIDTH = 1080f
    const val VIEWPORT_HEIGHT = 1080f
    val VIEWPORT_CENTER = vec2(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2)
}


// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
fun main(arg: Array<String>) {
    val grid = SquareGrid(50, 80).also { AldousBroder(it).forEach {} }
    val dists = Distance(grid.get(grid.height / 2, grid.width / 2)!!)
    playScene(SquareGridDistance(grid, dists))
}

fun playScene(scene: Scene) {
    val config = Lwjgl3ApplicationConfiguration()
    config.setForegroundFPS(60)
    config.setTitle("Kotlin Mazes")
    config.setWindowedMode(EngineConfig.VIEWPORT_WIDTH.toInt() / 2, EngineConfig.VIEWPORT_HEIGHT.toInt() / 2)
    Lwjgl3Application(scene, config)
}