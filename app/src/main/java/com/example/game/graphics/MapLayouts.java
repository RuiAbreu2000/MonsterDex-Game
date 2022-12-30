package com.example.game.graphics;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.NUMBER_OF_MAP_ROWS;
import static com.example.game.SharedViewModel.TILESIZE;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

// Has the informatation about the current map
public class MapLayouts {

    private SpriteSheet spritesheet;
    private int[] mapSpriteSheetIndex;
    private Sprite[][] mapSprite;
    private Bitmap bitmap;

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
    public Bitmap grassMap() {
        // Build Tile Index Map
        mapSpriteSheetIndex = new int[NUMBER_OF_MAP_ROWS*NUMBER_OF_MAP_COLUMNS];
        buildRandomLayout(groundIndexes, grassMapProbabilities);

        // Build Sprite Array
        mapSprite = new Sprite[NUMBER_OF_MAP_ROWS][NUMBER_OF_MAP_COLUMNS];
        buildSpriteArray();

        // Build Bitmap
        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        bitmap = Bitmap.createBitmap(NUMBER_OF_MAP_ROWS*TILESIZE, NUMBER_OF_MAP_COLUMNS*TILESIZE, conf); // this creates a MUTABLE bitmap
        buildBitmap();

        // return bitmap to sharedviewmodel
        return bitmap;
    }

    public void dirtMap(){
        return ;
    }

    // CLOUD MAPS
    public void clearskytMap(){
        return ;
    }

    public void stormmyMap(){
        return ;
    }


    public void buildSpriteArray(){

        // Build Map with Sprites
        for (int iRow = 0; iRow < NUMBER_OF_MAP_ROWS; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_MAP_COLUMNS; iCol++) {
                // get corresponding sprite
                mapSprite[iRow][iCol] = spritesheet.getTile(mapSpriteSheetIndex[iRow+iCol]);
            }
        }
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
                mapCanvas.drawBitmap(mapSprite[iRow][iCol].getSpriteBitmap(), current_x, current_y, null);
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

    // Get Functions
    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public int[] getTileIndexMap(){
        return this.mapSpriteSheetIndex;
    }

    public void getMosterMatrix(){
        return;
    }

}
