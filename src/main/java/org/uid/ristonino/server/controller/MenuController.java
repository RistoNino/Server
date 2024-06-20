package org.uid.ristonino.server.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import org.uid.ristonino.server.controller.dialog.ModalCategoryController;
import org.uid.ristonino.server.model.services.CategoryService;
import org.uid.ristonino.server.model.services.IngredientsService;
import org.uid.ristonino.server.model.services.MenuService;
import org.uid.ristonino.server.model.types.*;
import org.uid.ristonino.server.view.SceneHandler;

import java.util.ArrayList;

public class MenuController {

    private static MenuController instance = null;

    public MenuController() { instance = this; }

    public static MenuController getInstance() {
        return instance;
    }

    private SceneHandler sceneHandler = SceneHandler.getInstance();
    private MenuService menuService = new MenuService();
    private Menu m = menuService.getMenu();
    private IngredientsService IngredientsService = new IngredientsService();
    private CategoryService categoryService = new CategoryService();




    ObservableList<Categoria> categories;
    ObservableList<Item> items;



    ObservableList<Flag> flags;
    ObservableList<Ingrediente> ingredientes;

    @FXML
    private ListView<Ingrediente> IngredientiListView;

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
        ingredientes = FXCollections.observableArrayList(IngredientsService.getIngredients());

        articoliListView.setItems(items);
        categoriaListView.setItems(categories);
        flagListView.setItems(flags);
        IngredientiListView.setItems(ingredientes);
    }

    public void addCategoryObservableList(Categoria categoria) {
        categories.add(categoria);
    }
//    public void addIngredientObservableList(Categoria categoria) {
//        categories.add(categoria);
//    }

    public <T> void addObjectObservableList(T object, ObservableList<T> observableList) {
        observableList.add(object);
    }

    @FXML
    public void addCategory(ActionEvent actionEvent) {
        sceneHandler.createModal("Crea categoria", "dialog.fxml");
    }

    @FXML
    public void addIngredient(ActionEvent actionEvent) {
        sceneHandler.createModal("Crea Ingrediente", "dialog-ingredient.fxml");
    }

    @FXML
    public void deleteCategory(ActionEvent actionEvent) {
        // notifica sei sicuro?

       // System.out.println(categoriaListView.getSelectionModel().getSelectedItem().getIdCategoria());
//        categories.remove();
//        categoryService.removeCategory()
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
