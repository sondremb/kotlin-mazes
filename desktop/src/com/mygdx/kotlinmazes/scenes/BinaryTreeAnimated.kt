package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.drawers.TriangleDrawer
import com.mygdx.kotlinmazes.grids.triangle.TriangleGrid
import com.mygdx.kotlinmazes.mazegeneration.Prims

fun main() {
    BinaryTreeAnimated().play()
}

class BinaryTreeAnimated : Scene() {
    // [SPACE] = play/pause
    // [R]     = reset
    // [→]     = next step
    private var isPlaying = false
    private var timeSinceLastUpdate = 0f
    lateinit var grid: TriangleGrid
    private lateinit var drawer: TriangleDrawer
    private var algo: Prims? = null
    override fun init() {
        super.init()
        grid = TriangleGrid(30)
        drawer = TriangleDrawer(shapeRenderer, grid)
    }

    override fun draw() {
        ScreenUtils.clear(Color.WHITE)
        timeSinceLastUpdate += Gdx.graphics.deltaTime
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            algo = null
            grid.resetLinks()
        }
        if (shouldUpdate()) {
            timeSinceLastUpdate = 0f
            algo = algo ?: Prims(grid!!)
            if (algo!!.hasNext()) {
                algo!!.next()
            }
        }
        drawer.drawUnlinkedBorders(Color.GRAY)
    }

    private fun shouldUpdate(): Boolean {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            isPlaying = !isPlaying
        }
        if (isPlaying || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            return true
        }
        return false
    }
}