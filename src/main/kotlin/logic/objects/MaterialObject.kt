package logic.objects

import java.util.*

abstract class MaterialObject(var vectorSpeed: Vector, var x: Double, var y: Double) {

    abstract fun collision(nonSpeedObject: NonSpeedObject)

    fun move(time: Double) {
        x += vectorSpeed.x * time
        y += vectorSpeed.y * time
    }
}