package org.uid.ristonino.server.model;

import java.util.ArrayList;

public class Item {
    private int Id;
    private String Name;
    private String Description;
    private String ImageUrl;
    private int Category;
    private double Price;
    private ArrayList<String> Ingredients = new ArrayList<>();
    private String note;

    public Item(int id, String name, String description, String imageUrl, int category, ArrayList<String> ingredients) {
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.ImageUrl = imageUrl;
        this.Category = category;
        this.Ingredients = ingredients;
    }

    public Item(int id, String name, double Price, String Note) {
        this.Id = id;
        this.Name = name;
        this.Price = Price;
        this.note = Note;
    }
    public Item(int id, String name, double Price) {
        this.Id = id;
        this.Name = name;
        this.Price = Price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", Category=" + Category +
                ", Price=" + Price +
                ", Ingredients=" + Ingredients +
                ", note='" + note + '\'' +
                '}';
    }
}
