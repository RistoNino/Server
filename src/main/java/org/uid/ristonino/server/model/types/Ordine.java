package org.uid.ristonino.server.model.types;

import javafx.util.Pair;

import java.util.ArrayList;

public class Ordine {
    private ArrayList<Pair<Integer, Item>> listaOrdine = new ArrayList<Pair<Integer, Item>>();

    public Ordine() {}

    public void insertItem(Item i, int quantity) {
        listaOrdine.add(new Pair<>(quantity, i));
    }



    @Override
    public String toString() {
        return "Ordine{" +
                "listaOrdine=" + listaOrdine +
                '}';
    }


    public ArrayList<Pair<Integer, Item>> getListaOrdine() {
        return listaOrdine;
    }
}
