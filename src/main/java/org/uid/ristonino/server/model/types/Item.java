package org.uid.ristonino.server.model.types;

import java.util.ArrayList;

public class Item {
    private int Id;
    private String Name;
    private String Description;
    private String Image;
    private int Category;
    private double Price;
    private ArrayList<String> Ingredients = new ArrayList<>();
    private String note;

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public double getPrice() {
        return Price;
    }

    public String getNote() {
        return note;
    }

    public int getCategory() {
        return Category;
    }

    public void setCategory(int category) {
        Category = category;
    }

    public ArrayList<String> getIngredients() {
        return Ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        Ingredients = ingredients;
    }
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Item(int id, String name, String description, String image, int category, ArrayList<String> ingredients) {
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.Image = image;
        this.Category = category;
        this.Ingredients = ingredients;
    }

    public Item(int id, String name, double Price, String Note) {
        this.Id = id;
        this.Name = name;
        this.Price = Price;
        this.note = Note;
    }
    public Item(int id, String name, String description, String image, double Price, int category) {
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.Price = Price;
        this.Image = image;
        this.Category = category;
    }

    public Item(String name, String note, double Price){
        this.Name = name;
        this.note= note;
        this.Price = Price;
    }

    public void addIngredient(String i) {
        Ingredients.add(i);
    }

    @Override
    public String toString() {
        return "Item{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Description='" + Description + '\'' +
                ", ImageUrl='" + Image + '\'' +
                ", Category=" + Category +
                ", Price=" + Price +
                ", Ingredients=" + Ingredients +
                ", note='" + note + '\'' +
                '}';
    }
}
