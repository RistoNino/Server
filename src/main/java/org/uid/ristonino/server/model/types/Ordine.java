package org.uid.ristonino.server.model.types;

import javafx.util.Pair;

import java.util.ArrayList;

public class Ordine {


    private ArrayList<Pair<Integer, Item>> listaOrdine = new ArrayList<Pair<Integer, Item>>();

    private int idOrdine;

    private int idTavolo;

    public Ordine() {}

    public void insertItem(Item i, int quantity) {
        listaOrdine.add(new Pair<>(quantity, i));
    }

    public ArrayList<Pair<Integer, Item>> getListaOrdine() {
        return listaOrdine;
    }
    public void setListaOrdine(ArrayList<Pair<Integer, Item>> listaOrdine) {
        this.listaOrdine = listaOrdine;
    }

    public int getIdTavolo() {
        return idTavolo;
    }

    public void setIdTavolo(int idTavolo) {
        this.idTavolo = idTavolo;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }


    @Override
    public String toString() {
        return "Ordine{" +
                "listaOrdine=" + listaOrdine +
                ", idOrdine=" + idOrdine +
                ", idTavolo=" + idTavolo +
                '}';
    }
}
