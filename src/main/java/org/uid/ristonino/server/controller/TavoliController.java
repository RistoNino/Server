//TODO: Aggiungere tasto che aggiorna i tavoli
//TODO: Aggiornare tavoli ogni tot secondi
//TODO: Sistemare i font e l'interfaccia in generale

package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import org.uid.ristonino.server.model.services.TableService;


import java.io.IOException;

public class TavoliController {

    @FXML
    private FlowPane tavoliFlow;

    private final TableService table=TableService.getInstance();


    public void initialize() {
        System.out.println("SOn qua1");

        loadTavoli();
    }


    public void loadTavoli(){
        try{
            for(int i=1; i<= table.getNumberOfTables(); i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/uid/ristonino/server/view/templete/table.fxml"));
                Node tavolo=loader.load();
                TableLabelController controller = loader.getController();
                tavoliFlow.getChildren().add(controller.setTable(i));
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }




}
