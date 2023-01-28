package com.mygdx.kotlinmazes

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
fun main(arg: Array<String>) {
    val config = Lwjgl3ApplicationConfiguration()
    config.setForegroundFPS(60)
    config.setTitle("Kotlin Mazes")
    val grid = Grid(10, 10)
    val topLeft = grid.get(9, 0)!!
    topLeft.link(topLeft.north!!)
    topLeft.link(topLeft.east!!)
    val bottomRight = grid.get(0, 9)!!
    bottomRight.link(bottomRight.west!!)
    Lwjgl3Application(DrawGrid(grid), config)
}
