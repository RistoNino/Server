package org.uid.ristonino.server.model.types;

public class Categoria {
    private String name;
    private int id;

    public Categoria(String name) {
        this.name = name;
    }

    public Categoria(String name, int idCategoria) {
        this.name = name;
        this.id = idCategoria;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int idCategoria) {
        this.id = idCategoria;
    }

    @Override
    public String toString() {
        return name;
    }
}
