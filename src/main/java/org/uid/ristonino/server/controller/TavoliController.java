
package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import org.kordamp.ikonli.javafx.FontIcon;
import org.uid.ristonino.server.model.services.OrderService;
import org.uid.ristonino.server.model.services.TableService;
import org.uid.ristonino.server.model.types.Table;


import java.io.IOException;
public class TavoliController {
    @FXML
    public StackPane tavoliStackPane;

    @FXML
    public BorderPane borderPaneTavoli;

    @FXML
    private FlowPane tavoliFlow;

    private boolean notification=false; //Usato per attivare o disattivare le eventuali notifiche sulla scena
    private boolean isActive=true;
    private static TavoliController instance=null;

    public TavoliController(){instance=this;} //Classe singleton

    public static TavoliController getInstance() {
        return instance;
    }

    private final TableService table=TableService.getInstance();


    public void initialize() throws IOException {
        loadTavoli();
//        System.out.println("initialize chiamato in TavoliController");
//        System.out.println("borderPaneTavoli: " + borderPaneTavoli);
        notification=true;
    }


    public void loadTavoli(){
        if(notification){
            this.addNotification();
        }

        try{
            tavoliFlow.getChildren().clear();
            for(Table t:table.getTable()){


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uid/ristonino/server/view/template/table.fxml"));
                Node tavolo=loader.load();
                TableLabelController controller = loader.getController();
                tavoliFlow.getChildren().add(controller.setTable(t.getId()));
            }
            setStateTable();
            tavoliFlow.getChildren().add(addUpdateLabel());

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }



    public void addNotification(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uid/ristonino/server/view/template/notification.fxml"));
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
        updateIcon.setIconSize(50);
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

    void openSidebar(int numTav) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uid/ristonino/server/view/sidebarTable.fxml"));
        Parent rightPane = loader.load();
        SidebarTableController controller = loader.getController();
        controller.openSidebar(numTav);
        this.borderPaneTavoli.setRight(rightPane);

    }

    void closeSidebar() {

        borderPaneTavoli.setRight(null);
    }

    OrderService allOrd=OrderService.getInstance();

    void setStateTable(){ //Prende tutti gli stati dal database, e colora di verde se è pagato, altrimenti colora di giallo. Lo richiamo ogni volta che devo aggiornare lo stato dei tavoli

        for(int i=0; i<tavoliFlow.getChildren().size(); i++) {
            if (tavoliFlow.getChildren().get(i).getId() != null) {
                int idFlow = Integer.parseInt(tavoliFlow.getChildren().get(i).getId());
                for (Pair<Integer, Boolean> x : allOrd.getOrdiniONo()) {
                    if (idFlow == x.getKey()) {
                        // System.out.println("Integer.parseInt(tavoliFlow.getChildren().get(i).getId()), x.getKey()" + Integer.parseInt(tavoliFlow.getChildren().get(i).getId()) + " " + x.getKey());
                        if (x.getValue()) {
                            tavoliFlow.getChildren().get(i).getStyleClass().add("tablePagato");
                        } else {
                            tavoliFlow.getChildren().get(i).getStyleClass().add("tableNoPagato");
                        }
                    }

                }
            }
        }
    }

    public void setTableStateById(int id, int state) {
        allOrd.setStateById(id, state);
        setStateTable();
    }
}

