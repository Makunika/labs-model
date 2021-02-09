package GUI

import GUI.views.MyView
import javafx.stage.Stage
import tornadofx.*

class MyApp: App(MyView::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 1000.0
        stage.height = 600.0
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}