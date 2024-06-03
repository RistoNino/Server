

package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ItemTableController {

    @FXML
    public VBox totalVbox;
    @FXML
    public Text piattoText;
    @FXML
    public Text priceText;
    @FXML
    public Text noteText;

    public VBox setItem(String nomePiatto, String notePiatto, double prezzoPiatto, int quantity){
        noteText.setWrappingWidth(170);
        piattoText.setText(quantity+"x "+nomePiatto);
        noteText.setText(notePiatto);
        priceText.setText("€ "+prezzoPiatto+" cad.");
        return totalVbox;
    }


}
