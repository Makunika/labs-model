package GUI.views

import GUI.Worker
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.beans.property.SimpleStringProperty
import javafx.concurrent.Service
import javafx.scene.Group
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
    private val angleWall = SimpleIntegerProperty(25)
    private val width = SimpleIntegerProperty(200)
    private val height = SimpleIntegerProperty(200)
    private val u = SimpleDoubleProperty(0.15)
    private val time = SimpleIntegerProperty(60)
    private val b = SimpleIntegerProperty(20)
    private val LResult = SimpleDoubleProperty()
    private val timeResult = SimpleStringProperty()


    lateinit var circle: Circle
    lateinit var group: Group
    private var worker = Worker {}


    override val root = borderpane {
        center {
            group = group {
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
                        field("Угол стены") {
                            textfield(angleWall)
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
                        button("Вычислить") {
                            action {
                                try {
                                    modelHockey.setWasherParams(speed.get(), angleWasher.get(), u.get())
                                    modelHockey.setParams(b.get(), this@Hockey.width.get(), angleWall.get(), this@Hockey.height.get())
                                    modelHockey.time = time.get()
                                    group.children.clear()
                                    circle.centerXProperty().set(modelHockey.washer.x)
                                    circle.centerYProperty().set(-modelHockey.washer.y)
                                    group.add(circle)
                                    modelHockey.walls.forEach { wall ->
                                        group.add(Line(wall.x1, -wall.y1, wall.x2, -wall.y2))
                                    }

                                    if (!modelHockey.validateParam()) {
                                        alert(Alert.AlertType.WARNING, "Ошибка валидации", "Поля заполнены неверно")
                                    } else {
                                        val array: MutableList<SpeedCoords> = ArrayList()
                                        modelHockey.run { speedCoords ->
                                            array.add(speedCoords)
                                        }
                                        LResult.set(modelHockey.washer.L)
                                        timeResult.set("${modelHockey.washer.time} cек")

                                        worker.handle = {
                                            for (sc in array) {
                                                var time = 1.seconds
                                                if (sc.positions.isEmpty()) {
                                                    println("empty: x = ${sc.x}, y = ${sc.y}, speed = ${sc.speed}")
                                                    if (sc.speed.length().toInt() == 0) {
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
                                        worker.startWorker()
                                    }
                                }
                                catch (e: RuntimeException) {
                                    e.printStackTrace()
                                    alert(Alert.AlertType.WARNING, "Ошибка", e.message)
                                }

                            }
                        }
                    }
                    fieldset("Результаты") {
                        field("Пройдено") {
                            textfield(LResult)
                        }
                        field("Время до остановки") {
                            textfield(timeResult)
                        }
                    }
                }
            }
        }
    }
}