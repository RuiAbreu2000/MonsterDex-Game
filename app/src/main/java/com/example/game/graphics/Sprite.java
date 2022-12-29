package com.example.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

    private final SpriteSheet spriteSheet;
    private final Rect rect;


    public Sprite(SpriteSheet spriteSheet, Rect rect) {
        this.spriteSheet = spriteSheet;
        this.rect = rect;
    }

    public void draw(Canvas canvas, int x, int y) {
        // Draws a part of a image
        canvas.drawBitmap(
                SpriteSheet.getBitmap(),
                rect,
                new Rect(x, y, x+getWidth(), y+getHeight()),
                null
        );
    }

    public Bitmap getSpriteBitmap(){
        Bitmap region = Bitmap.createBitmap(spriteSheet.getBitmap(), rect.right, rect.top, rect.width(), rect.height());
        return region;
    }

    public int getWidth() {
        return rect.width();
    }

    public int getHeight() {
        return rect.height();
    }
}
