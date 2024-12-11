package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.Distance
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.grids.square.SquareCell
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.AldousBroder
import com.mygdx.kotlinmazes.utils.graphics.Gradient
import kotlin.math.min

fun main() {
    val grid = SquareGrid(50, 80).also { AldousBroder(it).forEach {} }
    SquareGridDistanceClick(grid).play()
}

class SquareGridDistanceClick(private val grid: SquareGrid) : Scene() {

    private var distance: Distance? = null
    private var hoveredCell: SquareCell? = null
    private lateinit var drawer: SquareGridDrawer
    private val gradient = Gradient.Plasma

    override fun init() {
        val width = EngineConfig.VIEWPORT_WIDTH / grid.width
        val height = EngineConfig.VIEWPORT_HEIGHT / grid.height
        val sideLength = min(width, height)
        drawer = SquareGridDrawer(shapeRenderer, sideLength)
    }

    override fun update() {
        val cursorPos = cursorPosition()
        val (c, r) = drawer.gridCoordsFromScreenCoords(cursorPos)
        hoveredCell = grid.get(r, c)

        if (hoveredCell != null && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            distance = Distance(hoveredCell!!)
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
        shapeRenderer.color = Color.BLACK
        grid.cells().forEach(drawer::drawUnlinkedBorders)
    }
}
