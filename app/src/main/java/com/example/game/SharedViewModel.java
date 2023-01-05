package com.example.game;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.game.databases.AppDatabase;
import com.example.game.databases.Monster;
import com.example.game.databases.MonsterDex;
import com.example.game.graphics.MapLayouts;
import com.example.game.graphics.SpriteSheet;

import java.util.List;
import java.util.Stack;

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
    Stack<Fragment> fragmentStack = new Stack<Fragment>();

    // Sprite Sheets
    public SpriteSheet spritesheet;
    public SpriteSheet airMonsterSpritesheet;
    public SpriteSheet bugMonsterSpritesheet;
    public SpriteSheet fireMonsterSpritesheet;
    public SpriteSheet groundMonsterSpritesheet;
    public SpriteSheet waterMonsterSpritesheet;
    public SpriteSheet symbolsSpriteSheet;
    public SpriteSheet fixedSpriteSheet;


    // Current Zone Vars
    public int currentZoneLevel = 1;                // Level of Current Zone
    public String currentZone = null;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);

        // Spritesheets
        spritesheet = new SpriteSheet(application, "tiles");
        airMonsterSpritesheet = new SpriteSheet(application, "airMonsters");
        bugMonsterSpritesheet = new SpriteSheet(application, "bugMonsters");
        fireMonsterSpritesheet = new SpriteSheet(application, "fireMonsters");
        groundMonsterSpritesheet = new SpriteSheet(application, "groundMonsters");
        waterMonsterSpritesheet = new SpriteSheet(application, "waterMonsters");
        symbolsSpriteSheet = new SpriteSheet(application, "symbols");
        fixedSpriteSheet = new SpriteSheet(application, "fixed");
        maplayouts = new MapLayouts(spritesheet, symbolsSpriteSheet, fixedSpriteSheet);
        maplayouts.setSymbolsSpriteSheet();
    }

    public AppDatabase getDatabase(){
        return db;
    }

    public Bitmap getBitmap() {     // Returns Map Bitmap
        return maplayouts.getBitmap();
    }


    public int[] getTileMatrix() {  // Returns Array with Tile Numbers
        return maplayouts.getTileIndexMap().clone();
    }

    public int[][] getMonsterArray(){ return maplayouts.getMonsterArray().clone();}
    // Get Map Functions
    public void getMap(String map) { // Builds map on MapLayouts
        switch(map) {
            case "grass":
                currentZone = "grass";                               // Updates current Zone
                maplayouts.grassMap(currentZoneLevel);                               // Builds Map
            case "home":
                currentZone = "home";                               // Updates current Zone
                maplayouts.homeMap();                               // Builds Map

        }
    }

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

    public void incrementLevel() {
        this.currentZoneLevel += 1;
    }

    public void addFragment(Fragment fragment){
        fragmentStack.push(fragment);
        fragmentStack.lastElement().onPause();
    }
    public Fragment getLastFragment(){
        return fragmentStack.lastElement();
    }

    public MonsterDex getRandomMonsterByType(String type){
        List<MonsterDex> monsterdex = db.monsterDexDao().getAllMonstersByType(type);
        int rand = (int)(Math.random()*(monsterdex.size()-0+1)+0);
        MonsterDex mosnter = monsterdex.get(rand-(rand%3));

        return mosnter;
    }
    public void setMyMonster(int i){
        List<MonsterDex> monsters = db.monsterDexDao().getAllMonsters();
        MonsterDex m = monsters.get(i);
        Monster m2 = new Monster();
        m2.name = m.name;
        m2.type = m.type;
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
}