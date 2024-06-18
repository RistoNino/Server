

package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;

public class ImpostazioniController {
    @FXML
    public BorderPane impostazioniBorder;

    public void initialize(){
        System.out.println("Impostazioni");
    }

    public void addLogo(MouseEvent event) {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
            fileChooser.getExtensionFilters().addAll(extFilterPNG);

            //Show open file dialog
            File file = fileChooser.showOpenDialog(null);

            if (file != null) {
                Image image = new Image(file.toURI().toString());
                HomePageController.getInstance().logoImage.setImage(image);
            }
    }

}