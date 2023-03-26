package com.mygdx.kotlinmazes

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ktx.app.KtxApplicationAdapter
import ktx.graphics.use


abstract class Scene : KtxApplicationAdapter {
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var viewport: Viewport
    lateinit var camera: Camera
    var showViewportEdge = false

    override fun create() {
        camera = OrthographicCamera(EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT).also {
            it.setToOrtho(false, EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT)
        }
        viewport = FitViewport(EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT, camera)
        shapeRenderer = ShapeRenderer().also {
            it.projectionMatrix.setToOrtho2D(0f, 0f, EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT)
        }
    }

    final override fun render() {
        super.render()
        draw()
        if (showViewportEdge) {
            shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
                shapeRenderer.color = Color.RED
                shapeRenderer.rect(0f, 0f, EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT)
            }
        }
    }

    abstract fun draw()

    override fun dispose() {
        shapeRenderer.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}