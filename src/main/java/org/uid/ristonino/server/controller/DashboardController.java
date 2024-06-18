//TODO: Riempire gli altri grafici
package org.uid.ristonino.server.controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import org.uid.ristonino.server.model.services.OrderService;
import org.uid.ristonino.server.model.services.TableService;
import java.text.ParseException;

public class DashboardController {

    @FXML
    public PieChart contiAperti=new PieChart();

    @FXML
    private LineChart<?, ?> guadagniAnno;

    @FXML
    private StackedBarChart<?, ?> guadagniSettimana;

    @FXML
    private PieChart pieChart=new PieChart();

    private final TableService tableService=TableService.getInstance();

    private final OrderService orderService=OrderService.getInstance();
    public void initialize() throws ParseException {
        setCopertiChart();
        setContiApertiChart();
    }

    public void setCopertiChart(){
        int totOc= tableService.getTotalCoperti();

        int totPos =tableService.getTotalMaxCoperti();

        System.out.println("Total occupied: "+totOc);
        System.out.println("Total Liberi: "+ totPos);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Liberi", totPos),
                        new PieChart.Data("Occupati", totOc));

        pieChart.getData().addAll(pieChartData);

        pieChartData.forEach(data -> data.nameProperty().bind(Bindings.concat( data.pieValueProperty(), " ",data.getName()))); //Mi permette di visualizzare i valori a schermo
    }

    public void setContiApertiChart(){
        int totNonP= orderService.getTotalOrderNonPagati();
        int totP =orderService.getTotalOrderPagati();

        System.out.println("Totale Pagati: "+totP);
        System.out.println("Total Non Pagati: "+ totNonP);
        ObservableList<PieChart.Data> dataOrder =
                FXCollections.observableArrayList(
                        new PieChart.Data("Pagati", totP),
                        new PieChart.Data("Non Pagati", totNonP));

        contiAperti.getData().addAll(dataOrder);

        dataOrder.forEach(data -> data.nameProperty().bind(Bindings.concat( data.pieValueProperty(), " Ordini ",data.getName()))); //Mi permette di visualizzare i valori a schermo



    }

}

