package com.example.game;

import android.graphics.Bitmap;

public class monster_class {
    Bitmap image;
    String name;
    String lvl;
    String type;

    public monster_class(Bitmap image, String name) {
        this.image = image;
        this.name = name;

    }

    public monster_class(Bitmap image, String name, String lvl, String type){
        this.image = image;
        this.name = name;
        this.lvl = lvl;
        this.type = type;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return lvl;
    }

    public String getType(){
        return type;
    }
}
