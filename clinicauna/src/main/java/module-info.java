module Clinicauna {
     //JAVAFX
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.base;
    requires java.logging;
   
    //JAKARTA
    requires jakarta.xml.bind;
    requires jakarta.ws.rs;
    //IMAGE AND FILES SERIALIZATION
    requires org.apache.commons.compress;
    requires org.apache.commons.io;
    //MESSAGES
    requires org.controlsfx.controls;
    requires AnimateFX;
    
   
    opens cr.ac.una.clinicauna to javafx.fxml, javafx.graphics;
    opens cr.ac.una.clinicauna.controller to javafx.fxml, javafx.controls, com.jfoenix;
}
