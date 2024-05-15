package org.uid.ristonino.server.model;

import java.util.ArrayList;

public class Item {
    private int Id;
    private String Name;
    private String Description;
    private String ImageUrl;
    private int Category;
    private ArrayList<String> Ingredients = new ArrayList<>();

    public Item(int id, String name, String description, String imageUrl, int category, ArrayList<String> ingredients) {
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.ImageUrl = imageUrl;
        this.Category = category;
        this.Ingredients = ingredients;
    }
}
