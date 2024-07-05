package org.uid.ristonino.server.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import org.uid.ristonino.server.model.services.*;
import org.uid.ristonino.server.model.types.*;
import org.uid.ristonino.server.view.SceneHandler;

import java.util.Optional;

public class MenuController {

    private static MenuController instance = null;

    public MenuController() { instance = this; }

    public static MenuController getInstance() {
        return instance;
    }

    private SceneHandler sceneHandler = SceneHandler.getInstance();
    private MenuService menuService = new MenuService();
    private Menu m = menuService.getMenu();
    private IngredientsService ingredientsService = IngredientsService.getInstance();
    private CategoryService categoryService = CategoryService.getInstance();
    private ItemService itemService = ItemService.getInstance();
    private FlagService flagService = FlagService.getInstance();

    ObservableList<Categoria> categories;
    ObservableList<Item> items;
    ObservableList<Flag> flags;
    ObservableList<Ingrediente> ingredientes;

    @FXML
    private ListView<Ingrediente> ingredientiListView;

    @FXML
    private Tab altroTab;

    @FXML
    private ListView<Item> articoliListView;

    @FXML
    private Tab articoliTab;

    @FXML
    private ListView<Categoria> categoriaListView;

    @FXML
    private ListView<Flag> flagListView;

    @FXML
    private Button creaArticoloBtn;

    @FXML
    private Button creaCategoriaBtn;

    @FXML
    private Button creaFlagBtn;

    @FXML
    private Button rimuoviFlagBtn;

    @FXML
    private Button rimuoviArticoloBtn;

    @FXML
    private Button creaIngredientiBtn;

    @FXML
    private Button rimuoviIngredientiBtn;



    public void initialize() {
        categories = FXCollections.observableArrayList(m.getCategories());
        items = FXCollections.observableArrayList(m.getItems());
        flags = FXCollections.observableArrayList(m.getFlags());
        ingredientes = FXCollections.observableArrayList(ingredientsService.getIngredients());

        articoliListView.setItems(items);
        categoriaListView.setItems(categories);
        flagListView.setItems(flags);
        ingredientiListView.setItems(ingredientes);
    }


    public <T> void addObjectObservableList(T object, ObservableList<T> observableList) {
        observableList.add(object);
    }

    @FXML
    public void addCategory(ActionEvent actionEvent) {
        sceneHandler.createModal("Crea categoria", "dialog.fxml");
    }

    @FXML
    public void deleteCategory(ActionEvent actionEvent) {
        Categoria c = categoriaListView.getSelectionModel().getSelectedItem();
        if (c != null) {
            Optional<ButtonType> result = sceneHandler.createConfirmation("Elimina categoria", "Sei sicuro di volere eleminare questa categoria?", "Questa azione è irreversibile");
            if (result.get() == ButtonType.OK) {
                categories.remove(c);
                categoryService.removeCategory(c);
            }
        }
        else {
            sceneHandler.createErrorMessage("Nessun categoria selezionata");
        }
    }

    @FXML
    public void addIngredient(ActionEvent actionEvent) {
        sceneHandler.createModal("Crea Ingrediente", "dialog-ingredient.fxml");
    }

    @FXML
    public void deleteIngredient(ActionEvent actionEvent) {
        Ingrediente i = ingredientiListView.getSelectionModel().getSelectedItem();
        if (i != null) {
            Optional<ButtonType> result = sceneHandler.createConfirmation("Elimina Ingrediente", "Sei sicuro di volere eleminare questo ingrediente?", "Questa azione è irreversibile");
            if (result.get() == ButtonType.OK) {
                ingredientes.remove(i);
                ingredientsService.removeIngredient(i);
            }
        }
        else {
            sceneHandler.createErrorMessage("Nessun ingrediente selezionato");
        }
    }

    @FXML
    public void addFlag(ActionEvent actionEvent) {
        sceneHandler.createModal("Crea Allergene o altro", "dialog-flag.fxml");
    }

    @FXML
    public void deleteFlag(ActionEvent actionEvent) {
        Flag f = flagListView.getSelectionModel().getSelectedItem();
        if (f != null) {
            Optional<ButtonType> result = sceneHandler.createConfirmation("Elimina allergene/altro", "Sei sicuro di volere eleminare questo allergene o altro?", "Questa azione è irreversibile");
            if (result.get() == ButtonType.OK) {
                flags.remove(f);
                flagService.removeFlag(f);
            }
        }
        else {
            sceneHandler.createErrorMessage("Nessun allergene/altro selezionato");
        }
    }

    @FXML
    public void addItem(ActionEvent actionEvent) {
        sceneHandler.createModal("Crea Articolo", "dialog-item.fxml");
    }

    @FXML
    public void deleteItem(ActionEvent actionEvent) {
        Optional<ButtonType> result = sceneHandler.createConfirmation("Elimina Articolo", "Sei sicuro di volere eleminare questo articolo?", "Questa azione è irreversibile");
        if (result.get() == ButtonType.OK) {
            Item i = articoliListView.getSelectionModel().getSelectedItem();
            items.remove(i);
            itemService.removeItem(i);
        }
    }

    public ObservableList<Ingrediente> getObservableIngredientes() {
        return ingredientes;

    }

    public ObservableList<Flag> getObservableFlags() {
        return flags;
    }

    public ObservableList<Item> getObservableItems() {
        return items;
    }

    public ObservableList<Categoria> getObservableCategories() {
        return categories;
    }
}
