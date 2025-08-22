package me.hteppl.data.database;

import lombok.Getter;
import me.hteppl.data.utils.Create;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import javax.sql.DataSource;

@Getter
public abstract class Database {

    private final Sql2o sql2o;

    public Database(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public Database(DataSource ds) {
        this.sql2o = Create.createFromDataSource(ds);
    }

    public Connection openConnection() {
        return this.sql2o.open();
    }

    public Connection beginTransaction() {
        return this.sql2o.beginTransaction();
    }

    public Connection beginTransaction(int isolationLevel) {
        return this.sql2o.beginTransaction(isolationLevel);
    }

    public void execute(String rawQuery) {
        if (rawQuery == null || rawQuery.isEmpty()) return;

        try (Connection connection = this.openConnection()) {
            connection.createQuery(rawQuery).executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute query: " + rawQuery, e);
        }
    }
}