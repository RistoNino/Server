package org.uid.ristonino.server.model;

import javafx.scene.control.Tab;

public class Table {
    private int id;
    private String ip;
    private boolean isoccupied;

    public Table(int id, String ip, boolean isoccupied) {
        this.id = id;
        this.ip = ip;
        this.isoccupied = isoccupied;
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

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", isoccupied=" + isoccupied +
                '}';
    }
}
