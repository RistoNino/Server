//TODO: Cambiare da arraylist a vector (in caso avessimo bisogno di thread)

package org.uid.ristonino.server.model.services;
import javafx.util.Pair;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Item;
import org.uid.ristonino.server.model.types.Ordine;

import java.util.ArrayList;

public class OrderService {

    private static OrderService instance;

    public static OrderService getInstance() {

        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }


    private ArrayList<Pair<Integer, Ordine>> ordini= new ArrayList<>();
    private int idOrder;

    public OrderService() {}

    public void load(){
        DatabaseHandler db=DatabaseHandler.getInstance();
        db.loadOrders();
    }

    public void update(){
        ordini= new ArrayList<>();
        this.load();
    }

    public void addOrder(Ordine ord) {
        ordini.add(new Pair<>(idOrder, ord));
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }


    @Override
    public String toString() {
        return "OrderService{" +
                ", ordini=" + ordini +
                '}';
    }

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



}
