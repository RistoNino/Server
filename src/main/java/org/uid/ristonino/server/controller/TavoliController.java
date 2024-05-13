//Idea sidebar gestore tavoli: se la sidebar(anchor pane) è visibile, cambiare dimensioni parte centrale
//Oppure, impostare alla sidebar una grandezza=0 se non è visibile, e quando sarà visibile impostare una grandezza
//del 15% della larghezza della scena centrale (fatto)


//Implementata sidebar ma trovati alcuni problemi da risolvere. Quando si cambia scena i tavoli già presenti se cliccati
//non aprono la sidebar
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





    public Button setStandardButton(){
        Button btn=new Button();
        FontIcon tableIcon = new FontIcon("mdi2t-table-furniture");
        tableIcon.setIconSize(45);
        btn.setGraphic(tableIcon);
        btn.setText("");
        btn.setOnMouseClicked(event ->
                openSideBar()
        );
        return btn;

    }

    //Fare lista di bottoni e inserirla sul flowpane
    public void initialize() {
        sideBarTavoli.setPrefWidth(50);
        System.out.println("Sono quaaaa");
        flowPaneTavoli.getChildren().remove(tavoloLabel);

        FontIcon myIcon = new FontIcon("mdi2p-plus-thick"); //MaterialDesign2
        myIcon.setIconSize(30);
        tavoloLabel.setGraphic(myIcon);
        tavoloLabel.setText("");

        if(!SceneHandler.getInstance().buttonsVector.isEmpty()){
            System.out.println("Sono dentro l'if");
            for(int i=0; i<SceneHandler.getInstance().buttonsVector.size(); i++){
                flowPaneTavoli.getChildren().add(SceneHandler.getInstance().buttonsVector.elementAt(i));
                System.out.println(SceneHandler.getInstance().buttonsVector.elementAt(i));
            }
        }

        flowPaneTavoli.getChildren().add(tavoloLabel);

    }


    @FXML
    public void addTable() {
        System.out.println("Sono qua");
        flowPaneTavoli.getChildren().remove(tavoloLabel);
        SceneHandler.getInstance().buttonsVector.addElement(setStandardButton());
        System.out.println(" addtable "+SceneHandler.getInstance().buttonsVector.lastElement());

        System.out.println(SceneHandler.getInstance().buttonsVector.size());
        flowPaneTavoli.getChildren().add(SceneHandler.getInstance().buttonsVector.lastElement());
        flowPaneTavoli.getChildren().add(tavoloLabel);
    }

    @FXML
    public void openSideBar(){

        System.out.println("Sono quaua "+listviewOrder.isVisible()+" "+sideBarTavoli.isVisible()+" larghezza "+sideBarTavoli.getWidth()
                            +" largh listview"+listviewOrder.getWidth()+" class "+SceneHandler.getInstance().getStage().getScene().getClass());

        orderText.setText("Ordini");
        totalText.setText("Totale:");
        exitLabel.setText("X");
        listviewOrder.setVisible(true);
        sideBarTavoli.setPrefWidth((flowPaneTavoli.getWidth()*25)/100);
    }

    @FXML
    void closeSideBar() {
        orderText.setText("");
        totalText.setText("");
        exitLabel.setText("");
        //listviewOrder.setVisible(false);
        sideBarTavoli.setPrefWidth(50);

    }

}
