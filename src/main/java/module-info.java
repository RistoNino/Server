module org.uid.ristonino.server {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires spring.security.crypto;
    requires spring.core;
    requires java.desktop;
    requires io.vertx.core;

    opens org.uid.ristonino.server to javafx.fxml;
    opens org.uid.ristonino.server.controller to javafx.fxml;
    exports org.uid.ristonino.server.controller;
    exports org.uid.ristonino.server.model;
    exports org.uid.ristonino.server.view;
    exports org.uid.ristonino.server;
    opens org.uid.ristonino.server.model to javafx.fxml;
    exports org.uid.ristonino.server.model.types;
    opens org.uid.ristonino.server.model.types to javafx.fxml;
}