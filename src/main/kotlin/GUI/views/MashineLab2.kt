package GUI.views

import interfacesLab.Experiment
import interfacesLab.Lab
import interfacesLab.excel.StorageResult
import javafx.application.Platform
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.paint.Paint
import javafx.scene.robot.Robot
import logic.ModelSquare
import tornadofx.*
import java.awt.AWTException
import kotlin.random.Random


/**
 * @author Максим Пшибло
 */
class MashineLab2 : Fragment("Интерфейсы лаб2") {

    override val root: Parent;

    private var time: Long = 0
    private var isActive = false
    private var currentSize = 2
    private var currentButton = Random.nextInt(0, currentSize)
    private var currentStep = 1
    private var activeLab = Lab.Lab1

    private val buttons: Array<Button> = Array(9) {
        button {
            text = it.toString()
            action {
                if (text.toInt() == currentButton && isActive) {
                    isActive = false
                    val t = System.currentTimeMillis() - time
                    statLabel.text = "$t мс"
                    currentStep++
                    StorageResult.instance.addExperimentToLab1(Experiment(currentSize, t))
                }
            }
            style {
                backgroundColor = MultiValue(arrayOf(Paint.valueOf("#808080")))
                border = null
                textFill = Paint.valueOf("#ffff00")
                fontSize = 14.pt
                fontFamily = "Courier New"
            }
            minHeight = 45.0
            maxHeight = 45.0
            prefHeight = 45.0
            prefWidth = 65.0
            minWidth = 65.0
            maxWidth = 65.0

        }
    }

    private val number: Label

    private val statLabel = label {
        text = "0 мс"
        style {
            fontSize = 14.pt
        }
    }

    init {

        number = label {
            text = currentButton.toString()
            style {
                paddingRight = 50
                paddingLeft = 40
                paddingTop = 50
                fontSize = 100.pt
                fontFamily = "Courier New"
            }
        }

        root = borderpane {
            left {
                style {
                    paddingLeft = 10
                    paddingTop = 10
                    paddingBottom = 40
                }
                borderpane {
                    left {
                        vbox(10) {
                            buttons.forEach { button -> this.add(button) }
                        }
                    }
                }
            }
            center {
                pane {
                    add(number)
                }
            }
            right {
                form {
                    style {
                        paddingRight = 500.0
                    }
                    add(statLabel)
                    button("Продолжить") {
                        action {
                            if (!isActive) {
                                when(activeLab) {
                                    Lab.Lab1 -> {
                                        if (currentStep == activeLab.numberStep) {
                                            currentStep = 1
                                            currentSize++
                                        }
                                        if (currentSize == activeLab.list.last() + 1) {
                                            StorageResult.instance.saveToExcel()
                                            statLabel.text = "Закончено!"
                                        }
                                        updateNumbers()
                                        currentButton = Random.nextInt(0, currentSize)
                                        number.text = currentButton.toString()
                                        spawnPosition()
                                    }
                                }
                                isActive = true
                                time = System.currentTimeMillis()
                            }
                        }
                    }
                    button("Отмена") {
                        action {
                            println("alo")
                        }
                    }
                }
            }
        }
        spawnPosition()
        updateNumbers()
    }



    private fun spawnPosition() {
        Platform.runLater {
            try {
                val robot = Robot()
                val coord = root.localToScreen(10.0 + 65 + 10, 10 + ((currentSize - 1) * (45 + 10) + 45).toDouble() / 2.0);
                robot.mouseMove(coord.x, coord.y)
            } catch (e: AWTException) {
                e.printStackTrace()
            }
        }
    }

    private fun updateNumbers() {
        var s = arrayOf<Int>()
        for(i in buttons.indices) {
            if (i >= currentSize) {
                buttons[i].isDisable = true
                buttons[i].isVisible = false
            } else {
                buttons[i].isDisable = false
                buttons[i].isVisible = true
                var n: Int
                do {
                    n = Random.nextInt(0, currentSize)
                } while (s.contains(n))
                buttons[i].text = n.toString()
                s += n
            }

        }
    }
}