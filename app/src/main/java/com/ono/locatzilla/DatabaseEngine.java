package com.ono.locatzilla;

import android.content.Context;

import androidx.room.Room;

public class DatabaseEngine {

    private static DatabaseEngine databaseEngine = null;
    private Context context;
    DatabaseClass db;

    private DatabaseEngine() {

    }

    private DatabaseEngine(Context context) {
        this.context = context;
    }

    public static DatabaseEngine getInstance() {
        if (databaseEngine == null) {
            databaseEngine = new DatabaseEngine();
        }

        return databaseEngine;
    }

    public static DatabaseEngine getInstance(Context context) {
        if (databaseEngine == null) {
            databaseEngine = new DatabaseEngine(context);
        }

        return databaseEngine;
    }

    public void initializeDatabase() {
        db = Room.databaseBuilder(context, DatabaseClass.class, "locatzilla_database").fallbackToDestructiveMigration().build();
    }

    public DatabaseClass getDBInstance() {
        return db;
    }


}
