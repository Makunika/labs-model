package GUI.views

import tornadofx.View
import tornadofx.tabpane

class MyView: View("Моделирование") {
    override val root = tabpane {
        tab<Throw>()
        tab<Square>()
        tab<Hockey>()
    }
}



