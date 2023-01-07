package com.example.game.graphics;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.NUMBER_OF_MAP_ROWS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

// Has the informatation about the current map
public class MapLayouts {

    // SpriteSheets
    private SpriteSheet spritesheet;
    private SpriteSheet symbolsSpriteSheet;
    private SpriteSheet fixedSpriteSheet;

    private int[] mapSpriteSheetIndex;
    private int[][] monsterArray;
    public Sprite[][] mapSprite;
    private Bitmap bitmap;
    private MapInformation mapInformation;

    // Symbol Tiles
    public Sprite arrowUp;
    private Sprite arrowDown;
    private Sprite monsterSymbol;
    private Sprite arrowRight;
    private Sprite bossSymbol;
    private Sprite bugDungeonSymbol;

    // Tile Indexes
    int[] groundIndexes = {2, 6, 10, 14};

    // Map Generator Probabilities
    int[] grassMapProbabilities =     new int[]{70,75,95,100}; // 70% grass, 20% dirt, 10% rocks

    public MapLayouts(SpriteSheet spritesheet, SpriteSheet symbolsSpriteSheet, SpriteSheet fixedSpriteSheet) {
        this.spritesheet = spritesheet;
        this.symbolsSpriteSheet = symbolsSpriteSheet;
        this.fixedSpriteSheet = fixedSpriteSheet;
        this.mapInformation = new MapInformation();
        mapSpriteSheetIndex = new int[NUMBER_OF_MAP_ROWS*NUMBER_OF_MAP_COLUMNS];
        monsterArray = new int[NUMBER_OF_MAP_ROWS][NUMBER_OF_MAP_COLUMNS];
        mapSprite = new Sprite[NUMBER_OF_MAP_ROWS][NUMBER_OF_MAP_COLUMNS];
    }


    // GET MAPAS
    public void homeMap() {
        // Build Sprite Array
        buildSpriteArrayFixed(mapInformation.homeMap);
        Log.w("A", "HOME");

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmapFixed();

    }

    public void zoneSelection_1() {
        // Build Sprite Array
        buildSpriteArrayFixed(mapInformation.zoneSelection_1Map);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmapFixed();
    }

    public void zoneSelection_2() {
        // Build Sprite Array
        buildSpriteArrayFixed(mapInformation.zoneSelection_2Map);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmapFixed();
    }

    public void zoneSelection_3_cold() {
        // Build Sprite Array
        buildSpriteArrayFixed(mapInformation.zoneSelection_3ColdMap);
        mapSprite[2][3] = arrowRight;

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmapFixed();
    }

    public void zoneSelection_3_hot() {
        // Build Sprite Array
        buildSpriteArrayFixed(mapInformation.zoneSelection_3HotMap);
        mapSprite[2][3] = arrowRight;

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmapFixed();
    }

    public void zoneSelection_3_mild() {
        // Build Sprite Array
        buildSpriteArrayFixed2(mapInformation.zoneSelection_3MildMap);
        mapSprite[2][3] = arrowRight;

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmapFixed();
    }

    // DUNGEON MAPS
    public void waterDungeon(int currentZoneLevel, SpriteSheet waterTiles) {
        buildSpriteArray(currentZoneLevel, mapInformation.waterMapLevels[currentZoneLevel-1], waterTiles, 20);

        // Build Monster array
        buildMonsterArray(currentZoneLevel);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmap();

    }

    public void fireDungeon(int currentZoneLevel, SpriteSheet fireTiles) {
        // Build Sprite Array
        buildSpriteArray(currentZoneLevel, mapInformation.fireMapLevels[currentZoneLevel-1], fireTiles, 19);

        // Build Monster array
        buildMonsterArray(currentZoneLevel);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmap();
    }

    public void groundDungeon(int currentZoneLevel, SpriteSheet groundTiles) {
        // Build Sprite Array
        buildSpriteArray(currentZoneLevel, mapInformation.groundMapLevels[currentZoneLevel-1], groundTiles, 15);

        // Build Monster array
        buildMonsterArray(currentZoneLevel);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmap();
    }

