package org.uid.ristonino.server.controller.dialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.uid.ristonino.server.model.services.CategoryService;
import org.uid.ristonino.server.model.types.Categoria;
import org.uid.ristonino.server.view.SceneHandler;

public class DialogCategoryController {

    private final SceneHandler sceneHandler = SceneHandler.getInstance();
    private final CategoryService categoryService = CategoryService.getInstance();

    @FXML
    private Button createCategoryBtn;


    @FXML
    private TextField nameTextField;


    public void createCategory(ActionEvent actionEvent) {
        if (nameTextField.getText().isEmpty()) {
            sceneHandler.createErrorMessage("Il nome della categoria non può essere vuoto");
        }
        else {
            Categoria categoria = new Categoria(nameTextField.getText());
            categoryService.addCategory(categoria);
            
        }
    }
}
