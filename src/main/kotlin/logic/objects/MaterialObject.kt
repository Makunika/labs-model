package logic.objects

abstract class MaterialObject(var vectorSpeed: Vector, var x: Double, var y: Double) {

    abstract fun move(walls: Array<Wall>)

    var time = 0
        get() = field
}