//TODO: Cambiare da arraylist a vector (in caso avessimo bisogno di thread)

package org.uid.ristonino.server.model.services;
import io.vertx.core.Future;
import io.vertx.core.Promise;
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

    private ArrayList<Pair<Integer, Ordine>> ordini = new ArrayList<>();
    private final DatabaseHandler db = DatabaseHandler.getInstance();

    private int idTavolo;

    public OrderService() {}

    public void load(){
        db.loadOrderNew();
    }

    public void update(){
        ordini= new ArrayList<>();
        this.load();
    }

    public void addOrder(Ordine ord) {
        ordini.add(new Pair<>(idTavolo, ord));
    }

    public Future<Ordine> saveOrder(Ordine ord) {
        Promise<Ordine> promise = Promise.promise();
        db.createOrder(ord);
        promise.complete(ord);
        return promise.future();
    }

    public ArrayList<Pair<Integer, Item>> getOrder(int id) {
        ArrayList<Pair <Integer, Item>> ord = new ArrayList<>();
        for (Pair<Integer, Ordine> ordini : ordini) {

            if (ordini.getKey() == id) {

                int quantity=ordini.getValue().getListaOrdine().getFirst().getKey();
                String nomePiatto=ordini.getValue().getListaOrdine().getFirst().getValue().getName();
                String notePiatto=ordini.getValue().getListaOrdine().getFirst().getValue().getNotes();
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

    public void setIdTavolo(int idTavolo) {
        this.idTavolo = idTavolo;
    }

    @Override
    public String toString() {
        return "OrderService{" +
                ", ordini=" + ordini +
                '}';
    }

}
