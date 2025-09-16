package me.seetch.data.database;

import me.seetch.data.utils.Create;

public class MySQLDatabase extends Database {

    // Default MySQL port is 3306
    public MySQLDatabase(String host, String database, String user, String password) {
        this(host, 3306, database, user, password);
    }

    public MySQLDatabase(String host, int port, String database, String user, String password) {
        super(Create.createMariaDB(host, port, database, user, password, null));
    }

    public MySQLDatabase(String host, int port, String database, String user, String password, String properties) {
        super(Create.createMariaDB(host, port, database, user, password, properties));
    }
}
