package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.kotlinmazes.EngineConfig
import ktx.app.KtxApplicationAdapter
import ktx.graphics.use
import ktx.math.vec2


abstract class Scene : KtxApplicationAdapter {
    lateinit var shapeRenderer: ShapeRenderer
    lateinit var viewport: Viewport
    lateinit var camera: Camera
    var showViewportEdge = true

    fun play() {
        val config = Lwjgl3ApplicationConfiguration()
        config.setForegroundFPS(60)
        config.setTitle("Kotlin Mazes")
        config.setWindowedMode(EngineConfig.VIEWPORT_WIDTH.toInt() / 2, EngineConfig.VIEWPORT_HEIGHT.toInt() / 2)
        Lwjgl3Application(this, config)
    }

    final override fun create() {
        camera = OrthographicCamera(EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT).also {
            it.setToOrtho(false, EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT)
        }
        viewport = FitViewport(EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT, camera)
        shapeRenderer = ShapeRenderer().also {
            it.projectionMatrix.setToOrtho2D(0f, 0f, EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT)
        }
        init()
    }

    open fun init() {}

    final override fun render() {
        super.render()
        update()
        draw()
        if (showViewportEdge) {
            shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
                shapeRenderer.color = Color.RED
                shapeRenderer.rect(0f, 0f, EngineConfig.VIEWPORT_WIDTH - 1f, EngineConfig.VIEWPORT_HEIGHT - 1f)
            }
        }
    }

    open fun update() {}

    abstract fun draw()


    override fun dispose() {
        shapeRenderer.dispose()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    fun cursorPosition(): Vector2 {
        // unproject the mouse pointer
        val pointer = vec2(Gdx.input.x.toFloat(), Gdx.input.y.toFloat())
        return viewport.unproject(pointer)
    }
}