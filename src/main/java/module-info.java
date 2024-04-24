module org.uid.ristonino.server {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens org.uid.ristonino.server to javafx.fxml;
    exports org.uid.ristonino.server;
}