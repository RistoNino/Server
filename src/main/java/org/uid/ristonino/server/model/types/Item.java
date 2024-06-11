package org.uid.ristonino.server.model.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Item {
    @JsonProperty("id")
    private int Id;
    private String Name;
    private String Description;
    @JsonIgnore
    private String pathImage;
    private String base64;
    private int Category;
    private double Price;
    private ArrayList<String> Ingredients = new ArrayList<>();
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("removedIngredients")
    private String removedIngredients;


    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public double getPrice() {
        return Price;
    }

    public String getNotes() {
        return notes;
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

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getRemovedIngredients() {
        return removedIngredients;
    }

    public void setRemovedIngredients(String removedIngredients) {
        this.removedIngredients = removedIngredients;
    }

    public Item(int id, String name, String description, String pathImage, int category, ArrayList<String> ingredients) {
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.pathImage = pathImage;
        this.Category = category;
        this.Ingredients = ingredients;
    }

    public Item(int id, String name, double Price, String notes) {
        this.Id = id;
        this.Name = name;
        this.Price = Price;
        this.notes = notes;
    }
    public Item(int id, String name, String description, String pathImage, double Price, int category) {
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.Price = Price;
        this.pathImage = pathImage;
        this.Category = category;
    }

    public Item(int id, String notes, String removedIngredients) {
        this.Id = id;
        this.notes = notes;
        this.removedIngredients = removedIngredients;
    }

    public Item(String name, String notes, double Price){
        this.Name = name;
        this.notes = notes;
        this.Price = Price;
    }

    public void addIngredient(String i) {
        Ingredients.add(i);
    }

    @Override
    public String toString() {
        return Name + '\n' +
                 Description + '\n' +
                 Price + '\n';
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
