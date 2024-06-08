package org.uid.ristonino.server.model.types;

import javafx.util.Pair;

import java.util.ArrayList;

public class Ordine {
    private ArrayList<Pair<Integer, Item>> listaOrdine = new ArrayList<Pair<Integer, Item>>();
    private int idOrdine;



    private int idTavolo;
    private String notes;

    public Ordine() {}

    public void insertItem(Item i, int quantity) {
        listaOrdine.add(new Pair<>(quantity, i));
    }

    public ArrayList<Pair<Integer, Item>> getListaOrdine() {
        return listaOrdine;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "listaOrdine=" + listaOrdine +
                ", idOrdine=" + idOrdine +
                ", idTavolo=" + idTavolo +
                ", notes='" + notes + '\'' +
                '}';
    }
}
