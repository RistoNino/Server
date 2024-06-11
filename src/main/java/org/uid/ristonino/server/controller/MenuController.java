package org.uid.ristonino.server.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import org.uid.ristonino.server.model.services.MenuService;
import org.uid.ristonino.server.model.types.Categoria;
import org.uid.ristonino.server.model.types.Flag;
import org.uid.ristonino.server.model.types.Item;
import org.uid.ristonino.server.model.types.Menu;

public class MenuController {

    private MenuService menuService = new MenuService();
    private Menu m = menuService.getMenu();

    ObservableList<Categoria> categories;
    ObservableList<Item> items;
    ObservableList<Flag> flags;

    @FXML
    private Tab altroTab;

    @FXML
    private ListView<Item> articoliListView;

    @FXML
    private Tab articoliTab;

    @FXML
    private ListView<Categoria> categoriaListView;

    @FXML
    private ListView<Flag> categoriaListView2;

    @FXML
    private Tab categoriaTab;

    @FXML
    private Button creaArticoloBtn;

    @FXML
    private Button creaCategoriaBtn;

    @FXML
    private Button creaCategoriaBtn2;

    @FXML
    private Button modificaArticoloBtn;

    @FXML
    private Button modificaCategoriaBtn;

    @FXML
    private Button modificaCategoriaBtn2;

    @FXML
    private Button rimuoviArticoloBtn;

    @FXML
    private Button rimuoviCategoriaBtn;

    @FXML
    private Button rimuoviCategoriaBtn2;


    public void initialize() {
        categories = FXCollections.observableArrayList(m.getCategories());
        items = FXCollections.observableArrayList(m.getItems());
        flags = FXCollections.observableArrayList(m.getFlags());

        articoliListView.setItems(items);
        categoriaListView.setItems(categories);
        categoriaListView2.setItems(flags);
    }
}
