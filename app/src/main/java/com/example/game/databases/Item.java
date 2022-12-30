package com.example.game.databases;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)    //automatically generates pk
    public int id;			            // primary key

    @ColumnInfo(name = "name")          // Name of the item
    public String name;

    @ColumnInfo(name = "type")          // Type of the item
    public String type;

    @ColumnInfo(name = "value")         // Item value
    public String value;

    @ColumnInfo(name = "price")         // Item shop price
    public Integer price;

    @ColumnInfo(name = "amount")         // Item shop price
    public Integer amount;

    // Get and Set Functions
    public int getId(){return this.id;}

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

}
