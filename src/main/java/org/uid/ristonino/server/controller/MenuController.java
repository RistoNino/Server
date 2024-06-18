package org.uid.ristonino.server.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import org.uid.ristonino.server.model.services.IngredientsService;
import org.uid.ristonino.server.model.services.MenuService;
import org.uid.ristonino.server.model.types.*;

public class MenuController {

    private MenuService menuService = new MenuService();
    private Menu m = menuService.getMenu();
    private IngredientsService IngredientsService = new IngredientsService();

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
    @FXML
    public void addCategory(ActionEvent actionEvent) {
        // apri modal e ottieni dati
    }
}
