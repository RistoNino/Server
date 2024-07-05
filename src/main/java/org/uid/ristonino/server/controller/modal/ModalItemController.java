package org.uid.ristonino.server.controller.modal;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;

import javafx.stage.FileChooser;
import org.uid.ristonino.server.controller.MenuController;
import org.uid.ristonino.server.model.services.ItemService;
import org.uid.ristonino.server.model.types.Categoria;
import org.uid.ristonino.server.model.types.Flag;
import org.uid.ristonino.server.model.types.Ingrediente;
import org.uid.ristonino.server.model.types.Item;
import org.uid.ristonino.server.view.SceneHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class ModalItemController {

    private String pathImage;
    private SceneHandler sceneHandler = SceneHandler.getInstance();
    private MenuController menuController = MenuController.getInstance();
    private ItemService itemService = ItemService.getInstance();

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
    private Button showImageBtn;

    @FXML
    private ComboBox<Categoria> comboBoxCategories;

    @FXML
    private Button createItemBtn;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label flagsLabel;

    @FXML
    private Label headerLabel;

    @FXML
    private Label ingredientsLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField priceTextField;


    public void initialize() {
        listViewFlags.setItems(flags);
        listViewIngredients.setItems(ingredientes);
        comboBoxCategories.setItems(categories);
        comboBoxCategories.getSelectionModel().selectFirst();

        listViewFlags.setCellFactory(CheckBoxListCell.forListView(flag -> flag.selectedProperty()));
        listViewIngredients.setCellFactory(CheckBoxListCell.forListView(ingrediente -> ingrediente.selectedProperty()));

        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (change.getControlNewText().matches("(\\d+(\\.|,| *)(\\d+| *))")){
                return change;
            } else {
                change.setText("");
                return change;
            }
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        priceTextField.setTextFormatter(textFormatter);
    }

    public ArrayList<Flag> getSelectedFlags() {
        ArrayList<Flag> selectedFlags = new ArrayList<>();
        for (Flag flag : flags) {
            if (flag.isSelected()) {
                selectedFlags.add(flag);
            }
        }
        return selectedFlags;
    }

    public ArrayList<Ingrediente> getSelectedIngredients() {
        ArrayList<Ingrediente> selectedIngredients = new ArrayList<>();
        for (Ingrediente ingrediente : ingredientes) {
            if (ingrediente.isSelected()) {
                selectedIngredients.add(ingrediente);
            }
        }
        return selectedIngredients;
    }


    private void clearAllInputs() {
        nameTextField.clear();
        descriptionTextArea.clear();
        comboBoxCategories.getSelectionModel().clearSelection();
        listViewIngredients.getSelectionModel().clearSelection();
        listViewFlags.getSelectionModel().clearSelection();
        pathImage = null;
    }

    @FXML
    void createItem(ActionEvent event) {

        if (nameTextField.getText().isEmpty()) {
            sceneHandler.createErrorMessage("Il nome del piatto non può essere vuoto");
        }
        else if (descriptionTextArea.getText().isEmpty()) {
            sceneHandler.createErrorMessage("La descrizione del piatto non può essere vuota");
        }
        else if (!descriptionTextArea.getText().matches("^[a-zA-ZÀ-ÿ0-9.,;:!?()'\"\\s-]+$")) {
            sceneHandler.createErrorMessage("La descrizione del piatto non è valida");
        }
        else if (!nameTextField.getText().matches("^[a-zA-ZÀ-ÿ0-9.,;:!?()'\"\\s-]+$")) {
            sceneHandler.createErrorMessage("Il nome del piatto non è valido");
        }
        else if (pathImage == null || pathImage.isEmpty()) {
            sceneHandler.createErrorMessage("Caricare un'immagine.");
        }
        else if (priceTextField.getText().isEmpty()) {
            sceneHandler.createErrorMessage("Inserire un prezzo");
        }
        else if (getSelectedFlags().isEmpty() || getSelectedIngredients().isEmpty()) {
            sceneHandler.createErrorMessage("Impossibile creare un piatto senza ingredienti o allegeni/flag associati");
        }
        else {
            Item i = new Item(
                    nameTextField.getText(),
                    descriptionTextArea.getText(),
                    Double.parseDouble(priceTextField.getText()),
                    pathImage,
                    comboBoxCategories.getSelectionModel().selectedItemProperty().get(),
                    getSelectedIngredients(),
                    getSelectedFlags());
            if (itemService.createItem(i)) {
                menuController.addObjectObservableList(i, menuController.getObservableItems());
                clearAllInputs();
            }
            else {
                sceneHandler.createErrorMessage("Errore durante la creazione del piatto");
            }
            sceneHandler.closeModal();
        }
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
        if (pathImage != null && !pathImage.isEmpty()) {
            sceneHandler.showImagePopup(pathImage);
        } else {
            sceneHandler.createErrorMessage("Nessuna immagine caricata");
        }
    }

    @FXML
    void closeHandle(ActionEvent event) {
        sceneHandler.closeModal();
    }
}
