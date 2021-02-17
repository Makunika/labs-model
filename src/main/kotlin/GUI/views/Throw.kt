package GUI.views

import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.control.Alert
import javafx.scene.paint.Color
import logic.ModelThrow
import tornadofx.*

class Throw : Fragment("Бросок") {

    private val vProperty = SimpleStringProperty()
    private val alphaProperty = SimpleStringProperty()
    private val stepProperty = SimpleStringProperty()
    private val resultAnalysisProperty = SimpleStringProperty()
    private val resultImiaticProperty = SimpleStringProperty()
    private val timeAnalysisProperty = SimpleStringProperty()
    private val timeImitaticProperty = SimpleStringProperty()
    private var chart: ObjectProperty<ObservableList<XYChart.Data<Number, Number>>>? = null

    override val root = borderpane {
        center {
            pane {
                style {
                    padding = box(10.px);
                    backgroundColor += Color(0.0, 1.0, 1.0, 1.0)
                }
                linechart("График броска", NumberAxis(), NumberAxis()) {
                    series("График полета") {
                        this@Throw.chart = dataProperty()
                    }
                }
            }
        }
        left {
            vbox(20) {
                form {
                    fieldset("Данные для аналитического и имитационного алгоритма") {
                        field("Начальная скорость") {
                            textfield(vProperty) {
                                text = "10"
                            }
                        }
                        field("Угол броска в градусах") {
                            textfield(alphaProperty) {
                                text = "45"
                            }
                        }
                    }
                    fieldset("Данные только для имитационного алгоритма") {
                        field("Шаг моделирования") {
                            textfield(stepProperty) {
                                text = "0.0001"
                            }
                        }
                    }
                    button("Вычислить") {
                        action {
                            val modelThrow = ModelThrow();
                            if (vProperty.get() == null || !vProperty.get().isDouble() ||
                                alphaProperty.get() == null || !alphaProperty.get().isDouble() ||
                                stepProperty.get() == null || !stepProperty.get().isDouble()) {
                                alert(Alert.AlertType.WARNING, "Ошибка валидации", "Поля заполнены неверно")
                                return@action
                            }
                            modelThrow.v0 = vProperty.get().toDouble()
                            modelThrow.alpha = alphaProperty.get().toDouble()
                            modelThrow.step = stepProperty.get().toDouble()
                            if (!modelThrow.validateParam()) {
                                alert(Alert.AlertType.WARNING, "Ошибка валидации", "Поля заполнены неверно")
                            } else {
                                resultAnalysisProperty.set(modelThrow.lengthAnalytic().toString())
                                timeAnalysisProperty.set(modelThrow.getTime().toString())
                                resultImiaticProperty.set(modelThrow.lengthImitatic().toString())
                                timeImitaticProperty.set(modelThrow.getTime().toString())
                                chart?.get()?.clear()
                                modelThrow.resultMap.forEach { (x, y) ->
                                    chart?.get()?.add(XYChart.Data(x, y))
                                }
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