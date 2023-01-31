package com.mygdx.kotlinmazes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.math.norm
import com.mygdx.kotlinmazes.math.vec2Int


class DrawSquareGrid(private val grid: SquareGrid, private val distance: Distance) : Scene() {

    // farger basert på colormapet "plasma" fra matplotlib
    // hex-koder hentet fra https://waldyrious.net/viridis-palette-generator/
    private var gradient: Gradient = Gradient(
            Color.valueOf("#f0f921"),
            Color.valueOf("#f89540"),
            Color.valueOf("#cc4778"),
            Color.valueOf("#7e03a8"),
            Color.valueOf("#0d0887")
    )

    override fun init() {
        viewport = FitViewport(grid.width.toFloat(), grid.height.toFloat(), camera)
        shape.projectionMatrix.setToOrtho2D(0f, 0f, grid.width.toFloat(), grid.height.toFloat())
    }

    override fun render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        for (cell in grid.cells) {
            val r = cell.row
            val c = cell.column

            shape.begin(ShapeRenderer.ShapeType.Filled)
            val color = gradient.sample(norm(0f, distance.max.toFloat(), distance[cell].toFloat()))
            shape.color = color
            shape.rect(c.toFloat(), r.toFloat(), 1f, 1f)
            shape.end()

            shape.begin(ShapeRenderer.ShapeType.Line)
            shape.color = Color.BLACK
            if (!cell.isLinked(cell.north)) {
                shape.line(vec2Int(c, r + 1), vec2Int(c + 1, r + 1))
            }
            if (!cell.isLinked(cell.east)) {
                shape.line(vec2Int(c + 1, r), vec2Int(c + 1, r + 1))
            }
            if (!cell.isLinked(cell.south)) {
                shape.line(vec2Int(c, r), vec2Int(c + 1, r))
            }
            if (!cell.isLinked(cell.west)) {
                shape.line(vec2Int(c, r), vec2Int(c, r + 1))
            }
            shape.end()
        }
    }
}