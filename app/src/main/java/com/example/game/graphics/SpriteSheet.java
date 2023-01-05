package com.example.game.graphics;

import static com.example.game.SharedViewModel.SPRITE_HEIGHT_PIXELS;
import static com.example.game.SharedViewModel.SPRITE_WIDTH_PIXELS;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.game.R;

// Responsible for returning the specific Sprite we are interested in
// We can have many sprites in this class but we use it to return the one we want
public class SpriteSheet {
    private Bitmap bitmap;

    public SpriteSheet(Context context, String source) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;

        if (source.equals("tiles")) {
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.water_map_tiles, bitmapOptions);
        }else if(source.equals("airMonsters")){
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.air_monsters, bitmapOptions);
        }else if(source.equals("bugMonsters")){
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bug_monsters, bitmapOptions);
        }else if(source.equals("fireMonsters")){
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_monsters, bitmapOptions);
        }else if(source.equals("groundMonsters")){
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ground_monsters, bitmapOptions);
        }else if(source.equals("waterMonsters")){
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.water_monsters, bitmapOptions);
        }
        else if(source.equals("symbols")){
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.symbols, bitmapOptions);
        }else if(source.equals("fixed")){
            this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fixed_tiles, bitmapOptions);
        }
    }


    public Bitmap getBitmap() {
        return this.bitmap;
    }

    // Vai buscar o tile da sprite sheet utilizando as coordenadas dos tiles na spritesheet
    public Sprite getTile(int tileNumber) {
        switch(tileNumber) {
            case 1:
                return getSpriteByIndex(0,0);
            case 2:
                return getSpriteByIndex(0,1);
            case 3:
                return getSpriteByIndex(0,2);
            case 4:
                return getSpriteByIndex(0,3);
            case 5:
                return getSpriteByIndex(1,0);
            case 6:
                return getSpriteByIndex(1,1);
            case 7:
                return getSpriteByIndex(1,2);
            case 8:
                return getSpriteByIndex(1,3);
            case 9:
                return getSpriteByIndex(2,0);
            case 10:
                return getSpriteByIndex(2,1);
            case 11:
                return getSpriteByIndex(2,2);
            case 12:
                return getSpriteByIndex(2,3);
            case 13:
                return getSpriteByIndex(3,0);
            case 14:
                return getSpriteByIndex(3,1);
            case 15:
                return getSpriteByIndex(3,2);
            case 16:
                return getSpriteByIndex(3,3);
            default:
                return null;
        }
    }

   // Returns a bitmap of the SprintSheet and a Rect corresponding to the desired Sprite
   private Sprite getSpriteByIndex(int idxRow, int idxCol) {
       return new Sprite(this, new Rect(
               idxCol*SPRITE_WIDTH_PIXELS,
              idxRow*SPRITE_HEIGHT_PIXELS,
                (idxCol + 1)*SPRITE_WIDTH_PIXELS,
               (idxRow + 1)*SPRITE_HEIGHT_PIXELS
        ));
   }
    public Sprite getMonsterTile(int tileNumber) {
        switch(tileNumber) {
            case 1:
                return getSpriteByIndex(0,0);
            case 2:
                return getSpriteByIndex(0,1);
            case 3:
                return getSpriteByIndex(0,2);
            case 4:
                return getSpriteByIndex(1,0);
            case 5:
                return getSpriteByIndex(1,1);
            case 6:
                return getSpriteByIndex(1,2);
            case 7:
                return getSpriteByIndex(2,0);
            case 8:
                return getSpriteByIndex(2,1);
            case 9:
                return getSpriteByIndex(2,2);
            case 10:
                return getSpriteByIndex(3,0);
            case 11:
                return getSpriteByIndex(3,1);
            case 12:
                return getSpriteByIndex(3,2);
            default:
                return null;
        }
    }
}
