package GUI

import javafx.concurrent.Service
import javafx.concurrent.Task

/**
 * @author Максим Пшибло
 */
class Worker(var handle: () -> Unit): Service<Unit>() {

    fun startWorker() {
        if (isRunning) {
            this.cancel()
        }
        reset()
        start()
    }

    override fun createTask(): Task<Unit> {
        return object : Task<Unit>() {
            override fun call() {
                handle()
            }
        }
    }
}