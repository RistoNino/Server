package org.uid.ristonino.server.model.types;

public class Flag {
    int Id;
    String Name;
    String pathImage;

    public Flag(int id, String name, String pathImage) {
        Id = id;
        Name = name;
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
}
