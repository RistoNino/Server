//TODO: Riempire gli altri grafici

package org.uid.ristonino.server.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import org.uid.ristonino.server.model.services.TableService;
import java.util.ArrayList;

public class DashboardController {


    @FXML
    private LineChart<?, ?> guadagniAnno;

    @FXML
    private StackedBarChart<?, ?> guadagniSettimana;

    @FXML
    private PieChart pieChart=new PieChart();

    private final TableService tableService=TableService.getInstance();

    public void initialize() {
        this.setPieChart();
    }

    public void setPieChart(){
        int totOc= tableService.getTotalCoperti();

        int totPos =tableService.getTotalMaxCoperti();

        System.out.println("Total occupied: "+totOc);
        System.out.println("Total Liberi: "+ totPos);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Liberi", totPos),
                        new PieChart.Data("Occupati", totOc));

        pieChart.getData().addAll(pieChartData);

        pieChartData.forEach(data -> data.nameProperty().bind(Bindings.concat( data.pieValueProperty(), " Posti ",data.getName()))); //Mi permette di visualizzare i valori a schermo
    }

    //Riempire gli altri grafici
    //Inserire tasto che aggiorna i grafici e inserire aggiornamento automatico in base al tempo
}

