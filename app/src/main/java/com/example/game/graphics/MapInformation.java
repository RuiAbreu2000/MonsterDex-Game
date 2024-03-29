package com.example.game.graphics;

public class MapInformation {
    public String[] waterTileType = {
            "ground", "water", "water", "water",
            "water", "water", "ground", "ground",
            "water", "water", "water", "water",
            "water", "water", "water", "water",
    };
    public String[] fireTileType = {
            "fire", "fire", "ground", "fire",
            "fire", "fire", "fire", "fire",
            "fire", "fire", "fire", "ground",
            "fire", "fire", "fire", "fire",
    };
    public String[] groundTileType = {
            "ground", "bug", "ground", "ground",
            "ground", "ground", "water", "ground",
            "ground", "ground", "ground", "ground",
            "ground", "ground", "ground", "ground",
    };
    public String[] airTileType = {
            "air", "air", "air", "air",
            "air", "air", "water", "air",
            "air", "air", "fire", "air",
            "air", "air", "air", "air",
    };

    public String[] bugTileType = {
            "bug", "bug", "bug", "bug",
            "bug", "ground", "water", "air",
            "bug", "bug", "bug", "bug",
            "air", "ground", "bug", "bug",
    };



    // HOME MAP
    public int[] homeMap = {
            6, 7, 8, 9,
            18, 13, 12, 19,
            3, 16, 12, 1,
            1, 1, 17, 1,
            1, 1, 11, 1,
    };

    public int[] zoneSelection_1Map = {
            1, 21, 22, 1,
            1, 17, 17, 1,
            3, 12, 12, 2,
            1, 17, 17, 1,
            1, 23, 24, 1,
    };

    public int[] zoneSelection_2Map = {
            15, 15, 15, 15,
            1, 1, 1, 1,
            3, 14, 16, 2,
            1, 17, 1, 1,
            1, 25, 1, 1,
    };

    public int[] zoneSelection_3HotMap = {
            10, 10, 10, 10,
            10, 10, 10, 10,
            10, 10, 10, 10,
            10, 10, 10, 10,
            10, 10, 10, 10,
    };

    public int[] zoneSelection_3ColdMap = {
            20, 20, 20, 20,
            20, 20, 20, 20,
            20, 20, 20, 20,
            20, 20, 20, 20,
            20, 20, 20, 20,
    };

    public int[] zoneSelection_3MildMap = {
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
    };

    // DUNGEONS
    public int[][] waterMapLevels = {
            {       // Level 1
                    2, 1, 2, 2,
                    1, 2, 2, 1,
                    2, 2, 2, 2,
                    2, 1, 2, 1,
                    1, 2, 2, 2,
            },            { // Level 2
            2, 2, 2, 2,
            2, 2, 2, 2,
            2, 2, 2, 2,
            2, 2, 2, 2,
            2, 2, 2, 2,
    },            { // Level 3
            3, 3, 3, 3,
            2, 3, 3, 2,
            2, 2, 2, 1,
            1, 2, 2, 2,
            2, 2, 2, 1,
    },            { // Level 4
            3, 1, 3, 3,
            3, 3, 3, 3,
            3, 3, 3, 3,
            2, 2, 2, 2,
            2, 2, 2, 2,
    },            { // Level 5
            4, 4, 3, 3,
            4, 4, 3, 3,
            4, 4, 3, 3,
            3, 3, 3, 3,
            3, 3, 3, 3,
    },            { // Level 6
            4, 4, 4, 4,
            4, 4, 4, 4,
            4, 4, 4, 4,
            3, 4, 4, 3,
            3, 3, 3, 3,
    },            { // Level 7
            4, 4, 4, 5,
            5, 5, 4, 5,
            4, 4, 5, 5,
            4, 4, 4, 3,
            3, 3, 3, 3,
    },           { // Level 8
            5, 5, 5, 5,
            5, 5, 5, 5,
            4, 4, 5, 5,
            4, 4, 5, 5,
            4, 4, 3, 3,
    },            { // Level 9
            6, 6, 6, 5,
            6, 6, 6, 6,
            5, 5, 6, 5,
            5, 5, 5, 5,
            5, 5, 5, 5,
    },            { // Level 10
            6, 4, 4, 6,
            6, 7, 4, 6,
            6, 4, 7, 6,
            6, 7, 4, 6,
            6, 5, 5, 6,
    },            { // Level 11
            7, 4, 4, 7,
            4, 4, 7, 4,
            4, 7, 4, 4,
            7, 4, 7, 4,
            4, 4, 6, 6,
    },            { // Level 12
            6, 6, 8, 6,
            6, 6, 6, 6,
            6, 6, 6, 6,
            7, 4, 4, 7,
            7, 4, 7, 7,
    },            { // Level 13
            8, 6, 6, 8,
            8, 6, 6, 8,
            6, 8, 6, 8,
            6, 6, 6, 8,
            6, 6, 6, 6,
    },            {         // Level 14
            9, 9, 9, 9,
            9, 9, 9, 9,
            9, 9, 9, 9,
            9, 9, 9, 9,
            9, 9, 9, 9,
    },            {         // Level 15
            9, 10, 10, 10,
            9, 10, 10, 10,
            9, 9, 10, 10,
            10, 10, 10, 10,
            9, 9, 9, 9,
    },            {         // Level 15
            10, 11, 11, 11,
            11, 11, 11, 11,
            11, 11, 11, 10,
            10, 10, 10, 10,
            10, 10, 10, 10,
    }
            ,            { // Level 16
            12, 13, 13, 13,
            12, 12, 13, 13,
            11, 12, 12, 13,
            11, 11, 12, 13,
            11, 11, 11, 12,
    },
                        { // Level 17
            14, 13, 13, 13,
            13, 14, 13, 13,
            13, 13, 13, 14,
            14, 13, 13, 13,
            13, 13, 13, 13
    },
                        { // Level 18
            15, 15, 14, 14,
            15, 15, 14, 14,
            15, 15, 15, 14,
            14, 14, 14, 14,
            14, 14, 14, 14
    },                  { // Level 19
            16, 15, 15, 15,
            15, 15, 15, 15,
            15, 15, 14, 14,
            15, 14, 14, 14,
            15, 14, 14, 14
    },                  { // Level 20
            16, 16, 16, 16,
            16, 16, 16, 16,
            16, 16, 16, 16,
            16, 15, 16, 15,
            14, 16, 16, 16
    }

    };

