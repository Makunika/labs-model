package GUI.views

import tornadofx.View
import tornadofx.tabpane

class MyView: View("Моделирование") {
    override val root = tabpane {
        tab<Hockey>()
        tab<MashineLab2>()
        tab<Throw>()
        tab<Square>()
    }
}



