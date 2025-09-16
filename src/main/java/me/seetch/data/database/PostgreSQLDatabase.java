package me.seetch.data.database;

import me.seetch.data.utils.Create;

public class PostgreSQLDatabase extends Database {

    // Default PostgreSQL port is 5432
    public PostgreSQLDatabase(String host, String database, String user, String password) {
        this(host, 5432, database, user, password);
    }

    public PostgreSQLDatabase(String host, int port, String database, String user, String password) {
        super(Create.createPostgreSQL(host, port, database, user, password, null));
    }

    public PostgreSQLDatabase(String host, int port, String database, String user, String password, String properties) {
        super(Create.createPostgreSQL(host, port, database, user, password, properties));
    }
}
