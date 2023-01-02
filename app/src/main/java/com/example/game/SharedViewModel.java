package com.example.game;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.game.databases.AppDatabase;
import com.example.game.databases.MonsterDex;
import com.example.game.graphics.MapLayouts;
import com.example.game.graphics.SpriteSheet;

public class SharedViewModel extends AndroidViewModel{

    // MAP CONSTANTS
    public static final int NUMBER_OF_MAP_ROWS = 5;
    public static final int NUMBER_OF_MAP_COLUMNS = 5;
    public static final int SPRITE_WIDTH_PIXELS = 256;
    public static final int SPRITE_HEIGHT_PIXELS = 256;
    public static final int MAP_X_SIZE = 1280;              //SPRITE_WIDTH_PIXELS * NUMBER_OF_MAP_COLUMNS
    public static final int MAP_Y_SIZE = 1280;              //SPRITE_HEIGHT_PIXELS * NUMBER_OF_MAP_ROWS
    public static final int TILESIZE = 256;

    // Important
    public AppDatabase db;
    public MapLayouts maplayouts;

    // Sprite Sheets
    public SpriteSheet spritesheet;
    public SpriteSheet waterMonsterSpritesheet;
    public SpriteSheet symbolsSpriteSheet;


    // Current Zone Vars
    public int currentZoneLevel = 1;                // Level of Current Zone
    public String currentZone = null;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        // Spritesheets

        spritesheet = new SpriteSheet(application, "tiles");
        waterMonsterSpritesheet = new SpriteSheet(application, "waterMonsters");
        symbolsSpriteSheet = new SpriteSheet(application, "symbols");
        maplayouts = new MapLayouts(spritesheet);
        //maplayouts.setSymbolsSpriteSheet(symbolsSpriteSheet);
    }

    // Get Map Functions
    public AppDatabase getDatabase(){
        return db;
    }

    public Bitmap getBitmap() {     // Returns Map Bitmap
        return maplayouts.getBitmap();
    }

    public int[] getTileMatrix() {  // Returns Array with Tile Numbers
        return maplayouts.getTileIndexMap().clone();
    }

    public int[] getMonsterArray(){ return maplayouts.getMonsterArray().clone();}

    public void getMap(String map) { // Builds map on MapLayouts
        switch(map) {
            case "grass":
                currentZone = "grass";                               // Updates current Zone
                maplayouts.grassMap(currentZoneLevel);                               // Builds Map
        }
    }

    public SpriteSheet getWaterMonsterSpriteSheet() {
        return this.waterMonsterSpritesheet;
    }

    // Add to Database Functions
    public void createNewMonster(MonsterDex newMonster){
        db.monsterDexDao().addMonster(newMonster);
    }
}