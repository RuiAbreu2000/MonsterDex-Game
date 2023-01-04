package com.example.game.databases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MonsterDex {
    @PrimaryKey(autoGenerate = true)    //automatically generates pk
    public int id;			            // primary key

    @ColumnInfo(name = "name")          // Monster Name
    public String name;

    @ColumnInfo(name = "type")          // Monster type
    public String type;

    @ColumnInfo(name = "health")        // Monster health
    public Integer health;

    @ColumnInfo(name = "attack")        // Monster attack
    public Integer attack;

    @ColumnInfo(name = "defense")       // Monster defense
    public Integer defense;

    @ColumnInfo(name = "isBoss")         // Monster level
    public Boolean isBoss;

    @ColumnInfo(name = "bArray")
    public byte[] bArray;

    @ColumnInfo(name = "rarity")
    public String rarity;

    @ColumnInfo(name = "evolution")
    public Integer evolution;


    @ColumnInfo(name = "evolvesFrom")
    public String evolvesFrom;

    // Get Functions
    public int getId(){return this.id;}

    // Set Functions


}
