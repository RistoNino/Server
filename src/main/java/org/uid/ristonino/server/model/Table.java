package org.uid.ristonino.server.model;

import javafx.scene.control.Tab;

public class Table {
    private int id;
    private String ip;
    private boolean isoccupied;
    private int occupied;
    private int maxOccupied;

    public Table(int id, String ip, boolean isoccupied, int occupied, int maxOccupied) {
        this.id = id;
        this.ip = ip;
        this.isoccupied = isoccupied;
        this.occupied = occupied;
        this.maxOccupied = maxOccupied;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public boolean isIsoccupied() {
        return isoccupied;
    }

    public void setIsoccupied(boolean isoccupied) {
        this.isoccupied = isoccupied;
    }

    public int getOccupied() {
        return occupied;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public int getMaxOccupied() {
        return maxOccupied;
    }

    public void setMaxOccupied(int maxOccupied) {
        this.maxOccupied = maxOccupied;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", isoccupied=" + isoccupied +
                ", occupied=" + occupied +
                ", maxOccupied=" + maxOccupied +
                '}';
    }




}
