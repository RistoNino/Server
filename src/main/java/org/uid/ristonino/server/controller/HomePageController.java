package org.uid.ristonino.server.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.uid.ristonino.server.view.SceneHandler;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class HomePageController implements Initializable{
    private final static String SCENE_PATH = "/org/uid/ristonino/server/";
    private final static String VIEW_PATH = SCENE_PATH + "view/";

    public StackPane contentArea;

    public AnchorPane sideBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //sideBar.setMinHeight();

        //Creare funzione per grandezza sidebar
        sideBar.setPrefWidth(((SceneHandler.getInstance().getStage().widthProperty().getValue()*17.5)/100));
        System.out.println(sideBar.getPrefWidth());
        try {
            Parent fxml= FXMLLoader.load(Objects.requireNonNull(getClass().getResource(VIEW_PATH + "dashboard.fxml")));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxml);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void dashboard() throws IOException {
        Parent fxml= FXMLLoader.load(Objects.requireNonNull(getClass().getResource(VIEW_PATH+"dashboard.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

    public void tavoli() throws IOException {
        Parent fxml= FXMLLoader.load(Objects.requireNonNull(getClass().getResource(VIEW_PATH + "tavoli.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }
    public void menu() throws IOException {
        Parent fxml= FXMLLoader.load(Objects.requireNonNull(getClass().getResource(VIEW_PATH + "menu.fxml")));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

}
