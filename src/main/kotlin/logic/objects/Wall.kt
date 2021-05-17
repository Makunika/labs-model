package logic.objects

import kotlin.math.cos
import kotlin.math.sin

class Wall(val x1: Double, val y1: Double, x2: Double, y2: Double) {

    var y2 = y2
        get() = field

    var x2 = x2
        get() = field

    var normal: Vector = Vector(y2 - y1, -(x2 - x1)).normalize()

    fun rotateNormal() {
        //normal = normal.rotate(180.0)
    }

    fun rotate(degree: Double) {
        val radians = Math.toRadians(degree)
        val length = Vector(x1, y1, x2, y2).length()
        x2 = x1 + length * sin(radians)
        y2 = y1 + length * cos(radians)
    }

    override fun toString(): String {
        return "NonSpeedObject(x1=$x1, y1=$y1, x2=$x2, y2=$y2, normal=$normal)"
    }
}