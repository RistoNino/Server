//IMPORTANTE: Come stampare su list view

package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.kordamp.ikonli.javafx.FontIcon;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.Ordine;
import org.uid.ristonino.server.model.Table;
import org.uid.ristonino.server.view.SceneHandler;
import javafx.scene.control.Separator;

import java.util.ArrayList;

public class TavoliController {


    @FXML
    private FlowPane flowPaneTavoli;

    @FXML
    private AnchorPane sideBarTavoli;

    @FXML
    private Button tavoloButton;

    @FXML
    private Text totalText;

    @FXML
    private Text orderText;

    @FXML
    private Label exitLabel;

    @FXML
    private Text noteText;

    @FXML
    private Text piattoText;

    @FXML
    private VBox totalVbox;

    private final double prezzoCoperti=1.50;    //Fare in modo che il ristoratore possa modificare questa voce (dalle impostazioni magari)

    private final DatabaseHandler db = DatabaseHandler.getInstance();
    private int numberOfTable = db.getTables().getLast().getId();

    private ArrayList<Table>table =db.getTables();

    private Ordine ord;

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



    public void setExitIcon(){

        FontIcon myIcon = new FontIcon("mdi2c-close-thick");
        myIcon.setIconSize(30);
        exitLabel.setGraphic(myIcon);
        exitLabel.setText("");


    }

    public Label setUpdateIcon(){
        Label updateIcon=new Label();
        FontIcon myIcon = new FontIcon("mdi2u-update");
        myIcon.setIconSize(25);
        updateIcon.setGraphic(myIcon);
        updateIcon.setText("");
        updateIcon.setOnMouseClicked(event -> updateTable());
        return updateIcon;
    }

    public void initialize() {

        flowPaneTavoli.getChildren().clear();
        closeSideBar();
        System.out.println("lastid:"+db.getTables().getLast().getId());
        flowPaneTavoli.getChildren().remove(tavoloButton);
        updateTable();
    }

    public void addTableNew(int id) {
        Button bottone = setStandardButton();
        bottone.setId(String.valueOf(id));
        bottone.setText(Integer.toString(id));
        System.out.println("Aggiungo bottone con id:"+id);
        SceneHandler.getInstance().buttonsVector.addElement(bottone);
        flowPaneTavoli.getChildren().add(SceneHandler.getInstance().buttonsVector.lastElement());
    }


    public void updateTable(){
        flowPaneTavoli.getChildren().clear();
        numberOfTable = db.getTables().getLast().getId();
        System.out.println("******lastid:"+numberOfTable);
        for(int i=1; i<=numberOfTable; i++){
            this.addTableNew(i);
        }
        flowPaneTavoli.getChildren().add(setUpdateIcon());
    }
    @FXML
    public void openSideBar(){
        sideBarTavoli.setVisible(true);
        totalVbox.setVisible(true);
        orderText.setText("Ordini");
        totalText.setText("Totale:");
        setExitIcon();
        sideBarTavoli.setPrefWidth((SceneHandler.getInstance().sideWidht(20)));

    }

    @FXML
    public void tableClicked(String idBottone){
        System.out.println("Id: "+ idBottone);
        totalVbox.getChildren().clear();
        openSideBar();
        System.out.println("Bottone: "+ idBottone);
        this.addItems(idBottone);
        double totOrd=ord.totalCount(Integer.parseInt(idBottone));
        double totCop=prezzoCoperti*((double)table.get(Integer.parseInt(idBottone)-1).getOccupied());
        double tot=totOrd+totCop;
        System.out.println(totOrd+totCop);
        totalText.setText("Totale: "+ tot);

    }

    public void addItems(String idBottone){
        ord=db.getOrdineByTableId(Integer.parseInt(idBottone));
        table=db.getTables();

        Text txtN;

        for(int i=0; i<ord.getSize(); i++){
            AnchorPane anchorPane=new AnchorPane();                     //Creo ogni volta un AnchorPane così da poter inserire
                                                                            //a sx il nome del piatto
                                                                            //a dx il suo prezzo

            totalVbox.getChildren().add(anchorPane);                    //Aggiungo l'AnchorPane alla Vbox

            Text txtP=new Text(ord.getListQuantity().get(i)+"x "+ord.getListOrder().get(i)); //Creo un nuovo Text con all'interno il nome del piatto

            txtP.setFont(piattoText.getFont());                         //Assegno al testo creato un font

            AnchorPane.setTopAnchor(txtP,0.0);                  //Modifico i costrains del text appena creato in modo che
                                                                            //il nome stia ancorato sopra senza sovrapporsi agli altri elementi
            anchorPane.getChildren().add(txtP);                         //Aggiungo all'AnchorPane

            Text txtPr=new Text("€"+((ord.getListPrice().get(i)))+" cad.");   //Creo un nuovo Text con all'interno il prezzo del piatto
            txtPr.setFont(piattoText.getFont());                            //Assegno al Text lo stesso font del nome

            AnchorPane.setTopAnchor(txtPr,0.0);                     //Modifico i costrains del text appena creato in modo che
                                                                                //il prezzo stia ancorato sopra senza sovrapporsi agli altri elementi

            AnchorPane.setRightAnchor(txtPr, 5.0);                  //Modifico i constrains del text appena creato in modo che
                                                                                //il prezzo sia ancorato a dx

            anchorPane.getChildren().add(txtPr);                            //Aggiungo all'AnchorPane il prezzo

            if(ord.getListNote().get(i)==null){                             //Se ci sono note, le aggiunge, altrimenti lascio lo spazio vuoto
                txtN=new Text(" ");
            }
            else{
                txtN=new Text(" Note: "+ord.getListNote().get(i));
            }


            txtN.setFont(noteText.getFont());
            totalVbox.getChildren().add(txtN);
            Separator separator=new Separator();
            separator.setPadding(new Insets(2,0,2,0));      //Imposto i padding per il separatore
            totalVbox.getChildren().add(separator);



        }
        System.out.println("Grandezza table:"+table.size());
        //Inserisco un nuovo elemento con all'interno il numero dei coperti (ed il prezzo di essi)
        int numCop= table.get(Integer.parseInt(idBottone)-1).getOccupied();

        Text textC=new Text();
        textC.setFont(piattoText.getFont());
        textC.setTextAlignment(TextAlignment.CENTER);

        if(numCop==1){
            textC.setText("1x Coperto: €"+prezzoCoperti);
        }
        else{
            textC.setText(numCop+"x Coperti: €"+prezzoCoperti+" cad.");
        }

        totalVbox.getChildren().add(textC);

    }




    @FXML
    void closeSideBar() {
        orderText.setText("");
        totalText.setText("");
        exitLabel.setText("");
        totalVbox.setVisible(false);
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
