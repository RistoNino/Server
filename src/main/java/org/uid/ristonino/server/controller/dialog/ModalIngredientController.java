package org.uid.ristonino.server.controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.uid.ristonino.server.controller.MenuController;
import org.uid.ristonino.server.model.services.IngredientsService;
import org.uid.ristonino.server.model.types.Categoria;
import org.uid.ristonino.server.model.types.Ingrediente;
import org.uid.ristonino.server.view.SceneHandler;

public class ModalIngredientController {

    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private MenuController menuController = MenuController.getInstance();
    private IngredientsService ingredientService = IngredientsService.getInstance();
    @FXML
    private TextField nameTextField;

    @FXML
    public void createIngredient(ActionEvent actionEvent) {
        if (nameTextField.getText().isEmpty()) {
            sceneHandler.createErrorMessage("Il nome dell'ingrediente non può essere vuoto");
        }
        else if (!nameTextField.getText().matches("^[a-zA-Z\\s]+$")) {
            sceneHandler.createErrorMessage("Il nome dell'ingrediente non valido");
        }
        else {
            Ingrediente ingrediente = new Ingrediente(nameTextField.getText());
            if (ingredientService.addIngredient(ingrediente)) {
                menuController.addObjectObservableList(ingrediente, menuController.getObservableIngredientes());
            }
            nameTextField.clear();
            sceneHandler.closeModal();
        }
    }

    @FXML
    public void closeHandle(ActionEvent actionEvent) {
        sceneHandler.closeModal();
    }
}
