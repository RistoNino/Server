//Cambiare organizzazione schermata principale

package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.uid.ristonino.server.model.Debug;
import org.uid.ristonino.server.model.services.OrderService;
import org.uid.ristonino.server.model.services.TableService;
import org.uid.ristonino.server.view.SceneHandler;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

public class HomePageController implements Initializable{
    private final static String SCENE_PATH = "/org/uid/ristonino/server/";
    private final static String VIEW_PATH = SCENE_PATH + "view/";


    @FXML
    private Button dashButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button tavButton;


    @FXML
    public AnchorPane sideBar;

    @FXML
    private BorderPane homepagePane;

    public Parent dashb, table, men;

    private Parent loadScene(String sc) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(getClass().getResource(VIEW_PATH + sc)));
    }
    OrderService orderService = OrderService.getInstance();
    TableService tableService = TableService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButtonState(dashButton);
        orderService.load();
        tableService.load();
        sideBar.setPrefWidth(((SceneHandler.getInstance().sideWidht(17.5))));
        Debug.getInstance().print("Sidebar larghezza: "+sideBar.getPrefWidth());

        try {
            dashb=loadScene("dashboard.fxml");
            table=loadScene("tavoli.fxml");
            men=loadScene("menu.fxml");

            homepagePane.setCenter(dashb);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void removeButtonState(Button button) {
        if(button.getStyleClass().contains("buttonHomePressed")){
            System.out.println("Sono qua");
            button.getStyleClass().remove("buttonHomePressed");

        }

    }

    private void addButtonState(Button button) {
        if(!button.getStyleClass().contains("buttonHomePressed"))
            button.getStyleClass().add("buttonHomePressed");
    }

    public void dashboard() {
        addButtonState(dashButton);
        removeButtonState(menuButton);
        removeButtonState(tavButton);
        homepagePane.setCenter(dashb);
    }
    public void tavoli(){
        addButtonState(tavButton);
        removeButtonState(menuButton);
        removeButtonState(dashButton);

        homepagePane.setCenter(table);
    }
    public void menu(){
        addButtonState(menuButton);
        removeButtonState(dashButton);
        removeButtonState(tavButton);
        homepagePane.setCenter(men);
    }


}
