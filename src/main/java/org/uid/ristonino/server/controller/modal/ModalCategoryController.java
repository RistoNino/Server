package org.uid.ristonino.server.controller.modal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.uid.ristonino.server.controller.MenuController;
import org.uid.ristonino.server.model.services.CategoryService;
import org.uid.ristonino.server.model.types.Categoria;
import org.uid.ristonino.server.view.SceneHandler;

public class ModalCategoryController {

    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final CategoryService categoryService = CategoryService.getInstance();
    private MenuController menuController = MenuController.getInstance();

    @FXML
    private Button createCategoryBtn;

    @FXML
    private TextField nameTextField;


    @FXML
    public void createCategory(ActionEvent actionEvent) {
        if (nameTextField.getText().isEmpty()) {
            sceneHandler.createErrorMessage("Il nome della categoria non può essere vuoto");
        }
        else if (!nameTextField.getText().matches("^[a-zA-Z\\s]+$")) {
            sceneHandler.createErrorMessage("Il nome della categoria non valido");
        }
        else {
            Categoria categoria = new Categoria(nameTextField.getText());
            if (categoryService.addCategory(categoria)) {
                menuController.addObjectObservableList(categoria, menuController.getObservableCategories());
            }
            nameTextField.clear();
            sceneHandler.closeModal();
        }
    }

    @FXML
    public void closeHandle(ActionEvent event) {
        sceneHandler.closeModal();
    }
}
