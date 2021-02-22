package logic

import logic.objects.*
import kotlin.math.sin

class ModelHockey: BaseModel() {
//    val u = 0.15-0.20
//    val g = 9.81

    var walls = arrayOf(
        Wall(0.0, 0.0, 0.0, 200.0),
        Wall(50.0, 0.0, 50.00, 200.0),
        Wall(0.0, 200.0, 20.0, 300.0),
        Wall(50.0, 200.0, 70.0, 300.0),
        Wall(21.0,280.0, 69.0, 280.0)
    )
    val washer = Washer(10.0, 0.45, Vector(-15.0, 55.0), 25.0, 0.0)

    var time: Int = 60
        set(value) {
            if (time <= 0) {
                throw IllegalArgumentException("time <= 0")
            }
            field = value
        }

    override fun getTime(): Long {
        return time.toLong()
    }


    override fun validateParam(): Boolean {
        return true
    }

    override fun fillInLoopParam() {
        TODO("Not yet implemented")
    }

    fun run(handle: (SpeedCoords) -> Unit) {
        washer.L = 0.0
        washer.time = 0
        handle(SpeedCoords(washer.x, washer.y, arrayOf(), washer.vectorSpeed.copy()))
        for(i in 0..time) {
            val positions = washer.move(walls)
            handle(SpeedCoords(washer.x, washer.y, positions, washer.vectorSpeed.copy()))
        }
    }


    fun setWasherParams(speed: Int, angle: Int, u: Double) {
        if (angle < 0 || angle > 180) {
            throw IllegalArgumentException("Washer:  < 0 || angle > 180")
        }
        washer.vectorSpeed = Vector(1.0, 0.0).rotate(angle.toDouble()).mul(speed.toDouble())
        washer.u = u
    }

    fun setParams(b: Int, l: Int, angle: Int, h: Int) {
        if (b + washer.radius >= l) {
            throw IllegalArgumentException("Wall: b + washer.radius >= l")
        }
        if (angle < 0 || angle > 180) {
            throw IllegalArgumentException("Wall: angle < 0 || angle > 180")
        }
        if (h < washer.radius) {
            throw IllegalArgumentException("Wall: h < washer.radius")
        }
        walls = arrayOf(
            Wall(0.0, 0.0, 0.0, h.toDouble()),
            Wall(l.toDouble(), 0.0, l.toDouble(), h.toDouble()),
            Wall(0.0, h.toDouble(), 300.0, h.toDouble()),
            Wall(l.toDouble(), h.toDouble(), 300.0 + l.toDouble(), h.toDouble()),
            Wall(21.0,280.0, 69.0, 280.0)
        )
        walls[2].rotate(angle.toDouble())
        walls[3].rotate(angle.toDouble())
        walls[4] = Wall(-100.0,300.0, 100.0, 300.0)

        washer.x = b.toDouble()
        washer.y = 0.0
    }
}