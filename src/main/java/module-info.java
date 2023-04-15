module com.pressing.pressingplus {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mailapi;

    opens com.pressing.pressingplus to javafx.fxml;
    exports com.pressing.pressingplus;
}