package com.example.game.graphics;

import static com.example.game.SharedViewModel.NUMBER_OF_MAP_COLUMNS;
import static com.example.game.SharedViewModel.NUMBER_OF_MAP_ROWS;

import java.util.Random;

public class MapLayouts {

    // Tile Indexes
    int[] groundIndexes = {1, 5, 9, 13};
    int[] cloudIndexes = {3, 7, 11, 16};

    // Map Generator Probabilities
    int[] grassMapProbabilities =     new int[]{70,90,95,100}; // 70% grass, 20% dirt, 10% rocks
    int[] dirtMapProbabilities =      new int[]{20,25,95,100}; // 70% dirt, 20% grass, 10% rocks
    int[] clearskyMapProbabilities =  new int[]{};
    int[] stormmyMapProbabilities =   new int[]{};


    // GROUND MAPS
    public int[] grassMap() {
        return buildRandomLayout(groundIndexes, grassMapProbabilities);
    }

    public int[] dirtMap(){
        return buildRandomLayout(groundIndexes, dirtMapProbabilities);
    }

    // CLOUD MAPS
    public int[] clearskytMap(){
        return buildRandomLayout(cloudIndexes, clearskyMapProbabilities);
    }

    public int[] stormmyMap(){
        return buildRandomLayout(cloudIndexes, stormmyMapProbabilities);
    }

    private int[] buildRandomLayout(int[] indexes, int[] mapProbabilities){
        Random rand = new Random();
        int[] randomlayout = new int[NUMBER_OF_MAP_ROWS*NUMBER_OF_MAP_COLUMNS];
        for (int i = 0; i < NUMBER_OF_MAP_ROWS*NUMBER_OF_MAP_COLUMNS; i++) {
            int randomNumber = rand.nextInt(100);
            if(randomNumber >= 0 && randomNumber <= mapProbabilities[0]){                             // Index 0
                randomlayout[i] = indexes[0];
            }else if(randomNumber > mapProbabilities[0] && randomNumber <= mapProbabilities[1]){    // Index 1
                randomlayout[i] = indexes[1];
            }else if(randomNumber > mapProbabilities[1] && randomNumber <= mapProbabilities[2]){     // index 2
                randomlayout[i] = indexes[2];
            }else if(randomNumber > mapProbabilities[2] && randomNumber <= 100){                    // Index 3
                randomlayout[i] = indexes[3];
            }
        }
        return randomlayout;
    }

}
