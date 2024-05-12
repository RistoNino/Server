//Idea sidebar gestore tavoli: se la sidebar(anchor pane) è visibile, cambiare dimensioni parte centrale
//Oppure, impostare alla sidebar una grandezza=0 se non è visibile, e quando sarà visibile impostare una grandezza
//del 15% della larghezza della scena centrale

package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.kordamp.ikonli.javafx.FontIcon;

public class TavoliController {

    @FXML
    private FlowPane flowPaneTavoli;

    @FXML
    private AnchorPane sideBarTavoli;

    @FXML
    private Button tavoloButton;

    @FXML
    private Label tavoloLabel;


    //Fare lista di bottoni e inserirla sul flowpane
    public void initialize() {
        FontIcon myIcon = new FontIcon("mdi2p-plus-thick"); //MaterialDesign2
        myIcon.setIconSize(20);
        tavoloLabel.setGraphic(myIcon);
        tavoloLabel.setText("");
        FontIcon tableIcon = new FontIcon("mdi2t-table-furniture");
        tableIcon.setIconSize(45);
        tavoloButton.setGraphic(tableIcon);
        tavoloButton.setText("");
    }


    @FXML
    void addTable(MouseEvent event) {
        System.out.println("Sono qua");

    }

}
