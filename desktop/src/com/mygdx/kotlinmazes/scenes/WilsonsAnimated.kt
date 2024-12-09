package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.drawers.HexDrawer
import com.mygdx.kotlinmazes.grids.hex.HexGrid
import com.mygdx.kotlinmazes.mazegeneration.Wilsons
import com.mygdx.kotlinmazes.playScene
import kotlin.math.min
import kotlin.math.sqrt

fun main() {
    val grid = HexGrid(15)
    playScene(WilsonsAnimated(grid))
}

class WilsonsAnimated(private val grid: HexGrid) : Scene() {
    private lateinit var drawer: HexDrawer
    private val algorithm = Wilsons(grid)
    override fun init() {
        super.init()
        val sidelengthBasedOnWidth = EngineConfig.VIEWPORT_WIDTH / (3f * grid.radius + 2f)
        val sidelengthBasedOnHeight = EngineConfig.VIEWPORT_HEIGHT / ((2f * grid.radius + 1f) * sqrt(3f))
        val sidelength = min(sidelengthBasedOnWidth, sidelengthBasedOnHeight)

        drawer = HexDrawer(shapeRenderer, sidelength, EngineConfig.VIEWPORT_CENTER)
    }

    override fun draw() {
        ScreenUtils.clear(Color.WHITE)
        var state: Wilsons.State? = null
        if (!algorithm.isFinished()) {
            state = algorithm.step()
        }

        if (state == null) {
            grid.cells().forEach {
                drawer.fill(it, if (it.links.isEmpty()) Color.GRAY else Color.WHITE)
            }
            shapeRenderer.color = Color.GRAY
            grid.cells().forEach(drawer::drawBorders)
            return
        }
        grid.cells().forEach {
            val isHead = state.path.first() == it
            val inPath = state.path.contains(it)
            if (isHead) {
                drawer.fill(it, Color.BLUE)
            } else if (state.state == Wilsons.State.StateType.LINKED && inPath) {
                drawer.fill(it, Color.GREEN)
            } else if (state.state == Wilsons.State.StateType.SEARCHING && inPath) {
                drawer.fill(it, Color.RED)
            } else if (state.unvisited.contains(it)) {
                drawer.fill(it, Color.GRAY)
            } else {
                drawer.fill(it, Color.WHITE)
                drawer.drawBorders(it, Color.GRAY)
            }
        }
        shapeRenderer.color = Color.GRAY
        grid.cells().forEach(drawer::drawBorders)
    }
}