    public int[][] fireMapLevels = {
            {       // Level 1
                    1, 1, 1, 2,
                    1, 2, 1, 1,
                    1, 1, 1, 1,
                    1, 1, 1, 1,
                    1, 1, 1, 1,
            },            { // Level 2
            1, 2, 2, 1,
            2, 2, 2, 2,
            2, 2, 2, 1,
            1, 2, 2, 1,
            1, 1, 1, 1,
    },            { // Level 3
            2, 3, 3, 3,
            2, 3, 3, 3,
            2, 2, 2, 3,
            1, 1, 2, 2,
            1, 1, 1, 2,
    },            { // Level 4
            2, 2, 3, 3,
            2, 2, 3, 3,
            2, 2, 3, 3,
            1, 1, 3, 3,
            1, 1, 3, 3,
    },            { // Level 5
            4, 4, 3, 4,
            4, 4, 3, 4,
            3, 3, 3, 4,
            3, 3, 3, 4,
            3, 3, 3, 3,
    },            { // Level 6
            5, 5, 5, 5,
            4, 4, 4, 4,
            4, 4, 4, 4,
            3, 3, 4, 4,
            3, 3, 4, 4,
    },            { // Level 7
            5, 5, 5, 5,
            4, 5, 5, 5,
            5, 4, 4, 4,
            3, 3, 4, 4,
            3, 3, 4, 4,
    },           { // Level 8
            5, 5, 5, 5,
            5, 5, 5, 5,
            5, 5, 5, 5,
            5, 5, 5, 5,
            4, 4, 4, 4,
    },            { // Level 9
            6, 6, 6, 6,
            6, 6, 6, 6,
            5, 6, 6, 5,
            5, 5, 5, 5,
            5, 5, 5, 5,
    },            { // Level 10
            7, 7, 7, 7,
            7, 7, 7, 7,
            7, 6, 6, 6,
            6, 6, 6, 4,
            6, 6, 6, 4,
    },            { // Level 11
            8, 8, 8, 8,
            8, 8, 8, 8,
            7, 7, 7, 7,
            6, 7, 7, 7,
            6, 6, 4, 4,
    },            { // Level 12
            9, 9, 9, 9,
            9, 9, 9, 8,
            9, 9, 8, 8,
            8, 8, 8, 8,
            8, 8, 8, 8,
    },            { // Level 13
            9, 10, 10, 9,
            9, 10, 10, 9,
            9, 9, 9, 9,
            8, 8, 8, 3,
            4, 8, 8, 4,
    },            { // Level 14
            11, 10, 10, 11,
            10, 10, 10, 10,
            10, 11, 10, 10,
            9, 9, 10, 10,
            9, 9, 9, 9,
    },            { // Level 15
            12, 12, 12, 12,
            10, 10, 10, 10,
            10, 10, 10, 10,
            10, 11, 10, 10,
            10, 10, 11, 10,
    },            { // Level 15
            12, 10, 10, 12,
            10, 10, 12, 10,
            10, 12, 10, 10,
            10, 10, 10, 10,
            11, 10, 10, 10,
    },            { // Level 16
            13, 13, 13, 13,
            13, 13, 13, 13,
            13, 13, 10, 10,
            10, 10, 10, 10,
            10, 10, 10, 12,
    },            { // Level 17
            14, 15, 14, 14,
            13, 14, 14, 13,
            13, 13, 13, 13,
            13, 13, 13, 12,
            13, 13, 13, 13,
    },            { // Level 18
            14, 14, 14, 15,
            15, 14, 14, 14,
            14, 14, 15, 14,
            14, 15, 14, 14,
            14, 14, 14, 14,
    },            { // Level 19
            14, 16, 16, 14,
            14, 16, 16, 14,
            14, 14, 14, 15,
            15, 14, 14, 14,
            14, 14, 14, 14,
    }

    };

