package com.w9530581.letsplan;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DtoMemory.class}, version = 2)
public abstract class DatabaseClass extends RoomDatabase {
    public abstract MemoriesDao memoriesDao();
}
