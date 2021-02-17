package GUI.views

import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import javafx.scene.paint.Color
import logic.ModelSquare
import tornadofx.*

class Square : Fragment("Площадь") {

    private var widthProperty: DoubleProperty = SimpleDoubleProperty()
    private var heightProperty: DoubleProperty = SimpleDoubleProperty()
    private var radiusProperty: DoubleProperty = SimpleDoubleProperty()
    private var xProperty: DoubleProperty = SimpleDoubleProperty();
    private var yProperty: DoubleProperty = SimpleDoubleProperty();
    private val stepProperty = SimpleStringProperty()

    private val resultAnalysisProperty = SimpleStringProperty()
    private val resultImiaticProperty = SimpleStringProperty()
    private val timeAnalysisProperty = SimpleStringProperty()
    private val timeImitaticProperty = SimpleStringProperty()

    override val root = borderpane {
        center {
            group {
                rectangle {
                    fill = Color(0.0, 0.0, 1.0, 0.7)
                    width = 300.0
                    widthProperty = widthProperty()
                    widthProperty.addListener(ChangeListener { _, _, newValue ->
                        xProperty.set(newValue.toDouble() / -2.0)
                    })
                    height = 150.0
                    heightProperty = heightProperty()
                    heightProperty.addListener(ChangeListener { _, _, newValue ->
                        yProperty.set(newValue.toDouble() / -2.0)
                    })
                    x = -width / 2
                    y = -height / 2
                    xProperty = xProperty()
                    yProperty = yProperty()
                }
                circle {
                    fill = Color(1.0, 0.0, 0.0, 0.7)
                    centerX = 0.0
                    centerY = 0.0
                    radius = 50.0
                    radiusProperty = radiusProperty()
                }
            }
        }
        left {
            vbox(20) {
                form {
                    fieldset("Данные для аналитического и имитационного алгоритма") {
                        field("Ширина прямоугольника") {
                            textfield(widthProperty)
                        }
                        field("Высота прямоугольника") {
                            textfield(heightProperty)
                        }
                        field("Радиус круга") {
                            textfield(radiusProperty)
                        }
                    }
                    fieldset("Данные только для имитационного алгоритма") {
                        field("Количество испытаний") {
                            textfield(stepProperty) {
                                text = "100000"
                            }
                        }
                    }
                    button("Вычислить") {
                        action {
                            val modelSquare = ModelSquare()
                            if (stepProperty.get() == null || !stepProperty.get().isInt()) {
                                alert(Alert.AlertType.WARNING, "Ошибка валидации", "Поля заполнены неверно")
                                return@action
                            }
                            modelSquare.width = widthProperty.get()
                            modelSquare.height = heightProperty.get()
                            modelSquare.radius = radiusProperty.get()
                            modelSquare.N = stepProperty.get().toLong()

                            if (!modelSquare.validateParam()) {
                                alert(Alert.AlertType.WARNING, "Ошибка валидации", "Поля заполнены неверно")
                            } else {
                                resultAnalysisProperty.set(modelSquare.squareAnalytic().toString())
                                timeAnalysisProperty.set(modelSquare.getTime().toString())
                                resultImiaticProperty.set(modelSquare.squareImitatic().toString())
                                timeImitaticProperty.set(modelSquare.getTime().toString())
                            }
                        }
                    }
                }
                form {
                    fieldset("Результаты") {
                        field("Результат аналитического метода") {
                            textfield(resultAnalysisProperty)
                        }
                        field("Время аналитического метода") {
                            textfield(timeAnalysisProperty)
                        }
                        field("Результат имитационного метода") {
                            textfield(resultImiaticProperty)
                        }
                        field("Время имтационного метода") {
                            textfield(timeImitaticProperty)
                        }
                    }
                }
            }

        }

    }
}