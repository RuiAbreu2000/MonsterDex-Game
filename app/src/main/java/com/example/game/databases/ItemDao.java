package com.example.game.databases;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

// ITEM INVENTORY DATABASE
@Dao
public interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // Ignores if there is an existing Item
    void addItem(Item item);

    // Get Queries
    @Query("SELECT * FROM Item WHERE name=:name")
    Item getItemByName(String name);

    @Query("SELECT * FROM Item WHERE id =:id")
    Item getItem(int id);

    // Set Queries
    @Query("UPDATE Item SET price=:price WHERE id =:id")
    void setPrice(int id, Integer price);

    @Query("UPDATE Item SET amount=:amount WHERE id =:id")
    void setAmount(int id, Integer amount);

    @Delete
    void deleteItem(Item item);
}
