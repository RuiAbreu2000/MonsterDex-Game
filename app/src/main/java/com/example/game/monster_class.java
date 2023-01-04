package com.example.game;

import android.graphics.Bitmap;

public class monster_class {
    Bitmap image;
    String name;

    public monster_class(Bitmap image, String name) {
        this.image = image;
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
