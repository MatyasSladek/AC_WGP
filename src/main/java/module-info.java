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
    requires batik.transcoder;
    requires batik.awt.util;
    requires batik.util;
    requires xmlgraphics.commons;

    opens com.github.matyassladek.ac_wgp to javafx.fxml;
    exports com.github.matyassladek.ac_wgp;
    opens com.github.matyassladek.ac_wgp.view to javafx.fxml;

    exports com.github.matyassladek.ac_wgp.controllers;
    exports com.github.matyassladek.ac_wgp.model;
    opens com.github.matyassladek.ac_wgp.model to com.fasterxml.jackson.databind;
    exports com.github.matyassladek.ac_wgp.management;
    opens com.github.matyassladek.ac_wgp.management to com.fasterxml.jackson.databind;
    exports com.github.matyassladek.ac_wgp.enums;
    opens com.github.matyassladek.ac_wgp.enums to com.fasterxml.jackson.databind, javafx.fxml;
    opens com.github.matyassladek.ac_wgp.controllers to com.fasterxml.jackson.databind, javafx.fxml;
}