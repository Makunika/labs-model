package GUI.views

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.scene.control.Alert
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import logic.ModelHockey
import logic.objects.SpeedCoords
import logic.objects.Vector
import tornadofx.*
import java.lang.Math.sin
import kotlin.concurrent.thread

class Hockey: Fragment("Хоккей") {

    private var modelHockey = ModelHockey()
    private val speed = SimpleIntegerProperty(15)
    private val angleWasher = SimpleIntegerProperty(25)
    private val width = SimpleIntegerProperty(200)
    private val height = SimpleIntegerProperty(200)
    private val u = SimpleDoubleProperty(0.15)
    private val time = SimpleLongProperty(60)
    private val b = SimpleIntegerProperty(20)

    lateinit var circle: Circle
    private var thread: Thread? = null


    override val root = borderpane {
        center {
            line {
                startX = modelHockey.walls[0].x1
                startY = modelHockey.walls[0].y1
                endX = modelHockey.walls[0].x2
                endY = modelHockey.walls[0].y2
            }
            val group = group {
                circle = circle {
                    fill = Color(1.0, 0.0, 0.0, 0.7)
                    centerX = modelHockey.washer.x
                    centerY = modelHockey.washer.y
                    radius = modelHockey.washer.radius
                }
            }
            modelHockey.walls.forEach { wall ->
                group.add(Line(wall.x1, -wall.y1, wall.x2, -wall.y2))
            }

        }
        left {
            vbox(20) {
                form {
                    fieldset("Данные для работы") {
                        field("Ширина") {
                            textfield(this@Hockey.width)
                        }
                        field("Высота") {
                            textfield(this@Hockey.height)
                        }
                        field("Скорость") {
                            textfield(speed)
                        }
                        field("Угол направления шайбы") {
                            textfield(angleWasher)
                        }
                        field("Коэф. трения") {
                            textfield(u)
                        }
                        field("Расстояния от правого бока") {
                            textfield(b)
                        }
                        field("Время") {
                            textfield(time)
                        }
                    }
                }
                button("Вычислить") {
                    action {
                        modelHockey.setWasherParams(speed.get(), angleWasher.get(), u.get())
                        if (!modelHockey.validateParam()) {
                            alert(Alert.AlertType.WARNING, "Ошибка валидации", "Поля заполнены неверно")
                        } else {
                            val array: MutableList<SpeedCoords> = ArrayList()
                            modelHockey.run { speedCoords ->
                                array.add(speedCoords)
                            }
                            thread = thread {
                                for (sc in array) {
                                    var time = 1.seconds
                                    if (sc.positions.isEmpty()) {
                                        println("empty")
                                        if (sc.speed.x == 0.0 && sc.speed.y == 0.0) {
                                            break
                                        }
                                        circle.centerXProperty().set(sc.x)
                                        circle.centerYProperty().set(-sc.y)
                                        Thread.sleep(time.toMillis().toLong())
                                    } else {
                                        time = (1.0 / sc.positions.size).seconds
                                        for (vector in sc.positions) {
                                            println("x = ${vector.x}, y = ${vector.y}")
                                            circle.centerXProperty().set(vector.x)
                                            circle.centerYProperty().set(-vector.y)
                                            Thread.sleep(time.toMillis().toLong())
                                        }
                                    }
                                }
                                println("L = ${modelHockey.washer.L}")
                            }
                        }
                    }
                }
            }

        }
    }
}