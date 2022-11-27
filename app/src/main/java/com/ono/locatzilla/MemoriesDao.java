package com.ono.locatzilla;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MemoriesDao {
    @Query("select * from DtoMemory")
    List<DtoMemory> getAllImages();

    @Insert
    void addNewMemory(DtoMemory memory);

    @Delete
    void deleteMemory(DtoMemory dtoMemory);

    @Query("delete from DtoMemory")
    void deleteAllMemories();
}
