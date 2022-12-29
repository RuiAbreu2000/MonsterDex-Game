package com.example.game;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import android.app.Application;

import com.example.game.TileRelated.Sprite;
import com.example.game.TileRelated.SpriteSheet;

public class SharedViewModel extends AndroidViewModel{

    public SpriteSheet spritesheet;
    public Sprite sprite;
    public static final int NUMBER_OF_MAP_ROWS = 10;
    public static final int NUMBER_OF_MAP_COLUMNS = 10;
    public static final int TILESIZE = 64;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        spritesheet = new SpriteSheet(application);
        //sprite = new Sprite();
    }

    public Sprite[][] getTestMap(){         // Map Fragment Calls this method to get Map with Sprites
        // Define Map
        int[] mapSpriteSheetIndex = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,};
        Sprite[][] mapSprite = new Sprite[NUMBER_OF_MAP_ROWS][NUMBER_OF_MAP_COLUMNS];
        // Build Map with Sprites
        for (int iRow = 0; iRow < NUMBER_OF_MAP_ROWS; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                // get corresponding sprite
                mapSprite[iRow][iCol] = spritesheet.getTile(mapSpriteSheetIndex[iRow+iCol]);
            }
        }
        return mapSprite;
    }

}