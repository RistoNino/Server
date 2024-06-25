package org.uid.ristonino.server.model.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Ingrediente {
    private int id;
    private String name;
    @JsonIgnore
    private BooleanProperty selected = new SimpleBooleanProperty(false);

    public Ingrediente(String name) {
        this.name = name;
    }

    public Ingrediente(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        return name;
    }
}
