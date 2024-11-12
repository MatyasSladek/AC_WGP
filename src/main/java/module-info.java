module com.github.matyassladek.ac_wgp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires static lombok;
    requires org.eclipse.jdt.annotation;
    requires java.logging;
    requires com.fasterxml.jackson.databind;

    opens com.github.matyassladek.ac_wgp to javafx.fxml;
    exports com.github.matyassladek.ac_wgp;
    exports com.github.matyassladek.ac_wgp.view;
    opens com.github.matyassladek.ac_wgp.view to javafx.fxml;
}