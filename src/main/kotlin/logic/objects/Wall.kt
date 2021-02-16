package logic.objects

class Wall(val x1: Double, val y1: Double, val x2: Double, val y2: Double) {

    val normal: Vector = Vector(y2 - y1, -(x2 - x1)).normalize();

    override fun toString(): String {
        return "NonSpeedObject(x1=$x1, y1=$y1, x2=$x2, y2=$y2, normal=$normal)"
    }


}