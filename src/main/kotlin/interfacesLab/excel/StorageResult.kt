package interfacesLab.excel

import interfacesLab.Experiment
import interfacesLab.Lab
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Максим Пшибло
 */
class StorageResult private constructor() {

    private var currentColumn = 0
    private val storageLab: MutableList<Experiment> = LinkedList()

    fun addExperimentToLab(experiment: Experiment) {
        storageLab.add(experiment)
        println("save LAB = { time: ${experiment.time}, number: ${experiment.numberPanel} }")
    }

    fun clear() {
        storageLab.clear()
        println("clear storageLab")
    }

    fun saveToExcel(lab: Lab) {
        val excel: Excel = Excel.instance
        excel.saveExperiment("Число элементов", "Время", getMiddleExperiment(storageLab, lab.list), currentColumn, lab.description)
        excel.saveAndClose()
        currentColumn += 3
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

    companion object {
        val instance = StorageResult()
    }
}