    public int[][] groundMapLevels = {
            {       // Level 1
                    1, 2, 1, 1,
                    1, 3, 1, 1,
                    2, 1, 2, 1,
                    2, 2, 2, 2,
                    2, 2, 2, 2,
            },            { // Level 2
            1, 1, 1, 1,
            1, 3, 3, 1,
            1, 3, 1, 1,
            2, 1, 1, 1,
            2, 2, 3, 3,
    },            { // Level 3
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 1, 1,
            1, 3, 3, 1,
            1, 2, 2, 1,
    },            { // Level 4
            1, 1, 1, 1,
            1, 1, 3, 1,
            3, 1, 1, 1,
            1, 1, 1, 1,
            1, 1, 3, 2,
    },            { // Level 5
            5, 4, 4, 4,
            4, 4, 4, 5,
            5, 4, 5, 4,
            4, 4, 4, 4,
            4, 5, 5, 4,
    },            { // Level 6
            4, 4, 4, 4,
            7, 4, 5, 4,
            4, 7, 5, 4,
            4, 4, 4, 4,
            4, 4, 4, 4,
    },            { // Level 7
            4, 4, 4, 4,
            4, 8, 8, 4,
            4, 8, 8, 4,
            4, 4, 4, 4,
            4, 4, 4, 4,
    },           { // Level 8
            8, 9, 9, 9,
            8, 8, 8, 9,
            8, 8, 8, 8,
            8, 8, 8, 8,
            1, 1, 1, 1,
    },            { // Level 9
            9, 10, 9, 10,
            9, 9, 9, 9,
            9, 9, 8, 8,
            8, 8, 8, 8,
            8, 8, 8, 8,
    },            { // Level 10
            9, 9, 9, 10,
            9, 10, 9, 9,
            9, 9, 10, 9,
            10, 9, 8, 8,
            9, 8, 10, 8,
    },            { // Level 11
            11, 12, 11, 11,
            11, 12, 12, 11,
            11, 11, 12, 11,
            11, 11, 12, 11,
            11, 11, 11, 11,
    },            { // Level 12
            12, 13, 12, 13,
            13, 12, 12, 12,
            13, 13, 12, 12,
            12, 12, 12, 12,
            12, 12, 13, 13,
    },            { // Level 13
            13, 14, 14, 14,
            12, 12, 14, 14,
            12, 12, 14, 14,
            13, 12, 14, 12,
            12, 12, 14, 14,
    },            { // Level 14
            15, 14, 15, 14,
            15, 15, 15, 15,
            14, 15, 14, 15,
            15, 15, 15, 15,
            14, 14, 14, 14,
    },            { // Level 15
            15, 16, 16, 15,
            15, 14, 14, 15,
            15, 14, 14, 15,
            15, 14, 14, 15,
            15, 14, 14, 15,
    },            { // Level 16
            12, 16, 16, 16,
            16, 16, 16, 16,
            16, 15, 16, 11,
            15, 16, 16, 15,
            15, 16, 15, 13,
    }
    };

