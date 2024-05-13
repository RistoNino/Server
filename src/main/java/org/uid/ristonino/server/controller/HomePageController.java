//Cambiare organizzazione schermata principale

package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.uid.ristonino.server.view.SceneHandler;


import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class HomePageController implements Initializable{
    private final static String SCENE_PATH = "/org/uid/ristonino/server/";
    private final static String VIEW_PATH = SCENE_PATH + "view/";


    public AnchorPane sideBar;

    @FXML
    private BorderPane homepagePane;

    public Parent dashb, table, men;

    private Parent loadScene(String sc) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(getClass().getResource(VIEW_PATH + sc)));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //sideBar.setMinHeight();

        //Creare funzione per grandezza sidebar
        sideBar.setPrefWidth(((SceneHandler.getInstance().sideWidht(17.5))));
        System.out.println("Sidebar larghezza: "+sideBar.getPrefWidth());



        try {
            dashb=loadScene("dashboard.fxml");
            table=loadScene("tavoli.fxml");
            men=loadScene("menu.fxml");

            homepagePane.setCenter(dashb);
            System.out.println("Ciaociaociao");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }



    public void dashboard() throws IOException {
        homepagePane.setCenter(dashb);
    }

    public void tavoli() throws IOException {
        homepagePane.setCenter(table);
    }
    public void menu() throws IOException {
        homepagePane.setCenter(men);
    }

}
