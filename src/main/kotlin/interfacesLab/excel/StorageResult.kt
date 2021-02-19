package interfacesLab.excel

import interfacesLab.Experiment
import interfacesLab.Lab
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Максим Пшибло
 */
class StorageResult private constructor() {

    private val storageLab1: MutableList<Experiment> = LinkedList()

    fun addExperimentToLab1(experiment: Experiment) {
        storageLab1.add(experiment)
        println("save LAB1 = { time: ${experiment.time}, number: ${experiment.numberPanel} }")
    }

    fun saveToExcel() {
        val excel: Excel = Excel.instance
        excel.saveExperiment("Время", "Число элементов", getMiddleExperiment(storageLab1, Lab.Lab1.list), 0)
        excel.saveAndClose()
    }

    private fun getMiddleExperiment(storage: List<Experiment>, orderBy: Array<Int>): List<Experiment> {
        val result: MutableList<Experiment> = LinkedList()

        for (compareEx in orderBy) {
            val times: MutableList<Long> = ArrayList()
            for (experiment in storage) {
                if (compareEx == experiment.numberPanel) {
                    times.add(experiment.time)
                }
            }
            var middle: Long = 0
            for (time in times) {
                middle += time
            }
            middle /= times.size.toLong()
            println("get middle for " + compareEx + " = " + middle + " by size = " + times.size)
            result.add(Experiment(compareEx, middle))
        }

        return result
    }

//    private fun getMiddleDouble(
//        list: List<Pair<Long, Pair<Long, Long>>>,
//        orderOne: List<Int>,
//        orderTwo: List<Int>
//    ): List<Pair<Long, Double>> {
//        val result: MutableList<Pair<Long, Double>> = LinkedList<Pair<Long, Double>>()
//        for (i in orderOne.indices) {
//            val compareOne = orderOne[i]
//            for (j in orderTwo.indices) {
//                val compareTwo = orderTwo[j]
//                val times: MutableList<Long> = ArrayList()
//                for (pair in list) {
//                    if (pair.snd.fst.toInt() == compareOne && pair.snd.snd.toInt() == compareTwo) {
//                        times.add(pair.fst)
//                    }
//                }
//                var middle: Long = 0
//                for (time in times) {
//                    middle += time
//                }
//                middle /= times.size.toLong()
//                println("get middle for " + compareOne + ", " + compareTwo + " = " + middle + " by size = " + times.size)
//                result.add(Pair(middle, compareOne.toDouble() / compareTwo.toDouble()))
//            }
//        }
//        return result
//    }

    companion object {
        val instance = StorageResult()
    }
}