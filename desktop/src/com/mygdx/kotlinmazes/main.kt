package com.mygdx.kotlinmazes

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.mygdx.kotlinmazes.mazegeneration.aldousBroder
import com.mygdx.kotlinmazes.grids.square.SquareGrid

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
fun main(arg: Array<String>) {
    val config = Lwjgl3ApplicationConfiguration()
    config.setForegroundFPS(60)
    config.setTitle("Kotlin Mazes")
    val grid = SquareGrid(50, 80).also { aldousBroder(it) }
    val dists = Distance(grid.get(grid.height / 2, grid.width / 2)!!)
    Lwjgl3Application(DrawSquareGrid(grid, dists), config)
}
