package com.mygdx.kotlinmazes

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
fun main(arg: Array<String>) {
    val config = Lwjgl3ApplicationConfiguration()
    config.setForegroundFPS(60)
    config.setTitle("Kotlin Mazes")
    val grid = Grid(50, 80).also { sideWinder(it) }
    val dists = Distance(grid, grid.get(0, 0)!!)
    Lwjgl3Application(DrawGrid(grid, dists), config)
}
