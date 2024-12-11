package com.mygdx.kotlinmazes.utils.graphics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.PixmapIO
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.zip.Deflater


class ScreenshotFactory(private val prefix: String = "screenshot") {
    init {
        println(Gdx.files.local("").file().absolutePath)
    }

    fun getScreenshot() {
        val pixmap = Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"))
        PixmapIO.writePNG(Gdx.files.local("$prefix-$timestamp.png"), pixmap, Deflater.DEFAULT_COMPRESSION, true)
        pixmap.dispose()
    }
}