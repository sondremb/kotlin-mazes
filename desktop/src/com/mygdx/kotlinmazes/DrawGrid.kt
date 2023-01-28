package com.mygdx.kotlinmazes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.kotlinmazes.math.vec2Int


class DrawGrid(val grid: Grid) : Scene() {

    override fun init() {
        viewport = FitViewport(grid.width.toFloat(), grid.height.toFloat(), camera)
        shape.projectionMatrix.setToOrtho2D(0f, 0f, grid.width.toFloat(), grid.height.toFloat())
    }

    override fun render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        shape.begin(ShapeRenderer.ShapeType.Line)
        shape.color = Color.BLACK
        for (cell in grid.cells) {
            val r = cell.row
            val c = cell.column
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
        }
        shape.end()
    }
}