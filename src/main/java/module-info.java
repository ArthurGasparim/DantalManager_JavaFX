module unoeste.fipp.dentalfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.json;
    requires kernel;
    requires layout;
    requires io;
    requires jasperreports;
    requires javafx.web;

    opens unoeste.fipp.dentalfx.db.entidades to javafx.base;
    opens unoeste.fipp.dentalfx to javafx.fxml;

    exports unoeste.fipp.dentalfx;
    exports unoeste.fipp.dentalfx.db.entidades;
}