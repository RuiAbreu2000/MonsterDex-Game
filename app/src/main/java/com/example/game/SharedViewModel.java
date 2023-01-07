package com.example.game;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.game.city.Battle;
import com.example.game.databases.AppDatabase;
import com.example.game.databases.Monster;
import com.example.game.databases.MonsterDex;
import com.example.game.graphics.MapLayouts;
import com.example.game.graphics.SpriteSheet;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class SharedViewModel extends AndroidViewModel{
    // TILESET INDEX TYPES

    // MAP CONSTANTS
    public static final int NUMBER_OF_MAP_ROWS = 5;
    public static final int NUMBER_OF_MAP_COLUMNS = 4;
    public static final int SPRITE_WIDTH_PIXELS = 256;
    public static final int SPRITE_HEIGHT_PIXELS = 256;
    public static final int MAP_X_SIZE = 1280;              //SPRITE_WIDTH_PIXELS * NUMBER_OF_MAP_COLUMNS
    public static final int MAP_Y_SIZE = 1280;              //SPRITE_HEIGHT_PIXELS * NUMBER_OF_MAP_ROWS
    public static final int TILESIZE = 256;

    // Important
    public AppDatabase db;
    public MapLayouts maplayouts;
    Stack<Fragment> fragmentStack = new Stack<Fragment>();

    // Sprite Sheets
        // Dungeon
    public SpriteSheet waterTiles;
    public SpriteSheet fireTiles;
    public SpriteSheet groundTiles;
    public SpriteSheet skyTiles;
        // Monsters
    public SpriteSheet airMonsterSpritesheet;
    public SpriteSheet bugMonsterSpritesheet;
    public SpriteSheet fireMonsterSpritesheet;
    public SpriteSheet groundMonsterSpritesheet;
    public SpriteSheet waterMonsterSpritesheet;

        // Other
    public SpriteSheet symbolsSpriteSheet;
    public SpriteSheet fixedSpriteSheet;
    public SpriteSheet spritesheet;


    // Game State Vars
    public int currentZoneLevel = 1;                // Level of Current Zone
    public String currentZone = null;
    public String monsterType;

    public int currentMonster = -1;
    public int currentMonster2 = 1;
    public int monster_change = 0;
    public int create = 0;
    public Battle.Character enemy;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);

        // Spritesheets
            // Dungeon
        waterTiles  = new SpriteSheet(application, "waterTiles");
        fireTiles= new SpriteSheet(application, "fireTiles");
        groundTiles= new SpriteSheet(application, "groundTiles");
        skyTiles= new SpriteSheet(application, "skyTiles");
            // Monsters
        airMonsterSpritesheet = new SpriteSheet(application, "airMonsters");
        bugMonsterSpritesheet = new SpriteSheet(application, "bugMonsters");
        fireMonsterSpritesheet = new SpriteSheet(application, "fireMonsters");
        groundMonsterSpritesheet = new SpriteSheet(application, "groundMonsters");
        waterMonsterSpritesheet = new SpriteSheet(application, "waterMonsters");
            // Other
        symbolsSpriteSheet = new SpriteSheet(application, "symbols");
        fixedSpriteSheet = new SpriteSheet(application, "fixedTiles");
        spritesheet = new SpriteSheet(application, "tiles");
        maplayouts = new MapLayouts(spritesheet, symbolsSpriteSheet, fixedSpriteSheet);
        maplayouts.setSymbolsSpriteSheet();
    }

    public AppDatabase getDatabase(){
        return db;
    }

    // Map Generation Functions
    public Bitmap getBitmap() {     // Returns Map Bitmap
        return maplayouts.getBitmap();
    }
    public int[] getTileMatrix() {  // Returns Array with Tile Numbers
        return maplayouts.getTileIndexMap().clone();
    }
    public int[][] getMonsterArray(){ return maplayouts.getMonsterArray().clone();}

    public void getMap(String map) { // Builds map on MapLayouts
        switch(map) {
            case "home":
                currentZone = "home";
                maplayouts.homeMap();
                return;
            case "zoneSelection_1":
                currentZone = "zoneSelection_1";
                maplayouts.zoneSelection_1();
                return;
            case "zoneSelection_2":
                currentZone = "zoneSelection_2";
                maplayouts.zoneSelection_2();
                return;
            case "waterDungeon":
                currentZone = "waterDungeon";
                maplayouts.waterDungeon(currentZoneLevel, waterTiles);
                return;
            case "fireDungeon":
                currentZone = "fireDungeon";
                maplayouts.fireDungeon(currentZoneLevel, fireTiles);
                return;
            case "groundDungeon":
                currentZone = "groundDungeon";
                maplayouts.groundDungeon(currentZoneLevel, groundTiles);
                return;
            case "airDungeon":
                currentZone = "airDungeon";
                maplayouts.airDungeon(currentZoneLevel, skyTiles);
                return;
            case "randomDungeon":
                currentZone = "randomDungeon";
                maplayouts.randomDungeon(currentZoneLevel, new SpriteSheet[]{waterTiles, fireTiles, groundTiles, skyTiles});
                return;
            case "zoneSelection_3_cold":
                currentZone = "zoneSelection_3";
                maplayouts.zoneSelection_3_cold();
                return;
            case "zoneSelection_3_hot":
                currentZone = "zoneSelection_3";
                maplayouts.zoneSelection_3_hot();
                return;
        }
    }

    // Return SpriteSheets
    public SpriteSheet getAirMonsterSpriteSheet() {
        return this.airMonsterSpritesheet;
    }

    public SpriteSheet getBugMonsterSpriteSheet() {
        return this.bugMonsterSpritesheet;
    }

    public SpriteSheet getFireMonsterSpriteSheet() {
        return this.fireMonsterSpritesheet;
    }

    public SpriteSheet getGroundMonsterSpriteSheet() {
        return this.groundMonsterSpritesheet;
    }

    public SpriteSheet getWaterMonsterSpriteSheet() {
        return this.waterMonsterSpritesheet;
    }

    // Add to Database Functions
    public void createNewMonster(MonsterDex newMonster){
        db.monsterDexDao().addMonster(newMonster);
    }

    public void createNewMonsterTeam(Monster newMonster){
        db.monsterDao().addMonster(newMonster);
    }

    // Functions for Zone Level
    public void incrementLevel() { this.currentZoneLevel += 1;}
    public void setLevelTo_1(){this.currentZoneLevel = 1;}
    public int getZoneLevel(){ Random rand = new Random(); return this.currentZoneLevel + rand.nextInt(5);}
    public int getLevel(){return this.currentZoneLevel;};
    public String getCurrentType() {return monsterType;}
    public void setCurrentType(int tile) {
        this.monsterType = maplayouts.getTileType(currentZoneLevel,currentZone, tile);
        Log.w("texto", this.monsterType);
    }
    public void setCurrentTypeRandom() {
        Random sheet = new Random();
        Random tile = new Random();
        String[] randomzone = new String[]{"waterDungeon", "fireDungeon", "groundDungeon", "airDungeon"};

        this.monsterType = maplayouts.getTileType(currentZoneLevel ,randomzone[sheet.nextInt(4)],  tile.nextInt(16));
        Log.w("texto", this.monsterType);
    }

    // Save Zone Before going into Battle
    public void addFragment(Fragment fragment){
        fragmentStack.push(fragment);
        fragmentStack.lastElement().onPause();
    }
    public Fragment getLastFragment(){
        return fragmentStack.lastElement();
    }

    // Monster DB Functions
    public MonsterDex getRandomMonsterByType(String type){
        List<MonsterDex> monsterdex = db.monsterDexDao().getAllMonstersByType(type);
        int rand = (int)(Math.random()*(monsterdex.size()-0+1)+0);
        int id = rand-(rand%3);
        if(id >= monsterdex.size()){monsterdex.get(0);}

        MonsterDex mosnter = monsterdex.get(id);


        return mosnter;
    }
    public void setMyMonster(int i){
        List<MonsterDex> monsters = db.monsterDexDao().getAllMonsters();    // Mudar para fazer query com o id
        MonsterDex m = monsters.get(i);
        Monster m2 = new Monster();
        m2.name = m.name;
        m2.type = m.type;
        m2.maxhealth = m.health;
        m2.health = m.health;
        m2.attack = m.attack;
        m2.defense = m.defense;
        m2.level = 5;
        m2.xp = 0;
        m2.bArray = m.bArray;
        m2.eve = m.evolution;
        m2.evolution = m.evolvesFrom;
        createNewMonsterTeam(m2);
    }

    public void setCurrentMonster(int id){
        currentMonster = id;
    }

    public int getCurrentMonster(){
        return currentMonster;
    }

    public Monster getMonsterById(int id){
        return db.monsterDao().getMonster(id);
    }

    public void setHeal(int health, int id){
        db.monsterDao().setHealth(health, id);
    }

    public void setCurrentMonster2(int id){
        currentMonster2 = id;
    }

    public int getCurrentMonster2(){
        return currentMonster2;
    }

    public void changeMonsterChange(){
        if(monster_change == 0){
            monster_change = 1;
        }else{
            monster_change = 0;
        }
    }

    public int getMonsterChange(){
        return monster_change;
    }

    public void changeCreated(){
        if(create == 0){
            create = 1;
        }else{
            create = 0;
        }
    }

    public int getCreated(){
        return create;
    }

    public int setCreatedZero(){
        return create = 0;
    }

    public int setCreatedOne(){
        return create = 1;
    }

    public void setCharacter(Battle.Character enemy){
        this.enemy = enemy;
    }

    public Battle.Character getCharacter(){
        return enemy;
    }
}
