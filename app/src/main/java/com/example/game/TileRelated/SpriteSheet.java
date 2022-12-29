package com.example.game.TileRelated;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.game.R;

// Responsible for returning the specific Sprite we are interested in
// We can ca have many sprites in this class but we use it to return the one we want
public class SpriteSheet {
    private static final int SPRITE_WIDTH_PIXELS = 64;
    private static final int SPRITE_HEIGHT_PIXELS = 64;
    private static Bitmap bitmap;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sprite_sheet, bitmapOptions);
    }

    //public Sprite[] getPlayerSpriteArray() {
    //    Sprite[] spriteArray = new Sprite[3];
    //   spriteArray[0] = new Sprite(this, new Rect(0*64, 0, 1*64, 64));
    //    spriteArray[1] = new Sprite(this, new Rect(1*64, 0, 2*64, 64));
    //    spriteArray[2] = new Sprite(this, new Rect(2*64, 0, 3*64, 64));
      //  return spriteArray;
   // }

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public Sprite getTile(int tileNumber) {
        switch(tileNumber) {
            case 1:                 // GREEN TILE
                return getSpriteByIndex(0,0);
            case 2:                 // BlLUE TILE
                return getSpriteByIndex(0,1);
            case 3:                 // BLUE/BLACK TILE
                return getSpriteByIndex(0,2);
            default:
                return null;
        }
    }


    //public Sprite getLavaSprite() {
       // return getSpriteByIndex(1, 1);
    //}

//    public Sprite getGroundSprite() {
  //      return getSpriteByIndex(1, 2);
    //}

    //public Sprite getGrassSprite() {
      //  return getSpriteByIndex(1, 3);
   // }

    //public Sprite getTreeSprite() {
      //  return getSpriteByIndex(1, 4);
    //}

   // Returns a bitmap of the SprintSheet and a Rect corresponding to the desired Sprit
   private Sprite getSpriteByIndex(int idxRow, int idxCol) {
       return new Sprite(this, new Rect(
               idxCol*SPRITE_WIDTH_PIXELS,
              idxRow*SPRITE_HEIGHT_PIXELS,
                (idxCol + 1)*SPRITE_WIDTH_PIXELS,
               (idxRow + 1)*SPRITE_HEIGHT_PIXELS
        ));
   }
}
