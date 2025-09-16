package me.seetch.data.database;

import me.seetch.data.utils.Create;

public class SQLiteDatabase extends Database {

    public SQLiteDatabase(String folder, String database) {
        super(Create.createSQLite(folder, database));
    }
}
