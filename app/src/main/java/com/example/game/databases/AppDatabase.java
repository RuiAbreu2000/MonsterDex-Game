package com.example.game.databases;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(entities = {Item.class, Monster.class, MonsterDex.class}, version= 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();	        // ITEM INVENTORY DATABASE
    public abstract MonsterDao monsterDao();	// MONSTER INVENTORY DATABASE
    public abstract MonsterDexDao monsterDexDao();	// MONSTER DEX DATABASE

    private static final String DB_NAME = "Database";
    private static volatile AppDatabase instance;                               // volatile ->  means that multiple threads can use a method and instance of the classes at the same time without any problem

    public static synchronized AppDatabase getInstance(Context context) {	                                        //synchronized for multithreading. Used to get Database Instance
        if (instance == null) {						                                                                // accessed by NotaDatabase.getInstance(context)
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).allowMainThreadQueries().build();	// Having multiple Instances is very expensive
        }                                                                                                            // .allowMainThreadQueries().build() can be used
        return instance;
    }

}
