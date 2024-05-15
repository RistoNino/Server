//IMPORTANTE: Come stampare su list view

package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import org.uid.ristonino.server.view.SceneHandler;


public class TavoliController {

    @FXML
    private FlowPane flowPaneTavoli;

    @FXML
    private AnchorPane sideBarTavoli;

    @FXML
    private Button tavoloButton;

    @FXML
    private ListView<?> listviewOrder;

    @FXML
    private Text totalText;

    @FXML
    private Label tavoloLabel;

    @FXML
    private Text orderText;

    @FXML
    private Label exitLabel;

    private int contatoreTavoli = 0;



    public Button setStandardButton(){
        Button btn=new Button();
        FontIcon tableIcon = new FontIcon("mdi2t-table-furniture");
        tableIcon.setIconSize(45);
        btn.setGraphic(tableIcon);
        btn.setText("");
        btn.setOnMouseClicked(event ->
                tableClicked(btn.getId())
        );
        return btn;
    }

    public void setPlusIcon(){
        FontIcon myIcon = new FontIcon("mdi2p-plus-thick"); //MaterialDesign2
        myIcon.setIconSize(30);
        tavoloLabel.setGraphic(myIcon);
        tavoloLabel.setText("");

    }

    public void setExitIcon(){

        FontIcon myIcon = new FontIcon("mdi2c-close-thick");
        myIcon.setIconSize(30);
        exitLabel.setGraphic(myIcon);
        exitLabel.setText("");


    }

    public void initialize() {
        closeSideBar();
        flowPaneTavoli.getChildren().remove(tavoloButton);
        flowPaneTavoli.getChildren().remove(tavoloLabel);
        setPlusIcon();
        flowPaneTavoli.getChildren().add(tavoloLabel);

    }

    @FXML
    public void addTable() {
        flowPaneTavoli.getChildren().remove(tavoloLabel);
        Button bottone = setStandardButton();
        bottone.setId(String.valueOf(contatoreTavoli));
        contatoreTavoli++;
        SceneHandler.getInstance().buttonsVector.addElement(bottone);
        flowPaneTavoli.getChildren().add(SceneHandler.getInstance().buttonsVector.lastElement());
        flowPaneTavoli.getChildren().add(tavoloLabel);
    }

    @FXML
    public void openSideBar(){
        sideBarTavoli.setVisible(true);
        listviewOrder.setVisible(true);
        orderText.setText("Ordini");
        totalText.setText("Totale:");
        setExitIcon();
        sideBarTavoli.setPrefWidth((SceneHandler.getInstance().sideWidht(17.5)));
    }

    @FXML
    public void tableClicked(String idBottone){
        openSideBar();
        Button bottone = searchButton(idBottone);

        System.out.println("Id bottone: " + idBottone);


    }

    @FXML
    void closeSideBar() {
        orderText.setText("");
        totalText.setText("");
        exitLabel.setText("");
        listviewOrder.setVisible(false);
        sideBarTavoli.setVisible(false);
        sideBarTavoli.setPrefWidth(0);

    }

    private Button searchButton(String id) {
        Button btn = new Button();
        for (Button button : SceneHandler.getInstance().buttonsVector) {
            if (button.getId().equals(id)) {
                btn=button;
            }
        }
        return btn;
    }

}
