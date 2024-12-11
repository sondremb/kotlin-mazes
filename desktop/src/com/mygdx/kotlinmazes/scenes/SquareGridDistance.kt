package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.solving.Distance
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.utils.graphics.Gradient
import com.mygdx.kotlinmazes.utils.graphics.GradientSampler

fun main() {
    SquareGridDistance().play()
}

class SquareGridDistance() : Scene() {
    private lateinit var grid: SquareGrid
    private lateinit var distance: Distance
    private lateinit var drawer: SquareGridDrawer;
    private lateinit var gradientSampler: GradientSampler

    override fun init() {
        grid = SquareGrid(50, 80)
        // koble sammen alle cellene i griden
        grid.cells().forEach { cell ->
            cell.east?.link(cell)
            cell.south?.link(cell)
        }
        // beregn avstanden fra nederst i venstre til alle andre celler
        distance = Distance(grid.get(0, 0)!!)
        drawer = SquareGridDrawer(shapeRenderer, grid)
        val gradient = Gradient(Color.WHITE, Color.PURPLE)
        // sett opp en sampler som er skalert for max-avstanden i griden
        gradientSampler = gradient.sampler(0f, distance.max.toFloat())
    }

    override fun draw() {
        ScreenUtils.clear(Color.WHITE)
        grid.cells().forEach {
            drawer.fill(it, gradientSampler.sample(distance[it].toFloat()))
        }
        drawer.drawUnlinkedBorders(Color.BLACK)
    }
}