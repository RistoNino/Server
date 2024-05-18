package org.uid.ristonino.server.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.Debug;
import org.uid.ristonino.server.view.SceneHandler;

public class LoginPageController {
    private final DatabaseHandler db = DatabaseHandler.getInstance();
    public TextField usernameField;
    public PasswordField passwordField;
    public Button loginButton;
    private boolean isDarkMode = true;

    @FXML
    private void faiCosa() {
        if (isDarkMode) {
            SceneHandler.getInstance().changeTheme("light");
            isDarkMode = false;
        } else {
            SceneHandler.getInstance().changeTheme("dark");
            isDarkMode = true;
        }
    }

    @FXML
    private void connectDb() {
        db.openConnection();
    }

    @FXML
    public void createUser(ActionEvent event) {
        if (Debug.IS_ACTIVE) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                SceneHandler.getInstance().createErrorMessage("Username and Password cannot be empty");
            }
            else {
                if (db.createUser(username,password,1)) {
                    SceneHandler.getInstance().createHomeScene();
                }
                else {
                    SceneHandler.getInstance().createErrorMessage("Username and Password already exists");
                    usernameField.clear();
                    passwordField.clear();
                }
            }
        }
    }

    @FXML
    public void loginUser(ActionEvent actionEvent) {
        if (!Debug.IS_ACTIVE) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                SceneHandler.getInstance().createErrorMessage("Username and Password cannot be empty");
            }
            else {
                if (db.checkPassword(username,password)) {
                    SceneHandler.getInstance().createHomeScene();
                    System.out.println("Good");
                }
                else {
                    SceneHandler.getInstance().createErrorMessage("Wrong password");
                    usernameField.clear();
                    passwordField.clear();
                }
            }
        }
        else {
            SceneHandler.getInstance().createHomeScene();
        }
    }
}
