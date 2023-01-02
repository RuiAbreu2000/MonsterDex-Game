package com.example.game.databases;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

// MONSTER DEX DAO
@Dao
public interface MonsterDexDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // Ignores if there is an existing Item
    void addMonster(MonsterDex monster);

    // Get Queries
    @Query("SELECT * FROM MonsterDex WHERE name=:name")
    Monster getMonsterByName(String name);

    @Query("SELECT * FROM MonsterDex WHERE id =:id")
    Monster getMonster(int id);

    @Query("SELECT * FROM MonsterDex ORDER BY id ASC")
    List<MonsterDex> getAllMonsters();

    @Query("SELECT * FROM MonsterDex WHERE type=:type")
    List<MonsterDex> getAllMonstersByType(String type);

    // Set Queries
    @Query("UPDATE MonsterDex SET health=:health, attack=:attack, defense=:defense WHERE id =:id")
    void updateMonster(int id, Integer health, Integer attack, Integer defense);

    @Query("UPDATE MonsterDex SET health=:health WHERE id =:id")
    void setHealth(Integer health, int id);

    @Query("UPDATE MonsterDex SET attack=:attack WHERE id =:id")
    void setAttack(Integer attack, int id);

    @Query("UPDATE MonsterDex SET defense=:defense WHERE id =:id")
    void setDefense(Integer defense, int id);

    @Delete
    void deleteMonster(MonsterDex monster);
}
