package logic

import logic.objects.*
import kotlin.math.sin

class ModelHockey: BaseModel() {
//    val u = 0.15-0.20
//    val g = 9.81

    val walls = arrayOf(
        Wall(0.0, 0.0, 0.0, 200.0),
        Wall(50.0, 0.0, 50.00, 200.0),
        Wall(0.0, 200.0, 20.0, 300.0),
        Wall(50.0, 200.0, 70.0, 300.0),
        Wall(21.0,280.0, 69.0, 280.0)
    )
    val washer = Washer(10.0, 0.45, Vector(-15.0, 55.0), 25.0, 0.0)

    private var time: Long = 60
        set(value) {
            field = value
        }

    override fun getTime(): Long {
        return time
    }


    override fun validateParam(): Boolean {
        return time > 0
    }

    override fun fillInLoopParam() {
        TODO("Not yet implemented")
    }

    fun run(handle: (SpeedCoords) -> Unit) {
        washer.L = 0.0
        handle(SpeedCoords(washer.x, washer.y, arrayOf(), washer.vectorSpeed))
        for(i in 0..time) {
            val positions = washer.move(walls)
            handle(SpeedCoords(washer.x, washer.y, positions, washer.vectorSpeed))
        }
    }

    fun setWasherParams(speed: Int, angle: Int, u: Double) {
        washer.vectorSpeed = Vector(5.0, 5.0 * sin(Math.toRadians(angle.toDouble()))).normalize().mul(speed.toDouble())
        washer.u = u
    }
}