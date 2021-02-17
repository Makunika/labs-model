package logic

import logic.objects.MaterialObject
import logic.objects.Vector
import logic.objects.Wall
import logic.objects.Washer

class ModelHockey(private val time: Long): BaseModel() {
//    val u = 0.15-0.20
//    val g = 9.81

    val walls = arrayOf(
        Wall(0.0, 20.0, 0.0, 200.0),
        Wall(50.0, 20.0, 50.00, 200.0),
        Wall(0.0, 200.0, 20.0, 300.0),
        Wall(50.0, 200.0, 70.0, 300.0),
        Wall(21.0,280.0, 69.0, 280.0)
    )
    val washer = Washer(10.0, 10.0, Vector(-55.0, 55.0), 25.0, 0.0)

    override fun getTime(): Long {
        return time
    }


    override fun validateParam(): Boolean {
        return time > 0
    }

    override fun fillInLoopParam() {
        TODO("Not yet implemented")
    }

    fun run(handle: (Double, Double, Array<Vector>) -> Unit) {
        handle(washer.x, washer.y, arrayOf())
        for(i in 0..time) {
            val positions = washer.move(walls)
            handle(washer.x, washer.y, positions)
        }
    }
}