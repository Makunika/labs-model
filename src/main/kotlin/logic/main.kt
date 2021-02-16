package logic

import logic.ModelSquare
import logic.ModelThrow
import logic.objects.Vector
import tornadofx.Vector2D
import kotlin.math.sqrt

fun main() {

//    val modelThrow = ModelThrow();
//    //modelThrow.fillParam();
////    println("Результат работы алгоритма 1: ${modelThrow.lengthAnalytic()}. Время его работы: ${modelThrow.getTime()}");
////    println("Результат работы алгоритма 2: ${modelThrow.lengthImitatic()}. Время его работы: ${modelThrow.getTime()}");
//    val modelSquare = ModelSquare();
//    modelSquare.fillParam();
////    println("${modelSquare.squareAnalytic()}")
//    println("Результат работы алгоритма 1: ${modelSquare.squareAnalytic()}. Время его работы: ${modelSquare.getTime()}");
//    println("Результат работы алгоритма 2: ${modelSquare.squareImitatic()}. Время его работы: ${modelSquare.getTime()}");
    val vector = Vector(-2.0 , 0.0)
    val normal = Vector(-0.5, -sqrt(3.0) / 2)
    print(vector.rotate(normal))
    print(vector.length())

}