package logic.objects

import kotlin.math.abs

class Washer(val radius: Double, val weight: Double, vectorSpeed: Vector, x: Double, y: Double): MaterialObject(vectorSpeed, x, y) {


    override fun move(walls: Array<Wall>) {
        val unitVector = vectorSpeed.copy().normalize();
        val speedRunner = vectorSpeed.copy()
        val speed = vectorSpeed.length()
        var distAfter: Double
        while (speed >= speedRunner.length()) {
            val x = speedRunner.x + this.x
            val y = speedRunner.y + this.y
            for (wall in walls) {
                val v = Vector(wall.x1, wall.y1, wall.x2, wall.y2);
                val w0 = Vector(x, y, wall.x1, wall.y1)
                val w1 = Vector(x, y, wall.x2, wall.y2)
                val dist = when {
                    Vector.dot(w0, v) >= 0 -> w0.length()
                    Vector.dot(w1, v) <= 0 -> w1.length()
                    else -> abs(((wall.y1 - wall.y2) * x + (wall.x1 - wall.x2) * y + (wall.y1 * wall.x2 - wall.y2 * wall.x1)) / v.length())
                }
                if (dist <= radius) {
                    if (dist != radius) {
                        speedRunner.sub(unitVector)
                    }
                    speedRunner.rotate(wall.normal)
                    vectorSpeed.rotate(wall.normal)
                    unitVector.rotate(wall.normal)
                    println("time = $time, rotate!, unit = $unitVector")
                    break
                }
            }
            speedRunner.add(unitVector);
        }
        speedRunner.sub(unitVector)
        x += speedRunner.x
        y += speedRunner.y
        println("time = $time, ${toString()}, unit = $unitVector")
        time++
    }

    override fun toString(): String {
        return "Washer(radius=$radius, weight=$weight, vectorSpeed=$vectorSpeed, x=$x, y=$y)"
    }


}

fun main() {
    val walls = arrayOf(Wall(0.0, 0.0, 0.0, 100.0))
    val washer = Washer(10.0, 10.0, Vector(-10.0, 0.0), 205.5, 25.0)
    for (i in 0..100) {
        washer.move(walls)
    }

}