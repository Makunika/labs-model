package GUI.views

import javafx.beans.property.DoubleProperty
import javafx.scene.Group
import javafx.scene.control.Alert
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import logic.ModelHockey
import logic.ModelSquare
import logic.objects.Vector
import tornadofx.*
import kotlin.concurrent.thread
import kotlin.time.milliseconds

class Hockey: Fragment("Хоккей") {

    private var modelHockey = ModelHockey(60)
    lateinit var circle: Circle


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
                group.add(Line(wall.x1, wall.y1, wall.x2, wall.y2))
            }

        }
        left {
            vbox(20) {
                button("Вычислить") {
                    action {
                        if (!modelHockey.validateParam()) {
                            alert(Alert.AlertType.WARNING, "Ошибка валидации", "Поля заполнены неверно")
                        } else {
                            var array = arrayOf<Pair<Vector, Array<Vector>>>()
                            modelHockey.run { x, y, positions ->
                                array += Pair(Vector(x, y), positions)
                            }
                            val t = thread {
                                var delta = 0L
                                var t = System.currentTimeMillis()
                                for (pair in array) {
                                    var time = 1.seconds
                                    if (pair.second.isEmpty()) {
                                        println("empty")
                                        circle.centerXProperty().set(pair.first.x)
                                        circle.centerYProperty().set(pair.first.y)
                                        Thread.sleep(time.toMillis().toLong())
                                    } else {
                                        println("alo")
                                        time = (1.0 / pair.second.size).seconds
                                        for (vector in pair.second) {
                                            println("x = ${vector.x}, y = ${vector.y}")
                                            circle.centerXProperty().set(vector.x)
                                            circle.centerYProperty().set(vector.y)
                                            Thread.sleep(time.toMillis().toLong())
                                        }
                                        println("!!!!!!!!!!!!!!!")
                                    }

                                }
                                println("gavno")
                                modelHockey = ModelHockey(60)
                            }
                        }
                    }
                }
            }

        }
    }
}