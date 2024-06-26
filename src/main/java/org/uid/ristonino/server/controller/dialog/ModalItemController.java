package org.uid.ristonino.server.controller.dialog;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;

import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.uid.ristonino.server.controller.HomePageController;
import org.uid.ristonino.server.controller.MenuController;
import org.uid.ristonino.server.model.services.CategoryService;
import org.uid.ristonino.server.model.services.IngredientsService;
import org.uid.ristonino.server.model.services.ItemService;
import org.uid.ristonino.server.model.types.Categoria;
import org.uid.ristonino.server.model.types.Flag;
import org.uid.ristonino.server.model.types.Ingrediente;
import org.uid.ristonino.server.model.types.Item;
import org.uid.ristonino.server.view.SceneHandler;
import org.w3c.dom.Text;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
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
        NumberFormat format = NumberFormat.getCurrencyInstance();
        priceTextField.setTextFormatter(new TextFormatter<>(c ->
                {
                    if (c.getControlNewText().isEmpty())
                    {
                        return c;
                    }
                    ParsePosition parsePosition = new ParsePosition( 0 );
                    Object object = format.parse(c.getControlNewText(), parsePosition);

                    return object == null || parsePosition.getIndex() < c.getControlNewText().length() ? null : c;
                })
        );
    }

    // iterano sulle liste observable
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
        // TODO: mostrare immagine
        // System.out.println(pathImage);
    }

    @FXML
    void closeHandle(ActionEvent event) {
        sceneHandler.closeModal();
    }
}
