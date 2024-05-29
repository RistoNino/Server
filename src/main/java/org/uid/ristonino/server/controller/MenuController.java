
package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.Debug;
import org.uid.ristonino.server.view.SceneHandler;


public class MenuController {

    private DatabaseHandler db = DatabaseHandler.getInstance();
    private Button test;

    @FXML
    private void test() {
        if (Debug.IS_ACTIVE) {
            //db.getOrdineByTableId(1);
            System.out.println("---");
            db.loadTable();
        }
    }
}
