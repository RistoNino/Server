//TODO: Cambiare da arraylist a vector (in caso avessimo bisogno di thread)
//TODO: Lasciare solo conti aperti e conti pagati
package org.uid.ristonino.server.model.services;
import javafx.util.Pair;
import io.vertx.core.Future;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Item;
import org.uid.ristonino.server.model.types.Ordine;

import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private int ordiniTotaliPagati;
    private int ordiniTotaliNonPagati;


    private static OrderService instance;

    public static OrderService getInstance() {

        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    private ArrayList<Pair<Integer, Ordine>> ordini = new ArrayList<>();
    private final DatabaseHandler db = DatabaseHandler.getInstance();

    private int idOrder;

    public OrderService() {}

    public void load(){
        db.loadOrders();
    }

    public void update(){
        ordini= new ArrayList<>();
        this.load();
    }

    public void addOrder(Ordine ord) {
        ordini.add(new Pair<>(idOrder, ord));
    }

//    public Future<Ordine> saveOrder(Ordine ord) {
//        db.createOrder(ord);
//
//        return Future.succeededFuture();
//    }

    public ArrayList<Pair<Integer, Item>> getOrder(int id) {
        ArrayList<Pair <Integer, Item>> ord = new ArrayList<>();
        for (Pair<Integer, Ordine> ordini : ordini) {
            if (ordini.getKey() == id) {
                int quantity=ordini.getValue().getListaOrdine().getFirst().getKey();
                String nomePiatto=ordini.getValue().getListaOrdine().getFirst().getValue().getName();
                String notePiatto=ordini.getValue().getListaOrdine().getFirst().getValue().getNote();
                double prezzoPiatto=ordini.getValue().getListaOrdine().getFirst().getValue().getPrice();
                ord.add(new Pair<>(quantity, new Item(nomePiatto, notePiatto, prezzoPiatto)));

            }
        }
        return ord;
    }

    public double getTotal(int id) {
        double tot=0;
        for (Pair<Integer, Ordine> ordini : ordini) {
            if (ordini.getKey() == id) {
                double prezzoPiatto=ordini.getValue().getListaOrdine().getFirst().getValue().getPrice();
                int quantity=ordini.getValue().getListaOrdine().getFirst().getKey();
                tot+=prezzoPiatto*(double)quantity;
            }
        }
        return tot;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public void setTotalOrderPagati(int tot){
        ordiniTotaliPagati=tot;
    }

    public int getTotalOrderPagati() {
        return ordiniTotaliPagati;
    }

    public void setTotalOrderNonPagati(int tot){
        ordiniTotaliNonPagati=tot;
    }

    public int getTotalOrderNonPagati() {
        return ordiniTotaliNonPagati;
    }


    @Override
    public String toString() {
        return "OrderService{" +
                ", ordini=" + ordini +
                '}';
    }

}
