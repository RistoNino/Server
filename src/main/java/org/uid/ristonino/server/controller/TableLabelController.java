package org.uid.ristonino.server.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class TableLabelController {
    @FXML
    public VBox vboxTable;

    @FXML
    private Label labIcon;

    @FXML
    private Label labCop;



    public VBox setTable(int numTav) {

        FontIcon tableIcon = new FontIcon("mdi2t-table-furniture");
        tableIcon.setIconSize(60);
        labIcon.setGraphic(tableIcon);
        labCop.setText("Tavolo: "+numTav);
        vboxTable.setId(String.valueOf(numTav));
        vboxTable.setOnMouseClicked(event -> {

            try {
                SidebarTableController.getInstance().openSidebar(numTav);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        return vboxTable;
    }


}
