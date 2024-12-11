package com.mygdx.kotlinmazes.drawers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.mygdx.kotlinmazes.utils.graphics.withColor

abstract class GridDrawer<T>(protected val shapeRenderer: ShapeRenderer) {
    abstract fun fill(cell: T)
    fun fill(cell: T, color: Color) {
        shapeRenderer.withColor(color) { fill(cell) }
    }

    abstract fun fill()
    fun fill(color: Color) {
        shapeRenderer.withColor(color) { fill() }
    }

    abstract fun drawUnlinkedBorders(cell: T)
    fun drawUnlinkedBorders(cell: T, color: Color) {
        shapeRenderer.withColor(color) { drawUnlinkedBorders(cell) }
    }

    abstract fun drawUnlinkedBorders()
    fun drawUnlinkedBorders(color: Color) {
        shapeRenderer.withColor(color) { drawUnlinkedBorders() }
    }


    abstract fun drawAllBorders(cell: T)
    fun drawAllBorders(cell: T, color: Color) {
        shapeRenderer.withColor(color) { drawAllBorders(cell) }
    }

    abstract fun drawAllBorders()
    fun drawAllBorders(color: Color) {
        shapeRenderer.withColor(color) { drawAllBorders() }
    }
}