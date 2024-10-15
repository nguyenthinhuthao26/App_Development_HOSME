package com.example.Chi.models;

public class Payment {
    int imvThumb;
    String Name;

    public Payment(int imvThumb, String name) {
        this.imvThumb = imvThumb;
        Name = name;
    }

    public int getImvThumb() {
        return imvThumb;
    }

    public void setImvThumb(int imvThumb) {
        this.imvThumb = imvThumb;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
