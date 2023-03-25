package com.mygdx.kotlinmazes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.kotlinmazes.utils.graphics.Gradient
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.utils.math.vec2Int
import ktx.graphics.use


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


    override fun init() {
        viewport = FitViewport(grid.width.toFloat(), grid.height.toFloat(), camera)
        shape.projectionMatrix.setToOrtho2D(0f, 0f, grid.width.toFloat(), grid.height.toFloat())
    }

    override fun render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        shape.use(ShapeRenderer.ShapeType.Filled) {
            for (cell in grid.cells) {
                val r = cell.row
                val c = cell.column

                it.color = gradient.sample(distance[cell].toFloat())
                it.rect(c.toFloat(), r.toFloat(), 1f, 1f)
            }
        }

        shape.use(ShapeRenderer.ShapeType.Line) {
            it.color = Color.BLACK
            it.line(vec2Int(0, 0), vec2Int(0, grid.height))
            it.line(vec2Int(0, 0), vec2Int(grid.width, 0))

            for (cell in grid.cells) {
                val r = cell.row
                val c = cell.column
                if (!cell.isLinked(cell.north)) {
                    it.line(vec2Int(c, r + 1), vec2Int(c + 1, r + 1))
                }
                if (!cell.isLinked(cell.east)) {
                    it.line(vec2Int(c + 1, r), vec2Int(c + 1, r + 1))
                }
            }
        }
    }
}