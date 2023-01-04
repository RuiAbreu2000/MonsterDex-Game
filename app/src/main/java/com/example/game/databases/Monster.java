package com.example.game.databases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// https://stackoverflow.com/questions/6341776/how-to-save-bitmap-in-database

@Entity
public class Monster {
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

    @ColumnInfo(name = "level")         // Monster level
    public Integer level;

    @ColumnInfo(name = "xp")         // Monster xp
    public Integer xp;

    @ColumnInfo(name = "eve")         // Monster level evolution
    public Integer eve;

    @ColumnInfo(name = "bArray")
    public byte[] bArray;

    // Get and Set Functions
    public int getId(){return this.id;}

    // Other maybe
    @ColumnInfo(name = "originalCreator")          // Creator of Monster if it has one
    public String originalCreator;

}
