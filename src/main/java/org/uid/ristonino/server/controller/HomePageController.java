package org.uid.ristonino.server.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.Debug;
import org.uid.ristonino.server.view.SceneHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.uid.ristonino.server.view.SceneHandler;

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

    public void dashboard(ActionEvent actionEvent) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/org/uid/ristonino/server/view/dashboard.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void tavoli(ActionEvent actionEvent) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/org/uid/ristonino/server/view/tavoli.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    public void menu(ActionEvent actionEvent) throws IOException {
        Parent fxml= FXMLLoader.load(getClass().getResource("/org/uid/ristonino/server/view/menu.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

}
