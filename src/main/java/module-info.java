module md.project.bankapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jbcrypt;
    requires jdk.compiler;

    opens md.project.bankapp to javafx.fxml;
    exports md.project.bankapp;
}