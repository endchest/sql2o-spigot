package me.hteppl.data.database;

import me.hteppl.data.utils.Create;

public class SQLiteDatabase extends Database {

    public SQLiteDatabase(String folder, String database) {
        super(Create.createSQLite(folder, database));
    }
}