    public void airDungeon(int currentZoneLevel, SpriteSheet skyTiles) {
        // Build Sprite Array
        buildSpriteArray(currentZoneLevel, mapInformation.airMapLevels[currentZoneLevel-1], skyTiles, 15);

        // Build Monster array
        buildMonsterArray(currentZoneLevel);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmap();
    }

    public void bugDungeon(int currentZoneLevel, SpriteSheet bugTiles) {
        // Build Sprite Array
        buildSpriteArray(currentZoneLevel, mapInformation.bugMapLevels[currentZoneLevel-1], bugTiles, 15);

        // Build Monster array
        buildMonsterArray(currentZoneLevel);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmap();
    }

    public void randomDungeon(int currentZoneLevel, SpriteSheet[] sheets) {
        Random sheet = new Random();
        Random tile = new Random();
        int sheet_i, tile_i;
        // Build Map with Sprites
        for (int iRow = 0; iRow < NUMBER_OF_MAP_ROWS; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                // Pick sheet
                sheet_i = sheet.nextInt(5);
                tile_i = tile.nextInt(16);
                mapSprite[iRow][iCol] = sheets[sheet_i].getTile(tile_i+1);
            }
        }
        // Set Arrow Symbols
        mapSprite[4][1] = arrowDown;

        if(currentZoneLevel < 20+1){ // 20 -> maxLevel
            mapSprite[0][1] = arrowUp;
        }else{
            mapSprite[0][1] = bossSymbol;
        }

