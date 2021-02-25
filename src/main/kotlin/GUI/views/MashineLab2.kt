package GUI.views

import interfacesLab.Experiment
import interfacesLab.Lab
import interfacesLab.excel.StorageResult
import javafx.application.Platform
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.paint.Paint
import javafx.scene.robot.Robot
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import tornadofx.*
import java.awt.AWTException
import kotlin.random.Random


/**
 * @author Максим Пшибло
 */
class MashineLab2 : Fragment("Интерфейсы лаб2") {

    override val root: Parent;

    private val width = 65
    private val height = 40

    private var time = 0L
    private var isActive = false
    private var currentStep = 0
    private var activeLab = Lab.Lab1
    private var currentButton = Random.nextInt(0, activeLab.getCurrent())

    private val buttons: Array<Button> = Array(activeLab.list.last()) {
        button {
            text = it.toString()
            action {
                if (text.toInt() == currentButton && isActive) {
                    isActive = false
                    val t = System.currentTimeMillis() - time
                    statLabel.text = "$t мс"
                    currentStep++
                    StorageResult.instance.addExperimentToLab(Experiment(activeLab.getCurrent(), t))
                    println(activeLab.toString())
                }
            }
            style {
                backgroundColor = MultiValue(arrayOf(Paint.valueOf("#808080")))
                border = null
                textFill = Paint.valueOf("#ffff00")
                fontSize = 14.pt
                fontFamily = "Courier New"
            }
            minHeight = this@MashineLab2.height.toDouble()
            maxHeight = this@MashineLab2.height.toDouble()
            prefHeight = this@MashineLab2.height.toDouble()
            prefWidth = this@MashineLab2.width.toDouble()
            minWidth = this@MashineLab2.width.toDouble()
            maxWidth = this@MashineLab2.width.toDouble()
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
                                if (currentStep == activeLab.numberStep) {
                                    currentStep = 0
                                    if (activeLab.getCurrent() == activeLab.list.last()) {
                                        StorageResult.instance.saveToExcel(activeLab.description)
                                        StorageResult.instance.clear()
                                        statLabel.text = "Закончено! Нажмите продолжить для следующего теста"
                                        if (!activeLab.hasNext())
                                            statLabel.text = "Закончено! Все тесты пройдены!"
                                        else
                                            activeLab = activeLab.nextLab()

                                        isActive = false
                                        return@action
                                    } else {
                                        activeLab.next()
                                    }
                                }
                                statLabel.text = ""
                                updateNumbers()
                                updateExperiment()
                                spawnPosition()
                                isActive = true
                                time = System.currentTimeMillis()
                            }
                        }
                    }
                    button("Отмена") {
                        action {
                            println("Отмена $activeLab")
                            isActive = false
                        }
                    }
                }
            }
        }
        spawnPosition()
        updateNumbers()
    }

    private fun updateExperiment() {
        currentButton = Random.nextInt(0, activeLab.getCurrent())
        number.text = currentButton.toString()
        when (activeLab) {
            Lab.Lab2_1_1 -> {
                number.isVisible = false
                buttons.forEach { button ->
                    if (button.text.toInt() == currentButton) {
                        button.style {
                            backgroundColor = MultiValue(arrayOf(Paint.valueOf("#808080")))
                            textFill = Paint.valueOf("#ffff00")
                            fontSize = 14.pt
                            fontFamily = "Courier New"
                            fontStyle = FontPosture.findByName(activeLab.value)
                        }
                    } else {
                        button.style {
                            backgroundColor = MultiValue(arrayOf(Paint.valueOf("#808080")))
                            textFill = Paint.valueOf("#ffff00")
                            fontSize = 14.pt
                            fontFamily = "Courier New"
                        }
                    }
                }
            }
            Lab.Lab2_1_2 -> {
                number.isVisible = false
                buttons.forEach { button ->
                    if (button.text.toInt() == currentButton) {
                        button.style {
                            backgroundColor = MultiValue(arrayOf(Paint.valueOf("#808080")))
                            textFill = Paint.valueOf("#ffff00")
                            fontSize = 14.pt
                            fontFamily = "Courier New"
                            fontWeight = FontWeight.findByName(activeLab.value)
                        }
                    } else {
                        button.style {
                            backgroundColor = MultiValue(arrayOf(Paint.valueOf("#808080")))
                            textFill = Paint.valueOf("#ffff00")
                            fontSize = 14.pt
                            fontFamily = "Courier New"
                        }
                    }
                }
            }
            Lab.Lab2_1_3 -> {
                number.isVisible = false
                buttons.forEach { button ->
                    if (button.text.toInt() == currentButton) {
                        button.style {
                            backgroundColor = MultiValue(arrayOf(Paint.valueOf("#808080")))
                            textFill = Paint.valueOf("#ffff00")
                            fontSize = 14.pt
                            underline = activeLab.value.toBoolean()
                        }
                    } else {
                        button.style {
                            backgroundColor = MultiValue(arrayOf(Paint.valueOf("#808080")))
                            textFill = Paint.valueOf("#ffff00")
                            fontSize = 14.pt
                            fontFamily = "Courier New"
                        }
                    }
                }
            }
            Lab.Lab2_2_1, Lab.Lab2_2_2, Lab.Lab2_2_3, Lab.Lab2_2_4 -> {
                number.isVisible = false
                buttons.forEach { button ->
                    if (button.text.toInt() == currentButton) {
                        button.style {
                            backgroundColor = MultiValue(arrayOf(Paint.valueOf("#808080")))
                            textFill = Paint.valueOf("#ffff00")
                            fontSize = (activeLab.value.toInt()).pt
                            fontFamily = "Courier New"
                        }
                    } else {
                        button.style {
                            backgroundColor = MultiValue(arrayOf(Paint.valueOf("#808080")))
                            textFill = Paint.valueOf("#ffff00")
                            fontSize = 14.pt
                            fontFamily = "Courier New"
                        }
                    }
                }
            }
            else -> return
        }
    }


    private fun spawnPosition() {
        Platform.runLater {
            try {
                val robot = Robot()
                val coord = root.localToScreen(10.0 + width + 10, 10 + ((activeLab.getCurrent() - 1) * (height + 10) + height).toDouble() / 2.0);
                robot.mouseMove(coord.x, coord.y)
            } catch (e: AWTException) {
                e.printStackTrace()
            }
        }
    }

    private fun updateNumbers() {
        var s = arrayOf<Int>()
        for(i in buttons.indices) {
            if (i >= activeLab.getCurrent()) {
                buttons[i].isDisable = true
                buttons[i].isVisible = false
            } else {
                buttons[i].isDisable = false
                buttons[i].isVisible = true
                var n: Int
                do {
                    n = Random.nextInt(0, activeLab.getCurrent())
                } while (s.contains(n))
                buttons[i].text = n.toString()
                s += n
            }

        }
    }
}