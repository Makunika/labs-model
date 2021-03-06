package logic.objects

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.sin

class Washer(val radius: Double, var u: Double, vectorSpeed: Vector, x: Double, y: Double): MaterialObject(vectorSpeed, x, y) {

    var L: Double = 0.0

    override fun move(walls: Array<Wall>): Array<Vector> {
        if (vectorSpeed.length().toInt() != 0) {
            var unitVector = vectorSpeed.copy().normalize()
            println("unit = $unitVector")
            val speedRunner = unitVector.copy()
            val speed = vectorSpeed.length()
            L += speed
            var position = arrayOf<Vector>()
            var x = this.x
            var y = this.y
            while (speed >= speedRunner.length()) {
                //println("vectorSpeed = $vectorSpeed, speedRunner = $speedRunner")
                x += unitVector.x
                y += unitVector.y
                position += Vector(x, y)
                //println("x = $x, y = $y")
                for (wall in walls) {
                    val v = Vector(wall.x1, wall.y1, wall.x2, wall.y2);
                    val w0 = Vector(x, y, wall.x1, wall.y1)
                    val w1 = Vector(x, y, wall.x2, wall.y2)
                    val dist = when {
                        Vector.dot(w0, v) >= 0 -> w0.length()
                        Vector.dot(w1, v) <= 0 -> w1.length()
                        else -> {
                            val vL = v.length()
                            val w0L = w0.length()
                            val w1L = w1.length()
                            val angle = acos((w1L * w1L + vL * vL - w0L * w0L) / (2.0 * w1L * vL))
                            //println("angle = $angle")
                            sin(angle) * w1L
                        }
                    }
//                    println("wall = $wall, dist = $dist")
                    if (dist <= radius) {
                        if (dist != radius) {
                            speedRunner.sub(unitVector)
                        } else {
                            if (Vector.dot(w0, v) >= 0 || Vector.dot(w1, v) <= 0) {
                                break
                            }
                        }
                        println("====")
                        println("wall = $wall, dist = $dist")
                        println("vectorSpeed = $vectorSpeed, speedRunner = $speedRunner, dist = $dist")
                        speedRunner.rotate(wall.normal)
                        vectorSpeed.rotate(wall.normal)
                        val length = vectorSpeed.length() - (u * 15)
                        if (length <= 0) {
                            vectorSpeed = Vector(0.0, 0.0)
                        } else {
                            vectorSpeed.normalize().mul(length)
                        }
                        unitVector = vectorSpeed.copy().normalize()
                        println("rotate!, vectorSpeed = $vectorSpeed, speedRunner = $speedRunner, dist = $dist")
                        break
                    }
                }
                speedRunner.add(unitVector);
            }
            speedRunner.sub(unitVector)
            this.x = x
            this.y = y
            println("time = $time, ${toString()}, unit = $unitVector")
            time++
            val length = vectorSpeed.length() - (u * 9.81)
            if (length <= 0) {
                vectorSpeed = Vector(0.0, 0.0)
            } else {
                vectorSpeed.normalize().mul(length)
            }
            //println("speed = ${vectorSpeed.length()}")
            println("exit, this = $this\n=========================================================================================\n")
            return position
        } else {
            return arrayOf()
        }
    }

    override fun toString(): String {
        return "Washer(radius=$radius, u=$u, vectorSpeed=$vectorSpeed, x=$x, y=$y, L=$L)"
    }


}

fun main() {
    val walls = arrayOf(Wall(0.0, 0.0, 0.0, 100.0))
    val washer = Washer(10.0, 10.0, Vector(-15.0, 15.0), 15.0, -10.0)
    for (i in 0..2) {
        washer.move(walls)
    }

}