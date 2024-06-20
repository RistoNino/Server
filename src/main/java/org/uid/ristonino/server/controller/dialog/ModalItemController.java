package org.uid.ristonino.server.controller.dialog;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;
import org.uid.ristonino.server.controller.MenuController;
import org.uid.ristonino.server.model.services.CategoryService;
import org.uid.ristonino.server.model.services.IngredientsService;
import org.uid.ristonino.server.model.types.Categoria;
import org.uid.ristonino.server.model.types.Flag;
import org.uid.ristonino.server.model.types.Ingrediente;
import org.uid.ristonino.server.model.types.Item;

public class ModalItemController {

    private MenuController menuController = MenuController.getInstance();

    ObservableList<Categoria> categories = menuController.getObservableCategories();
    ObservableList<Flag> flags = menuController.getObservableFlags();
    ObservableList<Ingrediente> ingredientes = menuController.getObservableIngredientes();

    @FXML
    private Label categoryLabel;

    @FXML
    private ListView<Flag> listViewFlags;

    @FXML
    private ListView<Ingrediente> listViewIngredients;

    @FXML
    private Button closeModalBtn;

    @FXML
    private ComboBox<Categoria> comboBoxCategories;

    @FXML
    private Button createItemBtn;

    @FXML
    private Label descriptionLabel;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Label flagsLabel;

    @FXML
    private Label headerLabel;

    @FXML
    private Label ingredientsLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField nameTextField;


    public void initialize() {
        listViewFlags.setItems(flags);
        listViewIngredients.setItems(ingredientes);
        comboBoxCategories.setItems(categories);

        listViewFlags.setCellFactory(CheckBoxListCell.forListView(new Callback<Flag, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Flag flag) {
                return flag.selectedProperty();
            }
        }));

        listViewIngredients.setCellFactory(CheckBoxListCell.forListView(new Callback<Ingrediente, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Ingrediente ingrediente) {
                return ingrediente.selectedProperty();
            }
        }));

    }


    @FXML
    void createItem(ActionEvent event) {

    }


    @FXML
    void closeHandle(ActionEvent event) {

    }
}
