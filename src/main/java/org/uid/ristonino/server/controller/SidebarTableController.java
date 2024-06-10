//TODO: Creare funzione creaScontrino

package org.uid.ristonino.server.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.uid.ristonino.server.model.services.OrderService;
import org.uid.ristonino.server.model.services.TableService;
import org.uid.ristonino.server.model.types.Item;
import org.uid.ristonino.server.view.SceneHandler;

import java.io.IOException;
import java.util.ArrayList;

public class SidebarTableController {
    @FXML
    public Label tableText;
    @FXML
    public Label totalText;
    @FXML
    public VBox orderVBox;

    @FXML
    public Label payButton;

    OrderService orderService = OrderService.getInstance();
    TableService tableService = TableService.getInstance();

    private static SidebarTableController instance;

    @FXML
    private AnchorPane sidebarRight;

    public SidebarTableController() {
        instance = this; // Imposta l'istanza corrente come singleton
    }

    public static SidebarTableController getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        closeSidebar();

    }

    public void openSidebar(int numTav) throws IOException {
        orderVBox.getChildren().clear();
        sidebarRight.setPrefWidth((SceneHandler.getInstance().sideWidht(20)));
        orderService.update();
        if (sidebarRight != null) {
            tableText.setText("Ordine Tavolo "+numTav+":");
            ArrayList<Pair<Integer, Item>> ord =orderService.getOrder(numTav);
            for(int i=0; i<ord.size();i++)
            {
                FXMLLoader loader= new FXMLLoader(getClass().getResource("/org/uid/ristonino/server/view/templete/item.fxml"));
                Node item=loader.load();
                ItemTableController controller = loader.getController();

                int quantity=ord.get(i).getKey();
                String nomePiatto=ord.get(i).getValue().getName();
                String notePiatto=ord.get(i).getValue().getNotes();
                double prezzoPiatto=ord.get(i).getValue().getPrice();

                orderVBox.getChildren().add(controller.setItem(nomePiatto, notePiatto, prezzoPiatto, quantity));
            }
            int nCoperti=tableService.getCoperti(numTav);
            Label coperti =new Label();
            double prezzoCoperti=1.50;
            coperti.setText(nCoperti+"x coperti €"+prezzoCoperti);
            coperti.setStyle("-fx-font-size: 13; -fx-font-weight: 700");
            orderVBox.getChildren().add(coperti);



            totalText.setText("Totale: €"+(orderService.getTotal(numTav)+((double)nCoperti*prezzoCoperti)));
            sidebarRight.setVisible(true);
        }
        else{
            System.out.println("sidebarRight is null");
        }
    }

    public void closeSidebar(){
        tableText.setText("");
        totalText.setText("");
        sidebarRight.setPrefWidth(0);
        sidebarRight.setVisible(false);
    }
}