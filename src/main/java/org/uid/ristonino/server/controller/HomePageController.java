package org.uid.ristonino.server.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomePageController implements Initializable{

    public StackPane contentArea;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent fxml= FXMLLoader.load(getClass().getResource("/org/uid/ristonino/server/view/dashboard.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void dashboard() throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/org/uid/ristonino/server/view/dashboard.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void tavoli() throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/org/uid/ristonino/server/view/tavoli.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    public void menu() throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/org/uid/ristonino/server/view/menu.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

}
