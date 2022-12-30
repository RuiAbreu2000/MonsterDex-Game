package com.example.game.databases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    // Get and Set Functions
    public int getId(){return this.id;}

    // Other maybe
    @ColumnInfo(name = "originalCreator")          // Creator of Monster if it has one
    public String originalCreator;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)    // Monster Custom Image
    public byte[] image;
}