        // Build Monster array
        buildMonsterArray(currentZoneLevel);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmap();

    }

    // BUILD SPRITE ARRAY
    public void buildSpriteArray(int currentZoneLevel, int[] mapTiles, SpriteSheet sheet, int maxLevel){
        // Build Map with Sprites
        int tile_counter = 0;
        for (int iRow = 0; iRow < NUMBER_OF_MAP_ROWS; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                // get corresponding sprite
                //Log.w("texto", String.valueOf(mapTiles[tile_counter]));
                mapSprite[iRow][iCol] = sheet.getTile(mapTiles[tile_counter]);
                tile_counter+=1;
            }
        }
        // Set Arrow Symbols
        mapSprite[4][1] = arrowDown;

        if(currentZoneLevel < maxLevel+1){
            mapSprite[0][1] = arrowUp;
        }else{
            mapSprite[0][1] = bossSymbol;
        }
    }

    public void buildSpriteArrayFixed(int[] mapTiles){
        // Build Map with Sprites
        int tile_counter = 0;
        for (int iRow = 0; iRow < NUMBER_OF_MAP_ROWS; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                // get corresponding sprite
                //Log.w("texto", String.valueOf(mapTiles[tile_counter]));
                mapSprite[iRow][iCol] = fixedSpriteSheet.getTile_5x5(mapTiles[tile_counter]);
                tile_counter += 1;
            }
        }
    }

    public void buildSpriteArrayFixed2(int[] mapTiles){     // Para o bug dungeon
        // Build Map with Sprites
        int tile_counter = 0;
        for (int iRow = 0; iRow < NUMBER_OF_MAP_ROWS; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                // get corresponding sprite
                //Log.w("texto", String.valueOf(mapTiles[tile_counter]));
                mapSprite[iRow][iCol] = fixedSpriteSheet.getTile_5x5(mapTiles[tile_counter]);
                tile_counter += 1;
            }
        }
        mapSprite[2][0] = bugDungeonSymbol;
    }

    // BUILD BITMAPS
    public void buildBitmap(){
        int current_left = 0;
        int current_top = 0;
        int current_right = TILESIZE;
        int current_bot = TILESIZE;
        // Create canvas to draw on it
        Canvas mapCanvas = new Canvas(bitmap);
        Paint p = new Paint();
        p.setAlpha(50);

        // Add all sprite bitmaps to canvas
        for (int iRow = 0; iRow < NUMBER_OF_MAP_ROWS; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                mapCanvas.drawBitmap(mapSprite[iRow][iCol].getSpriteBitmap(), null, new Rect(current_left, current_top,current_right, current_bot), null);
                if(monsterArray[iRow][iCol] > 0 && !(iRow == 0 && iCol == 1) && !(iRow == 4 && iCol == 1) ){    // Draw Monster Symbol if Monster exists in this tile
                    mapCanvas.drawBitmap(monsterSymbol.getSpriteBitmap(), null, new Rect(current_left, current_top,current_right, current_bot), p);
                }
                current_left += TILESIZE;
                current_right += TILESIZE;
            }
            current_left = 0;
            current_right = TILESIZE;
            current_top += TILESIZE;
            current_bot += TILESIZE;
        }
        return;
    }

    public void buildBitmapFixed(){
        int current_left = 0;
        int current_top = 0;
        int current_right = TILESIZE;
        int current_bot = TILESIZE;
        // Create canvas to draw on it
        Canvas mapCanvas = new Canvas(bitmap);

        // Add all sprite bitmaps to canvas
        for (int iRow = 0; iRow < NUMBER_OF_MAP_ROWS; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                mapCanvas.drawBitmap(mapSprite[iRow][iCol].getSpriteBitmap(), null, new Rect(current_left, current_top,current_right, current_bot), null);
                current_left += TILESIZE;
                current_right += TILESIZE;
            }
            current_left = 0;
            current_right = TILESIZE;
            current_top += TILESIZE;
            current_bot += TILESIZE;
        }
        return;
    }


    // BUILD MONSTER ARRAY
    public void buildMonsterArray(int currentZoneLevel) {
        // 0 if no Monster; 1< if Monster and number is their level
        Random rand = new Random();
        int monsterProbability, randomMonsterLevel;
        int maxMonstersInZone = 3;
        int numberOfMonsters = 0;
        for (int i = 0; i < NUMBER_OF_MAP_ROWS; i++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                monsterProbability = rand.nextInt(100);                                 // Generate number between 0 and 100
                if(monsterProbability <= 20 && numberOfMonsters <= maxMonstersInZone){                 // 20% probability of adding monster to Tile
                    monsterArray[i][iCol] = 1;
                    numberOfMonsters = numberOfMonsters + 1;
                }else{
                    monsterArray[i][iCol] = 0;
                }
            }

        }

    }

    // Get Functions
    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public int[] getTileIndexMap(){
        return this.mapSpriteSheetIndex;
    }

    public int[][] getMonsterArray(){
        return this.monsterArray;
    }

    // Set Functions
    public void setSymbolsSpriteSheet(){
        // get symbol Sprites
        arrowUp = symbolsSpriteSheet.getMonsterTile(5);
        arrowDown = symbolsSpriteSheet.getMonsterTile(4);
        monsterSymbol = symbolsSpriteSheet.getMonsterTile(1);
        bossSymbol = symbolsSpriteSheet.getMonsterTile(10);
        bugDungeonSymbol = symbolsSpriteSheet.getMonsterTile(8);
        arrowRight = symbolsSpriteSheet.getMonsterTile(6);
    }

    public String getTileType(int currentZoneLevel, String currentZone, int tile) {
        Log.w("texto", "getTileType");
        Log.w("texto", String.valueOf(currentZoneLevel));
        Log.w("texto", String.valueOf(tile));
        Log.w("texto", String.valueOf(mapInformation.waterMapLevels[currentZoneLevel][tile]));
        switch(currentZone) {
            case "waterDungeon":
                return mapInformation.waterTileType[mapInformation.waterMapLevels[currentZoneLevel][tile]];
            case "fireDungeon":
                return mapInformation.fireTileType[mapInformation.fireMapLevels[currentZoneLevel][tile]];
            case "groundDungeon":
                return mapInformation.groundTileType[mapInformation.groundMapLevels[currentZoneLevel][tile]];
            case "airDungeon":
                return mapInformation.airTileType[mapInformation.airMapLevels[currentZoneLevel][tile]];
            case "bugDungeon":
                return mapInformation.bugTileType[mapInformation.airMapLevels[currentZoneLevel][tile]];
        }
        return "erro";
    }

}
