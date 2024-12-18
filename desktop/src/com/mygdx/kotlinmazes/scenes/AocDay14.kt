package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.Config
import ktx.graphics.use
import kotlin.math.min


fun main() {
    AocDay14().play()
}

class AocDay14 : Scene() {
    private var seconds = -1
    private lateinit var robots: List<Robot>
    private val size = Coordinate(101, 103)
    private var sideLength: Float = 0F
    private lateinit var offset: Vector2
    private lateinit var font: BitmapFont
    private var timeSinceUpdate = 0f
    private var isPlaying = false

    override fun init() {
        super.init()

        font = BitmapFont()

        val rect = Rectangle(0F, 0F, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT)
        sideLength = min(rect.width / size.x, rect.height / size.y)
        offset = Vector2(
            rect.x + (rect.width - sideLength * size.x) / 2,
            rect.y + (rect.height - sideLength * size.y) / 2
        )
        robots = parseInput(lines.lines())
    }

    private fun shouldUpdate(): Boolean {
        if (isPlaying && timeSinceUpdate > 0.1f) {
            return true
        }
        return Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)
    }

    override fun update() {
        timeSinceUpdate += Gdx.graphics.deltaTime
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            isPlaying = !isPlaying
        }
        if (shouldUpdate()) {
            seconds++
            timeSinceUpdate = 0f
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            seconds--
        } else {
            return
        }
        robots.forEach {
            it.moveToStep(size, seconds)
        }
    }

    override fun draw() {
        ScreenUtils.clear(Color.WHITE)
        shapeRenderer.color = Color.BLACK
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            robots.forEach {
                shapeRenderer.rect(
                    it.position.x.toFloat() * sideLength + offset.x,
                    it.position.y.toFloat() * sideLength + offset.y,
                    sideLength,
                    sideLength
                )
            }
        }
        val spriteBatch = SpriteBatch().also { it.begin() }

        font.draw(spriteBatch, "Seconds: $seconds", 10F, 20F)
        spriteBatch.end()
    }
}

fun part2(input: List<String>): String {
    val robots = parseInput(input)
    var size = Coordinate(101, 103)
    var seconds = 100
    while (true) {
        seconds++
        robots.forEach {
            it.position = Coordinate(
                mod(it.position.x + it.velocity.x, size.x),
                mod(it.position.y + it.velocity.y, size.y)
            )
        }
        println(seconds)
        Thread.sleep(200)
    }
    return ""
}

private fun parseInput(input: List<String>): List<Robot> {
    return input.map { line ->
        val (pstring, vstring) = line.split(" ")
        val (px, py) = pstring.substring(2).split(",").map { it.toInt() }
        val (vx, vy) = vstring.substring(2).split(",").map { it.toInt() }
        Robot(Coordinate(px, py), Coordinate(vx, vy))
    }
}

data class Robot(var position: Coordinate, var velocity: Coordinate) {
    private val initialPosition = position.copy()
    fun move(size: Coordinate, steps: Int = 1) {
        position = Coordinate(
            mod(position.x + velocity.x * steps, size.x),
            mod(position.y + velocity.y * steps, size.y)
        )
    }

    fun moveToStep(size: Coordinate, step: Int) {
        position = Coordinate(
            mod(initialPosition.x + velocity.x * step, size.x),
            mod(initialPosition.y + velocity.y * step, size.y)
        )
    }
}

private fun mod(a: Int, b: Int): Int {
    return (a % b + b) % b
}

data class Coordinate(val x: Int, val y: Int) {
    companion object {
        val Origin = Coordinate(0, 0)
        val Up = Coordinate(0, -1)
        val Down = Coordinate(0, 1)
        val Left = Coordinate(-1, 0)
        val Right = Coordinate(1, 0)
    }

    val r = y
    val c = x

    operator fun plus(other: Coordinate) = Coordinate(x + other.x, y + other.y)
    operator fun minus(other: Coordinate) = Coordinate(x - other.x, y - other.y)
    operator fun times(scalar: Int) = Coordinate(x * scalar, y * scalar)
    operator fun div(scalar: Int) = Coordinate(x / scalar, y / scalar)
    val neighbors get() = listOf(Up, Down, Left, Right).map { this + it }
    fun turnRight() = Coordinate(-y, x)
    fun turnLeft() = Coordinate(y, -x)
}

