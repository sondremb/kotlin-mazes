package com.mygdx.kotlinmazes

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.math.vec2Int
import ktx.math.times
import kotlin.math.min

const val STROKE = 1f
const val WIDTH = 400f
const val HEIGHT = 400f

class DrawGrid(val grid: Grid) : ApplicationAdapter() {
    lateinit var shape: ShapeRenderer
    override fun create() {
        shape = ShapeRenderer()
    }

    override fun render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        shape.begin(ShapeRenderer.ShapeType.Line)
        shape.color = Color.BLACK
        val x = STROKE + (WIDTH - STROKE) / grid.columns
        val y = STROKE + (HEIGHT - STROKE) / grid.rows
        val side = min(x, y).toInt()
        for (cell in grid.cells) {
            val r = cell.row
            val c = cell.column
            if (!cell.isLinked(cell.north)) {
                shape.line(vec2Int(c, r) * side, vec2Int(c + 1, r) * side)
            }
            if (!cell.isLinked(cell.east)) {
                shape.line(vec2Int(c + 1, r) * side, vec2Int(c + 1, r + 1) * side)
            }
            if (!cell.isLinked(cell.south)) {
                shape.line(vec2Int(c, r + 1) * side, vec2Int(c + 1, r + 1) * side)
            }
            if (!cell.isLinked(cell.west)) {
                shape.line(vec2Int(c, r) * side, vec2Int(c, r + 1) * side)
            }
        }
        shape.end()
    }

    override fun dispose() {
        shape.dispose()
    }
}