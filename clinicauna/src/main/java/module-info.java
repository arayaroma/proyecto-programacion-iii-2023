module Clinicauna {
    requires java.base;
    requires java.logging;
    requires javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.graphics;
    requires jakarta.xml.bind;
    requires jakarta.xml.ws;
    requires org.apache.commons.compress;
    requires org.apache.commons.io;
    requires org.controlsfx.controls;
    // requires AnimateFX;

    opens cr.ac.una.clinicauna to javafx.fxml;
    // opens cr.ac.una.evacomuna.util to javafx.fxml;
    // opens cr.ac.una.evacomuna.controller to javafx.fxml;
    // opens cr.ac.una.evacomuna.dto;
    // opens cr.ac.una.controller;

    exports cr.ac.una.clinicauna;
    // exports cr.ac.una.clinicauna.controller;
    // exports cr.ac.una.controller;
}
