package com.mygdx.kotlinmazes

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.Viewport


abstract class Scene : ApplicationAdapter() {
    lateinit var shape: ShapeRenderer
    lateinit var viewport: Viewport
    val camera = OrthographicCamera()
    override fun create() {
        shape = ShapeRenderer()
        init()
    }

    abstract fun init()

    override fun dispose() {
        shape.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}