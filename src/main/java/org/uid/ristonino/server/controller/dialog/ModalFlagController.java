package org.uid.ristonino.server.controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.uid.ristonino.server.controller.MenuController;
import org.uid.ristonino.server.model.services.FlagService;
import org.uid.ristonino.server.model.services.IngredientsService;
import org.uid.ristonino.server.model.types.Flag;
import org.uid.ristonino.server.model.types.Ingrediente;
import org.uid.ristonino.server.view.SceneHandler;

import java.io.File;

public class ModalFlagController {


    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private MenuController menuController = MenuController.getInstance();
    private FlagService flagService = FlagService.getInstance();
    private String pathImage;

    @FXML
    private Button closeModalBtn;

    @FXML
    private Button createFlagBtn;

    @FXML
    private TextField nameTextField;

    @FXML
    private Button showImageBtn;

    @FXML
    private Button uploadImageBtn;

    private void clearAllInputs() {
        nameTextField.clear();
        pathImage = null;
    }


    @FXML
    public void createFlag(ActionEvent actionEvent) {
        if (nameTextField.getText().isEmpty()) {
            sceneHandler.createErrorMessage("Il nome dell'allergeno/altro non può essere vuoto");
        }
        else if (!nameTextField.getText().matches("^[a-zA-Z\\s]+$")) {
            sceneHandler.createErrorMessage("Il nome dell'allergeno/altro non è valido");
        }
        else if (pathImage == null || pathImage.isEmpty()) {
            sceneHandler.createErrorMessage("Carica una foto");
        }
        else {
            Flag f = new Flag(nameTextField.getText(), pathImage);
            if (flagService.addFlag(f)) {
                menuController.addObjectObservableList(f, menuController.getObservableFlags());
                clearAllInputs();
            }
            else {
                sceneHandler.createErrorMessage("Errore durante la creazione della flag");
            }
            sceneHandler.closeModal();
        }
    }

    @FXML
    public void closeHandle(ActionEvent actionEvent) {
        sceneHandler.closeModal();
    }

    @FXML
    public void uploadImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterPhotos = new FileChooser.ExtensionFilter(
                "Image files (*.png, *.jpg, *.jpeg, *.gif, *.bmp)",
                "*.png", "*.PNG", "*.jpg", "*.JPG", "*.jpeg", "*.JPEG", "*.gif", "*.GIF", "*.bmp", "*.BMP"
        );
        fileChooser.getExtensionFilters().addAll(extFilterPhotos);

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            pathImage = file.getAbsolutePath();
            showImageBtn.setVisible(true);
        }
    }


    @FXML
    public void showImage(ActionEvent actionEvent) {
        // TODO: mostrare immagine
        // System.out.println(pathImage);
    }
}
