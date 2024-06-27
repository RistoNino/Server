package org.uid.ristonino.server.model.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Flag {
    private int Id;
    private String Name;
    @JsonIgnore
    private String pathImage;
    @JsonIgnore
    private BooleanProperty selected = new SimpleBooleanProperty(false);



    private String base64;

    public Flag(int id, String name, String pathImage) {
        this.Id = id;
        this.Name = name;
        this.pathImage = pathImage;
    }

    public Flag(String name, String pathImage) {
        this.Name = name;
        this.pathImage = pathImage;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }


    // Getter per la proprietà osservabile
    public BooleanProperty selectedProperty() {
        return selected;
    }

    // Getter per il valore della proprietà
    public boolean isSelected() {
        return selected.get();
    }

    // Setter per il valore della proprietà
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    @Override
    public String toString() {
        return Name;
    }
}
