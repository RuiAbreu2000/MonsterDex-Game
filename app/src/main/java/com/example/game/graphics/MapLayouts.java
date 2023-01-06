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
    private Sprite itemSymbol;
    private Sprite bossSymbol;

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


    // MAPAS
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
        Log.w("A", "ZONNE 1");

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmapFixed();
    }

    // DUNGEON MAPS
    public void waterDungeon(int currentZoneLevel, SpriteSheet waterTiles) {
        buildSpriteArray(currentZoneLevel, mapInformation.waterMapLevels[currentZoneLevel], waterTiles, 20);

        // Build Monster array
        buildMonsterArray(currentZoneLevel);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmap();

    }

    public void fireDungeon(int currentZoneLevel, SpriteSheet fireTiles) {
        // Build Sprite Array
        buildSpriteArray(currentZoneLevel, mapInformation.fireMapLevels[currentZoneLevel], fireTiles, 19);

        // Build Monster array
        buildMonsterArray(currentZoneLevel);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmap();
    }

    public void groundDungeon(int currentZoneLevel, SpriteSheet groundTiles) {
        // Build Sprite Array
        buildSpriteArray(currentZoneLevel, mapInformation.groundMapLevels[currentZoneLevel], groundTiles, 15);

        // Build Monster array
        buildMonsterArray(currentZoneLevel);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmap();
    }

    public void airDungeon(int currentZoneLevel, SpriteSheet skyTiles) {
        // Build Sprite Array
        buildSpriteArray(currentZoneLevel, mapInformation.airMapLevels[currentZoneLevel], skyTiles, 15);

        // Build Monster array
        buildMonsterArray(currentZoneLevel);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_COLUMNS * TILESIZE, NUMBER_OF_MAP_ROWS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmap();
    }


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

        if(currentZoneLevel < maxLevel){
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
                mapSprite[iRow][iCol] = fixedSpriteSheet.getTile(mapTiles[tile_counter]);
                tile_counter += 1;
            }
        }
    }

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

    private void buildRandomLayout(int[] indexes, int[] mapProbabilities){
        Random rand = new Random();
        for (int i = 0; i < NUMBER_OF_MAP_ROWS*NUMBER_OF_MAP_COLUMNS; i++) {
            int randomNumber = rand.nextInt(100);

            //Log.w("a", String.valueOf(randomNumber));
            if(randomNumber >= 0 && randomNumber <= mapProbabilities[0]){                           // Index 0
                mapSpriteSheetIndex[i] = indexes[0];
            }else if(randomNumber > mapProbabilities[0] && randomNumber <= mapProbabilities[1]){    // Index 1
                mapSpriteSheetIndex[i] = indexes[1];
            }else if(randomNumber > mapProbabilities[1] && randomNumber <= mapProbabilities[2]){    // index 2
                mapSpriteSheetIndex[i] = indexes[2];
            }else if(randomNumber > mapProbabilities[2] && randomNumber <= 100){                    // Index 3
                mapSpriteSheetIndex[i] = indexes[3];
            }
        }
        return;
    }

    public void buildMonsterArray(int currentZoneLevel) {
        // 0 if no Monster; 1< if Monster and number is their level
        Random rand = new Random();
        Random rand2 = new Random();
        int monsterProbability, randomMonsterLevel;
        int maxMonstersInZone = 4;
        int numberOfMonsters = 0;
        for (int i = 0; i < NUMBER_OF_MAP_ROWS; i++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                monsterProbability = rand.nextInt(100);                                 // Generate number between 0 and 100
                if(monsterProbability <= 20 && numberOfMonsters <= maxMonstersInZone){                 // 20% probability of adding monster to Tile
                    randomMonsterLevel = rand2.nextInt(((currentZoneLevel + 8) - currentZoneLevel) + 1) + currentZoneLevel;  // (max - min) + 1) + min
                    monsterArray[i][iCol] = randomMonsterLevel;
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
    }

}
