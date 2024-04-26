package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.view.SceneHandler;

public class LoginPageController {
    private boolean isDarkMode = true;
    @FXML private Label test;

    @FXML
    private void faiCosa() {
        if (isDarkMode) {
            SceneHandler.getInstance().changeTheme("light");
            test.setText("Changed theme to light");
            isDarkMode = false;
        } else {
            SceneHandler.getInstance().changeTheme("dark");
            test.setText("Changed theme to dark");
            isDarkMode = true;
        }
    }

    @FXML
    private void connectDb() {
        DatabaseHandler.getInstance().openConnection();
    }
}
