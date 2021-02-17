module alo {
    requires javafx.controls;
    requires javafx.graphics;
    requires tornadofx;
    requires kotlin.stdlib;
    requires java.desktop;
    opens GUI;
    opens GUI.views;
}