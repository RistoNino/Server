
package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.Debug;


public class MenuController {

    private final DatabaseHandler db = DatabaseHandler.getInstance();

    @FXML
    private void test() {
        if (Debug.IS_ACTIVE) {
            //db.getOrdineByTableId(1);
            System.out.println("---");
            db.loadTable();
        }
    }
}
