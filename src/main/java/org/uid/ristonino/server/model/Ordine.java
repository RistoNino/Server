package org.uid.ristonino.server.model;

import javafx.util.Pair;

import java.util.ArrayList;

public class Ordine {
    private int id;
    private ArrayList<Pair<Integer, Item>> listaOrdine = new ArrayList<Pair<Integer, Item>>();
    public Ordine() {}

    public Ordine(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean insertItem(Item i, int quantity) {
        return listaOrdine.add(new Pair<>(id, i));
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "listaOrdine=" + listaOrdine +
                ", id=" + id +
                '}';
    }
}