const val lines = """p=74,51 v=36,-94
p=1,54 v=77,23
p=58,21 v=97,-3
p=61,79 v=-35,4
p=71,46 v=-34,-92
p=93,54 v=38,30
p=16,15 v=81,-26
p=31,5 v=62,6
p=9,45 v=49,-90
p=14,26 v=12,26
p=57,91 v=-36,-89
p=59,94 v=66,8
p=24,46 v=55,36
p=87,38 v=-65,-13
p=95,0 v=95,30
p=0,93 v=-43,63
p=76,68 v=39,57
p=43,91 v=-37,26
p=74,68 v=-51,-8
p=99,73 v=-17,-15
p=64,92 v=35,54
p=23,75 v=15,-76
p=93,47 v=77,-27
p=58,34 v=63,89
p=29,24 v=-84,3
p=71,41 v=38,5
p=17,79 v=47,-21
p=91,22 v=11,-81
p=19,11 v=-94,-65
p=30,59 v=2,88
p=7,47 v=15,32
p=64,18 v=-35,-20
p=32,68 v=48,-18
p=45,42 v=-41,93
p=6,28 v=-70,54
p=74,35 v=36,89
p=22,77 v=86,-36
p=52,40 v=-68,-22
p=21,78 v=19,92
p=15,62 v=-84,-30
p=48,86 v=-34,4
p=47,64 v=11,-85
p=42,52 v=65,-31
p=51,29 v=-35,13
p=60,9 v=-91,48
p=87,93 v=-26,73
p=54,61 v=-40,-49
p=88,33 v=-87,73
p=79,36 v=-29,-94
p=79,51 v=4,63
p=74,61 v=5,-2
p=82,82 v=-60,6
p=70,73 v=-63,23
p=94,68 v=10,-42
p=94,4 v=-59,56
p=8,3 v=48,-68
p=75,73 v=67,-70
p=49,36 v=-38,79
p=33,102 v=90,-28
p=52,82 v=-36,88
p=69,34 v=-37,-1
p=73,66 v=-34,69
p=1,41 v=-86,-10
p=42,41 v=-44,70
p=15,3 v=16,-18
p=88,46 v=75,36
p=67,33 v=-63,-60
p=97,102 v=79,-3
p=90,90 v=-30,56
p=15,15 v=62,-90
p=45,1 v=-73,54
p=91,7 v=-26,79
p=15,93 v=83,-53
p=69,100 v=75,-37
p=11,69 v=-86,-32
p=6,33 v=-54,28
p=92,97 v=56,37
p=76,42 v=4,57
p=14,34 v=50,30
p=21,54 v=-71,29
p=14,18 v=-50,26
p=13,102 v=18,18
p=29,3 v=78,-64
p=24,86 v=59,-55
p=54,88 v=60,-87
p=28,94 v=-14,92
p=65,69 v=69,-40
p=46,80 v=26,65
p=57,41 v=-31,57
p=96,40 v=-23,9
p=77,97 v=70,-30
p=38,8 v=89,-70
p=86,80 v=-92,61
p=57,36 v=98,57
p=35,34 v=-81,99
p=52,101 v=-97,-74
p=20,12 v=-48,22
p=58,88 v=-71,92
p=40,0 v=-39,-81
p=58,80 v=-7,-82
p=31,94 v=16,75
p=72,56 v=-98,40
p=9,70 v=-72,94
p=58,51 v=97,78
p=22,51 v=-82,-29
p=72,5 v=-65,-28
p=0,91 v=47,33
p=89,41 v=9,-12
p=59,39 v=98,34
p=8,62 v=30,-77
p=57,55 v=97,-25
p=3,58 v=70,76
p=87,12 v=87,69
p=64,27 v=96,93
p=52,13 v=30,89
p=100,5 v=-84,-28
p=57,38 v=-3,-69
p=43,19 v=-42,62
p=56,72 v=31,2
p=66,71 v=-97,-9
p=69,82 v=67,67
p=15,0 v=52,-47
p=59,100 v=-68,-26
p=87,27 v=-88,-23
p=41,83 v=-66,-61
p=69,67 v=-30,42
p=69,35 v=-68,36
p=12,31 v=-50,7
p=94,66 v=77,-23
p=67,37 v=35,70
p=79,30 v=-90,74
p=68,69 v=36,23
p=32,72 v=90,-55
p=53,61 v=95,78
p=72,92 v=-65,-7
p=20,94 v=-71,25
p=91,30 v=-26,66
p=5,39 v=82,-31
p=8,15 v=46,60
p=36,25 v=-5,-71
p=30,44 v=-78,-25
p=43,26 v=99,70
p=61,32 v=64,-81
p=44,100 v=22,-87
p=82,97 v=83,-81
p=22,33 v=52,-35
p=72,77 v=-83,52
p=53,46 v=-76,93
p=69,91 v=-34,-13
p=51,10 v=-38,-5
p=3,40 v=-42,7
p=29,15 v=-44,43
p=41,81 v=-18,-45
p=89,45 v=77,-10
p=35,6 v=-95,83
p=78,79 v=7,33
p=39,9 v=24,-41
p=9,30 v=-86,83
p=99,50 v=-88,-88
p=88,24 v=-92,49
p=11,4 v=94,93
p=28,88 v=22,-13
p=43,68 v=34,45
p=55,30 v=-99,7
p=0,8 v=41,22
p=40,56 v=-6,-4
p=16,51 v=80,-94
p=49,99 v=27,56
p=71,53 v=-97,-86
p=56,54 v=98,-44
p=87,44 v=80,-88
p=60,70 v=98,-40
p=24,15 v=-39,-57
p=37,78 v=-10,-75
p=54,27 v=30,87
p=14,21 v=17,5
p=40,2 v=26,-66
p=17,62 v=-55,-81
p=6,19 v=4,7
p=41,67 v=-70,75
p=29,60 v=-10,91
p=83,50 v=-66,51
p=94,20 v=78,-16
p=68,20 v=30,26
p=34,17 v=59,-62
p=95,45 v=42,40
p=10,36 v=-86,9
p=4,36 v=84,68
p=63,1 v=36,56
p=56,54 v=-75,59
p=26,87 v=89,33
p=16,1 v=-83,-28
p=64,87 v=38,-45
p=71,33 v=31,68
p=61,23 v=23,-91
p=9,1 v=17,75
p=96,84 v=-40,-73
p=8,19 v=12,-62
p=58,87 v=75,-40
p=43,2 v=-8,-47
p=64,89 v=-29,86
p=53,31 v=66,87
p=26,92 v=-84,-14
p=34,66 v=24,2
p=59,37 v=-33,89
p=11,36 v=15,17
p=84,75 v=87,26
p=8,17 v=83,85
p=9,64 v=-85,-33
p=76,57 v=-97,-27
p=63,86 v=-1,-51
p=0,78 v=-21,6
p=31,29 v=-79,-54
p=31,5 v=-9,-79
p=47,22 v=-5,-39
p=20,95 v=88,-30
p=52,11 v=41,97
p=95,47 v=78,93
p=91,49 v=11,-32
p=87,52 v=78,-73
p=87,98 v=74,77
p=68,61 v=34,82
p=2,67 v=-83,-44
p=78,11 v=92,26
p=46,33 v=-1,34
p=1,21 v=-16,-3
p=78,57 v=77,-27
p=71,5 v=38,-68
p=94,40 v=76,-54
p=68,65 v=24,-45
p=35,13 v=-74,-85
p=79,87 v=72,52
p=83,45 v=-66,-63
p=42,29 v=-36,-8
p=91,44 v=-38,23
p=100,45 v=80,15
p=87,50 v=20,-51
p=90,58 v=-59,4
p=40,48 v=62,49
p=39,58 v=-47,-48
p=71,15 v=-71,-53
p=23,57 v=-81,-42
p=14,62 v=49,-23
p=49,10 v=30,62
p=52,54 v=98,-69
p=8,54 v=83,72
p=91,25 v=-23,5
p=18,25 v=53,33
p=80,14 v=74,-22
p=37,11 v=-40,-12
p=87,17 v=-64,16
p=44,2 v=84,96
p=29,8 v=22,16
p=82,23 v=-68,-87
p=87,34 v=25,-27
p=3,56 v=-20,-21
p=34,8 v=-5,20
p=13,25 v=51,-37
p=84,28 v=9,67
p=19,57 v=-17,-65
p=2,9 v=8,-85
p=12,25 v=-16,24
p=32,54 v=-76,36
p=54,67 v=73,50
p=48,24 v=-34,20
p=7,19 v=-20,26
p=15,90 v=46,8
p=100,0 v=41,22
p=70,18 v=15,-30
p=54,76 v=97,18
p=41,58 v=-2,14
p=74,37 v=69,-79
p=42,87 v=-54,9
p=51,92 v=-38,48
p=75,9 v=-98,-22
p=31,25 v=-30,93
p=38,70 v=30,65
p=89,77 v=-28,-99
p=48,78 v=96,-78
p=83,86 v=12,-19
p=69,13 v=-25,-57
p=2,87 v=-3,-11
p=78,76 v=67,67
p=21,60 v=58,84
p=0,75 v=12,-19
p=55,96 v=84,35
p=31,35 v=-12,-66
p=44,79 v=-2,33
p=65,101 v=-65,-28
p=69,66 v=70,69
p=39,28 v=54,51
p=1,69 v=-20,46
p=93,30 v=11,-14
p=34,48 v=96,55
p=77,60 v=43,80
p=15,32 v=84,5
p=66,3 v=-5,4
p=71,16 v=41,-98
p=92,12 v=-92,3
p=83,60 v=-66,-80
p=11,11 v=83,37
p=34,74 v=-9,-99
p=48,72 v=-71,58
p=42,42 v=-49,99
p=52,68 v=96,63
p=29,25 v=-48,43
p=60,71 v=-16,-46
p=15,52 v=95,37
p=63,63 v=99,42
p=71,96 v=35,-47
p=87,44 v=-33,-56
p=31,54 v=36,-74
p=99,56 v=87,-48
p=65,45 v=-37,52
p=80,82 v=-24,-61
p=58,72 v=-98,2
p=23,4 v=15,37
p=60,71 v=-29,90
p=8,41 v=-51,53
p=62,66 v=-99,-2
p=19,11 v=55,73
p=6,51 v=-55,-27
p=43,48 v=66,51
p=69,73 v=-27,2
p=43,100 v=-40,33
p=82,75 v=-29,-36
p=83,24 v=39,-60
p=30,92 v=-75,-63
p=29,35 v=27,36
p=83,97 v=80,-22
p=88,17 v=75,-41
p=22,91 v=-19,-59
p=93,101 v=70,21
p=79,60 v=10,-80
p=34,59 v=-10,25
p=25,31 v=88,53
p=48,92 v=-58,-66
p=43,17 v=-72,-62
p=71,84 v=-70,96
p=74,14 v=-69,-3
p=18,93 v=17,-91
p=49,40 v=61,60
p=78,100 v=1,8
p=62,33 v=65,-54
p=83,47 v=-28,77
p=92,15 v=79,-22
p=69,7 v=-4,91
p=32,15 v=74,-71
p=60,98 v=-30,-13
p=20,68 v=-81,61
p=59,30 v=33,-33
p=30,28 v=-78,68
p=54,40 v=8,-16
p=93,102 v=-83,-26
p=94,0 v=44,73
p=79,83 v=-73,-26
p=63,29 v=32,-41
p=13,5 v=-9,36
p=66,55 v=-34,82
p=81,30 v=4,-69
p=42,62 v=90,88
p=60,6 v=-81,-38
p=80,62 v=44,4
p=33,43 v=92,-73
p=83,2 v=-28,56
p=21,96 v=-84,-72
p=59,52 v=-74,-2
p=3,7 v=20,81
p=60,62 v=66,-69
p=58,102 v=-36,-87
p=75,3 v=71,16
p=20,67 v=-71,68
p=44,32 v=-69,-73
p=13,51 v=-61,4
p=51,5 v=-39,-19
p=63,24 v=-34,-77
p=85,60 v=75,46
p=6,91 v=81,33
p=31,8 v=-76,-70
p=41,64 v=96,-23
p=87,9 v=-94,-1
p=6,68 v=-86,61
p=94,48 v=-91,-92
p=24,49 v=-48,15
p=1,70 v=-89,84
p=59,33 v=51,-59
p=87,66 v=70,6
p=45,28 v=94,91
p=95,68 v=45,82
p=61,0 v=3,-32
p=38,52 v=-10,-46
p=64,60 v=-96,78
p=17,31 v=36,-4
p=64,4 v=-34,-66
p=67,94 v=5,-64
p=49,9 v=61,-64
p=99,4 v=-56,-3
p=31,62 v=91,-23
p=95,39 v=49,78
p=34,46 v=61,61
p=30,32 v=14,92
p=24,87 v=49,25
p=51,69 v=29,-21
p=52,56 v=-71,-4
p=27,82 v=-79,-97
p=38,53 v=-77,76
p=84,68 v=69,-80
p=89,81 v=7,27
p=77,50 v=5,36
p=36,100 v=61,-34
p=73,70 v=13,-81
p=10,6 v=49,62
p=85,81 v=72,-15
p=89,51 v=8,36
p=9,19 v=-84,-58
p=82,44 v=-64,19
p=82,38 v=-94,-58
p=75,62 v=-32,-23
p=22,1 v=-14,39
p=76,18 v=67,91
p=70,0 v=-64,33
p=27,100 v=72,-45
p=34,43 v=23,47
p=5,31 v=-53,70
p=45,95 v=-70,6
p=77,67 v=84,2
p=80,0 v=70,48
p=87,1 v=50,94
p=91,68 v=18,55
p=77,50 v=37,78
p=62,93 v=33,77
p=38,46 v=-38,45
p=93,32 v=-91,72
p=84,25 v=24,26
p=21,89 v=-41,15
p=46,101 v=-38,94
p=36,91 v=-9,35
p=50,39 v=93,-75
p=17,25 v=-99,51
p=74,81 v=-30,52
p=49,42 v=86,-38
p=83,2 v=8,64
p=98,80 v=50,-55
p=100,66 v=80,2
p=12,91 v=57,-73
p=62,54 v=-41,11
p=23,23 v=-3,-66
p=70,30 v=18,6
p=77,40 v=-30,51
p=95,13 v=-73,45
p=19,0 v=-47,-32
p=45,31 v=-63,-28
p=34,4 v=-78,20
p=80,91 v=-62,-32
p=71,68 v=70,63
p=78,14 v=38,79
p=8,39 v=80,-52
p=93,73 v=-79,-1
p=54,55 v=-42,-71
p=81,50 v=41,97
p=60,88 v=71,-76
p=76,77 v=9,-59
p=20,98 v=-84,65
p=30,42 v=19,-28
p=56,33 v=22,66
p=34,74 v=-74,42
p=64,54 v=99,-48
p=59,78 v=-4,44
p=71,91 v=-64,-95
p=98,49 v=82,21
p=62,9 v=-79,7
p=97,66 v=-24,2
p=54,96 v=-98,-23
p=69,6 v=-37,24
p=27,87 v=-51,-97
p=6,82 v=-58,65
p=33,34 v=51,96
p=40,37 v=26,24
p=27,82 v=22,-93
p=0,96 v=48,-51
p=67,74 v=26,62
p=60,28 v=-86,74
p=96,48 v=80,15
p=58,96 v=-69,71
p=27,80 v=-10,-79
p=33,15 v=54,-26
p=76,14 v=-96,-83
p=10,16 v=-23,1
p=55,44 v=96,53
p=12,88 v=-56,-8
p=35,56 v=-77,-21
p=84,77 v=4,-63
p=63,35 v=1,-67
p=48,61 v=-37,99
p=14,81 v=-85,86
p=98,23 v=13,43
p=33,56 v=90,-82
p=27,20 v=79,26
p=91,102 v=79,12
p=26,91 v=-80,-91"""