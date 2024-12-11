package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.RecursiveDivision
import com.mygdx.kotlinmazes.utils.math.inset

fun main() {
    BinaryTreeAnimated().play()
}

class BinaryTreeAnimated : Scene() {
    // [SPACE] = play/pause
    // [R]     = reset
    // [→]     = next step
    private var isPlaying = false
    private var timeSinceLastUpdate = 0f
    lateinit var grid: SquareGrid
    private lateinit var drawer: SquareGridDrawer
    private var algo: RecursiveDivision? = null
    override fun init() {
        super.init()
        val size = 16
        grid = SquareGrid(size, size)
        drawer = SquareGridDrawer(shapeRenderer, grid, EngineConfig.VIEWPORT_RECT.inset(10f))
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
            algo = algo ?: RecursiveDivision(grid!!)
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
        if (isPlaying && timeSinceLastUpdate > 0.2f || Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            return true
        }
        return false
    }
}