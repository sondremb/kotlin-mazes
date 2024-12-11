package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
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
        drawer = SquareGridDrawer(shapeRenderer, grid)
    }

    override fun draw() {
        ScreenUtils.clear(Color.WHITE)
        drawer.drawUnlinkedBorders(Color.GRAY)
    }
}