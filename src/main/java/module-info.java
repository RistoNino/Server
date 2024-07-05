module org.uid.ristonino.server {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires spring.security.crypto;
    requires io.vertx.core;
    requires io.vertx.web;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens org.uid.ristonino.server to javafx.fxml;
    opens org.uid.ristonino.server.controller to javafx.fxml;
    exports org.uid.ristonino.server.model.types to com.fasterxml.jackson.databind;
    exports org.uid.ristonino.server.controller;
    exports org.uid.ristonino.server.model;
    exports org.uid.ristonino.server.view;
    exports org.uid.ristonino.server;
    opens org.uid.ristonino.server.model to javafx.fxml;
    exports org.uid.ristonino.server.controller.modal;
    opens org.uid.ristonino.server.controller.modal to javafx.fxml;
}