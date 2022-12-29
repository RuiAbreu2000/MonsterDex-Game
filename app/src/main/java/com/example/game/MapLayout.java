package com.example.game;

public class MapLayout {
    public static final int TILE_WIDTH_PIXELS = 64;
    public static final int TILE_HEIGHT_PIXELS = 64;
    public static final int NUMBER_OF_ROW_TILES = 10;
    public static final int NUMBER_OF_COLUMN_TILES = 10;

    private int[][] layout;

    public MapLayout() {
        initializeLayout();
    }

    public int[][] getLayout() {
        return layout;
    }

    private void initializeLayout() {
        // 0 - Green
        // 1 - Blue
        // 2 - Blue with Black
        layout = new int[][] {
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0}
        };
    }
}
