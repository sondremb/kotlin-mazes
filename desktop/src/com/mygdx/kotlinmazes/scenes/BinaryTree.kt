package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.grids.square.SquareGrid

fun main() {
    BinaryTree().play()
}

class BinaryTree : Scene() {
    lateinit var grid: SquareGrid
    private lateinit var drawer: SquareGridDrawer
    override fun init() {
        super.init()
        val size = 16
        grid = SquareGrid(size, size)
        val sidelength = (EngineConfig.VIEWPORT_HEIGHT - 20) / size
        drawer = SquareGridDrawer(shapeRenderer, sidelength)
    }

    override fun draw() {
        ScreenUtils.clear(Color.WHITE)
        grid.cells().forEach {
            drawer.drawUnlinkedBorders(it, Color.GRAY)
        }
    }
}