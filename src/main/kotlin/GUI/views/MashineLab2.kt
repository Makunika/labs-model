package GUI.views

import javafx.application.Platform
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.paint.Paint
import javafx.scene.robot.Robot
import tornadofx.*
import java.awt.AWTException
import kotlin.random.Random


/**
 * @author Максим Пшибло
 */
class MashineLab2() : Fragment("Интерфейсы лаб2") {

    override val root: Parent;

    private var time: Long = 0
    private var isActive = false
    private var currentSize = 9
    private var currentButton = Random.nextInt(0, 9)

    val buttons: Array<Button> = Array(9) {
        button {
            text = it.toString()
            action {
                println("Hello!!!! im $text")
                if (text.toInt() == currentButton && isActive) {
                    isActive = false
                    val t = System.currentTimeMillis() - time
                    statLabel.text = "$t мс"
                    updateNumbers()
                    currentButton = Random.nextInt(0, 9)
                    number.text = currentButton.toString()
                    spawnPosition()
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
                    center {
                        pane {
                            style {
                                paddingRight = 50
                                paddingLeft = 40
                                paddingTop = 50
                            }
                            add(number)
                        }
                    }
                }
            }
            right {
                add(statLabel)
            }
            setOnMouseMoved {
                if (!isActive) {
                    isActive = true
                    time = System.currentTimeMillis()
                }
            }
        }
        spawnPosition()
        updateNumbers()
    }



    fun spawnPosition() {
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

    fun updateNumbers() {
        var s = arrayOf<Int>()
        buttons.forEach { button ->
            var n: Int
            do {
                n = Random.nextInt(0, currentSize)
            } while (s.contains(n))
            button.text = n.toString()
            s += n
        }
    }
}