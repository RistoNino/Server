package org.uid.ristonino.server.model.types;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Flag {
    private int Id;
    private String Name;
    @JsonIgnore
    private String pathImage;



    private String base64;

    public Flag(int id, String name, String pathImage) {
        this.Id = id;
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

    @Override
    public String toString() {
        return Name;
    }
}
