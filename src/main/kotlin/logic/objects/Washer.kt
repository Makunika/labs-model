package logic.objects

import kotlin.math.min

class Washer(val radius: Double, val weight: Double,vectorSpeed: Vector, x: Double, y: Double): MaterialObject(vectorSpeed, x, y) {


    override fun collision(nonSpeedObject: NonSpeedObject) {
        val vx = vectorSpeed.x.toInt() + 1;
        val vy = vectorSpeed.y.toInt() + 1;
        var min1: Int;
        var k: Int;
        if (vx > vy) {
            min1 = vy;
            k = vx / vy;
        } else if (vx == vy) {
            min1 = vx;
            k = vx / vy;
        } else {

        }
        val vOneX: Int;
        val vOneY: Int;
    }
}