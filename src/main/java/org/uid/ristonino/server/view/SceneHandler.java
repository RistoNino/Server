//TODO: Eseguire test e vedere se buttonsVector è realmente necessario.

package org.uid.ristonino.server.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uid.ristonino.server.controller.dialog.ModalCategoryController;
import org.uid.ristonino.server.model.Debug;

import java.io.IOException;
import java.util.Objects;

public class SceneHandler {
    private Stage stage;
    private Scene scene;
    private Stage modalStage;
    // Path
    private final static String SCENE_PATH = "/org/uid/ristonino/server/";
    private final static String CSS_PATH = SCENE_PATH + "css/";
    private final static String VIEW_PATH = SCENE_PATH + "view/";

    // Risoluzione
    private final static double minWidth = 1200;
    private final static double minHeight = 600;

    private static final SceneHandler instance = new SceneHandler();
    // Va ricordato di essere preso da file / server
    private String theme = "light";
    public static SceneHandler getInstance() {
        return instance;
    }

    private SceneHandler() {
    }
    private void applyTheme() {
        this.scene.getStylesheets().clear();
        this.scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH + theme + "/main.css")).toExternalForm());
        this.scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH + "buttons.css")).toExternalForm());
        this.scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH + "fonts.css")).toExternalForm());
        this.scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH + "border.css")).toExternalForm());
        this.scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(CSS_PATH + "background.css")).toExternalForm());

    }
    public void changeTheme(String newTheme) {
        if (newTheme.equals(this.theme)) {
            return;
        }
        try {
            Objects.requireNonNull(getClass().getResource(CSS_PATH + theme + "/main.css"));
            this.theme = newTheme;
        } finally {
            applyTheme();
        }
    }

    public void init(Stage stage){
        if (this.stage == null) {
            this.stage = stage;
            this.stage.setWidth(Debug.width);
            this.stage.setHeight(Debug.height);
            this.stage.setTitle("RistoNino");
            createLoginScene();
            this.stage.setScene(scene);
            this.stage.show();
        }
    }

    private void setResolution() {
        this.stage.setMinWidth(minWidth);
        this.stage.setMinHeight(minHeight);
        if (Debug.IS_ACTIVE) {
            this.stage.setHeight(Debug.height);
            this.stage.setWidth(Debug.width);
        } else {
            this.stage.setFullScreen(true);
        }
        this.stage.setScene(scene);
        this.stage.show();
        this.stage.setResizable(false);
        applyTheme();
    }

    private <T> T loadRootFromFXML(String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
        return fxmlLoader.load();
    }

    private <T> T getControllerClass(String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resourceName)));
        return fxmlLoader.getController();
    }

    public void createHomeScene() {
        try {
            scene.setRoot(loadRootFromFXML(VIEW_PATH + "home-page.fxml"));
            setResolution();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void createLoginScene() {
        try {
            if (scene == null) {
                scene = new Scene(loadRootFromFXML(VIEW_PATH + "login-page.fxml"));
            } else {
                scene.setRoot(loadRootFromFXML(VIEW_PATH + "login-page.fxml"));
            }

        } catch (IOException ignored) {
        } finally {
            setResolution();
        }
    }

    public void closeModal() {
        modalStage.close();
    }

    public void createModal(String titleModal, String pathModal) {
        try {
            modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initOwner(stage);
            modalStage.setTitle(titleModal);
            Scene modalScene = new Scene(loadRootFromFXML(VIEW_PATH + "dialogMenu/" + pathModal));
            modalStage.setScene(modalScene);
            modalStage.showAndWait();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void createErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setContentText(message);
        alert.show();
    }


    public double sideWidth(double percentage){
        return (stage.widthProperty().getValue()*percentage)/100;
    }

    public Scene getScene() {
        return scene;
    }
}

