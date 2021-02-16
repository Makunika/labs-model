package logic.objects

class NonSpeedObject(val x1: Double, val y1: Double, val x2: Double, val y2: Double) {

    val normal: Vector = Vector(y2 - y1, -(x2 - x1)).normalize();

    override fun toString(): String {
        return "NonSpeedObject(x1=$x1, y1=$y1, x2=$x2, y2=$y2, normal=$normal)"
    }


}

fun main() {
    var nonSpeedObject = NonSpeedObject(0.0, 0.0, 10.0, 10.0);

    print(nonSpeedObject)
}