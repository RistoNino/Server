//TODO: Cambiare da arraylist a vector (in caso avessimo bisogno di thread)
//TODO: Lasciare solo conti aperti e conti pagati
package org.uid.ristonino.server.model.services;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import javafx.util.Pair;
import org.uid.ristonino.server.model.DatabaseHandler;
import org.uid.ristonino.server.model.types.Item;
import org.uid.ristonino.server.model.types.Ordine;


import java.util.ArrayList;

import static java.lang.Math.round;

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
    private ArrayList<Pair<Integer, Boolean>> ordPagatiONo = new ArrayList<>();
    private final DatabaseHandler db = DatabaseHandler.getInstance();

    public void insertOrderState(int id, boolean state){
        ordPagatiONo.add(new Pair<>(id, state));
    }

    public ArrayList<Pair<Integer, Boolean>> getOrdiniONo(){
        return ordPagatiONo;
    }

    private int idTavolo;

    public OrderService() {}

    public void load(){
        db.loadOrderNew();
    }

    public void update(){
        ordini= new ArrayList<>();
        ordPagatiONo= new ArrayList<>();
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
                double quantity=ordini.getValue().getListaOrdine().getFirst().getKey();

                tot+=prezzoPiatto*quantity;

            }
        }
        return round(tot * 100.0) / 100.0;
    }

    public void setIdTavolo(int idTavolo) {
        this.idTavolo = idTavolo;
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


    public void removeOrder(int id){
        db.removeOrderByTableId(id);
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
