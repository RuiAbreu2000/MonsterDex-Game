package com.example.game.databases;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

// MONSTER INVENTORY DATABASE
@Dao
public interface MonsterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // Ignores if there is an existing Item
    void addMonster(Monster monster);

    // Get Queries
    @Query("SELECT * FROM Monster WHERE name=:name")
    Monster getMonsterByName(String name);

    @Query("SELECT * FROM Monster WHERE id =:id")
    Monster getMonster(int id);

    @Query("SELECT * FROM Monster ORDER BY id ASC")
    List<Monster> getAllMonsters();

    @Query("SELECT * FROM Monster WHERE type=:type")
    List<Monster> getAllMonstersByType(String type);

    // Set Queries
    @Query("UPDATE Monster SET health=:health, attack=:attack, defense=:defense WHERE id =:id")
    void updateMonster(int id, Integer health, Integer attack, Integer defense);

    @Query("UPDATE Monster SET health=:health WHERE id =:id")
    void setHealth(Integer health, int id);

    @Query("UPDATE Monster SET attack=:attack WHERE id =:id")
    void setAttack(Integer attack, int id);

    @Query("UPDATE Monster SET defense=:defense WHERE id =:id")
    void setDefense(Integer defense, int id);

    @Query("UPDATE Monster SET level=:level WHERE id =:id")
    void setLevel(Integer level, int id);

    @Delete
    void deleteMonster(Monster monster);
}
