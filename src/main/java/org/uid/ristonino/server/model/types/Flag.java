package org.uid.ristonino.server.model.types;

public class Flag {
    int Id;
    String Name;
    String Image;

    public Flag(int id, String name, String Image) {
        this.Id = id;
        this.Name = name;
        this.Image = Image;
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

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }
}
