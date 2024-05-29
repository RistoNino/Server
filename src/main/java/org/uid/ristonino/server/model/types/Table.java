package org.uid.ristonino.server.model.types;

public class Table {
    private int id;
    private String ip;
    private boolean isoccupied;
    private int occupied;
    private int maxOccupied;

    public Table() {}

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

    public boolean isIsoccupied() {
        return isoccupied;
    }

    public int getOccupied() {
        return occupied;
    }


    public int getMaxOccupied() {
        return maxOccupied;
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
