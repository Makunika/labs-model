package logic

import logic.ModelSquare
import logic.ModelThrow

fun main() {

    val modelThrow = ModelThrow();
    //modelThrow.fillParam();
//    println("Результат работы алгоритма 1: ${modelThrow.lengthAnalytic()}. Время его работы: ${modelThrow.getTime()}");
//    println("Результат работы алгоритма 2: ${modelThrow.lengthImitatic()}. Время его работы: ${modelThrow.getTime()}");
    val modelSquare = ModelSquare();
    modelSquare.fillParam();
//    println("${modelSquare.squareAnalytic()}")
    println("Результат работы алгоритма 1: ${modelSquare.squareAnalytic()}. Время его работы: ${modelSquare.getTime()}");
    println("Результат работы алгоритма 2: ${modelSquare.squareImitatic()}. Время его работы: ${modelSquare.getTime()}");
}