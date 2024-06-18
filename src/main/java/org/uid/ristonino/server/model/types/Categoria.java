package org.uid.ristonino.server.model.types;

public class Categoria {
    private String Nome;
    private int idCategoria;

    public Categoria(String nome, int idCategoria) {
        this.Nome = nome;
        this.idCategoria = idCategoria;
    }
    public String getNome() {
        return Nome;
    }
    public void setNome(String nome) {
        Nome = nome;
    }
    public int getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "Nome='" + Nome + '\'' +
                ", idCategoria=" + idCategoria +
                '}';
    }
}
