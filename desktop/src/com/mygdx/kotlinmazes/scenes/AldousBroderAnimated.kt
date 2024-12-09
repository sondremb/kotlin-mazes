package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.drawers.HexDrawer
import com.mygdx.kotlinmazes.grids.hex.HexGrid
import com.mygdx.kotlinmazes.mazegeneration.AldousBroderState
import com.mygdx.kotlinmazes.mazegeneration.AldousBroderStepped
import com.mygdx.kotlinmazes.playScene
import kotlin.math.min
import kotlin.math.sqrt

fun main() {
    val grid = HexGrid(20)
    playScene(AldousBroderAnimated(grid))
}

class AldousBroderAnimated(private val grid: HexGrid) : Scene() {
    private lateinit var drawer: HexDrawer
    private val algorithm = AldousBroderStepped(grid)
    override fun init() {
        super.init()
        val sidelengthBasedOnWidth = EngineConfig.VIEWPORT_WIDTH / (3f * grid.radius + 2f)
        val sidelengthBasedOnHeight = EngineConfig.VIEWPORT_HEIGHT / ((2f * grid.radius + 1f) * sqrt(3f))
        val sidelength = min(sidelengthBasedOnWidth, sidelengthBasedOnHeight)

        drawer = HexDrawer(shapeRenderer, sidelength, EngineConfig.VIEWPORT_CENTER)
    }

    override fun draw() {
        ScreenUtils.clear(Color.WHITE)
        var state: AldousBroderState? = null
        if (!algorithm.isFinished()) {
            do {
                state = algorithm.step()
            } while (state?.addedCell == null)
        }

        grid.cells().forEach {
            if (state != null && state.checkedCell == it) {
                drawer.fill(it, Color.RED)
            } else if (state != null && state.addedCell == it) {
                drawer.fill(it, Color.GREEN)
            } else if (it.links.isEmpty()) {
                drawer.fill(it, Color.GRAY)
            }
        }
        shapeRenderer.color = Color.GRAY
        grid.cells().forEach(drawer::drawBorders)
    }
}