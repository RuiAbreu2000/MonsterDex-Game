package com.example.game;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import android.app.Application;
import android.util.Log;

import com.example.game.graphics.MapLayouts;
import com.example.game.graphics.Sprite;
import com.example.game.graphics.SpriteSheet;

public class SharedViewModel extends AndroidViewModel{

    // MAP CONSTANTS
    public static final int NUMBER_OF_MAP_ROWS = 4;
    public static final int NUMBER_OF_MAP_COLUMNS = 4;
    public static final int SPRITE_WIDTH_PIXELS = 256;
    public static final int SPRITE_HEIGHT_PIXELS = 256;
    public static final int TILESIZE = 256;


    public SpriteSheet spritesheet;
    public MapLayouts maplayouts = new MapLayouts();

    public SharedViewModel(@NonNull Application application) {
        super(application);
        spritesheet = new SpriteSheet(application);
        //sprite = new Sprite();
    }

    public Sprite[][] getTestMap(){         // Map Fragment Calls this method to get Map with Sprites
        // Get Map Layout
        int[] mapSpriteSheetIndex = maplayouts.grassMap().clone();

        // Debug
        Log.w("a", String.valueOf(mapSpriteSheetIndex.length));
        Log.w("a", "ÕN SHAREDVIEWMODEL");

        // Create Map Sprite
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