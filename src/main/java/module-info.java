module com.github.matyassladek.ac_wgp.ac_wgp {
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

    opens com.github.matyassladek.ac_wgp.ac_wgp to javafx.fxml;
    exports com.github.matyassladek.ac_wgp.ac_wgp;
}