package logic.objects

import kotlin.math.sqrt

data class Vector(var x: Double, var y: Double) {

    companion object {
        fun dot(vector1: Vector, vector2: Vector) : Double = vector1.x * vector2.x + vector1.y * vector2.y
    }

    fun mul(number: Double): Vector {
        x *= number
        y *= number
        return this
    }

    fun sub(vector: Vector): Vector {
        x -= vector.x
        y -= vector.y
        return this
    }

    fun del(number: Double): Vector {
        x /= number
        y /= number
        return this
    }

    fun length(): Double = sqrt(x * x + y * y)

    fun rotate(normal: Vector): Vector {
        return this.sub(normal.mul(2.0 * dot(normal, this)))
    }

    fun normalize(): Vector {
        return this.del(this.length())
    }

    override fun toString(): String {
        return "Vector(x=$x, y=$y)"
    }


}
