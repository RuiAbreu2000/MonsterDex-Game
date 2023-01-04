package com.example.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Sprite {

    private final SpriteSheet spriteSheet;
    private final Rect rect;


    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }


    public Bitmap getSpriteBitmap(){
        Bitmap region = Bitmap.createBitmap(this.spriteSheet.getBitmap(), rect.left, rect.top, rect.width(), rect.height());
        return region;
    }


    public int getWidth() {
        return rect.width();
    }

    public int getHeight() {
        return rect.height();
    }

}
