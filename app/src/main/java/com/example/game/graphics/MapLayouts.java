package com.example.game.graphics;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.NUMBER_OF_MAP_ROWS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

// Has the informatation about the current map
public class MapLayouts {

    private SpriteSheet spritesheet;
    private int[] mapSpriteSheetIndex;
    private int[][] monsterArray;
    public Sprite[][] mapSprite;
    private Bitmap bitmap;
    private SpriteSheet symbolsSpriteSheet;

    // Symbol Tiles
    public Sprite arrowUp;
    private Sprite arrowDown;
    private Sprite monsterSymbol;
    private Sprite itemSymbol;

    // Tile Indexes
    int[] groundIndexes = {2, 6, 10, 14};

    // Map Generator Probabilities
    int[] grassMapProbabilities =     new int[]{70,75,95,100}; // 70% grass, 20% dirt, 10% rocks

    public MapLayouts(SpriteSheet spritesheet, SpriteSheet symbolsSpriteSheet) {
        this.spritesheet = spritesheet;
        this.symbolsSpriteSheet = symbolsSpriteSheet;
        mapSpriteSheetIndex = new int[NUMBER_OF_MAP_ROWS*NUMBER_OF_MAP_COLUMNS];
        monsterArray = new int[NUMBER_OF_MAP_ROWS][NUMBER_OF_MAP_COLUMNS];
        mapSprite = new Sprite[NUMBER_OF_MAP_ROWS][NUMBER_OF_MAP_COLUMNS];
    }


    // GROUND MAPS
    public void grassMap(int currentZoneLevel) {
        // Build Tile Index Map
        this.buildRandomLayout(groundIndexes, grassMapProbabilities);


        // Build Sprite Array
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
        mapSprite[0][2] = arrowDown;
        mapSprite[4][2] = arrowUp;

        return;
    }

    public void buildBitmap(){
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
                if(monsterArray[iRow][iCol] > 0 && !(iRow == 0 && iCol == 2) && !(iRow == 4 && iCol == 2) ){    // Draw Monster Symbol if Monster exists in this tile
                    mapCanvas.drawBitmap(monsterSymbol.getSpriteBitmap(), null, new Rect(current_left, current_top,current_right, current_bot), null);
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

    private void buildRandomLayout(int[] indexes, int[] mapProbabilities){
        Random rand = new Random();
        Log.w("a", "buildRandomLayout");
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
        Log.w("a", "Setting Monster Symbol");
        // get symbol Sprites
        arrowUp = symbolsSpriteSheet.getMonsterTile(5);
        arrowDown = symbolsSpriteSheet.getMonsterTile(4);
        monsterSymbol = symbolsSpriteSheet.getMonsterTile(1);
    }

}
