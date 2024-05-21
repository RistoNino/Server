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

    public void insertItem(Item i, int quantity) {
        listaOrdine.add(new Pair<>(quantity, i));
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "listaOrdine=" + listaOrdine +
                ", id=" + id +
                '}';
    }


    public double totalCount(int idTable){
        double total = 0;
        for (Pair<Integer, Item> price : listaOrdine) {
            total = total + price.getValue().getPrice()*price.getKey();
        }

        return total;
    }


    public ArrayList<String> getListOrder() {
        ArrayList<String> listOrder = new ArrayList<>();
        for (Pair<Integer, Item> piattoNome : listaOrdine) {
                listOrder.add(piattoNome.getValue().getName());
        }

        return listOrder;
    }


    public ArrayList<String> getListNote() {
        ArrayList<String> listNote = new ArrayList<>();
        for (Pair<Integer, Item> nota : listaOrdine) {

                listNote.add(nota.getValue().getNote());
        }

        return listNote;
    }

    public ArrayList<Double> getListPrice(){
        ArrayList<Double> listPrice = new ArrayList<>();
        for (Pair<Integer, Item> price : listaOrdine) {

                listPrice.add(price.getValue().getPrice());
        }

        return listPrice;
    }

    public ArrayList<Integer> getListQuantity(){
        ArrayList<Integer> listQuantity = new ArrayList<>();
        for (Pair<Integer, Item> quantity : listaOrdine) {
            listQuantity.add(quantity.getKey());
        }
        return listQuantity;
    }

    public int getSize(){
        return listaOrdine.size();
    }


}
