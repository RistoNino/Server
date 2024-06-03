//TODO: Aggiornare tavoli ogni tot secondi
//TODO: Sistemare i font e l'interfaccia in generale

package org.uid.ristonino.server.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;
import org.uid.ristonino.server.model.services.TableService;


import java.io.IOException;

public class TavoliController {
    @FXML
    public StackPane tavoliStackPane;

    @FXML
    private FlowPane tavoliFlow;

    private boolean notification=false; //Usato per attivare o disattivare le eventuali notifiche sulla scena
    private boolean isActive=true;
    public static TavoliController instance;

    public static TavoliController getInstance(){
        if(instance == null){
            instance = new TavoliController();
        }
        return instance;
    }

    private final TableService table=TableService.getInstance();
    private final TableService order=TableService.getInstance();


    public void initialize() {
        loadTavoli();
        notification=true;
    }


    public void loadTavoli(){
        if(notification){
            this.addNotification();
        }

        try{
            tavoliFlow.getChildren().clear();
            for(int i=1; i<= table.getNumberOfTables(); i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uid/ristonino/server/view/templete/table.fxml"));
                Node tavolo=loader.load();
                TableLabelController controller = loader.getController();
                tavoliFlow.getChildren().add(controller.setTable(i));
            }
            tavoliFlow.getChildren().add(addUpdateLabel());

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }



    public void addNotification(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uid/ristonino/server/view/templete/notification.fxml"));
            Node notification=loader.load();
            NotificationController controller = loader.getController();
            tavoliStackPane.getChildren().add(controller.setNotification("Tavoli Aggiornati!", "mdi2c-check-bold"));
            isActive=false;
            tavoliFlow.getChildren().removeLast();
            tavoliFlow.getChildren().add(addUpdateLabel());
            //Evento per gestire l'animazione fade in/fade out
            controller.doAnimationIn().setOnFinished(event -> controller.doAnimationOut().setOnFinished(actionEvent -> {
                isActive = true;
                tavoliFlow.getChildren().removeLast();
                tavoliFlow.getChildren().add(addUpdateLabel());
                tavoliStackPane.getChildren().removeLast();
            }));
        }catch (IOException e){

        }

    }



    public Label addUpdateLabel(){
        Label label = new Label();
        FontIcon updateIcon = new FontIcon("mdi2u-update");
        updateIcon.setIconSize(30);
        if(isActive)
            label.setOnMouseClicked(event -> {
                loadTavoli();
            } );
        else{
            updateIcon.setFill(Color.GREY);
        }
        label.setGraphic(updateIcon);
        return label;
    }


}
