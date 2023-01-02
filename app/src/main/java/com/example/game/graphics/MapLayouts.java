package com.example.game.graphics;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.NUMBER_OF_MAP_ROWS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

// Has the informatation about the current map
public class MapLayouts {

    private SpriteSheet spritesheet;
    private int[] mapSpriteSheetIndex;
    private int[] monsterArray;
    private Sprite[][] mapSprite;
    private Bitmap bitmap;
    private SpriteSheet symbolsSpriteSheet;

    // Symbol Tiles
    private Sprite arrowUp;
    private Sprite arrowDown;
    private Sprite monsterSymbol;
    private Sprite itemSymbol;

    // Tile Indexes
    int[] groundIndexes = {1, 5, 9, 13};
    int[] cloudIndexes = {3, 7, 11, 16};

    // Map Generator Probabilities
    int[] grassMapProbabilities =     new int[]{70,75,95,100}; // 70% grass, 20% dirt, 10% rocks
    int[] dirtMapProbabilities =      new int[]{20,25,95,100}; // 70% dirt, 20% grass, 10% rocks
    int[] clearskyMapProbabilities =  new int[]{};
    int[] stormmyMapProbabilities =   new int[]{};

    public MapLayouts(SpriteSheet spritesheet) {
        this.spritesheet = spritesheet;
    }


    // GROUND MAPS
    public void grassMap(int currentZoneLevel) {
        monsterArray = new int[NUMBER_OF_MAP_ROWS*NUMBER_OF_MAP_COLUMNS];
        // Build Tile Index Map
        mapSpriteSheetIndex = new int[NUMBER_OF_MAP_ROWS*NUMBER_OF_MAP_COLUMNS];
        this.buildRandomLayout(groundIndexes, grassMapProbabilities);

        // Build Sprite Array
        mapSprite = new Sprite[NUMBER_OF_MAP_ROWS][NUMBER_OF_MAP_COLUMNS];
        this.buildSpriteArray();

        // Build Monster Array
        this.buildMonsterArray(currentZoneLevel);

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_ROWS * TILESIZE, NUMBER_OF_MAP_COLUMNS * TILESIZE, conf); // this creates a MUTABLE bitmap
        this.buildBitmap();

        return;
    }


    public void buildSpriteArray(){
        // Build Map with Sprites
        for (int iRow = 0; iRow < NUMBER_OF_MAP_ROWS; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                // get corresponding sprite
                mapSprite[iRow][iCol] = spritesheet.getTile(mapSpriteSheetIndex[iRow+iCol]);
            }
        }
        // Set Arrow Symbols
        //mapSprite[0][2] = arrowUp;
        //mapSprite[4][2] = arrowDown;

        return;
    }

    public void buildBitmap(){
        int current_x = 0;
        int current_y = 0;
        // Create canvas to draw on it
        Canvas mapCanvas = new Canvas(bitmap);

        // Add all sprite bitmaps to canvas
        for (int iRow = 0; iRow < NUMBER_OF_MAP_ROWS; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                Log.w("a", String.valueOf(monsterArray[iRow+iCol]));
                mapCanvas.drawBitmap(mapSprite[iRow][iCol].getSpriteBitmap(), current_x, current_y, null);
                if(monsterArray[iRow+iCol] > 0){    // Draw Monster Symbol if Monster exists in this tile
                    Log.w("a", "INNN");
                    //mapCanvas.drawBitmap(monsterSymbol.getSpriteBitmap(), current_x, current_y, null);
                }
                current_x += TILESIZE;
            }
            current_y += TILESIZE;
            current_x = 0;
        }
        return;
    }

    private void buildRandomLayout(int[] indexes, int[] mapProbabilities){
        Random rand = new Random();

        for (int i = 0; i < NUMBER_OF_MAP_ROWS*NUMBER_OF_MAP_COLUMNS; i++) {
            int randomNumber = rand.nextInt(100);
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
        for (int i = 0; i < NUMBER_OF_MAP_ROWS*NUMBER_OF_MAP_COLUMNS; i++) {
            monsterProbability = rand.nextInt(100);                                 // Generate number between 0 and 100
            if(monsterProbability <= 20 && numberOfMonsters <= maxMonstersInZone){                 // 20% probability of adding monster to Tile
                randomMonsterLevel = rand.nextInt(((currentZoneLevel + 8) - currentZoneLevel) + 1) + currentZoneLevel;  // (max - min) + 1) + min
                monsterArray[i] = randomMonsterLevel;
                numberOfMonsters++;
            }else{
                monsterArray[i] = 0;
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

    public int[] getMonsterArray(){
        return this.monsterArray;
    }

    // Set Functions
    public void setSymbolsSpriteSheet(SpriteSheet sheet){
        this.symbolsSpriteSheet = sheet;
        // get symbol Sprites
        arrowUp = symbolsSpriteSheet.getMonsterTile(5);
        arrowDown = symbolsSpriteSheet.getMonsterTile(4);
        monsterSymbol = symbolsSpriteSheet.getMonsterTile(1);
    }

}
