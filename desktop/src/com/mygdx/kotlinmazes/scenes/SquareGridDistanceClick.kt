package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.Config
import com.mygdx.kotlinmazes.Distance
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.grids.square.SquareCell
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.AldousBroder
import com.mygdx.kotlinmazes.utils.graphics.Gradient
import com.mygdx.kotlinmazes.utils.graphics.ScreenshotFactory
import com.mygdx.kotlinmazes.utils.math.inset

fun main() {
    val grid = SquareGrid(20, 20)
    SquareGridDistanceClick(grid).play()
}

class SquareGridDistanceClick(private val grid: SquareGrid) : Scene() {

    private var distance: Distance? = null
    private var hoveredCell: SquareCell? = null
    private lateinit var drawer: SquareGridDrawer
    private var gradient = Gradient(Color.WHITE, Color.PURPLE)
    private lateinit var screenshotFactory: ScreenshotFactory

    override fun init() {
        drawer = SquareGridDrawer(shapeRenderer, grid, Config.VIEWPORT_RECT.inset(1f))
        runAlgorithm()
    }

    private fun runAlgorithm() {
        grid.resetLinks()
        distance = null
        screenshotFactory = ScreenshotFactory("ald-bro")
        AldousBroder.on(grid)
    }

    override fun update() {
        val cursorPos = cursorPosition()
        val (c, r) = drawer.gridCoordsFromScreenCoords(cursorPos)
        hoveredCell = grid.get(r, c)

        if (hoveredCell != null && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            distance = Distance(hoveredCell!!)
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            runAlgorithm()
        }
    }

    override fun draw() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        if (distance != null) {
            val gradientSampler = gradient.sampler(0f, distance!!.max.toFloat())
            grid.cells().forEach {
                var color = gradientSampler.sample(distance!![it].toFloat())
                if (it.row == hoveredCell?.row && it.column == hoveredCell?.column) {
                    // lighten color if hovered
                    color = Color(color.r * 1.5f, color.g * 1.5f, color.b * 1.5f, color.a)
                }
                drawer.fill(it, color)
            }
        } else if (hoveredCell != null) {
            drawer.fill(hoveredCell!!, Color.CORAL)
        }
        drawer.drawUnlinkedBorders(Color.BLACK)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            screenshotFactory.getScreenshot()
        }
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            gradient = Gradient(Color.WHITE, randomColor())
        }
    }

    private fun randomColor(): Color {
        return Color().fromHsv(Math.random().toFloat() * 360, 0.85f, 0.5f).also { it.a = 1f }
    }
}