    public int[][] airMapLevels = {
            {                   // Level 1
                    2, 1, 1, 1,
                    1, 1, 1, 1,
                    1, 1, 2, 1,
                    1, 1, 1, 1,
                    1, 1, 1, 1,
            },            { // Level 2
            1, 1, 1, 1,
            1, 2, 2, 2,
            1, 2, 2, 2,
            1, 1, 1, 1,
            1, 1, 1, 1,
    },            { // Level 3
            2, 2, 2, 2,
            2, 2, 3, 2,
            3, 2, 2, 2,
            2, 2, 2, 2,
            2, 2, 1, 1,
    },            { // Level 4
            3, 3, 3, 3,
            3, 3, 3, 3,
            3, 3, 2, 3,
            2, 3, 3, 3,
            2, 2, 2, 1,
    },            { // Level 5
            2, 3, 4, 3,
            4, 3, 3, 3,
            3, 3, 3, 3,
            3, 3, 3, 4,
            1, 3, 3, 3,
    },            { // Level 6
            5, 5, 5, 5,
            4, 5, 5, 4,
            4, 4, 4, 4,
            4, 4, 4, 4,
            4, 4, 4, 4,
    },            { // Level 7
            6, 6, 6, 6,
            6, 6, 6, 6,
            6, 6, 6, 6,
            6, 5, 5, 6,
            5, 5, 5, 5,
    },           { // Level 8
            7, 7, 7, 7,
            7, 7, 6, 6,
            7, 7, 7, 7,
            6, 6, 7, 7,
            6, 6, 6, 6,
    },            { // Level 9
            11, 7, 11, 7,
            7, 1, 7, 11,
            11, 7, 7, 7,
            7, 11, 7, 7,
            11, 7, 7, 7,
    },            { // Level 10
            9, 8, 8, 8,
            8, 8, 9, 8,
            9, 8, 8, 8,
            8, 8, 9, 8,
            8, 8, 8, 8,
    },            { // Level 11
            10, 10, 8, 10,
            10, 10, 10, 10,
            10, 8, 8, 10,
            8, 10, 8, 10,
            10, 10, 8, 10,
    },            { // Level 12
            10, 10, 10, 10,
            10, 11, 10, 10,
            10, 10, 10, 10,
            10, 10, 10, 11,
            10, 10, 10, 10,
    },            { // Level 13
            12, 2, 2, 2,
            2, 2, 12, 2,
            2, 2, 2, 2,
            12, 2, 2, 12,
            2, 2, 2, 2,
    },            { // Level 14
            14, 14, 14, 14,
            14, 14, 14, 14,
            13, 13, 13, 13,
            13, 13, 13, 13,
            13, 13, 13, 13,
    },            { // Level 15
            14, 15, 14, 15,
            14, 14, 14, 14,
            14, 14, 14, 14,
            14, 14, 14, 14,
            13, 13, 13, 13,
    },            { // Level 16
            15, 16, 14, 16,
            15, 14, 16, 14,
            14, 15, 14, 15,
            14, 14, 14, 14,
            13, 13, 13, 13,
    }
    };

    public int[][] bugMapLevels = {
            {                   // Level 1
                    1, 1, 1, 1,
                    1, 1, 2, 1,
                    1, 2, 1, 1,
                    1, 1, 1, 1,
                    1, 1, 1, 1,
            },            { // Level 2
            1, 1, 2, 2,
            2, 2, 1, 2,
            1, 2, 2, 2,
            2, 2, 2, 2,
            1, 1, 1, 1,
    },            { // Level 3
            3, 3, 6, 3,
            3, 3, 3, 3,
            3, 3, 3, 3,
            2, 2, 2, 2,
            1, 1, 1, 1,
    },            { // Level 4
            2, 3, 3, 3,
            3, 3, 5, 3,
            3, 3, 2, 2,
            3, 2, 3, 3,
            3, 3, 3, 3,
    },            { // Level 5
            3, 3, 4, 3,
            3, 4, 4, 3,
            3, 3, 3, 3,
            4, 3, 3, 5,
            3, 3, 3, 3,
    },            { // Level 6
            4, 3, 4, 4,
            3, 4, 4, 4,
            4, 4, 5, 4,
            4, 4, 3, 4,
            4, 4, 4, 4,
    },            { // Level 7
            4, 4, 4, 4,
            5, 4, 7, 4,
            5, 5, 7, 4,
            4, 5, 3, 4,
            4, 4, 4, 4,
    },           { // Level 8
            3, 8, 3, 8,
            3, 3, 8, 3,
            3, 8, 3, 3,
            8, 3, 8, 3,
            3, 3, 3, 3,
    },            { // Level 9
            5, 8, 4, 9,
            3, 3, 3, 3,
            3, 5, 3, 3,
            3, 3, 4, 3,
            3, 3, 4, 3,
    },            { // Level 10
            10, 10, 10, 10,
            7, 10, 10, 10,
            10, 10, 5, 10,
            10, 10, 10, 10,
            10, 10, 10, 10,
    },            { // Level 11
            10, 10, 10, 10,
            10, 5, 10, 12,
            10, 10, 11, 10,
            10, 12, 12, 12,
            10, 10, 10, 10,
    },            { // Level 12
            10, 10, 12, 10,
            12, 12, 12, 12,
            10, 10, 10, 10,
            12, 10, 10, 10,
            10, 10, 11, 10,
    },            { // Level 13
            6, 10, 13, 13,
            13, 13, 13, 13,
            13, 10, 13, 10,
            6, 13, 13, 10,
            13, 13, 13, 13,
    },            { // Level 14
            15, 14, 14, 14,
            14, 14, 14, 14,
            14, 15, 14, 14,
            15, 14, 15, 13,
            14, 14, 15, 14,
    },            { // Level 15
            15, 16, 15, 14,
            14, 14, 14, 14,
            14, 15, 16, 16,
            16, 14, 15, 14,
            14, 14, 15, 14,
    }
    };
}
