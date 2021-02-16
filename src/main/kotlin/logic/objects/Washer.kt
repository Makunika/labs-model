package logic.objects

class Washer(val radius: Double, val weight: Double, vectorSpeed: Vector, x: Double, y: Double): MaterialObject(vectorSpeed, x, y) {


    override fun move(walls: Array<Wall>) {
        val unitVector = vectorSpeed.copy().normalize();
        val speedRunner = vectorSpeed.copy()
        val speed = vectorSpeed.length()
        while (speed <= speedRunner.length()) {
            val x = speedRunner.x + this.x
            val y = speedRunner.y + this.y
            for (wall in walls) {
                val v = Vector(wall.x1, wall.y1, wall.x2, wall.y2);
                val w0 = Vector(x, y, wall.x1, wall.y1)
                val w1 = Vector(x, y, wall.x2, wall.y2)
                val dist = when {
                    Vector.dot(w0, v) >= 0 -> w0.length()
                    Vector.dot(w1, v) <= 0 -> w1.length()
                    else -> ((wall.y1 - wall.y2) * x + (wall.x1 - wall.x2) * y + (wall.x1 + wall.y2 - wall.x2 * wall.y1)) / v.length()
                }
                if (dist <= radius) {
                    speedRunner.sub(unitVector)
                    speedRunner.rotate(wall.normal)
                    break
                }
            }
            speedRunner.add(unitVector);
        }
        x += speedRunner.x
        y += speedRunner.y
        println("time = $time, ${toString()}")
        time++;
    }

    override fun toString(): String {
        return "Washer(radius=$radius, weight=$weight, vectorSpeed=$vectorSpeed, x=$x, y=$y)"
    }


}

fun main() {
    val walls = arrayOf(Wall(0.0, 0.0, 0.0, 10.0))
    val washer = Washer(1.0, 10.0, Vector(-1.0, 0.0), 3.0, 5.0)
    washer.move(walls)
    washer.move(walls)
    washer.move(walls)
    washer.move(walls)
    washer.move(walls)
    washer.move(walls)
    washer.move(walls)
    washer.move(walls)
    washer.move(walls)
    washer.move(walls)
    washer.move(walls)
}