package com.mygdx.kotlinmazes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.kotlinmazes.utils.graphics.Gradient
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.utils.math.vec2Int
import ktx.graphics.use
import ktx.math.times
import ktx.math.vec2
import kotlin.math.min


class DrawSquareGrid(private val grid: SquareGrid, private val distance: Distance) : Scene() {

    // farger basert på colormapet "plasma" fra matplotlib
    // hex-koder hentet fra https://waldyrious.net/viridis-palette-generator/
    private var gradient = Gradient(
        Color.valueOf("#f0f921"),
        Color.valueOf("#f89540"),
        Color.valueOf("#cc4778"),
        Color.valueOf("#7e03a8"),
        Color.valueOf("#0d0887")
    ).sampler(0f, distance.max.toFloat())


    override fun draw() {
        val width = EngineConfig.VIEWPORT_WIDTH / grid.width
        val height = EngineConfig.VIEWPORT_HEIGHT / grid.height
        val size = min(width, height)

        ScreenUtils.clear(1f, 1f, 1f, 1f)
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            for (cell in grid.cells) {
                val r = cell.row
                val c = cell.column

                it.color = gradient.sample(distance[cell].toFloat())
                it.rect(c * size, r * size, size, size)
            }
        }

        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            it.color = Color.BLACK
            it.line(vec2(0f, 0f), vec2(0f, grid.height * size))
            it.line(vec2(0f, 0f), vec2(grid.width * size, 0f))

            for (cell in grid.cells) {
                val r = cell.row
                val c = cell.column
                if (!cell.isLinked(cell.north)) {
                    it.line(vec2Int(c, r + 1) * size, vec2Int(c + 1, r + 1) * size)
                }
                if (!cell.isLinked(cell.east)) {
                    it.line(vec2Int(c + 1, r) * size, vec2Int(c + 1, r + 1) * size)
                }
            }
        }
    }
}